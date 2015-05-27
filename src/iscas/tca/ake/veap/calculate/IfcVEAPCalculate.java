package iscas.tca.ake.veap.calculate;


import java.math.BigInteger;

/**
 * @author zn
 * @CreateTime 2014-9-11ÉÏÎç9:44:27
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
public interface IfcVEAPCalculate {

	
	/**
	 * TODO:<calculate <U, K>>
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
	 * TODO:<get the u_cs connected>
	 * @param u_cs
	 * @return 
	 */
	public String connectUcs(U_C[] u_cs);
	/**
	 * TODO:<en_decode>
	 * @param msg
	 * @param key
	 * @return en_decode
	 */
	public byte[] encrypMsg(byte[] msg, byte[] key);
	public byte[] decryptC(byte[] c, byte[] key);
	public byte[] getMac(byte[] mk, String groupID, String sid, byte[] GD,
			String sABXY);
	public String getABXY(BigInteger A, BigInteger B, BigInteger Ax,
			BigInteger X, BigInteger Y);
	
	/**
	 * TODO:<return U_C corresponding to U>
	 * @return 
	 */
	public U_C findByU(U_C[] ucs,byte[] U);
}
