package iscas.tca.ake.veap;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.ProtocolStack;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.message.veap.EnumVEAPMsgType;
import iscas.tca.ake.message.veap.VEAPMessage;
import iscas.tca.ake.test.swing.module.bulletin.Bulletin_Veap;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.rand.IfcRand;
import iscas.tca.ake.util.rand.Rand;
import iscas.tca.ake.veap.bulletin.IfcBulletinClient;
import iscas.tca.ake.veap.calculate.IfcVEAPCalculate;
import iscas.tca.ake.veap.calculate.U_C;
import iscas.tca.ake.veap.calculate.VEAPCalculate;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-9-11上午9:37:36
 */
public class VEAPClient implements IfcAkeProtocol {

	BigInteger m_q;
	BigInteger m_g;
	String m_id;
	String m_password;
	String m_groupID;
	int m_lenK;
	int m_lenSK;
	
	BigInteger m_randa;
	BigInteger m_randb;
	BigInteger m_A;
	BigInteger m_B;
	BigInteger m_pvd;// pvd
	
	byte[] m_U;//ID假名
	byte[] m_K;
	byte[] m_C;//密文
	
	//从公告板上获取的数据
	BigInteger m_X;
	U_C[] m_u_cs;
	
	String m_sUCs;//连接好的U_Cs字符串
	byte[] m_MS;//明文
	// 身份验证
	byte[] m_MK;
	byte[] m_VuVsSK;
	byte[] m_GD;
	// 最终秘钥
	byte[] m_SK;
	//S2C发来的数据
	String m_sid;
	byte[] m_VsS;//s发来的Vs
	
	BigInteger m_Y;
	BigInteger m_Ax;
	BigInteger m_Ap;

	ProtocolStack<EnumVEAPMsgType> m_proStack;
	IfcVEAPCalculate m_calculate;
	IfcBulletinClient m_bulletinClient;
	boolean m_isVerified;
	
	long m_t;
	long m_t0;
	@Override
	public boolean init(IfcInitData init) throws Exception {
		m_proStack = new ProtocolStack<EnumVEAPMsgType>();
		//设置参数
		InitVEAPClientData initClientData = (InitVEAPClientData) init;
		this.m_q = initClientData.getM_q();
		this.m_g = initClientData.getM_g();
		this.m_groupID =initClientData.getM_groupID();
		this.m_id = initClientData.getM_ID();
		this.m_password = initClientData.getM_pw(); 
		
		this.m_bulletinClient = initClientData.getM_bulletinClient();
		this.m_lenSK = initClientData.getM_lenSK();
		this.m_lenK = initClientData.getM_lenK();
		
		this.m_calculate = new VEAPCalculate();
		// ---undo -----参数的合法性检查
		EnumVEAPMsgType[] order = {EnumVEAPMsgType.S2C};
		m_proStack.initProtocolStack(order);
		this.m_isVerified = false;
		
		//给变量申请内存
		m_U = new byte[m_lenK];
		m_K = new byte[m_lenK];
		m_C = null;
		return true;
	}

