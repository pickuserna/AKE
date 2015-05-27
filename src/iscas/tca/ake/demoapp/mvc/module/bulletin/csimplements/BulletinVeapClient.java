package iscas.tca.ake.demoapp.mvc.module.bulletin.csimplements;

import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinVEAPClient;
import iscas.tca.ake.veap.calculate.U_C;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Time;

/**
 * description£º<server send the S2CBulletinData to client >
 * @author zn
 * @CreateTime 2014-9-12ÉÏÎç9:40:04
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
public class BulletinVeapClient extends IfcBulletinVEAPClient implements Serializable{	
	
	public BulletinVeapClient() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param x
	 * @param u_cs
	 * @param t
	 * @param t0
	 * @param groupID
	 */
	
	
	public BulletinVeapClient(BigInteger x, 
			U_C[] u_cs, 
			Long t, 
			Long t0,
			String groupID){
		super(x, u_cs, t, t0, groupID);
	}
	@Override
	public IfcBulletinVEAPClient fetchData(String groupID) throws Exception {
		return this;
	}
	public String toString(){
		return this.groupID+this.timeOut+this.publishTime;
	}
}
