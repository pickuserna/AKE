package iscas.tca.ake.test.swing.module.bulletin.csdata;

import java.io.Serializable;
import java.math.BigInteger;

public class NAPS2CMsg<T> implements Serializable{
	T msg;
	public static String MSGBulletinTypeHash = "BulletinHashMsgType";
	public static String MSGBulltinTypeConfigSecurity = "BulletinConfigSecurityMsgType";
	public static String MSGBulltinTypeResponseSecurity = "BulletinResponseSecurityMsgType";
	String msgType;
	//HashMsg
	public static class HashMsg{
		
	}
	public NAPS2CMsg(T msg, String msgType){
		this.msg = msg;
		this.msgType = msgType;
	}
	//1st msg
	public static class ConfigMsg{
		BigInteger g;
		BigInteger q;
		byte[][] cjs;
		
	}
	
	//2nd msg
	public static class ResponseMsg{
		BigInteger Ax;
	}
	
}