	@Override
	public IfcMessage startProtocol() {
		// TODO Auto-generated method stub
		return generateUAB();
	}
	/**
	 * TODO:<生成UAB消息>
	 * @return 
	 */
	private IfcMessage generateUAB()
	{
		this.m_pvd = new User(m_id,m_password).getPvd();
		//a, b
		IfcRand r = new Rand();
		
		this.m_randa = r.randOfMax(m_q);
		this.m_randb = r.randOfMax(m_q);
		//A',A,B
		m_Ap = Assist.modPow(this.m_g, m_randa, m_q);
		this.m_B = Assist.modPow(m_g, m_randb, m_q);
		this.m_A = Assist.modMutiply(m_Ap, this.m_pvd, this.m_q);
		//构造消息
		VEAPMessage vm = new VEAPMessage();
		VEAPMessage.UABData data = vm.new UABData(this.m_groupID,m_A, m_B);
		vm.setVEAPMsg(EnumVEAPMsgType.UAB, data);
		return vm;
		
	}
	/**
	 * TODO:<检查消息的合法性并提取消息变量>
	 * @param m
	 * @return 
	 */
	private boolean drawInfo(IfcMessage m) {
		// 判断消息是否合法，合序
		VEAPMessage vm = (VEAPMessage)m;
		if(vm.isMsgLegle() &&
				this.m_proStack.isInOrder(vm.getM_msgType())){
			switch(vm.getM_msgType()){
			case S2C: 
				VEAPMessage.S2CData data =(VEAPMessage.S2CData)vm.getM_data();
				if(data.isMsgLegle()){
					this.m_Ax = data.getData_Ax();
					this.m_sid = data.getData_SID();
					this.m_VsS = data.getData_VS();
					this.m_Y = data.getData_Y();
					return true;
				}
				else return false;
			}
		}
		return false;
	}
	@Override
	public IfcMessage processMessage(IfcMessage m) throws Exception {
		// TODO Auto-generated method stub
		if (drawInfo(m)) {
			VEAPMessage vm = (VEAPMessage) m;
			m_proStack.pop();
			switch (vm.getM_msgType()) {
			case S2C:
//				try{
					
					return generateVerify();
//				}catch(Exception e){
//					e.printStackTrace();
//					return null;
//				}
			}
		}
		return null;
	}
	//在等待期间可以进行的运算
	//pvd计算，公告栏取数据，连接U_Cs,在U_Cs中寻找c
	private void waitingcall()throws Exception
	{
		User user = new User(m_id, m_password);
		this.m_pvd = user.getPvd();
		fetchBulletin();//从公告板上取数据
		this.m_sUCs = m_calculate.connectUcs(this.m_u_cs);//连接u_cs
		
	
	}
	/**
	 * TODO:<验证服务器，通过则产生Verify验证消息>
	 * @return 
	 */
	private IfcMessage generateVerify() throws Exception{
		//-------------多线程优化----------------
		waitingcall();
		//需要抛出异常
		BigInteger Xa = Assist.modPow(m_X, m_randa, m_q);
		BigInteger inverse = Assist.modInverse(Xa, m_q);
		BigInteger pvdx = Assist.modMutiply(m_Ax, inverse, m_q);
		//计算U_K_C
		byte[] u_k = m_calculate.getU_K(m_id, m_X, m_pvd, pvdx);
		int[] lens = {m_lenK,m_lenK};
		
		byte[][] bss = new byte[2][];
		Assist.divideBytes(u_k, bss, lens);
		m_U= bss[0];
		m_K = bss[1];
		this.m_C = m_calculate.findByU(m_u_cs, m_U).getM_c();
		//MS,MK,GD,Mac
		//check
		this.m_MS = m_calculate.decryptC(m_C, m_K);
		this.m_MK = m_calculate.getMK(m_MS,
				Assist.modPow(m_X, m_randb, m_q),
				Assist.modPow(m_Y, m_randb, m_q));
		this.m_GD = m_calculate.getGD(m_sUCs, m_t, m_t0);//waitingcall获取的
		String sABXY = m_calculate.getABXY(m_A, m_B, 
				m_Ax, 
				m_X, m_Y);
		this.m_VuVsSK = m_calculate.getMac(m_MK,m_groupID,
				m_sid, m_GD, sABXY);
		//验证Vs，构造VerifyData，保存SK
		int[] lensVS = {VEAPConstants.LengthOfVerify, 
				VEAPConstants.LengthOfVerify,
				VEAPConstants.LengthOfSK};
		byte[][] bssVS = new byte[3][];
		Assist.divideBytes(m_VuVsSK, bssVS, lensVS);
		if(Arrays.equals(m_VsS, bssVS[1])){
			m_isVerified = true;
			this.m_SK = bssVS[2];
			//构造消息
			VEAPMessage vm = new VEAPMessage();
			VEAPMessage.VerifyData data = vm.new VerifyData(bssVS[0]);
			vm.setVEAPMsg(EnumVEAPMsgType.Verify, data);
			
			return vm;
		}
		//未通过验证
		return null;
	}
	@Override
	public boolean isVerified() {
		// TODO Auto-generated method stub
		return m_isVerified;
	}

	@Override
	public boolean isProtocolOver() {
		// TODO Auto-generated method stub
		return m_proStack.isEmpty();
	}

	@Override
	public byte[] getsk() {
		// TODO Auto-generated method stub
		if(isVerified())
			return m_SK;
		return null;
	}

	/**
	 * TODO:<从公告板上获取数据，这里需要同步> 
	 */
	protected void fetchBulletin() throws Exception{
		this.m_bulletinClient.fetchData(this.m_groupID);
		m_X = this.m_bulletinClient.getX();
		m_t = this.m_bulletinClient.getT();
		m_t0 = this.m_bulletinClient.getT0();
		m_u_cs = this.m_bulletinClient.getU_Cs();
	}

	public String getM_groupID() {
		return m_groupID;
	}

	public void setM_groupID(String m_groupID) {
		this.m_groupID = m_groupID;
	}
}
