package iscas.tca.ake.util.rand;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;



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
public class Rand implements IfcRand {

	@Override
	public BigInteger randOfMax(BigInteger max) {
		// TODO Auto-generated method stub
		//不是真随机，因为产生机制!!
		int bitlen = max.bitLength();
		
		try{
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");	
			byte[] bs = new byte[bitlen];
			random.nextBytes(bs);
			BigInteger bir = new BigInteger(bs);
			return bir.mod(max);
		}
		catch(NoSuchAlgorithmException e)
		{
			System.out.println(e);
			return null;
		}
	
	}

	@Override
	public int randOfMax(int max) {
		// TODO Auto-generated method stub
		Random r = new Random();
		
		int i = r.nextInt(max);
		while(0==i){//the restriction of the Random Number
			i = r.nextInt(max);
		}
		return i;
	}

	public int randMinMax(int min, int max)
	{
		return randOfMax(max-min)+min;
	}
	@Override
	public String randString(int n, String base) {
		// TODO Auto-generated method stub
		int bLength = base.length();
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int index;
		for(int i=0; i<n; i++)
		{
			index = r.nextInt(bLength);
			sb.append(base.charAt(index));
		}
		return sb.toString();
	}

	@Override
	public String randString(int n) {
		// TODO Auto-generated method stub
		String base="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return randString(n, base);
	}

	public static void main(String[] args)
	{
		Random rd = new Random();
		Rand rand = new Rand();
		Set<String> set = new HashSet();
		for(int i=0; i<100;i++)
		{
			String s= rand.randString(12);		
			if(set.contains(s))
				System.out.println("===========================================================");
			set.add(s);
			System.out.println(s);
		}
		System.out.println("over");
	}
	public static String test_randString()
	{
		Rand r = new Rand();
		System.out.println(r.randString(6,"djoxijeol.cjojent"));
		return r.randString(6,"djoxijeol.cjojent");
	}
	public static BigInteger test_randOfMax()
	{
		Rand r = new Rand();
		BigInteger q = new BigInteger("10000000000000000000000");
		BigInteger b = r.randOfMax(q);
		System.out.println("randOfMax:"+q);
		System.out.println("randOfrst:"+b);
		return b;
	}
	@Ignore("ok")
	@Test
	public void testRandInt()
	{
		Random rd = new Random();
		for(int i=0; i<100;i++)
		{
			System.out.println(rd.nextInt());
		}
	}
	@Test
	public void testRandString()
	{
		Random rd = new Random();
		for(int i=0; i<100;i++)
		{
			System.out.println(randString(12));
		}
	}

	@Override
	public byte[] nextBytes(int len) {
		// TODO Auto-generated method stub
		byte[] bs  = new byte[len];
		try{
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");	
			random.nextBytes(bs);
		}catch(Exception e){
			e.printStackTrace();
		}
		return bs;
	}
}
