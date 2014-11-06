package iscas.tca.ake.napake;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.message.nap.EnumNAPMsgType;
import iscas.tca.ake.message.nap.NAPMessage;
import iscas.tca.ake.napake.calculate.FactoryCalculate;
import iscas.tca.ake.napake.calculate.IfcNapCalculate;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.connectStrings.ConnectStrsTask;
import iscas.tca.ake.util.exceptions.CannotFindSuchIDException;
import iscas.tca.ake.util.exceptions.CannotGenerateNewMsgException;
import iscas.tca.ake.util.exceptions.IllegalMsgException;
import iscas.tca.ake.util.exceptions.InitializationException;
import iscas.tca.ake.util.rand.Rand;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Stack;
//import static java.lang.System;
/**
 * ������<>
 * ������<NAPServer>
 * @author zn
 * @CreateTime 2014-8-16����2:26:21
 */
public class NAPServer implements IfcAkeProtocol {

	/*static*/ BigInteger m_q;//�Ƿ���Ҫstatic
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
	
	//BigInteger m_K;//���Բ���¼
	
	String[] m_pws;
	String m_SID;
	String[] m_IDs;
	byte[] m_Auths;
	byte[] m_sk;
	byte[] m_Authc;
	byte[] m_serverAuthc;
	 String m_ClientID;//�ʹ˷���˳���������Ŀͻ��˳���
	Stack<EnumNAPMsgType> m_stackProtocol;//Э��ջ���涨Э���ִ��˳��
	
	IfcNapCalculate m_NapCalculate;
	IfcNAPServerUser m_NapServerUser;
	boolean m_isInited;
	//����
	String m_Trans;
	private boolean m_isVerified = false;
	
	//���߳������ַ������ڷ���XStarBʱ���д���
	ConnectStrsTask m_cstAs;//���߳�����As
	ConnectStrsTask m_cstIDs;//���߳�����IDs�������ͻ�ȡ����һ��λ��
	/**
	 * 
	 */
	public NAPServer() {
		// TODO Auto-generated constructor stub
		this.m_isInited = false;
		this.m_sk = null;
	}
//	public boolean isM_isInited() {
//		return m_isInited;
//	}
	//noooooooooooooooo�ɿͻ������������Բ���ʵ��
	@Override	
	public IfcMessage startProtocol() {
		// 
		return null;
	}
	//nooooooooooooooooû�з������˵���֤����
	@Override	
	public boolean isVerified() {
		// TODO Auto-generated method stub
		return this.m_isVerified;
	}
	

