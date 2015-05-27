package iscas.tca.ake.message.nap;

import iscas.tca.ake.util.UtilMy;

import org.junit.Test;

/** the enumerable message types for the NAPAKE Protocol
 * @author zn
 * @CreateTime 2014-8-16ÏÂÎç2:02:17
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
public enum EnumNAPMsgType {
	GroupID("C->S | <ID of the group> "),
	SAs("S->C | <S,{Aj}1¡Üj¡Ün> "),
	XstarB("C->S | <X*, B>"),
	YAuths("S->C | <Y, Auths >"),
	Authc("C->S | <Authc>"),
	Succeed("Succed !"),
	Error("Error !!");//,MsgMissing;
	String description;
	private EnumNAPMsgType(String des){
		this.description = des;
	}
	public String toString(){
		return this.description;
	}
	public static void main(String[] args){
		testEnum();
	}
	@Test
	public static void testEnum(){
		
		System.out.println(EnumNAPMsgType.GroupID);
		EnumNAPMsgType enumnap = UtilMy.getEnumFromString(EnumNAPMsgType.class, "GroupID");
		String classFullName = enumnap.getClass().getName();
		UtilMy.print();
		String name = classFullName.substring(classFullName.lastIndexOf("\\."));
		UtilMy.print(name);
	}
	
}
