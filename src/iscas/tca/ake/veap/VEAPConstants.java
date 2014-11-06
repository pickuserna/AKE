package iscas.tca.ake.veap;


/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-9-11上午9:49:31
 */
public class VEAPConstants {
	//Mac 算法 长度：64bytes
	public static String AlgorithmOfMac = "HmacSHA512";
	//Hash算法 长度：32bytes
	public static String AlgorithmOfHash = "SHA-256";
	//AES秘钥长度 ：16bytes
	public static String AlgorithmOfED = "AES";
	
	public static final int LengthOfMac = 64;
	public static final int LengthOfHash = 32;
	
	//LengthOfMS=LengthOfK=LengthOfU -> AES秘钥  ->Hash长度的1/2
	public static final int LengthOfMS = 16;
	public static final int LengthOfK = LengthOfMS;
	//2*LengthOfVerify+LengthOfSK = 64,  LengthOfSK = 64-2*20=24
	public static final int LengthOfVerify = 20;
	public static final int LengthOfSK = LengthOfMac - 2*LengthOfVerify;
	//公告板测试模式
}
