package iscas.tca.ake.veap;

import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.veap.bulletin.IfcBulletinClient;

import java.math.BigInteger;

/**
 * √Ë ˆ£∫<>
 * @author zn
 * @CreateTime 2014-9-12…œŒÁ9:13:06
 */
public class InitVEAPClientData  implements IfcInitData{
	int m_lenK;
	int m_lenVerify;
	public int getM_lenVerify() {
		return m_lenVerify;
	}
	public void setM_lenVerify(int m_lenVerify) {
		this.m_lenVerify = m_lenVerify;
	}
	int m_lenSK;
	BigInteger m_g;
	BigInteger m_q;
	
	String m_ID;
	String m_pw;
	String m_groupID;// Ù”⁄ƒƒ∏ˆGroup°¢
	
	IfcBulletinClient m_bulletinClient;
	
	
	
	/**
	 * 
	 */
	public InitVEAPClientData() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param m_lenK
	 * @param m_lenSK
	 * @param m_g
	 * @param m_q
	 * @param m_ID
	 * @param m_pw
	 * @param m_groupID
	 * @param m_bulletinClient
	 */
	public InitVEAPClientData(int m_lenK, int m_lenSK, BigInteger m_g,
			BigInteger m_q, String m_ID, String m_pw, String m_groupID,
			IfcBulletinClient m_bulletinClient) {
		super();
		this.m_lenK = m_lenK;
		this.m_lenSK = m_lenSK;
		this.m_g = m_g;
		this.m_q = m_q;
		this.m_ID = m_ID;
		this.m_pw = m_pw;
		this.m_groupID = m_groupID;
		this.m_bulletinClient = m_bulletinClient;
	}
	public IfcBulletinClient getM_bulletinClient() {
		return m_bulletinClient;
	}
	public void setM_bulletinClient(IfcBulletinClient m_bulletinClient) {
		this.m_bulletinClient = m_bulletinClient;
	}
	public int getM_lenK() {
		return m_lenK;
	}
	public void setM_lenK(int m_lenK) {
		this.m_lenK = m_lenK;
	}
	public int getM_lenSK() {
		return m_lenSK;
	}
	public void setM_lenSK(int m_lenSK) {
		this.m_lenSK = m_lenSK;
	}
	public BigInteger getM_g() {
		return m_g;
	}
	public void setM_g(BigInteger m_g) {
		this.m_g = m_g;
	}
	public BigInteger getM_q() {
		return m_q;
	}
	public void setM_q(BigInteger m_q) {
		this.m_q = m_q;
	}
	public String getM_ID() {
		return m_ID;
	}
	public void setM_ID(String m_ID) {
		this.m_ID = m_ID;
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
	public void setM_groupID(String m_groupID) {
		this.m_groupID = m_groupID;
	}
	
	
}
