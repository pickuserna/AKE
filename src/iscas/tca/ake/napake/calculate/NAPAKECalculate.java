package iscas.tca.ake.napake.calculate;

import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.connectStrings.ConnectStrsTask;
import iscas.tca.ake.util.hashs.H0;
import iscas.tca.ake.util.hashs.H256;
import iscas.tca.ake.util.hashs.IfcHash;

import java.math.BigInteger;

/**
 * Class-Name£º<NAPCalculate>
 * @author zn
 * @CreateTime 2014-8-16ÏÂÎç2:58:05
 */
/*
 * Copyright (c) 20014-2041 Institute Of Software Chinese Academy Of Sciences
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @Organization: http://tca.iscas.ac.cn/
 * @author: Nan Zhou
 * @Aknowledge: Tutor Liwu Zhang , Alumnus Yan Zhang, Zhigang Gao
 * @Email: changchangge123@qq.com
 */
public class NAPAKECalculate implements IfcNapAKECalculate{

	
	@Override
	public BigInteger getAself(String id, BigInteger[] as, IfcBulletinNAPClient bn) {
		// TODO Auto-generated method stub
		int index = bn.index(id);
		return as[index];
	}

	//calculate PW
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

	//calculate the Auths
	public  byte[] getAuths(String sTrans, BigInteger biZ, BigInteger biK)
	{
		H0 h = new H0();
		sTrans = "1"+sTrans;
		return getHashedValue(sTrans,biZ,biK,h);
	}
	//calculate sessionKey
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
	//get the A from as corresponding to the id in ids
	public  BigInteger getAself(String id, String[] ids,BigInteger[] as)
	{
		int order = getOrder(ids, id);
		if(order!=-1)//-1 indicate that does not exist such id
			return as[order];
		return null;
	}
	//find the index of val in a
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
		NAPAKECalculate nc = new NAPAKECalculate();
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
