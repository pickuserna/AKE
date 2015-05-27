package iscas.tca.ake;

import iscas.tca.ake.message.veap.EnumVEAPMsgType;

import java.util.Stack;

/**
 * @author zn
 * the order of message to receive
 * @CreateTime 2014-9-4ÏÂÎç12:44:18
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
public class ProtocolStack<T> extends Stack<T> {
	
	//initialize the stack of the protocol
	public void initProtocolStack(T[] order)
	{	
		for(int i=order.length-1; i>=0; i--)
		{
			push(order[i]);
		}
	}
	
	public boolean isInOrder(T o) {
		return (peek().equals(o));
	}
	//for the test
	public static void main(String[] args)
	{
		ProtocolStack<EnumVEAPMsgType> ps = new ProtocolStack<EnumVEAPMsgType>();
		EnumVEAPMsgType[] order = { EnumVEAPMsgType.UAB,EnumVEAPMsgType.Verify};
		ps.initProtocolStack(order);
		System.out.println(ps.isInOrder(EnumVEAPMsgType.UAB));
		ps.pop();
		System.out.println(ps.isInOrder(EnumVEAPMsgType.Verify));
		ps.pop();
		System.out.println(ps.isEmpty());
	}
	
}
