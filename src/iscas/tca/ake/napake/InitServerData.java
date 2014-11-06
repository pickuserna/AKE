package iscas.tca.ake.napake;

import iscas.tca.ake.IfcInitData;

import java.math.BigInteger;

/**
 * 描述：<>
 * 类名：<InitNAPServerData>
 * @author zn
 * @CreateTime 2014-8-16上午11:02:54
 */
public class InitServerData implements IfcInitData{
	BigInteger m_q;//q群
	BigInteger m_g; //g底数
	
	IfcNAPServerUser m_NapServerUser;//NapServer协议的上层使用者，里面有需要它完成的功能
	String m_S;//S的ID
	/**
	 * @param m_q
	 * @param m_g
	 * @param m_S
	 * @param m_NapServerUser
	 */
	public InitServerData(BigInteger m_q, BigInteger m_g, String m_S,
			IfcNAPServerUser m_NapServerUser) {
		super();
		this.m_q = m_q;
		this.m_g = m_g;
		this.m_S = m_S;
		this.m_NapServerUser = m_NapServerUser;
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

	public String getM_S() {
		return m_S;
	}

	public void setM_S(String m_S) {
		this.m_S = m_S;
	}

	public IfcNAPServerUser getM_NapServerUser() {
		return m_NapServerUser;
	}

	public void setM_NapServerUser(IfcNAPServerUser m_NapServerUser) {
		this.m_NapServerUser = m_NapServerUser;
	}
	
}
