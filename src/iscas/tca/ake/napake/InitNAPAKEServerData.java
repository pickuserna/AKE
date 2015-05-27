package iscas.tca.ake.napake;

import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPServerHash;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinVEAPServer;
import iscas.tca.ake.veap.IfcGetUsers;

import java.math.BigInteger;

/**
 * @author zn
 * @CreateTime 2014-8-16ÉÏÎç11:02:54
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
public class InitNAPAKEServerData implements IfcInitData{
	BigInteger m_q;//q is the finite field containing q elements
	BigInteger m_g; //g is a group element
	
	IfcGetUsers m_getUsers;//you can get all the Users of a given group(denoted by groupID) by m_getUsers
	String m_S;//the name of the server 
	public IfcBulletinNAPServerHash m_bulletinNAPServer;//NAP Server Bulletin module
	/**
	 * @param m_q
	 * @param m_g
	 * @param m_S
	 * @param m_getUsers
	 */
	public InitNAPAKEServerData(BigInteger m_q, BigInteger m_g, String m_S,
			IfcGetUsers m_getUsers, IfcBulletinNAPServerHash bns) {
		super();
		this.m_q = m_q;
		this.m_g = m_g;
		this.m_S = m_S;
		this.m_getUsers = m_getUsers;
		this.m_bulletinNAPServer  = bns;
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

	public IfcGetUsers getM_getUsers() {
		return m_getUsers;
	}

	public void setM_NapServerUser(IfcGetUsers m_NapServerUser) {
		this.m_getUsers = m_NapServerUser;
	}
	
}
