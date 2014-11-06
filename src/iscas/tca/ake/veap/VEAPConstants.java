package iscas.tca.ake.veap;


/**
 * ������<>
 * @author zn
 * @CreateTime 2014-9-11����9:49:31
 */
public class VEAPConstants {
	//Mac �㷨 ���ȣ�64bytes
	public static String AlgorithmOfMac = "HmacSHA512";
	//Hash�㷨 ���ȣ�32bytes
	public static String AlgorithmOfHash = "SHA-256";
	//AES��Կ���� ��16bytes
	public static String AlgorithmOfED = "AES";
	
	public static final int LengthOfMac = 64;
	public static final int LengthOfHash = 32;
	
	//LengthOfMS=LengthOfK=LengthOfU -> AES��Կ  ->Hash���ȵ�1/2
	public static final int LengthOfMS = 16;
	public static final int LengthOfK = LengthOfMS;
	//2*LengthOfVerify+LengthOfSK = 64,  LengthOfSK = 64-2*20=24
	public static final int LengthOfVerify = 20;
	public static final int LengthOfSK = LengthOfMac - 2*LengthOfVerify;
	//��������ģʽ
}
