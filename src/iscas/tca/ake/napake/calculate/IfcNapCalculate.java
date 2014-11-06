package iscas.tca.ake.napake.calculate;

import iscas.tca.ake.util.connectStrings.ConnectStrsTask;

import java.math.BigInteger;

/**
 * ������<>
 * @author zn
 * @CreateTime 2014-8-21����6:33:19
 */
public interface IfcNapCalculate {
	  BigInteger getAself(String[] ids, BigInteger[] as, String id) ;
	  BigInteger getPW(String id, String pw, BigInteger q) ;
	  //�������Ҫɾ����
	  //String	getTrans(String[] ids, String sid, BigInteger[] as, BigInteger xStar, BigInteger B, BigInteger Y) ;
	  //������µ�getTrans()
	  String 	getTrans(String connectedIds, String sid, String connectedAs, BigInteger xStar, BigInteger B, BigInteger Y);
	  /**
	 * TODO:<���̷߳�ʽִ���ַ�����������>
	 * @param ss Ҫ���ӵ��ַ���
	 * @return @see ConnectStrTask
	 */
	  ConnectStrsTask		exeStrsCntTask(String[] ss);
	  ConnectStrsTask		exeStrsCntTask(BigInteger[] bs);
	  byte[]	getAuths(String sTrans, BigInteger biZ, BigInteger biK) ;
	  byte[]	getsk(String sTrans, BigInteger biZ, BigInteger biK) ;	           
	  byte[]	getAuthc(String sTrans, BigInteger biZ, BigInteger biK);
}
