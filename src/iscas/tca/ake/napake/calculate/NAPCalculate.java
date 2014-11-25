package iscas.tca.ake.napake.calculate;

import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.connectStrings.ConnectStrsTask;
import iscas.tca.ake.util.hashs.H0;
import iscas.tca.ake.util.hashs.H256;
import iscas.tca.ake.util.hashs.IfcHash;

import java.math.BigInteger;

/**
 * 描述：<>
 * 类名：<NAPCalculate>
 * @author zn
 * @CreateTime 2014-8-16下午2:58:05
 */
public class NAPCalculate implements IfcNapCalculate{

	
	@Override
	public BigInteger getAself(String id, BigInteger[] as, IfcBulletinNAPClient bn) {
		// TODO Auto-generated method stub
		int index = bn.index(id);
		return as[index];
	}

	//计算PW
	public  BigInteger getPW(String id, String pw, BigInteger q)
	{
		H256 h = new H256();
		String[] sn = {id, pw};
		String sJoin = Assist.connectStrings(sn).toString();
		byte[] bResult = h.process(sJoin);
		
		BigInteger bi = new BigInteger(bResult);
		bi = bi.abs();
		//while(bi.mod(q).equals(BigInteger.ZERO))
		return bi.mod(q);
	}
	
	@Override
	public byte[] getAuthc(String sTrans, BigInteger biZ, BigInteger biK) {
		// TODO Auto-generated method stub
		H0 h = new H0();
		sTrans = "2"+sTrans;
		return getHashedValue(sTrans,biZ,biK,h);
	}

	//计算Auths
	public  byte[] getAuths(String sTrans, BigInteger biZ, BigInteger biK)
	{
		H0 h = new H0();
		sTrans = "1"+sTrans;
		return getHashedValue(sTrans,biZ,biK,h);
	}
	//计算sk
	public  byte[] getsk(String sTrans, BigInteger biZ, BigInteger biK)
	{
		H0 h = new H0();
		sTrans = "0"+sTrans;
		return getHashedValue(sTrans, biZ, biK, h);
	}
	private  byte[] getHashedValue(String sTrans, BigInteger biZ, BigInteger biK, IfcHash h)
	{
		String sZ = biZ.toString();
		String sK  = biK.toString();
		
		String[] sn = {sTrans, sZ, sK};
		String sJoin = Assist.connectStrings(sn).toString();
		return h.process(sJoin);
	}
	//客户端获取自己的A
	public  BigInteger getAself(String id, String[] ids,BigInteger[] as)
	{
		int order = getOrder(ids, id);
		if(order!=-1)//-1表示没有找到对相应的ID
			return as[order];
		return null;
	}
	//查找val在数组a中的位置
	private  int getOrder(final String[] a, final String val)
	{
		for(int i=0; i<a.length; i++)
		{
			if(val.equals(a[i])){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * TODO:<>
	 * @param args 
	 */
//test functions
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NAPCalculate nc = new NAPCalculate();
		nc.test_getTrans();
	}
	 String test_getTrans()
	{
		String[] ids = {"idA","idB"};
		String sid = "sID";
		BigInteger[] bs = {new BigInteger("123456789"), new BigInteger("987654321")};
		BigInteger X = new BigInteger("2222");
		BigInteger B = new BigInteger("3333");
		BigInteger Y = new BigInteger("4444");
		ConnectStrsTask cstIDs = this.exeStrsCntTask(ids);
		ConnectStrsTask cstAs = this.exeStrsCntTask(bs);
		String result = getTrans(cstIDs.get().toString(), sid, cstAs.get().toString(), X, B, Y);
		System.out.println(result);
		return null;
	}
	 
	@Override
	public String getTrans(String connectedIds, String sid, String connectedAs,
			BigInteger xStar, BigInteger B, BigInteger Y) {
		// TODO Auto-generated method stub
		String[] ss = {connectedIds, sid, connectedAs, xStar.toString(), B.toString(), Y.toString()};
		return Assist.connectStrings(ss).toString();
	}
	@Override
	public ConnectStrsTask exeStrsCntTask(BigInteger[] bs){
		ConnectStrsTask cst = new ConnectStrsTask(bs);
		new Thread(cst).start();
		return cst;
	}
	@Override
	public ConnectStrsTask exeStrsCntTask(String[] ss) {
		// TODO Auto-generated method stub
		ConnectStrsTask cst = new ConnectStrsTask(ss);
		new Thread(cst).start();
		return cst;
	}

}
