package iscas.tca.ake.veap.calculate;

import iscas.tca.ake.util.Assist;

import java.io.Serializable;

/**
 * description <U,C>structure
 * @author zn
 * @CreateTime 2014-9-11ÉÏÎç10:12:28
 */
public class U_C implements Serializable{
	protected byte[] m_u;
	protected byte[] m_c;
	
	public byte[] getM_u() {
		return m_u;
	}

	public void setM_u(byte[] m_u) {
		this.m_u = m_u;
	}

	public byte[] getM_c() {
		return m_c;
	}
	
	public void setM_c(byte[] m_c) {
		this.m_c = m_c;
	}
	/**
	 * @return 
	 */
	public String u_c2String(){
		StringBuilder sb = new StringBuilder();
		sb.append(Assist.bytesToString(m_u));
		sb.append(Assist.bytesToString(m_c));
		return sb.toString();
	}
	public boolean containsU(byte[] U){
		if(this.m_u.equals(U))
			return true;
		return false;
	}
	
}
