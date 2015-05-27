package iscas.tca.ake.veap;

import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinVEAPClient;

import java.math.BigInteger;

/**
 * @TODO£º<initial data for the veap(SKI) protocol>
 * @author zn
 * @CreateTime 2014-9-12ÉÏÎç9:13:06
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
	String m_groupID;//ÊôÓÚÄÄ¸öGroup¡¢
	
	IfcBulletinVEAPClient m_bulletinClient;
	
	
	
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
			IfcBulletinVEAPClient m_bulletinClient) {
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
	public IfcBulletinVEAPClient getM_bulletinClient() {
		return m_bulletinClient;
	}
	public void setM_bulletinClient(IfcBulletinVEAPClient m_bulletinClient) {
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
