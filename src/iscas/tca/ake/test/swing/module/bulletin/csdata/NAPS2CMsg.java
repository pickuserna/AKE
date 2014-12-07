package iscas.tca.ake.test.swing.module.bulletin.csdata;

import iscas.tca.ake.test.swing.module.EnumTags;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPServerSecurity;
import iscas.tca.ake.util.Assist;

import java.io.Serializable;
import java.math.BigInteger;

public class NAPS2CMsg<T> implements Serializable{
	T msg;
	BulletinNAPMsgType msgType;
	public enum BulletinNAPMsgType implements Serializable{
		MSGBulletinTypeHash,
		MSGBulltinTypeConfigSecurity,
		MSGBulltinTypeResponseSecurity;
	}
	public NAPS2CMsg(T msg, BulletinNAPMsgType msgType){
		this.msg = msg;
		this.msgType = msgType;
	}
	//1st msg
	public static class ConfigMsg implements Serializable, IfcBulletinNAPClient {
		@Override
		public int index(String id) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
		@Override
		public String getMsgType() {
			// TODO Auto-generated method stub
			return EnumTags.NapBulletinSecurityMode;
		}
		@Override
		public String getConnectedPseus() {
			// TODO Auto-generated method stub
			return Assist.Connectbytes(this.cjs).toString();
		}
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
//	public static class ResponseMsg implements Serializable {
//		BigInteger Ax;
//	}
	
}
