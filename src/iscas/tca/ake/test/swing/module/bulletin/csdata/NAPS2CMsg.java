package iscas.tca.ake.test.swing.module.bulletin.csdata;

import java.io.Serializable;
import java.math.BigInteger;

public class NAPS2CMsg<T> implements Serializable{
	T msg;
	BulletinNAPMsgType msgType;
	public enum BulletinNAPMsgType{
		MSGBulletinTypeHash,
		MSGBulltinTypeConfigSecurity,
		MSGBulltinTypeResponseSecurity;
	}
	public NAPS2CMsg(T msg, BulletinNAPMsgType msgType){
		this.msg = msg;
		this.msgType = msgType;
	}
	//1st msg
	public static class ConfigMsg{
		BigInteger g;
		BigInteger q;
		byte[][] cjs;
		BigInteger X;
		
		public BigInteger getX(){
			return X;
		}
		public BigInteger getG() {
			return g;
		}
		public BigInteger getQ() {
			return q;
		}
		public byte[][] getCjs() {
			return cjs;
		}
		/**
		 * @param g
		 * @param q
		 * @param cjs
		 */
		public ConfigMsg(BigInteger g, BigInteger q, BigInteger X, byte[][] cjs) {
			super();
			this.g = g;
			this.q = q;
			this.cjs = cjs;
			this.X = X;
		}
	
		
	}
//	//2nd msg
//	public static class ResponseMsg{
//		BigInteger Ax;
//	}
	
}
