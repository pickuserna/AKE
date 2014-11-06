package iscas.tca.ake;

import iscas.tca.ake.message.veap.EnumVEAPMsgType;

import java.util.Stack;

/**
 * 描述：<>
 * 
 * @author zn
 * @CreateTime 2014-9-4下午12:44:18
 */
public class ProtocolStack<T> extends Stack<T> {
	//协议栈
	//Stack<T> m_proStack = new Stack<T>();
	
	//初始化协议栈
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
