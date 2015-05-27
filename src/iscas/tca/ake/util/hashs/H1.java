package iscas.tca.ake.util.hashs;
import iscas.tca.ake.util.Assist;

import java.security.MessageDigest;
/** 
 *ÃèÊö£º<SHA-256>
 * @author :zn   
 * @version £º2014-7-31 ÏÂÎç1:42:30

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
public class H1 implements IfcHash {

	@Override
	public byte[] process(String s) {
		// TODO Auto-generated method stub
		byte[] bs = s.getBytes();
		try{
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update(bs);
			return sha.digest();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		String text = "abc";
		H1 h = new H1();
		
		byte[] resultData = h.process(text);
		for(byte b: resultData)
		{
			System.out.println(b);
		}
		System.out.println(Assist.bytesToHexString(resultData));
	}
}


