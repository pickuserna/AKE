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
 * ������<>
 * @author zn
 * @CreateTime 2014-9-11����9:37:36
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
	
	byte[] m_U;//ID����
	byte[] m_K;
	byte[] m_C;//����
	
	//�ӹ�����ϻ�ȡ������
	BigInteger m_X;
	U_C[] m_u_cs;
	
	String m_sUCs;//���Ӻõ�U_Cs�ַ���
	byte[] m_MS;//����
	// �����֤
	byte[] m_MK;
	byte[] m_VuVsSK;
	byte[] m_GD;
	// ������Կ
	byte[] m_SK;
	//S2C����������
	String m_sid;
	byte[] m_VsS;//s������Vs
	
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
		//���ò���
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
		// ---undo -----�����ĺϷ��Լ��
		EnumVEAPMsgType[] order = {EnumVEAPMsgType.S2C};
		m_proStack.initProtocolStack(order);
		this.m_isVerified = false;
		
		//�����������ڴ�
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
	 * TODO:<����UAB��Ϣ>
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
		//������Ϣ
		VEAPMessage vm = new VEAPMessage();
		VEAPMessage.UABData data = vm.new UABData(this.m_groupID,m_A, m_B);
		vm.setVEAPMsg(EnumVEAPMsgType.UAB, data);
		return vm;
		
	}
	/**
	 * TODO:<�����Ϣ�ĺϷ��Բ���ȡ��Ϣ����>
	 * @param m
	 * @return 
	 */
	private boolean drawInfo(IfcMessage m) {
		// �ж���Ϣ�Ƿ�Ϸ�������
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
	//�ڵȴ��ڼ���Խ��е�����
	//pvd���㣬������ȡ���ݣ�����U_Cs,��U_Cs��Ѱ��c
	private void waitingcall()throws Exception
	{
		User user = new User(m_id, m_password);
		this.m_pvd = user.getPvd();
		fetchBulletin();//�ӹ������ȡ����
		this.m_sUCs = m_calculate.connectUcs(this.m_u_cs);//����u_cs
		
	
	}
	/**
	 * TODO:<��֤��������ͨ�������Verify��֤��Ϣ>
	 * @return 
	 */
	private IfcMessage generateVerify() throws Exception{
		//-------------���߳��Ż�----------------
		waitingcall();
		//��Ҫ�׳��쳣
		BigInteger Xa = Assist.modPow(m_X, m_randa, m_q);
		BigInteger inverse = Assist.modInverse(Xa, m_q);
		BigInteger pvdx = Assist.modMutiply(m_Ax, inverse, m_q);
		//����U_K_C
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
		this.m_GD = m_calculate.getGD(m_sUCs, m_t, m_t0);//waitingcall��ȡ��
		String sABXY = m_calculate.getABXY(m_A, m_B, 
				m_Ax, 
				m_X, m_Y);
		this.m_VuVsSK = m_calculate.getMac(m_MK,m_groupID,
				m_sid, m_GD, sABXY);
		//��֤Vs������VerifyData������SK
		int[] lensVS = {VEAPConstants.LengthOfVerify, 
				VEAPConstants.LengthOfVerify,
				VEAPConstants.LengthOfSK};
		byte[][] bssVS = new byte[3][];
		Assist.divideBytes(m_VuVsSK, bssVS, lensVS);
		if(Arrays.equals(m_VsS, bssVS[1])){
			m_isVerified = true;
			this.m_SK = bssVS[2];
			//������Ϣ
			VEAPMessage vm = new VEAPMessage();
			VEAPMessage.VerifyData data = vm.new VerifyData(bssVS[0]);
			vm.setVEAPMsg(EnumVEAPMsgType.Verify, data);
			
			return vm;
		}
		//δͨ����֤
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
	 * TODO:<�ӹ�����ϻ�ȡ���ݣ�������Ҫͬ��> 
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
