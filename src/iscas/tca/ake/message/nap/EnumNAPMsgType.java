package iscas.tca.ake.message.nap;

import org.junit.Test;

/**
 * ������<>
 * ������<EnumNapMsgType>
 * @author zn
 * @CreateTime 2014-8-16����2:02:17
 */
public enum EnumNAPMsgType {
	IDs("C->S | <IDs of A group> "),
	SAs("S->C | <S,{Aj}1��j��n> "),
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
		System.out.println(EnumNAPMsgType.IDs);
	}
}
