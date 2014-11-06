package iscas.tca.ake.veap.calculate;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.hashs.Hash_VEAP;
import iscas.tca.ake.util.hashs.IfcHash;
import iscas.tca.ake.veap.edcoder.EDCoder;
import iscas.tca.ake.veap.edcoder.IfcEDCoder;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-9-11ÏÂÎç12:13:20
 */
public class VEAPCalculate implements IfcVEAPCalculate {

	

	@Override
	public byte[] getU_K(String U, BigInteger X, BigInteger pvd, BigInteger pvdx) {
		// TODO Auto-generated method stub
		String[] ss = {U, Assist.ge2osp(X), Assist.ge2osp(pvd), Assist.ge2osp(pvdx)};
		String str = Assist.connectStrings(ss).toString();
		IfcHash h = new Hash_VEAP();
		return h.process(str);
	}

	@Override
	public byte[] getMK(byte[] MS, BigInteger Xb, BigInteger Yb) {
		// TODO Auto-generated method stub
		String strBytes = Assist.bytesToString(MS);
		String[] ss = { strBytes, Assist.ge2osp(Xb), Assist.ge2osp(Yb)};
		String str = Assist.connectStrings(ss).toString();
		IfcHash h = new Hash_VEAP();
		return (h.process(str));
	}

	@Override
	public byte[] getGD(String sU_Cs, Long t, Long t0) {
		// TODO Auto-generated method stub
		String[] ss= {sU_Cs, Long.toString(t), Long.toString(t0)};
		String str = Assist.connectStrings(ss).toString();
		
		return new Hash_VEAP().process(str);
	}
	@Override
	public String connectUcs(U_C[] u_cs) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<u_cs.length; i++)
		{
			sb.append(u_cs[i].u_c2String());
		}
		return sb.toString();
	}

	@Override
	public byte[] encrypMsg(byte[] msg, byte[] key) {
		// TODO Auto-generated method stub
		IfcEDCoder edcoder = new EDCoder();
		return edcoder.encrypto(msg, key);
	}

	@Override
	public byte[] decryptC(byte[] c, byte[] key) {
		// TODO Auto-generated method stub
		IfcEDCoder edcoder = new EDCoder();
		
		return edcoder.decrypto(c, key);
	}

	@Override
	public byte[] getMac(byte[] mk, String groupID, String sid, byte[] GD,
			String sABXY) {
		String sGD = Assist.bytesToString(GD);
		String[] ss = { groupID, sid, sGD, sABXY };
		String str = Assist.connectStrings(ss).toString();
		
		return new EDCoder().getMac(str.getBytes(), mk);
	}

	@Override
	public String getABXY(BigInteger A, BigInteger B, BigInteger Ax,
			BigInteger X, BigInteger Y) {
		// TODO Auto-generated method stub
		BigInteger[] bs = { A, B, Ax, X, Y };
		return Assist.connectStrings(bs).toString();
	}
	@Test
	public void testED()
	{
		byte[] key = new byte[16];
		Random rnd = new Random();
		rnd.nextBytes(key);
		String msg = "hello AES";
		byte[] c = encrypMsg(msg.getBytes(), key);
		String plain = Assist.bytesToString(decryptC(c, key));
		System.out.println(plain);
		Assert.assertEquals(msg, plain);
	}

	@Override
	public U_C findByU(U_C[] ucs, byte[] U) {
		// TODO Auto-generated method stub
		for(int i=0; i<ucs.length; i++)
		{
			if(Arrays.equals(ucs[i].getM_u(), U)){
				return ucs[i];
			}
		}
		return null;
	}
}
