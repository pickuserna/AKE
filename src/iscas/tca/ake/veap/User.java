package iscas.tca.ake.veap;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.hashs.Hash_VEAP;

import java.math.BigInteger;

/**
 * description<user structure£¬id-pw>
 * @author zn
 * @CreateTime 2014-9-11ÉÏÎç10:46:09
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
public class User{
	public String user_id;
	public String user_pw;
	/**
	 * TODO:<get the pvd of a user; pvd = Hash(id, password)>
	 * @return 
	 */
	public User(String id, String pw){
		this.user_id = id;
		this.user_pw = pw;
	}
	public BigInteger getPvd(){
		String ss[] ={user_id, user_pw};
		String str = Assist.connectStrings(ss).toString();
		byte[] bPvd =  new Hash_VEAP().process(str);
		
		return new BigInteger(bPvd).abs();
	}
	public String toString(){
		return this.user_id+":"+this.user_pw;
	}
}