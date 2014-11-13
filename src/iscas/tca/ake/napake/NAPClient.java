package iscas.tca.ake.napake;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.message.nap.EnumNAPMsgType;
import iscas.tca.ake.message.nap.NAPMessage;
import iscas.tca.ake.message.nap.NAPMessage.IDsData;
import iscas.tca.ake.napake.calculate.FactoryCalculate;
import iscas.tca.ake.napake.calculate.IfcNapCalculate;
import iscas.tca.ake.test.swing.module.bulletin.IfcBulletinNAP;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.connectStrings.ConnectStrsTask;
import iscas.tca.ake.util.exceptions.CannotGenerateNewMsgException;
import iscas.tca.ake.util.exceptions.IllegalMsgException;
import iscas.tca.ake.util.exceptions.InitializationException;
import iscas.tca.ake.util.rand.Rand;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Stack;

/**
 * 描述：<Nap协议的client>
 * 类名：<NapClient>
 * @author zn
 * @CreateTime 2014-8-16上午11:05:16
 */

public class NAPClient implements IfcAkeProtocol{
	BigInteger m_q;
	BigInteger m_g;
	
	BigInteger m_Y;
	BigInteger m_Xstar;
	BigInteger m_B;
	BigInteger m_X;
	BigInteger m_Z;
	BigInteger m_randx;
	BigInteger m_rc;
	BigInteger m_K;
	BigInteger[] m_As;
	BigInteger m_A;
	
	String m_pw;
	String m_SID;
	String[] m_IDs;
	String m_ID;
	byte[] m_Auths; 
	byte[] m_myAuths;
	byte[] m_sk;
	
	IfcNapCalculate m_NapCalculate;
	public boolean m_isInited;
	public boolean m_isVerified;
	String m_Trans; 
	//多线程连接字符串，在发送XStarB时进行处理
	ConnectStrsTask m_cstAs;//多线程连接As
	ConnectStrsTask m_cstIDs;//多线程连接IDs，启动和获取不在一个位置
	
	Stack<EnumNAPMsgType> m_stack;
	//bulletin 
	private IfcBulletinNAP m_bulletinNap;
	public NAPClient()
	{
		this.m_isInited = false;
		this.m_sk = null;
	}
	
	@Override
	public boolean init(IfcInitData init) throws InitializationException{
		// TODO Auto-generated method stub
		//获取底层计算object
		this.m_NapCalculate = FactoryCalculate.getCalculate("NAPCalculate");
		
		if(init instanceof InitClientData){
			InitClientData initClt = (InitClientData)init;
			//初始化数据
			this.m_pw = initClt.getM_pw();
			this.m_g = initClt.getM_g();
			this.m_q = initClt.getM_q();
			this.m_ID = initClt.getM_ID();
			this.m_IDs = initClt.getM_IDs();
			//record the bulletinNAP
			this.m_bulletinNap = initClt.m_bn;
			//参数合法性检查
			if(this.m_q.isProbablePrime(NAPConstants.ProbablePrimeCertainty) &&
					this.m_g.compareTo(BigInteger.ZERO)>0 &&
					null!=this.m_pw && 
					null != this.m_ID)// && 
					//isInArrays(this.m_ID, this.m_IDs))
			{
				//初始化协议栈
				EnumNAPMsgType[] order = { EnumNAPMsgType.SAs,EnumNAPMsgType.YAuths};
				initProtocolStack(order);
				//初始化成功
				this.m_isInited = true;
				return true;
			}
			else{
				System.out.println("init exception");
				throw new InitializationException();	
			}
		}
		else{
			return false;
		}
	}

	@Override
	public boolean isVerified() {
		return m_isVerified;
	}
	@Override
	public boolean isProtocolOver() {
		// TODO Auto-generated method stub
		return this.m_stack.isEmpty();
	}
	
	//初始化协议栈:协议栈设置接收消息的顺序
	public void initProtocolStack(EnumNAPMsgType[] order)
	{
		m_stack = new Stack<EnumNAPMsgType>();
		
		for(int i=order.length-1; i>=0; i--)
		{
			m_stack.push(order[i]);
		}
	}
	private boolean isOfflineDone = false;
	
