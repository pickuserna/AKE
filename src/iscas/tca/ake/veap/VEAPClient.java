package iscas.tca.ake.veap;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.ProtocolStack;
import iscas.tca.ake.demoapp.mvc.module.bulletin.csimplements.BulletinVeapClient;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinVEAPClient;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.message.veap.EnumVEAPMsgType;
import iscas.tca.ake.message.veap.VEAPMessage;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.rand.IfcRand;
import iscas.tca.ake.util.rand.Rand;
import iscas.tca.ake.veap.calculate.IfcVEAPCalculate;
import iscas.tca.ake.veap.calculate.U_C;
import iscas.tca.ake.veap.calculate.VEAPCalculate;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author zn
 * @CreateTime 2014-9-11上午9:37:36
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
	
	byte[] m_U;
	byte[] m_K;
	byte[] m_C;
	
	//the data published on the bulletin
	BigInteger m_X;
	U_C[] m_u_cs;
	
	String m_sUCs;//connected String using UCs
	byte[] m_MS;//MS in plain text
	// Verification variables
	byte[] m_MK;
	byte[] m_VuVsSK;
	byte[] m_GD;
	// SessinoKey
	byte[] m_SK;
	//S2C Message from Server to Client
	String m_sid;
	byte[] m_VsS;//Vs from S
	
	BigInteger m_Y;
	BigInteger m_Ax;
	BigInteger m_Ap;

	ProtocolStack<EnumVEAPMsgType> m_proStack;
	IfcVEAPCalculate m_calculate;
	IfcBulletinVEAPClient m_bulletinClient;
	boolean m_isVerified;
	
	long m_t;
	long m_t0;
	@Override
	public boolean init(IfcInitData init) throws Exception {
		m_proStack = new ProtocolStack<EnumVEAPMsgType>();
		//extract the info from initial data
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
		
		//apply memory space for the variables
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
	 * TODO:<generate UAB Message>
	 * @return 
	 */
	@Override
	public int getIDNum(){
		return this.m_u_cs.length;
	}
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
		//create the message 
		VEAPMessage vm = new VEAPMessage();
		VEAPMessage.UABData data = vm.new UABData(this.m_groupID,m_A, m_B);
		vm.setVEAPMsg(EnumVEAPMsgType.UAB, data);
		return vm;
		
	}
	/**
	 * TODO:<check if the message received is legal and extract the info in it>
	 * @param m : received Message
	 * @return if the message is legal
	 */
	private boolean drawInfo(IfcMessage m) {
		// check if the message is legal
		VEAPMessage vm = (VEAPMessage)m;
		if(vm.areMsgLegle() &&
				this.m_proStack.isInOrder(vm.getM_msgType())){
			switch(vm.getM_msgType()){
			case S2C: 
				VEAPMessage.S2CData data =(VEAPMessage.S2CData)vm.getM_data();
				if(data.areMsgLegle()){
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
	// off-line speed-up
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
	 * TODO:<verify the message from server, if passed then generate the Verify Msg>
	 * @return 
	 */
	private IfcMessage generateVerify() throws Exception{
		//-------------concurrent optimized----------------
		waitingcall();
		BigInteger Xa = Assist.modPow(m_X, m_randa, m_q);
		BigInteger inverse = Assist.modInverse(Xa, m_q);
		BigInteger pvdx = Assist.modMutiply(m_Ax, inverse, m_q);
		//calculate U_K_C
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
		//Verify Vs，generate VerifyData，store SK
		int[] lensVS = {VEAPConstants.LengthOfVerify, 
				VEAPConstants.LengthOfVerify,
				VEAPConstants.LengthOfSK};
		byte[][] bssVS = new byte[3][];
		Assist.divideBytes(m_VuVsSK, bssVS, lensVS);
		if(Arrays.equals(m_VsS, bssVS[1])){
			m_isVerified = true;
			this.m_SK = bssVS[2];
			//generate the message 
			VEAPMessage vm = new VEAPMessage();
			VEAPMessage.VerifyData data = vm.new VerifyData(bssVS[0]);
			vm.setVEAPMsg(EnumVEAPMsgType.Verify, data);
			
			return vm;
		}
		//failed to verify
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
	 * TODO:<get data from bulletin，need to be synchronized> 
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
