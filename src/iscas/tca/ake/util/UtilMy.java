package iscas.tca.ake.util;

import iscas.tca.ake.demoapp.mvc.module.tools.XMLTools;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


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
public class UtilMy {
	public static final String ROOT_CONFIG = "config";
	 /** 
     * base64 encode
     * @param bstr 
     * @return String 
     */  
	public static final String CODE = "utf-8";
    public static String base64_En(String data){  
    	byte[] bys = Charset.forName(CODE).encode(data).array();
    	return new sun.misc.BASE64Encoder().encode(bys);  
    }  
  
    /** 
     * base64 decode 
     * @param str 
     * @return string 
     */  
    public static String base64_De(String str){  
    byte[] bt = null;  
    try {  
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
        bt = decoder.decodeBuffer( str );  
    } catch (IOException e) {  
        e.printStackTrace();  
    }  
  
      return  Charset.forName(CODE).decode(ByteBuffer.wrap(bt)).toString(); 
    } 
	
	public static String md5(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data.getBytes());
			byte[] bits = md.digest();
			return new HexBinaryAdapter().marshal(bits);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String sha1(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(data.getBytes());
		byte[] bits = md.digest();
		return new HexBinaryAdapter().marshal(bits);
	}
	public static BigInteger genG(int bit, int seednum,BigInteger q){
		Random rnd = new Random();
		byte[] bits  = new byte[bit];
		rnd.nextBytes(bits);
		return new BigInteger(bits).abs().mod(q);
	}
	public static BigInteger genQ(int bit, int seednum){
		SecureRandom rnd = new SecureRandom();
		byte[] seed = rnd.generateSeed(seednum);
		return BigInteger.probablePrime(bit, rnd);
	}
	
	public static void main(String[] args) throws Exception {
//		String data = "abc";
//		// MD5
//		System.out.println("MD5 : " + md5(data));
//		// SHA1
//		System.out.println("SHA1 : " + sha1(data));
//		//base64
//		String cipher = base64_En(data);
//		System.out.println("Base64 En: "+cipher);
//		System.out.println("Base64 De: "+new String(base64_De(cipher)));
//		BigInteger q = genQ(1024, 10);
//		System.out.println("Q : " + q);
//		BigInteger g = genG(1024, 10, q);
//		System.out.println("G : " + g.toString());
		new UtilMy().
		testPrint();
	}
	
	//record the settins to config file
	public static void recordConfigToFile(String filePath, String[] tagPath, String value)throws Exception{
		XMLTools xml = new XMLTools();
		Document doc = xml.openXML(filePath, ROOT_CONFIG).getDocument();
		
		xml.setNode(tagPath, value);
		xml.writeToFile(filePath);
	}
	static String ID = "id";
	static String PASSWORD = "password";
	static String iSPASSED = "isPassed";
	static String ROOT  = "users";
	
//	public static void recordUsersToFile(String id, String password, boolean isPassed, boolean isPlain, String filePath)throws Exception{
//		String pID  = id;
//		String pPassword = password;
//		if(!isPlain){
//			pID = UtilMy.md5(id);
//			pPassword = UtilMy.md5(password);
//		}
//		XMLTools xml = new XMLTools();
//		Document doc = xml.openXML(filePath, ROOT).getDocument();
//		Node client = xml.appendElement(doc.getDocumentElement(), "client");
//		xml.appendTextElement(client, ID, pID);
//		xml.appendTextElement(client, PASSWORD,pPassword);
//		xml.appendTextElement(client, iSPASSED, isPassed+"");
//		xml.writeToFile("E:\\test\\xml\\users.xml");
//	}
	public static void print(Object... obs){
		for(Object o:obs){
//			if(o.getClass().isArray()){
//				print(o);
//			}
//			else{
				System.out.println(o);
//			}
		}
	}
	@Test
	public void testPrint(){
		int[] arr1 = {1,2 , 3};
	//	int[][] arr2 = {{4,5},{6,7,8}};
		print(arr1);
	}
	public static void printnb(Object... obs){
		for(Object o:obs){
			System.out.print(o.toString());
		}
	}
	
	public static void registGroup(String filePath, String groupID, String password)throws Exception{
		XMLTools xmlTools = new XMLTools(filePath, "groups");
		Node node = xmlTools.appendElement(xmlTools.getDocument().getDocumentElement(), "group");
		xmlTools.appendTextElement(node, "groupID", groupID);//(node, tagName);
		xmlTools.appendTextElement(node, "password", password);
		xmlTools.writeToFile();
	}
	
	/**
	 * A common method for all enums since they can't have another base class
	 * @param <T> Enum type
	 * @param c enum type. All enums must be all caps.
	 * @param string case insensitive
	 * @return corresponding enum, or null
	 */
	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string)
	{
	    if( c != null && string != null )
	    {
	        try
	        {
	            return Enum.valueOf(c, string.trim());
	        }
	        catch(IllegalArgumentException ex)
	        {
	        }
	    }
	    return null;
	}
}
