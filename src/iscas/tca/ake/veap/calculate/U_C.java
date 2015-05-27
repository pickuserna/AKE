package iscas.tca.ake.veap.calculate;

import iscas.tca.ake.util.Assist;

import java.io.Serializable;

/**
 * description <U,C>structure
 * @author zn
 * @CreateTime 2014-9-11����10:12:28
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
public class U_C implements Serializable{
	protected byte[] m_u;
	protected byte[] m_c;
	
	public byte[] getM_u() {
		return m_u;
	}

	public void setM_u(byte[] m_u) {
		this.m_u = m_u;
	}

	public byte[] getM_c() {
		return m_c;
	}
	
	public void setM_c(byte[] m_c) {
		this.m_c = m_c;
	}
	/**
	 * @return 
	 */
	public String u_c2String(){
		StringBuilder sb = new StringBuilder();
		sb.append(Assist.bytesToString(m_u));
		sb.append(Assist.bytesToString(m_c));
		return sb.toString();
	}
	public boolean containsU(byte[] U){
		if(this.m_u.equals(U))
			return true;
		return false;
	}
	
}
