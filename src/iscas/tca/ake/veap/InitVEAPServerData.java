package iscas.tca.ake.veap;

import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinVEAPServer;

import java.math.BigInteger;

/**
 * @author zn
 * @CreateTime 2014-9-12ÉÏÎç9:13:26
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
public class InitVEAPServerData implements IfcInitData {
	int m_lenMS;
	int m_lenVerify;
	int m_lenSK;
	
	BigInteger m_g;
	BigInteger m_q;
	String m_sid;
	//the tool to get the users
	IfcGetUsers m_getUsers;
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
