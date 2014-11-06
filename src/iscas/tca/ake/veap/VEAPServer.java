package iscas.tca.ake.veap;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.ProtocolStack;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.message.veap.EnumVEAPMsgType;
import iscas.tca.ake.message.veap.VEAPMessage;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.rand.Rand;
import iscas.tca.ake.veap.bulletin.IfcBulletinServer;
import iscas.tca.ake.veap.calculate.GroupData;
import iscas.tca.ake.veap.calculate.GroupInput;
import iscas.tca.ake.veap.calculate.IfcVEAPCalculate;
import iscas.tca.ake.veap.calculate.VEAPCalculate;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * ������<>
 * @author zn
 * @CreateTime 2014-9-11����9:37:53
 */
public class VEAPServer implements IfcAkeProtocol {
	//����
	int m_lenMS;
	int m_lenK;//=lenMS
	int m_lenVerify;
	int m_lenSK;
	//Ⱥ����
	BigInteger m_g;
	BigInteger m_q;
	//�ͻ��˷����ı���
	BigInteger m_A;
	BigInteger m_B;
	

	String m_sid;// server ������
	
	String m_groupID;
	BigInteger m_randx;
	BigInteger m_randy;
	BigInteger m_Y;
	BigInteger m_X;
	
	public String getM_groupID() {
		return m_groupID;
	}
	public void setM_groupID(String m_groupID) {
		this.m_groupID = m_groupID;
	}
	IfcVEAPCalculate m_calculate;
	ProtocolStack<EnumVEAPMsgType> m_stack;
	boolean m_isVerified;
	byte[] m_MS;
	byte[] m_GD;
	String m_sUCs;
	
	byte[] m_Vu;
	byte[] m_Vs;
	byte[] m_SK;
	byte[] m_cVu;// Client������Vu

	
	long m_t;
	long m_t0;
	
	IfcGetUsers m_getUsers;
	IfcBulletinServer m_bulletinServer;
	@Override
	public boolean init(IfcInitData init) throws Exception {
		//��ʼ��Э��ջ
		m_stack = new ProtocolStack<EnumVEAPMsgType>();
		EnumVEAPMsgType[] order = { EnumVEAPMsgType.UAB, EnumVEAPMsgType.Verify };
		m_stack.initProtocolStack(order);
		//��ȡ����
		InitVEAPServerData serverData = (InitVEAPServerData) init;
		m_g = serverData.getM_g();
		m_q = serverData.getM_q();
		
		
		m_lenK = m_lenMS = serverData.getM_lenMS();
		m_lenVerify = serverData.getM_lenVerify();
		m_lenSK = serverData.getM_lenSK();
	
		// ��������
		m_getUsers = serverData.getM_getUsers();//��ȡ�û����ϵķ���
		m_bulletinServer = serverData.getM_bulletinServer();//��ȡ�����
		this.m_calculate = new VEAPCalculate();
		this.m_isVerified = false;
		this.m_sid = serverData.getM_sid();
		
		return true;
	}
	//�ͻ���ʵ��
	@Override
	public IfcMessage startProtocol() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * TODO:<��ȡ��Ϣ�е���Ϣ>
	 * @param m
	 * @return 
	 */
	private boolean drawInfo(IfcMessage m) {
		VEAPMessage vm = (VEAPMessage) m;
		// ��Ϣ�Լ���Ϣ�����ݶ��Ϸ�
		if (vm.isMsgLegle() && vm.getM_data().isMsgLegle()) {

			switch (vm.getM_msgType()) {
			case UAB:
				VEAPMessage.UABData data = (VEAPMessage.UABData) vm.getM_data();
				this.m_A = data.getData_A();
				this.m_B = data.getData_B();
				this.m_groupID = data.getData_UID();
				break;
			case Verify:
				VEAPMessage.VerifyData data2 = (VEAPMessage.VerifyData) vm
						.getM_data();
				this.m_cVu = data2.getData_verify();
				break;
			default:
				return false;
			}
			return true;
		}
		return false;
	}
	@Override
	public IfcMessage processMessage(IfcMessage m) throws Exception {
		VEAPMessage vm = (VEAPMessage) m;
		// �ж���Ϣ�Ϸ�
		if (drawInfo(vm) && m_stack.isInOrder(vm.getM_msgType())) {
			//������������Ϣ��������Ϣ������Ϣջ����ջ
			m_stack.pop();
			switch (vm.getM_msgType()) {
			case UAB:
				//Э���ջ
				return createS2C();
			case Verify:
				//Э���ջ
				verify();
				return null;
			}
		}
		return null;
	}
	
	///////---------concurrent function --------/////	
	

	/**
	 * TODO:<�ӹ������ȡ���ݣ���������ڸ�groupID�������> 
	 */
	private void getBulletinData()throws Exception{
			
		GroupData gd;
		gd=m_bulletinServer.getGroupData(this.m_groupID);
		// ���û���ҵ�groupID,���¼��㲢������bulletin��
		if(null==gd){
			throw new UnknownError();
		}
		
		this.m_MS = gd.getM_MS();
		this.m_sUCs = gd.getM_sUCs();
		this.m_t =gd.getM_publishTime();
		this.m_X = gd.getM_X();
		m_t0= gd.getM_timeOut();
		
		this.m_randx = gd.getM_randx();
	}
	
	//////////////--------concurrent end-----------//////////////
	/**
	 * TODO:<����S2C��Ϣ>
	 * @return 
	 */
	private IfcMessage createS2C() throws Exception{
		this.m_randy = new Rand().randOfMax(m_q);
		this.m_Y = Assist.modPow(m_g, m_randy, m_q);
		//��ȡ������ϵ�����
		getBulletinData();
		//GD,MK,sABXY,Mac
		this.m_GD = m_calculate.getGD(this.m_sUCs, m_t, m_t0);
		byte[] mk = m_calculate.getMK(this.m_MS,
				Assist.modPow(m_B, m_randx, m_q),
				Assist.modPow(m_B, m_randy, m_q));
		BigInteger Ax = Assist.modPow(m_A, m_randx, m_q);
		String sABXY = m_calculate.getABXY(m_A, 
				m_B, 
				Ax,
				m_X, 
				m_Y);
		byte[] vuvssk = m_calculate.getMac(mk, 
				this.m_groupID, 
				m_sid, m_GD, 
				sABXY);
		//����vuvssk
		int[] lens = {m_lenVerify, m_lenVerify, m_lenSK};
		
		byte[][] bss = new byte[3][];
		Assist.divideBytes(vuvssk, bss, lens);
		m_Vu = bss[0];
		m_Vs = bss[1];
		m_SK = bss[2];
		//����S2C��Ϣ
		VEAPMessage vm =new VEAPMessage();
		VEAPMessage.S2CData data = vm.new S2CData(this.m_sid, 
				Ax, 
				m_Y,
				m_Vs);
		
		vm.setVEAPMsg(EnumVEAPMsgType.S2C, data);
		return vm;
	}
	
	
	private boolean verify() {
		if(Arrays.equals(m_cVu, m_Vu)){
			this.m_isVerified = true;
		}
		return m_isVerified;
	}
	@Override
	public boolean isVerified() {
		// TODO Auto-generated method stub
		return this.m_isVerified;
	}

	@Override
	public boolean isProtocolOver() {
		// TODO Auto-generated method stub
		return this.m_stack.isEmpty();
	}
	@Override
	public byte[] getsk() {
		if (this.isVerified()) {
			return m_SK;
		}
		return null;
	}

	

}
