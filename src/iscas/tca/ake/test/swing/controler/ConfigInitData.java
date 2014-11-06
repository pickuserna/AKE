package iscas.tca.ake.test.swing.controler;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * √Ë ˆ£∫<need to delete>
 * @author zn
 * @CreateTime 2014-10-11…œŒÁ9:39:25
 */
public class ConfigInitData implements Serializable{
	public final BigInteger g;
	public final BigInteger q;
	public final String proType;
	public String[] groupUserIDs;
	/**
	 * @param g
	 * @param q
	 * @param proType
	 */
	public ConfigInitData(BigInteger g, BigInteger q, String proType) {
		super();
		this.g = g;
		this.q = q;
		this.proType = proType;
	}
	public String toString(){
		return this.proType+this.q.bitLength();
	}
	public void setGroupUserIDs(String []groupUserIDs){
		this.groupUserIDs = groupUserIDs;
	}
}