	private void offlineSpeedUp(){
		isOfflineDone = false;
		Rand rd = new Rand();
		this.m_randx =  rd.randOfMax(this.m_q);
		this.m_rc = rd.randOfMax(this.m_q);
		this.m_X = Assist.modPow(this.m_g,this.m_randx, this.m_q);
		BigInteger hPW = this.m_NapCalculate.getPW(this.m_ID, this.m_pw, this.m_q);
		this.m_B = Assist.modPow(hPW, this.m_rc, this.m_q);
		synchronized(this){
			isOfflineDone = true;
			this.notifyAll();
		}
		System.out.println("speed up randx hashcode :"+this.m_randx.hashCode());
	}
	@Override
	public IfcMessage startProtocol() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("offline speed up start...");
				offlineSpeedUp();
				System.out.println("finished offline speed up!");
			}
		}).start();
		return createIDsMsg();
	}
	@Override
	public IfcMessage processMessage(IfcMessage m) throws IllegalMsgException, CannotGenerateNewMsgException, InitializationException, InterruptedException{
		// TODO Auto-generated method stub	
		//确保初始化成功
		if(!this.m_isInited){
			throw new InitializationException();
		}
		else{
			if(!drawInfo(m)){			
				throw new IllegalMsgException();
			}
			else{//消息合法				
				NAPMessage mNap = (NAPMessage)m;
				IfcMessage newMsg = null;
				//处理信息
				switch (mNap.getM_msgType()){
				case SAs:
					newMsg = createXstarBMsg();
					//connect the string
					this.m_cstIDs = this.m_NapCalculate.exeStrsCntTask(this.m_IDs);
					this.m_cstAs = this.m_NapCalculate.exeStrsCntTask(this.m_As);
					m_stack.pop();
					break;
				case YAuths:
					//verify Auths and generate authc
					newMsg = verifyAuths();
					m_stack.pop();
					break;
				default:
					throw new IllegalMsgException();
				}
				if(null==newMsg){
					//error when create the message
					throw new CannotGenerateNewMsgException();
				}
				else{
					return newMsg;
				}
			}
		}
	}
	
	private boolean drawInfo(IfcMessage m)
	{
		if(m.isMsgLegle() &&
				isInOrder(m)){
			NAPMessage mNap = (NAPMessage)m;
			
			//记录到NAPServer中
			switch(mNap.getM_msgType())
			{
			case SAs:
				NAPMessage.SAsData data = (NAPMessage.SAsData)mNap.getM_data();
				this.m_As = data.getM_As();
				this.m_SID = data.getM_SID();
				this.m_IDs = data.getM_IDs();
				this.m_A = this.m_NapCalculate.getAself(m_ID, m_As, m_bulletinNap);
				//this.m_A = this.m_NapCalculate.getAself(m_IDs, m_As, m_ID);
				//如果能够找到m_A，true，否则，返回false
				if(this.m_A!=null)
					break;
				else{
					return false;
				}
			case YAuths:
				NAPMessage.YAuthsData data2 = (NAPMessage.YAuthsData)mNap.getM_data();
				this.m_Auths = data2.getM_Auths();
				this.m_Y = data2.getM_Y();
				break;
			default:
				return false;
			}
			//pop the message in the stack
			//this.m_stack.pop();
			return true;	
		}
		return false;
	}
	private IfcMessage createIDsMsg()
	{
		//NAPMessage resultMsg = new NAPMessage();
	
		IDsData idsdata = IDsData.getIDsData(this.m_IDs);
		return NAPMessage.getNAPMessage(idsdata, EnumNAPMsgType.IDs);
		
	}
	private IfcMessage createXstarBMsg()throws InterruptedException
	{
		//Asynchronous speed up
		synchronized(this){
			if(!this.isOfflineDone){
				System.out.println("waiting for the speed up");
				this.wait();
			}
		}
		System.out.println("MainThread: createXstartBMsg() randx hashcode :"+this.m_randx.hashCode());
		
		this.m_Z = Assist.modPow(this.m_A, this.m_rc, this.m_q);
		this.m_Xstar = Assist.modMutiply(this.m_Z, this.m_X, this.m_q);
		//generate the data
		NAPMessage.XstarBData xbData = NAPMessage.XstarBData.getXstarBData(this.m_Xstar, this.m_B);
		return NAPMessage.getNAPMessage(xbData, EnumNAPMsgType.XstarB);
	}
	
	
	/**
	 * TODO:<Verify the Auths>
	 * @return true or false
	 */
	private IfcMessage verifyAuths()
	{

		this.m_K = Assist.modPow(this.m_Y, this.m_randx, this.m_q);
		//String trans = this.m_NapCalculate.getTrans(this.m_IDs, this.m_SID, this.m_As, this.m_Xstar, this.m_B, this.m_Y);
		if(null==this.m_cstAs || null==this.m_cstIDs){
			System.out.println("connect m_cstAs  and m_cstIDs failed");
			return null;
		}
		String trans = this.m_NapCalculate.getTrans(m_cstIDs.get().toString(), 
				this.m_SID, 
				m_cstAs.get().toString(),
				this.m_Xstar, this.m_B, this.m_Y);
		this.m_Trans = trans;
		this.m_myAuths = this.m_NapCalculate.getAuths(trans, this.m_Z, this.m_K);
		
		if(Arrays.equals(this.m_myAuths, this.m_Auths))
		{
			this.m_sk = this.m_NapCalculate.getsk(trans, this.m_Z, this.m_K);
			this.m_isVerified = true;
			byte[] authc = this.m_NapCalculate.getAuthc(trans, m_Z, m_K);
			NAPMessage.AuthcData authcData = new NAPMessage.AuthcData(authc);
			return new NAPMessage(authcData, EnumNAPMsgType.Authc);
			
		}
		this.m_isVerified = false;
		return null;
	}

	/**
	 * TODO:<处理Error消息>
	 * @param m
	 * @return 
	 */
