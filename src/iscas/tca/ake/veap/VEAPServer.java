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
 * 描述：<>
 * @author zn
 * @CreateTime 2014-9-11上午9:37:53
 */
public class VEAPServer implements IfcAkeProtocol {
	//长度
	int m_lenMS;
	int m_lenK;//=lenMS
	int m_lenVerify;
	int m_lenSK;
	//群运算
	BigInteger m_g;
	BigInteger m_q;
	//客户端发来的变量
	BigInteger m_A;
	BigInteger m_B;
	

	String m_sid;// server 的名字
	
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
	byte[] m_cVu;// Client发来的Vu

	
	long m_t;
	long m_t0;
	
	IfcGetUsers m_getUsers;
	IfcBulletinServer m_bulletinServer;
	@Override
	public boolean init(IfcInitData init) throws Exception {
		//初始化协议栈
		m_stack = new ProtocolStack<EnumVEAPMsgType>();
		EnumVEAPMsgType[] order = { EnumVEAPMsgType.UAB, EnumVEAPMsgType.Verify };
		m_stack.initProtocolStack(order);
		//提取数据
		InitVEAPServerData serverData = (InitVEAPServerData) init;
		m_g = serverData.getM_g();
		m_q = serverData.getM_q();
		
		
		m_lenK = m_lenMS = serverData.getM_lenMS();
		m_lenVerify = serverData.getM_lenVerify();
		m_lenSK = serverData.getM_lenSK();
	
		// 参数检验
		m_getUsers = serverData.getM_getUsers();//获取用户集合的方法
		m_bulletinServer = serverData.getM_bulletinServer();//获取公告板
		this.m_calculate = new VEAPCalculate();
		this.m_isVerified = false;
		this.m_sid = serverData.getM_sid();
		
		return true;
	}
	//客户端实现
	@Override
	public IfcMessage startProtocol() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * TODO:<提取消息中的信息>
	 * @param m
	 * @return 
	 */
	private boolean drawInfo(IfcMessage m) {
		VEAPMessage vm = (VEAPMessage) m;
		// 消息以及消息的内容都合法
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
		// 判断消息合法
		if (drawInfo(vm) && m_stack.isInOrder(vm.getM_msgType())) {
			//接收了这条消息，存下信息，在消息栈中退栈
			m_stack.pop();
			switch (vm.getM_msgType()) {
			case UAB:
				//协议出栈
				return createS2C();
			case Verify:
				//协议出栈
				verify();
				return null;
			}
		}
		return null;
	}
	
	///////---------concurrent function --------/////	
	

	/**
	 * TODO:<从公告板上取数据，如果不存在该groupID，则计算> 
	 */
	private void getBulletinData()throws Exception{
			
		GroupData gd;
		gd=m_bulletinServer.getGroupData(this.m_groupID);
		// 如果没有找到groupID,重新计算并发布到bulletin上
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
	 * TODO:<生成S2C消息>
	 * @return 
	 */
	private IfcMessage createS2C() throws Exception{
		this.m_randy = new Rand().randOfMax(m_q);
		this.m_Y = Assist.modPow(m_g, m_randy, m_q);
		//获取公告板上的数据
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
		//划分vuvssk
		int[] lens = {m_lenVerify, m_lenVerify, m_lenSK};
		
		byte[][] bss = new byte[3][];
		Assist.divideBytes(vuvssk, bss, lens);
		m_Vu = bss[0];
		m_Vs = bss[1];
		m_SK = bss[2];
		//构造S2C消息
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
