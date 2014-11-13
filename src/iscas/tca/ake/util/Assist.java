package iscas.tca.ake.util;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.junit.Test;

/**
 * 描述：<辅助运算和操作>
 * 类名：<Assist>
 * @author zn
 * @CreateTime 2014-8-16上午10:27:34
 */

public class Assist{
	
	/**
	 * TODO: 连接n个字符串
	 * name: connectStrings
	 * @param sn 要连接的字符串数组
	 * @return 连接了的字符串
	 */
	public static StringBuilder connectStrings(BigInteger... bs){
		if(bs==null){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<bs.length; i++){
			if(bs[i]==null)
				System.out.println("null pointer"+i);
			sb.append(bs[i].toString());
		}
		return sb;
	}
	public static StringBuilder connectStrings(String... ss) {
		// TODO Auto-generated method stub
		if(ss==null)
			return null;
		int totalCapacity =  0;
		StringBuilder sb = new StringBuilder();
		
		for(String s :ss){
			
			totalCapacity += s.length();
		}
		//一次性申请内存空间	
		sb.ensureCapacity(totalCapacity);	
		
		for(int i=0; i<ss.length; i++){
			sb.append(ss[i]);
		}
		return sb;
	}
	public static void main(String[] args){
		String[] ss={"1", "2","3"};
		System.out.println(connectStrings(ss));
	}
	/**
	 * TODO:<字节数组转成字符串>
	 * @param bs 要转的字节数组
	 * @return 
	 */
	public static String bytesToString(byte[] bs)
	{
		String s = new String(bs);
		return s;
	}
	//模运算
	public static BigInteger modPow(BigInteger g, BigInteger exponent, BigInteger m)
	{
			BigInteger result = g.modPow(exponent, m);
//		Constants.recordTime.hitIt("modPow()");
		return result;
	}
	public static String kvFormat(String key, String value){
		return "    "+key+" := "+value+"\r\n";
	}
	public static <T>String arrayFormatToString(String prefix, String suffix, T[]arr){
		StringBuilder sb = new StringBuilder();
		for(int i =0; i<arr.length; i++){
			sb.append(prefix+(i+" := ")+arr[i].toString()+suffix);
		}
		return sb.toString();
	}
	/**
	 * TODO:<乘法模运算>
	 * @param val1
	 * @param val2
	 * @param m
	 * @return (val1*val2)mod m
	 */
	public static BigInteger modMutiply(BigInteger val1, BigInteger val2, BigInteger m)
	{
		BigInteger val3 = val1.multiply(val2);
		return val3.mod(m);
	}
	/**
	 * TODO:<求逆元>
	 * name: modInverse
	 * @param m 
	 * @return s的逆元 
	 */
	public static BigInteger modInverse(BigInteger val, BigInteger m)
	{
		return val.modInverse(m);
	}
	
	/**
	 * TODO:<将字节数组转成16进制>
	 * @param data
	 * @return 
	 */
	public static String bytesToHexString(byte[] data)
	{
		return new HexBinaryAdapter().marshal(data);
	}
	
	public static String ge2osp(BigInteger b){
		return b.toString();
	}
	
	public static  byte[] getPart(byte[] bs, int start, int length) {
		// TODO Auto-generated method stub
		int lenBs = bs.length;

		if (lenBs - start >= length && length > 0) {
			byte[] part = new byte[length];
			for (int i = 0; i < length; i++) {
				part[i] = bs[start + i];
			}
			return part;
		}
		System.out.println("getPart() Error!!");
		return null;
	}
	/**
	 * TODO:<将sb按照lens指定的长度分成几份，分别放在ba>
	 * @param sb
	 * @param ba
	 * @param lens
	 * @return 
	 */
	public static boolean divideBytes(byte[] sb, byte[][] ba, int[] lens){
		int cursor = 0;
		int count = 0;
		for(int i=0; i<lens.length; i++){
			count+= lens[i];
		}
		//检验合法性
		if(lens.length<=0 ||
				count>sb.length){
			return false;
		}
		for(int i=0; i<lens.length; i++){
			ba[i] = getPart(sb,cursor, lens[i]);
			cursor += lens[i];
		}
		return true;
	}
	@Test 
	public void testDivide(){
		byte[] sb = new byte[20];
		int n = 3;
		sb[7] = 5;
		sb[0] = 65;
		byte[][] ba = new byte[n][];
		int[] lens = {2,6,3};
		divideBytes(sb, ba, lens);
		byte[] b = {2};
		System.out.println(Assist.bytesToHexString(sb));	
		for(int i=0; i<lens.length; i++)
			System.out.println(Assist.bytesToHexString(ba[i]));	
	}
	
	public static BigInteger modDivision(BigInteger dividend ,BigInteger divisor , BigInteger q){
		if(divisor.equals(BigInteger.ZERO))
			return null;
		BigInteger inverse = modInverse(divisor,q);
		return modPow(dividend, inverse, q);
	}
	
	public static <K, V> String traverseMap(Map<K, V> map) {
		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<K, V>> iter = map.entrySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			Map.Entry<K, V> entry = iter.next();
			sb.append("" + Assist.kvFormat(entry.getKey().toString(), entry.getValue().toString()));
		}
		return sb.toString();
	}
}
