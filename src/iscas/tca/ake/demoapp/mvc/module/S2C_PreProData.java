package iscas.tca.ake.demoapp.mvc.module;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * des£º<S send the PrepareInfo to Client at the prepare stage>
 * @author zn
 * @CreateTime 2014-10-9ÏÂÎç1:36:16
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
public class S2C_PreProData implements Serializable{
	public int bits;
	public BigInteger q;
	public BigInteger g;
	
	public String groupID;
	public String proType;
	public int idNum;
	public String url;
	/**
	 * @param bits
	 * @param groupID
	 * @param proType
	 * @param idNum
	 */
	public S2C_PreProData(int bits, String groupID, String proType, int idNum, String url) {
		super();
		this.bits = bits;
		this.groupID = groupID;
		this.proType = proType;
		this.idNum = idNum;
		this.url = url;
	}
	public String toString(){
		return this.proType+this.bits+this.idNum+this.url;
	}
	
}