	@Override
	public boolean init(IfcInitData init) throws Exception{
		// TODO Auto-generated method stub
		this.m_NapCalculate = FactoryCalculate.getCalculate("NAPCalculate");
		
		if(init instanceof InitServerData){
			
			InitServerData init2 = (InitServerData)init;
			this.m_NapServerUser = init2.getM_NapServerUser();
			this.m_q = (init2).getM_q();
			this.m_g = ( init2).getM_g();
			this.m_SID = (init2).getM_S();
			//������ݺϷ��ԣ�SID��Ϊ��,qΪ����, g>0,m_NapServerUser ��Ϊ��
			if(this.m_SID!=null && 
					this.m_q.isProbablePrime(NAPConstants.ProbablePrimeCertainty) &&
					this.m_g.compareTo(BigInteger.ZERO)>0 &&
					null != this.m_NapServerUser)
			{
				EnumNAPMsgType[] order = {EnumNAPMsgType.IDs,EnumNAPMsgType.XstarB,EnumNAPMsgType.Authc};
				initStack(order);
				//��ʼ���ɹ���Ψһ����
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
	 * TODO:<ʹ��order��Э��ջ���г�ʼ��>
	 * @param order ��Э��Ľ���˳�� [��һ�����ڶ�����������]
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
	public IfcMessage processMessage(IfcMessage m) throws InitializationException, IllegalMsgException, CannotGenerateNewMsgException{
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
				case IDs:
					newMsg =  createSAsMsg();
					this.m_cstIDs = this.m_NapCalculate.exeStrsCntTask(m_IDs);
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
					//��Ϣ���Ϸ�
					throw new IllegalMsgException();
				}
				
				if(null==newMsg){
					//�׳�������������Ϣ�쳣
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
	 * TODO:<���m�Ϸ� ,��ȡm�е���Ϣ>
	 * @param m:<��Ϣ>
	 * @return true �Ϸ���false:���Ϸ�
	 */
	private boolean drawInfo(IfcMessage m)
	{
		//�ж���Ϣ�Ƿ�Ϸ�������
	
		if(m.isMsgLegle() &&
				isInOrder(m)){
				NAPMessage mNap = (NAPMessage)m;
				
				//��¼��NAPServer��
				switch(mNap.getM_msgType())
				{
				case IDs:
					
					this.m_IDs = ((NAPMessage.IDsData)mNap.getM_data()).getM_IDs();//��¼m_IDs;	
					try{
						this.m_pws = this.m_NapServerUser.getPasswords(this.m_IDs);
					}
					catch(CannotFindSuchIDException notfind){
						System.out.println("Can not find such ids at the server");
						return false;
					}
					catch(Exception e){
						return false;
					}
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
				//��ȡ����Ϣ������Ϣջ���˳�
				//this.m_stackProtocol.pop();
				return true;	
		}
		return false;
	}


	/**
	 * TODO:<����SAs��Ϣ>
	 * @return ����޷�����������null
	 */
	
	private IfcMessage createSAsMsg(){
		
		Rand rd = new Rand();
		this.m_rS = rd.randOfMax(this.m_q);
		BigInteger hPW;
		//����As
		this.m_As = new BigInteger[this.m_IDs.length];

		for(int i=0; i<this.m_pws.length; i++)
		{
			hPW = this.m_NapCalculate.getPW(this.m_IDs[i], this.m_pws[i], this.m_q);
			this.m_As[i] = Assist.modPow(hPW, this.m_rS, this.m_q);
		}
		NAPMessage.SAsData data = NAPMessage.SAsData.getSAsData(this.m_SID,this.m_IDs , this.m_As);
		return NAPMessage.getNAPMessage(data, EnumNAPMsgType.SAs);
	}
	
	/**
	 * TODO:<����YAuths��Ϣ>
	 * @return 
	 */
	private IfcMessage createYAuthsMsg() 
	{
		//����
		Rand rd = new Rand();
	//	BigInteger Zp, Xp, Zinverse, Kp;
		this.m_Zp = Assist.modPow(this.m_B, this.m_rS, this.m_q);
		BigInteger Zinverse = Assist.modInverse(this.m_Zp, this.m_q);
		this.m_Xp = Assist.modMutiply(this.m_Xstar, Zinverse, this.m_q);
		this.m_randy = rd.randOfMax(this.m_q);
		this.m_Y = Assist.modPow(this.m_g, this.m_randy, this.m_q);
 	    this.m_Kp = Assist.modPow(this.m_Xp, this.m_randy, this.m_q);
		if(null==m_cstAs || null==m_cstIDs)
		{
			System.out.println("���ӵ��ַ���cstAs �� cstIDs����ȷ");
			return null;
		}
		String trans = this.m_NapCalculate.getTrans(this.m_cstIDs.get().toString(),
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
		//������Ϣ
		NAPMessage.YAuthsData data = NAPMessage.YAuthsData.getYAuthsData(this.m_Y, this.m_Auths);
		return NAPMessage.getNAPMessage(data, EnumNAPMsgType.YAuths);
		
	}
	
	/**
	 * TODO:<�ж���Ϣm�Ƿ����˳��>
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
	 * TODO:<��ȡsk>
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

	public String[] getM_pws() {
		return m_pws;
	}

	public String getM_SID() {
		return m_SID;
	}

	public String[] getM_IDs() {
		return m_IDs;
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
