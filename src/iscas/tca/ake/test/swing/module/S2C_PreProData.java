package iscas.tca.ake.test.swing.module;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * des��<S send the PrepareInfo to Client at the prepare stage>
 * @author zn
 * @CreateTime 2014-10-9����1:36:16
 */
public class S2C_PreProData implements Serializable{
	public int bits;
	public BigInteger q;
	public BigInteger g;
	
	public String groupID;
	public String proType;
	public int idNum;
	public String url;
	/**
	 * @param bits
	 * @param groupID
	 * @param proType
	 * @param idNum
	 */
	public S2C_PreProData(int bits, String groupID, String proType, int idNum, String url) {
		super();
		this.bits = bits;
		this.groupID = groupID;
		this.proType = proType;
		this.idNum = idNum;
		this.url = url;
	}
	public String toString(){
		return this.proType+this.bits+this.idNum+this.url;
	}
	
}
