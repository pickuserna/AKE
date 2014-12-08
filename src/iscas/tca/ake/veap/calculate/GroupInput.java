package iscas.tca.ake.veap.calculate;

import iscas.tca.ake.veap.User;

import java.math.BigInteger;

/**
 * <inputData when calculating the data of a group >
 * @author zn
 * @CreateTime 2014-9-11ÉÏÎç9:46:38
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
