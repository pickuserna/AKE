package iscas.tca.ake.napake.calculate;

import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.util.connectStrings.ConnectStrsTask;

import java.math.BigInteger;

/**
 * @author zn
 * @CreateTime 2014-8-21ÏÂÎç6:33:19
 */
public interface IfcNapAKECalculate {
	  BigInteger getAself(String id, BigInteger[] as, IfcBulletinNAPClient bn);
	  BigInteger getAself(String id, String[] ids, BigInteger[] as) ;
	  BigInteger getPW(String id, String pw, BigInteger q) ;
	  String 	getTrans(String connectedIds, String sid, String connectedAs, BigInteger xStar, BigInteger B, BigInteger Y);
	  /**
	 * TODO:<Connect Strings as a multitask>
	 * @param ss Strings to be connected
	 * @return @see ConnectStrTask
	 */
	  ConnectStrsTask		exeStrsCntTask(String[] ss);
	  ConnectStrsTask		exeStrsCntTask(BigInteger[] bs);
	  byte[]	getAuths(String sTrans, BigInteger biZ, BigInteger biK) ;
	  byte[]	getsk(String sTrans, BigInteger biZ, BigInteger biK) ;	           
	  byte[]	getAuthc(String sTrans, BigInteger biZ, BigInteger biK);
}
