package iscas.tca.ake.util.rand;
import java.math.BigInteger;

public interface IfcRand {
	
	/**
	 * TODO:<get random number between 0 and max>
	 * @param max
	 * @return random from range of 0-max
	 */
	BigInteger randOfMax(BigInteger max);
	int randOfMax(int max);
	
	
	/**
	 * TODO:<generate a random String of length n with the character base collection of s>
	 * @param n:the length of the String to be generated
	 * @param s: the base String
	 * @return a random String of n-length
	 */
	String randString(int n, String s);
	String randString(int n);
	
	byte[] nextBytes(int len);
}