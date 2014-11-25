package iscas.tca.ake.napake;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.message.nap.EnumNAPMsgType;
import iscas.tca.ake.message.nap.NAPMessage;
import iscas.tca.ake.napake.calculate.FactoryCalculate;
import iscas.tca.ake.napake.calculate.IfcNapCalculate;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPServer;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.connectStrings.ConnectStrsTask;
import iscas.tca.ake.util.exceptions.CannotGenerateNewMsgException;
import iscas.tca.ake.util.exceptions.IllegalMsgException;
import iscas.tca.ake.util.exceptions.InitializationException;
import iscas.tca.ake.util.rand.Rand;
import iscas.tca.ake.veap.IfcGetUsers;
import iscas.tca.ake.veap.User;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Stack;
//import static java.lang.System;
/**
 * 描述：<>
 * 类名：<NAPServer>
 * @author zn
 * @CreateTime 2014-8-16下午2:26:21
 */
public class NAPServer implements IfcAkeProtocol {

	/*static*/ BigInteger m_q;//是否需要static
	/*static*/ BigInteger m_g;
	
	BigInteger m_Y;
	BigInteger m_Xstar;
	BigInteger m_B;
	BigInteger m_randy;
	BigInteger m_rS;
	BigInteger[] m_As;
	
	BigInteger m_Zp;
	BigInteger m_Kp;
	BigInteger m_Xp;
	
	//BigInteger m_K;//可以不记录
	
	String m_SID;

//	String[] m_IDs;
//	String[] m_pws;
	User[] m_users;
	
	byte[] m_Auths;
	byte[] m_sk;
	byte[] m_Authc;
	byte[] m_serverAuthc;
	 String m_ClientID;//和此服务端程序相关联的客户端程序。
	Stack<EnumNAPMsgType> m_stackProtocol;//协议栈，规定协议的执行顺序
	
	IfcNapCalculate m_NapCalculate;
	IfcGetUsers m_getUsers;
	boolean m_isInited;
	//调试
	String m_Trans;
	private boolean m_isVerified = false;
	
	//多线程连接字符串，在发送XStarB时进行处理
	ConnectStrsTask m_cstAs;//多线程连接As
//	ConnectStrsTask m_cstIDs;//多线程连接IDs，启动和获取不在一个位置
	/**
	 * 
	 */
	
	//add groupID
	String m_groupID;
	IfcBulletinNAPServer m_bulletinNapServer;//nap bulletin
	String m_connnectedIDs;//connected IDs 
	
