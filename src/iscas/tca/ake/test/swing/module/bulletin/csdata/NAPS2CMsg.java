package iscas.tca.ake.test.swing.module.bulletin.csdata;

import java.math.BigInteger;

public class NAPS2CMsg<T> {
	T msg;
	//1st msg
	public NAPS2CMsg(T msg){
		this.msg = msg;
	}
	//2nd msg
	public static class ConfigMsg{
		BigInteger g;
		BigInteger q;
		byte[][] cjs;
		
	}
	
	public static class ResponseMsg{
		BigInteger Ax;
	}
	
}
