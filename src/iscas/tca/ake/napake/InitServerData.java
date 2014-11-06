package iscas.tca.ake.napake;

import iscas.tca.ake.IfcInitData;

import java.math.BigInteger;

/**
 * ������<>
 * ������<InitNAPServerData>
 * @author zn
 * @CreateTime 2014-8-16����11:02:54
 */
public class InitServerData implements IfcInitData{
	BigInteger m_q;//qȺ
	BigInteger m_g; //g����
	
	IfcNAPServerUser m_NapServerUser;//NapServerЭ����ϲ�ʹ���ߣ���������Ҫ����ɵĹ���
	String m_S;//S��ID
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