	public NAPServer() {
		// TODO Auto-generated constructor stub
		this.m_isInited = false;
		this.m_sk = null;
	}
//	public boolean isM_isInited() {
//		return m_isInited;
//	}
	//noooooooooooooooo由客户端启动，所以不用实现
	@Override	
	public IfcMessage startProtocol() {
		// 
		return null;
	}
	//noooooooooooooooo没有服务器端的验证机制
	@Override	
	public boolean isVerified() {
		// TODO Auto-generated method stub
		return this.m_isVerified;
	}
	@Override
	public int getIDNum(){
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean init(IfcInitData init) throws Exception{
		// TODO Auto-generated method stub
		this.m_NapCalculate = FactoryCalculate.getCalculate("NAPCalculate");
		
		if(init instanceof InitServerData){
			
			InitServerData init2 = (InitServerData)init;
			this.m_getUsers = init2.getM_getUsers();
			this.m_q = (init2).getM_q();
			this.m_g = ( init2).getM_g();
			this.m_SID = (init2).getM_S();
			this.m_bulletinNapServer = (init2).m_bulletinNAPServer;
			//检查数据合法性，SID不为空,q为素数, g>0,m_NapServerUser 不为空
			if(this.m_SID!=null && 
					this.m_q.isProbablePrime(NAPConstants.ProbablePrimeCertainty) &&
					this.m_g.compareTo(BigInteger.ZERO)>0 &&
					null != this.m_getUsers)
			{
				EnumNAPMsgType[] order = {EnumNAPMsgType.GroupID,EnumNAPMsgType.XstarB,EnumNAPMsgType.Authc};
				initStack(order);
				//初始化成功的唯一条件
				this.m_isInited = true;
				return true;
			}
			else {
				System.err.println("init error");
				throw new InitializationException();
			}
		}
		else{
			throw new InitializationException();
		}
	}
	
	/**
	 * TODO:<使用order对协议栈进行初始化>
	 * @param order ：协议的接收顺序 [第一条，第二条，第三条]
	 */
	public void initStack(EnumNAPMsgType[] order)
	{
		this.m_stackProtocol = new Stack<EnumNAPMsgType>();
		
		for(int i=order.length-1; i>=0; i--)
		{
			this.m_stackProtocol.push(order[i]);
		}
	}
	
	private void verifyClient(){
		this.m_isVerified = false;
		if(Arrays.equals(m_Authc, m_serverAuthc)){
			this.m_isVerified = true;
		}
		
	}
	@Override
	public IfcMessage processMessage(IfcMessage m) throws InitializationException, IllegalMsgException, CannotGenerateNewMsgException, Exception{
		// TODO Auto-generated method stub
		if(!this.m_isInited)
		{
			throw new InitializationException();
		}
		else{
			if(!drawInfo(m)){
				throw new IllegalMsgException();				
			}
			else{
				NAPMessage mNap = (NAPMessage)m;			
				IfcMessage newMsg= null;	
				switch (mNap.getM_msgType())
				{
				case GroupID:
					newMsg =  createSAsMsg();
					
//					this.m_cstIDs = this.m_NapCalculate.exeStrsCntTask(m_IDs);
					this.m_cstAs = this.m_NapCalculate.exeStrsCntTask(this.m_As);
					this.m_stackProtocol.pop();
					break;
				case XstarB:
					newMsg = createYAuthsMsg();
					this.m_stackProtocol.pop();
					break;
				case Authc:
					verifyClient();
					this.m_stackProtocol.pop();
					//end the protocol
					return null;
				default:
					//消息不合法
					throw new IllegalMsgException();
				}
				
				if(null==newMsg){
					//抛出不能生成新消息异常
					throw new CannotGenerateNewMsgException();
				}
				else{
					return newMsg;
				}
			}
		}
	}
	@Override
	public boolean isProtocolOver() {
		return m_stackProtocol.isEmpty();
	}
	/**
	 * TODO:<如果m合法 ,提取m中的信息>
	 * @param m:<消息>
	 * @return true 合法，false:不合法
	 */
	private boolean drawInfo(IfcMessage m)
	{
		//判断消息是否合法，合序
	
		if(m.isMsgLegle() &&
				isInOrder(m)){
				NAPMessage mNap = (NAPMessage)m;
				
				//记录到NAPServer中
				switch(mNap.getM_msgType())
				{
				case GroupID:
					this.m_groupID = ((NAPMessage.GroupIDData)mNap.getM_data()).getM_groupID();
					this.m_users = this.m_getUsers.getUsers(m_groupID);
					
					break;
				case XstarB:				
					NAPMessage.XstarBData data = (NAPMessage.XstarBData)mNap.getM_data();
					this.m_Xstar = data.getM_Xstar();
					this.m_B = data.getM_B();
					break;
				case Authc:
					this.m_Authc = ((NAPMessage.AuthcData)mNap.getM_data()).getAuthc();
					
					break;
					
				default:
					return false;
				}
				//提取了消息后，在消息栈中退出
				//this.m_stackProtocol.pop();
				return true;	
		}
		return false;
	}


	/**
	 * TODO:<生成SAs消息>
	 * @return 如果无法创建，返回null
	 */
	
	private IfcMessage createSAsMsg(){
		
		Rand rd = new Rand();
		this.m_rS = rd.randOfMax(this.m_q);
		BigInteger hPW;
		//计算As
		this.m_As = new BigInteger[this.m_users.length];

		for(int i=0; i<this.m_users.length; i++)
		{
			hPW = this.m_NapCalculate.getPW(this.m_users[i].user_id, this.m_users[i].user_pw, this.m_q);
			this.m_As[i] = Assist.modPow(hPW, this.m_rS, this.m_q);
		}
		NAPMessage.SAsData data = NAPMessage.SAsData.getSAsData(this.m_SID, this.m_As);
		return NAPMessage.getNAPMessage(data, EnumNAPMsgType.SAs);
	}
	
	/**
	 * TODO:<生成YAuths消息>
	 * @return 
	 */
	private IfcMessage createYAuthsMsg() throws Exception
	{
		//计算
		Rand rd = new Rand();
	//	BigInteger Zp, Xp, Zinverse, Kp;
		this.m_Zp = Assist.modPow(this.m_B, this.m_rS, this.m_q);
		BigInteger Zinverse = Assist.modInverse(this.m_Zp, this.m_q);
		this.m_Xp = Assist.modMutiply(this.m_Xstar, Zinverse, this.m_q);
		this.m_randy = rd.randOfMax(this.m_q);
		this.m_Y = Assist.modPow(this.m_g, this.m_randy, this.m_q);
 	    this.m_Kp = Assist.modPow(this.m_Xp, this.m_randy, this.m_q);
		if(null==m_cstAs)
		{
			System.out.println("连接的字符串cstAs 和 cstIDs不正确");
			return null;
		}
		String trans = this.m_NapCalculate.getTrans(this.m_bulletinNapServer.getConnectedPseudonyms(m_groupID),
				this.m_SID, 
				this.m_cstAs.get().toString(),
				this.m_Xstar, 
				this.m_B, 
				this.m_Y);
		//= this.m_NapCalculate.getTrans(this.m_IDs, this.m_SID, this.m_As, this.m_Xstar,this.m_B, this.m_Y);
		this.m_Trans = trans;
		this.m_Auths = this.m_NapCalculate.getAuths(trans, this.m_Zp, this.m_Kp);
		this.m_sk = this.m_NapCalculate.getsk(trans, this.m_Zp, this.m_Kp);
		this.m_serverAuthc = this.m_NapCalculate.getAuthc(trans, m_Zp, m_Kp);
		//构造消息
		NAPMessage.YAuthsData data = NAPMessage.YAuthsData.getYAuthsData(this.m_Y, this.m_Auths);
		return NAPMessage.getNAPMessage(data, EnumNAPMsgType.YAuths);
		
	}
	
	/**
	 * TODO:<判断消息m是否符合顺序>
	 * @param m
	 * @return boolean
	 */
	private boolean isInOrder(IfcMessage m)
	{
		NAPMessage mNap = (NAPMessage)m;
		if(this.m_stackProtocol.peek().equals(mNap.getM_msgType()))
		{
			//this.m_stack.pop();
			return true;
		}
		else {
			return false;
		
		}
	}
	/**
	 * TODO:<获取sk>
	 * @return sk
	 */
	public byte[] getsk()
	{
		return this.m_sk;
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

	public BigInteger getM_randy() {
		return m_randy;
	}

	public BigInteger getM_rS() {
		return m_rS;
	}

	public BigInteger[] getM_As() {
		return m_As;
	}

	public String getM_SID() {
		return m_SID;
	}

	public byte[] getM_Auths() {
		return m_Auths;
	}

	public byte[] getM_sk() {
		return m_sk;
	}

	public Stack getM_stack() {
		return m_stackProtocol;
	}

	public BigInteger getM_Zp() {
		return m_Zp;
	}

	public String getM_Trans() {
		return m_Trans;
	}

	public BigInteger getM_Kp() {
		return m_Kp;
	}

	public BigInteger getM_Xp() {
		return m_Xp;
	}

	public String getM_ClientID() {
		return m_ClientID;
	}

	/**
	 * TODO:<>
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
