package iscas.tca.ake.util.rand;
import java.math.BigInteger;

public interface IfcRand {
	
	/**
	 * TODO:<�õ�0-max֮��������>
	 * @param max
	 * @return 0-max�����
	 */
	BigInteger randOfMax(BigInteger max);
	int randOfMax(int max);
	
	
	/**
	 * TODO:<����һ�������n���ַ���>
	 * @param n:�ַ����ĳ���
	 * @param s:�ַ�Դ
	 * @return n������ַ���
	 */
	String randString(int n, String s);
	String randString(int n);
	
	byte[] nextBytes(int len);
}