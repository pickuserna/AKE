package iscas.tca.ake.veap.bulletin;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.veap.calculate.U_C;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 描述：<客户端取Bulletin数据>
 * @author zn
 * @CreateTime 2014-9-11上午10:09:07
 */
public abstract class IfcBulletinVEAPClient implements Serializable{
	protected BigInteger X;
	protected	U_C[] u_cs;
	protected Long publishTime;
	protected Long timeOut;
	protected	String groupID;
	public String getGroupID() {
		return groupID;
	}

	/**
	 * 
	 */
	public IfcBulletinVEAPClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param x
	 * @param u_cs
	 * @param t
	 * @param t0
	 * @param groupID
	 */
	public IfcBulletinVEAPClient(BigInteger x, U_C[] u_cs, Long t, Long t0,
			String groupID) {
		super();
		X = x;
		this.u_cs = u_cs;
		this.publishTime = t;
		this.timeOut = t0;
		this.groupID = groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	/**
	 * TODO:<从服务器的公告板上取数据>
	 * @return 如果取数据超时抛出异常
	 */
	public abstract IfcBulletinVEAPClient fetchData(String groupID) throws Exception;
	
	//Getters 
	public BigInteger getX() {
		return X;
	}
	public U_C[] getU_Cs() {
		return u_cs;
	}
	public void setU_Cs(U_C[] ucs){
		this.u_cs = ucs;
	}
	public Long getT() {
		return publishTime;
	}
	public Long getT0() {
		return timeOut;
	}

	
	public String toString(){
		return Assist.kvFormat("groupID", this.groupID)+Assist.kvFormat("timePublished", this.publishTime.toString());
		
	}
	public void setX(BigInteger x) {
		X = x;
	}

	public void setT(Long t) {
		this.publishTime = t;
	}

	public void setT0(Long t0) {
		this.timeOut = t0;
	}

	
}
