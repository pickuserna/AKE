package iscas.tca.ake.veap.calculate;


import java.math.BigInteger;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-9-11上午9:44:27
 */
public interface IfcVEAPCalculate {

	
	/**
	 * TODO:<计算<U, K>>
	 * @param U
	 * @param X
	 * @param pvd
	 * @param pvdx
	 * @return [][0]:U, [][1]:K
	 */
	public byte[] getU_K(String id, 
			BigInteger X, 
			BigInteger pvd,
			BigInteger pvdx);
	public byte[] getMK(byte[] MS, BigInteger Xb, BigInteger Yb);
	public byte[] getGD(String sU_Cs, Long t, Long t0);
	
	/**
	 * TODO:<将u_cs 连接成字符串>
	 * @param u_cs
	 * @return 
	 */
	public String connectUcs(U_C[] u_cs);
	/**
	 * TODO:<加解密>
	 * @param msg
	 * @param key
	 * @return 加解密结果
	 */
	public byte[] encrypMsg(byte[] msg, byte[] key);
	public byte[] decryptC(byte[] c, byte[] key);
	public byte[] getMac(byte[] mk, String groupID, String sid, byte[] GD,
			String sABXY);
	public String getABXY(BigInteger A, BigInteger B, BigInteger Ax,
			BigInteger X, BigInteger Y);
	
	/**
	 * TODO:<返回包含U的U_C对象>
	 * @return 
	 */
	public U_C findByU(U_C[] ucs,byte[] U);
}
