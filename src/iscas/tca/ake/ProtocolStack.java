package iscas.tca.ake;

import iscas.tca.ake.message.veap.EnumVEAPMsgType;

import java.util.Stack;

/**
 * @author zn
 * the order of message to receive
 * @CreateTime 2014-9-4ÏÂÎç12:44:18
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
