package iscas.tca.ake.veap.calculate;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.rand.Rand;
import iscas.tca.ake.veap.User;
import iscas.tca.ake.veap.edcoder.EDCoder;
import iscas.tca.ake.veap.edcoder.IfcEDCoder;

import java.io.Serializable;
import java.math.BigInteger;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * 描述：<计算一个group的数据，Server公布给Client，Server获取数据的数据源>
 * @author zn
 * @CreateTime 2014-9-11上午10:34:59
 */
public class GroupData implements Serializable{
	BigInteger m_randx;
	BigInteger m_X;//
	String m_groupID;//
	U_C[] m_ucs;//
	String m_sUCs;//连接好的UCs字符串
	byte[] m_MS;
	BigInteger m_q;
	//不是计算得到的值
	long m_timeOut;//
	long m_publishTime;//
	BigInteger m_g ;
	String groupDataID;
	/**
	 * TODO:<计算并获取Group数据>
	 * @param input
	 * @return 
	 */
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("published InfoMation:\r\n");
		sb.append("groupID = "+m_groupID+"\r\n");
		sb.append("publish Time =" +m_publishTime+"\r\n");
		sb.append("timeOut = "+m_timeOut+"\r\n");
		sb.append("MS = " +new HexBinaryAdapter().marshal(m_MS)+"\r\n");
		sb.append("randx = "+ m_randx+"\r\n");
		sb.append("X = "+m_X+"\r\n");
		sb.append("UC = "+ new HexBinaryAdapter().marshal(m_sUCs.getBytes())+"\r\n");
		return sb.toString();
	}
	
	public GroupData calGroupData(GroupInput input){
		m_groupID = input.getM_groupID();
		
		m_q = input.getM_q();
		m_g = input.getM_g();
		
		int lenMS= input.getM_lenMS();
		Rand rnd = new Rand();
		m_MS = rnd.nextBytes(lenMS);
		m_randx = rnd.randOfMax(m_q);
		m_X = Assist.modPow(m_g, m_randx, m_q);
		m_ucs = calU_Cs(input.getM_users(), lenMS);
		//连接成字符串
		m_sUCs = new VEAPCalculate().connectUcs(m_ucs);
		m_timeOut = input.timeOut;
		//System.out.println("MS:"+Assist.bytesToHexString(m_MS));
		return this;
	}
	/**
	 * TODO:<计算U_Cs>
	 * @return 
	 */
	private U_C[] calU_Cs(User[] users, int lenU){		
		byte[][] u_k = new byte[2][];
		int[] lens ={lenU, lenU};
		
		U_C[] u_cs = new U_C[users.length];
		
		IfcVEAPCalculate cal = new VEAPCalculate();
		IfcEDCoder edcoder = new EDCoder();

		for(int i=0; i<users.length; i++){
			BigInteger pvd = users[i].getPvd();
			BigInteger pvdX = Assist.modPow(pvd, m_randx, m_q);
			byte[] uk = cal.getU_K(users[i].user_id, m_X, pvd, 
					pvdX);
			Assist.divideBytes(uk, u_k, lens) ; 
			//System.out.println("X:"+m_X+"\tpvd:"+pvd+"\tpvdX:"+pvdX+"\trandx:"+m_randx);
			byte[] c = edcoder.encrypto(m_MS, u_k[1]);//
			//记录uc
			u_cs[i] = new U_C();
			System.out.println(u_cs[i]);
			u_cs[i].setM_u(u_k[0]);
			u_cs[i].setM_c(c);
		}
		return u_cs;
	}
	public void setPublishTime(Long publishTime){
		this.m_publishTime = publishTime;
	}
	public BigInteger getM_randx() {
		return m_randx;
	}
	public BigInteger getM_X() {
		return m_X;
	}
	public String getM_groupID() {
		return m_groupID;
	}
	public U_C[] getM_ucs() {
		return m_ucs;
	}
	public String getM_sUCs() {
		return m_sUCs;
	}
	public byte[] getM_MS() {
		return m_MS;
	}
	public BigInteger getM_q() {
		return m_q;
	}
	public long getM_timeOut() {
		return m_timeOut;
	}
	public long getM_publishTime() {
		return m_publishTime;
	}
	public String getGroupDataID() {
		return groupDataID;
	}
	
	////////////////////////////////////////////////////////////////
	
}
