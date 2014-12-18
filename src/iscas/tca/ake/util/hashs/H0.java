package iscas.tca.ake.util.hashs;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.rand.Rand;

import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @TODO <algorithm: SHA-256> 
 * @author zn
 * @version 2014-7-31ÏÂÎç1:42:56
 */
public class H0 implements IfcHash {

	@Override
	public byte[] process(String s) {
		// TODO Auto-generated method stub
	
		try{
			byte[] bs = s.getBytes("UTF8");
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update(bs);

			return (sha.digest());
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	public static void main(String[] args)
	{

	}
	@Test
	public void testH0()
	{
		H0 h = new H0();
		Rand rd = new Rand();
		Set<byte[]> set = new HashSet<byte[]>();
		byte[] resultData = null;
		for(int i=0; i<200;i++)
		{
			String text = rd.randString(20);
			resultData = h.process(text);
		
			TestCase.assertTrue(!set.contains(resultData));
			set.add(resultData);
			System.out.println(text);
			System.out.println(Assist.bytesToHexString(resultData));
		}
		//System.out.println(Assist.bytesToHexString(resultData));
	}
}
