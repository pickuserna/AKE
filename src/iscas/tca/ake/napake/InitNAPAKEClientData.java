package iscas.tca.ake.napake;

import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.demoapp.mvc.module.bulletin.csdata.BulletinNAPClientHashData;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPClient;

import java.math.BigInteger;

/**
 * InitData of the NAPClient
 * @author zn
 * @CreateTime 2014-8-16ÉÏÎç10:58:25
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
public class InitNAPAKEClientData implements IfcInitData{
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
	public InitNAPAKEClientData(String m_pw, String m_groupID, String m_ID,
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
