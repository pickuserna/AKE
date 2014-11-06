package iscas.tca.ake.napake.calculate;

import iscas.tca.ake.util.connectStrings.ConnectStrsTask;

import java.math.BigInteger;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-21下午6:33:19
 */
public interface IfcNapCalculate {
	  BigInteger getAself(String[] ids, BigInteger[] as, String id) ;
	  BigInteger getPW(String id, String pw, BigInteger q) ;
	  //这个是需要删除的
	  //String	getTrans(String[] ids, String sid, BigInteger[] as, BigInteger xStar, BigInteger B, BigInteger Y) ;
	  //这个是新的getTrans()
	  String 	getTrans(String connectedIds, String sid, String connectedAs, BigInteger xStar, BigInteger B, BigInteger Y);
	  /**
	 * TODO:<多线程方式执行字符串连接任务>
	 * @param ss 要连接的字符串
	 * @return @see ConnectStrTask
	 */
	  ConnectStrsTask		exeStrsCntTask(String[] ss);
	  ConnectStrsTask		exeStrsCntTask(BigInteger[] bs);
	  byte[]	getAuths(String sTrans, BigInteger biZ, BigInteger biK) ;
	  byte[]	getsk(String sTrans, BigInteger biZ, BigInteger biK) ;	           
	  byte[]	getAuthc(String sTrans, BigInteger biZ, BigInteger biK);
}
