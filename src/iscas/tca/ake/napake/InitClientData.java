package iscas.tca.ake.napake;

import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.test.swing.module.bulletin.csdata.BulletinNAPClientHashData;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClient;

import java.math.BigInteger;

/**
 * InitData of the NAPClient
 * @author zn
 * @CreateTime 2014-8-16ÉÏÎç10:58:25
 */
public class InitClientData implements IfcInitData{
	String m_pw;//password of the client
	String m_groupID;// the group that the client belongs to
	String m_ID;//the id of the client
	BigInteger m_q;// q is the finite field containing q elements
	BigInteger m_g; //g is a group element
	public IfcBulletinNAPClient m_bn;// the bulletin module of the NAPClient
	
	/**
	 * @param m_pw
	 * @param m_groupID
	 * @param m_ID
	 * @param m_q
	 * @param m_g
	 */
	public InitClientData(String m_pw, String m_groupID, String m_ID,
			BigInteger m_q, BigInteger m_g, IfcBulletinNAPClient bn) {
		super();
		this.m_pw = m_pw;
//		this.m_IDs = m_IDs;
		this.m_groupID = m_groupID;
		this.m_ID = m_ID;
		this.m_q = m_q;
		this.m_g = m_g;
		this.m_bn = bn;
	}
	public String getM_pw() {
		return m_pw;
	}
	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}
	public String getM_groupID() {
		return m_groupID;
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
