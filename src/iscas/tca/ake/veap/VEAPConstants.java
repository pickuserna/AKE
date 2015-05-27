package iscas.tca.ake.veap;


/**
 * @author zn
 * @CreateTime 2014-9-11ÉÏÎç9:49:31
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
public class VEAPConstants {
	//Mac algorithm's key length£º64bytes
	public static String AlgorithmOfMac = "HmacSHA512";
	//Hash algorithm's key length£º32bytes
	public static String AlgorithmOfHash = "SHA-256";
	//AES's key length£º16bytes
	public static String AlgorithmOfED = "AES";
	
	public static final int LengthOfMac = 64;
	public static final int LengthOfHash = 32;
	
	//LengthOfMS=LengthOfK=LengthOfU -> AES key length ->Hash length1/2
	public static final int LengthOfMS = 16;
	public static final int LengthOfK = LengthOfMS;
	//2*LengthOfVerify+LengthOfSK = 64,  LengthOfSK = 64-2*20=24
	public static final int LengthOfVerify = 20;
	public static final int LengthOfSK = LengthOfMac - 2*LengthOfVerify;
}
