package iscas.tca.ake.veap.edcoder;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.veap.VEAPConstants;

import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

/**
 * @author zn
 * @CreateTime 2014-9-11ÉÏÎç9:58:56
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
public class EDCoder implements IfcEDCoder {

	public Key bytes2Key(byte[] key){
		SecretKey sk = new SecretKeySpec(key, VEAPConstants.AlgorithmOfED);
		SecretKeySpec sk2 = new SecretKeySpec(sk.getEncoded(), sk.getAlgorithm());
		
		return sk2;
	}
	@Override
	public byte[] encrypto(byte[] msg, byte[] key){
		Key k = bytes2Key(key);
		try{
			Cipher cipher = Cipher.getInstance(k.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, k);
			return cipher.doFinal(msg);
		}catch(Exception e){
			System.out.println("encrypto Exception!");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] decrypto(byte[] msg, byte[] key) {
		// TODO Auto-generated method stub
		try{
			Key k = bytes2Key(key);
			Cipher cipher = Cipher.getInstance(k.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, k);
			return cipher.doFinal(msg);
		}catch(Exception e){
			System.out.println("decrypto Exception!");
			e.printStackTrace();
		}
		return null;
	}

	
	
	//------------------------------Mac-------------------------------//
	@Override
	public byte[] getMac(byte[] msg, byte[] key) {
		SecretKey skMac = new SecretKeySpec(msg, VEAPConstants.AlgorithmOfMac);
		try{
			Mac mac = Mac.getInstance(skMac.getAlgorithm());
			mac.init(skMac);
			return mac.doFinal(msg);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}
	@Test
	public void testED(){
		String msg = "hello";
		byte[] key = new byte[VEAPConstants.LengthOfMS];
		EDCoder ed = new EDCoder();
		byte[] c = ed.encrypto(msg.getBytes(), key);
		byte[] plain = ed.decrypto(c, key);
		System.out.println("plain:"+Assist.bytesToString(plain));
	}
	@Test
	public void testMac(){
		Random rnd = new Random();
		byte[] data = new byte[40];
		byte[] key = new byte[40];
		rnd.nextBytes(data);
		rnd.nextBytes(key);	
	}

}