//	private IfcMessage processError(IfcMessage m)
//	{
//		return null;
//	}
	
	public byte[] getsk()
	{
		return this.m_sk;
	}

	private boolean isInOrder(IfcMessage m)
	{
		NAPMessage mNap = (NAPMessage)m;
		if(this.m_stack.peek().equals(mNap.getM_msgType()))
		{
			//this.m_stack.pop();
			return true;
		}
		else {
			return false;
		
		}
	}
	private boolean isInArrays(String id, String[] ids)
	{
		for(int i=0 ;i<ids.length; i++)
		{
			if(id.equals(ids[i]))
			{
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public BigInteger getM_q() {
		return m_q;
	}
	public BigInteger getM_g() {
		return m_g;
	}
	public BigInteger getM_Y() {
		return m_Y;
	}
	public BigInteger getM_Xstar() {
		return m_Xstar;
	}
	public BigInteger getM_B() {
		return m_B;
	}
	public BigInteger getM_randx() {
		return m_randx;
	}
	public BigInteger[] getM_As() {
		return m_As;
	}
	public BigInteger getM_A() {
		return m_A;
	}
	public String getM_pw() {
		return m_pw;
	}
	public String getM_SID() {
		return m_SID;
	}
	public String[] getM_IDs() {
		return m_IDs;
	}
	public String getM_ID() {
		return m_ID;
	}
	public byte[] getM_Auths() {
		return m_Auths;
	}
	public byte[] getM_sk() {
		return m_sk;
	}
	public Stack<EnumNAPMsgType> getM_stack() {
		return m_stack;
	}
	public BigInteger getM_X() {
		return m_X;
	}
	public BigInteger getM_Z() {
		return m_Z;
	}
	public BigInteger getM_rc() {
		return m_rc;
	}
	public BigInteger getM_K() {
		return m_K;
	}
	public byte[] getM_myAuths() {
		return m_myAuths;
	}
	public String getM_Trans() {
		return m_Trans;
	}


}
