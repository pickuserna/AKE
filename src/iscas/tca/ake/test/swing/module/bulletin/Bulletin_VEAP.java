package iscas.tca.ake.test.swing.module.bulletin;

import iscas.tca.ake.veap.bulletin.IfcBulletinClient;
import iscas.tca.ake.veap.calculate.U_C;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Time;

/**
 * description£º<server send the S2CBulletinData to client >
 * @author zn
 * @CreateTime 2014-9-12ÉÏÎç9:40:04
 */
public class Bulletin_Veap extends IfcBulletinClient implements Serializable{	
	
	public Bulletin_Veap() {
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
	
	
	public Bulletin_Veap(BigInteger x, 
			U_C[] u_cs, 
			Long t, 
			Long t0,
			String groupID){
		super(x, u_cs, t, t0, groupID);
	}
	@Override
	public IfcBulletinClient fetchData(String groupID) throws Exception {
		return this;
	}
	public String toString(){
		return this.groupID+this.timeOut+this.publishTime;
	}
}
