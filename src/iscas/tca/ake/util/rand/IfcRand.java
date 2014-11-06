package iscas.tca.ake.util.rand;
import java.math.BigInteger;

public interface IfcRand {
	
	/**
	 * TODO:<得到0-max之间的随机数>
	 * @param max
	 * @return 0-max随机数
	 */
	BigInteger randOfMax(BigInteger max);
	int randOfMax(int max);
	
	
	/**
	 * TODO:<生成一个随机的n长字符串>
	 * @param n:字符串的长度
	 * @param s:字符源
	 * @return n长随机字符串
	 */
	String randString(int n, String s);
	String randString(int n);
	
	byte[] nextBytes(int len);
}