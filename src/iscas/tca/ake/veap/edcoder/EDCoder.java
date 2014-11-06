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
 * √Ë ˆ£∫<>
 * @author zn
 * @CreateTime 2014-9-11…œŒÁ9:58:56
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
