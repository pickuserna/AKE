package iscas.tca.ake.napake;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.message.nap.EnumNAPMsgType;
import iscas.tca.ake.message.nap.NAPMessage;
import iscas.tca.ake.message.nap.NAPMessage.GroupIDData;
import iscas.tca.ake.napake.calculate.FactoryCalculate;
import iscas.tca.ake.napake.calculate.IfcNapAKECalculate;
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
 * description：<Napclient Protocol>
 * class-name：<NapClient>
 * @author zn
 * @CreateTime 2014-8-16上午11:05:16
 */

/*
 * Copyright (c) 20014-2041 Institute Of Software Chinese Academy Of Sciences
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @Organization: http://tca.iscas.ac.cn/
 * @author: Nan Zhou
 * @Aknowledge: Tutor Liwu Zhang , Alumnus Yan Zhang, Zhigang Gao
 * @Email: changchangge123@qq.com
 */
public class NAPAKEClient implements IfcAkeProtocol{
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
//	String[] m_pseudonyms;
	String m_ID;
	byte[] m_Auths; 
	byte[] m_myAuths;
	byte[] m_sk;
	
	IfcNapAKECalculate m_NapCalculate;
	public boolean m_isInited;
	public boolean m_isVerified;
	String m_Trans; 
	ConnectStrsTask m_cstAs;//Concurrent mutipletask to connect As array
	
	Stack<EnumNAPMsgType> m_stack;
	//bulletin 
	private IfcBulletinNAPClient m_bulletinNapClient;
	private String m_groupID;
	
	public NAPAKEClient()
	{
		this.m_isInited = false;
		this.m_sk = null;
	}
	
	@Override
	public boolean init(IfcInitData init) throws InitializationException{
		// TODO Auto-generated method stub
		//m_NapCalculate undertake the task of calculation relevant to  NAP protocol
		this.m_NapCalculate = FactoryCalculate.getCalculate("NAPCalculate");
		
		if(init instanceof InitNAPAKEClientData){
			InitNAPAKEClientData initClt = (InitNAPAKEClientData)init;
			//initialization
			this.m_pw = initClt.getM_pw();
			this.m_g = initClt.getM_g();
			this.m_q = initClt.getM_q();
			this.m_ID = initClt.getM_ID();
			this.m_groupID = initClt.getM_groupID();
			//record the bulletinNAP
			this.m_bulletinNapClient = initClt.m_bn;
			//initialization validity checking
			if(this.m_q.isProbablePrime(NAPAKEConstants.ProbablePrimeCertainty) &&
					this.m_g.compareTo(BigInteger.ZERO)>0 &&
					null!=this.m_pw && 
					null != this.m_ID)// && 
					//isInArrays(this.m_ID, this.m_IDs))
			{
				//init the stack of the protocol 
				EnumNAPMsgType[] order = { EnumNAPMsgType.SAs,EnumNAPMsgType.YAuths};
				initProtocolStack(order);
				//set the flag of initialization
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
	public int getIDNum(){
		return this.m_As.length;
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
	
	//set the order of the protocol stack 
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
		return createGroupIDMsg();
	}
	@Override
	public IfcMessage processMessage(IfcMessage m) throws IllegalMsgException, CannotGenerateNewMsgException, InitializationException, InterruptedException{
		// TODO Auto-generated method stub	
		if(!this.m_isInited){
			throw new InitializationException();
		}
		else{
			//check if the message is legal and extract information from the message
			if(!drawInfo(m)){			
				throw new IllegalMsgException();
			}
			else{//	legal			
				NAPMessage mNap = (NAPMessage)m;
				IfcMessage newMsg = null;
				//process
				switch (mNap.getM_msgType()){
				case SAs:
					newMsg = createXstarBMsg();
					//connect the string
//					this.m_cstIDs = this.m_NapCalculate.exeStrsCntTask(this.m_pseudonyms);
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
		if(m.areMsgLegle() &&
				isInOrder(m)){
			NAPMessage mNap = (NAPMessage)m;
			
			switch(mNap.getM_msgType())
			{
			case SAs:
				NAPMessage.SAsData data = (NAPMessage.SAsData)mNap.getM_data();
				this.m_As = data.getM_As();
				this.m_SID = data.getM_SID();
				this.m_A = this.m_NapCalculate.getAself(m_ID, m_As, m_bulletinNapClient);
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
	private IfcMessage createGroupIDMsg()
	{
		//NAPMessage resultMsg = new NAPMessage();
	
		GroupIDData groupIDData = GroupIDData.getGroupIDData(this.m_groupID);
		return NAPMessage.getNAPMessage(groupIDData, EnumNAPMsgType.GroupID);
		
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
		if(null==this.m_cstAs ){
			System.out.println("connect m_cstAs  and m_cstIDs failed");
			return null;
		}
		String trans = this.m_NapCalculate.getTrans(this.m_bulletinNapClient.getConnectedPseus(), 
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
		if(this.m_stack.peek().equals(mNap.sGetMsgType()))
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
