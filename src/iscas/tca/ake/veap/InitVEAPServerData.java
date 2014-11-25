package iscas.tca.ake.veap;

import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinVEAPServer;

import java.math.BigInteger;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-9-12上午9:13:26
 */
public class InitVEAPServerData implements IfcInitData {
	int m_lenMS;
	int m_lenVerify;
	int m_lenSK;
	
	BigInteger m_g;
	BigInteger m_q;
	String m_sid;
	//获取用户的信息
	IfcGetUsers m_getUsers;
	//公告板相关的数据，从公告板上取数据
	IfcBulletinVEAPServer m_bulletinServer;
	
	
	/**
	 * 
	 */
	public InitVEAPServerData() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param m_lenMS
	 * @param m_lenVerify
	 * @param m_lenSK
	 * @param m_g
	 * @param m_q
	 * @param m_sid
	 * @param m_getUsers
	 * @param m_bulletinServer
	 */
	public InitVEAPServerData(int m_lenMS, int m_lenVerify, int m_lenSK,
			BigInteger m_g, BigInteger m_q, String m_sid,
			IfcGetUsers m_getUsers, IfcBulletinVEAPServer m_bulletinServer) {
		super();
		this.m_lenMS = m_lenMS;
		this.m_lenVerify = m_lenVerify;
		this.m_lenSK = m_lenSK;
		this.m_g = m_g;
		this.m_q = m_q;
		this.m_sid = m_sid;
		this.m_getUsers = m_getUsers;
		this.m_bulletinServer = m_bulletinServer;
	}
	public IfcBulletinVEAPServer getM_bulletinServer() {
		return m_bulletinServer;
	}
	public void setM_bulletinServer(IfcBulletinVEAPServer m_bulletinServer) {
		this.m_bulletinServer = m_bulletinServer;
	}
	public int getM_lenMS() {
		return m_lenMS;
	}
	public void setM_lenMS(int m_lenMS) {
		this.m_lenMS = m_lenMS;
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
	public String getM_sid() {
		return m_sid;
	}
	public void setM_sid(String m_sid) {
		this.m_sid = m_sid;
	}
	public int getM_lenVerify() {
		return m_lenVerify;
	}
	public void setM_lenVerify(int m_lenVerify) {
		this.m_lenVerify = m_lenVerify;
	}
	public IfcGetUsers getM_getUsers() {
		return m_getUsers;
	}
	public void setM_getUsers(IfcGetUsers m_getUsers) {
		this.m_getUsers = m_getUsers;
	}
	
	
}
