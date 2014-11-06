package iscas.tca.ake.napake;

import iscas.tca.ake.IfcInitData;

import java.math.BigInteger;

/**
 * 描述：<>
 * 类名：<InitNAPClientData>
 * @author zn
 * @CreateTime 2014-8-16上午10:58:25
 */
public class InitClientData implements IfcInitData{
	String m_pw;//自己的密码
	String[] m_IDs;//T数组
	String m_ID;//自己的ID
	BigInteger m_q;//q群
	BigInteger m_g; //g底数
	
	/**
	 * @param m_pw
	 * @param m_IDs
	 * @param m_ID
	 * @param m_q
	 * @param m_g
	 */
	public InitClientData(String m_pw, String[] m_IDs, String m_ID,
			BigInteger m_q, BigInteger m_g) {
		super();
		this.m_pw = m_pw;
		this.m_IDs = m_IDs;
		this.m_ID = m_ID;
		this.m_q = m_q;
		this.m_g = m_g;
	}
	public String getM_pw() {
		return m_pw;
	}
	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}
	public String[] getM_IDs() {
		return m_IDs;
	}
	public void setM_IDs(String[] m_IDs) {
		this.m_IDs = m_IDs;
	}
	public String getM_ID() {
		return m_ID;
	}
	public void setM_ID(String m_ID) {
		this.m_ID = m_ID;
	}
	public BigInteger getM_q() {
		return m_q;
	}
	public void setM_q(BigInteger m_q) {
		this.m_q = m_q;
	}
	public BigInteger getM_g() {
		return m_g;
	}
	public void setM_g(BigInteger m_g) {
		this.m_g = m_g;
	}
	
}
