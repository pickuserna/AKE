package iscas.tca.ake.util.hashs;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.rand.Rand;

import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

/** 
 *文件名：[文件名]
 *描述：<SHA算法>
 * @author 作者姓名:zn   
 * @version 创建时间：2014-7-31 下午1:42:56
 *修改人：
 *修改时间：YYYY—MM-DD
 *修改内容：<修改内容>
 *类说明 :使用SHA算法
 */

/**
 * 描述：<>
 * @author zn
 * @see [相关类]
 * @version 2014-7-31下午1:42:56
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
