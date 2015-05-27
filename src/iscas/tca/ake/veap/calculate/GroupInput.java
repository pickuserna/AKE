package iscas.tca.ake.veap.calculate;

import iscas.tca.ake.veap.User;

import java.math.BigInteger;

/**
 * <inputData when calculating the data of a group >
 * @author zn
 * @CreateTime 2014-9-11ÉÏÎç9:46:38
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
public class GroupInput {
	String m_groupID;
	User[] m_users;
	int m_lenMS;//byte length of the MS
	BigInteger m_g;
	BigInteger m_q;
	Long timeOut;
	
	
	/**
	 * @param m_groupID
	 * @param m_users
	 * @param m_lenMS
	 * @param m_g
	 * @param m_q
	 */
	public GroupInput(String m_groupID, User[] m_users, int m_lenMS,
			BigInteger m_g, BigInteger m_q, Long timeOut) {
		super();
		this.m_groupID = m_groupID;
		this.m_users = m_users;
		this.m_lenMS = m_lenMS;
		this.m_g = m_g;
		this.m_q = m_q;
		this.timeOut = timeOut;
	}


	public String getM_groupID() {
		return m_groupID;
	}


	public User[] getM_users() {
		return m_users;
	}


	public int getM_lenMS() {
		return m_lenMS;
	}


	public BigInteger getM_g() {
		return m_g;
	}


	public BigInteger getM_q() {
		return m_q;
	}


	public Long getTimeOut() {
		return timeOut;
	}
	
	
}
