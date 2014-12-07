package iscas.tca.ake.message.veap;

/**
 * @author zn
 * @CreateTime 2014-9-4上午10:16:27
 */
public enum EnumVEAPMsgType {
	UAB("C->S | <A,B> "), 
	S2C("S->C | <A^x, Y, Vs, SID> "), 
	Verify("C->S | <V'U>");//一共3条交互消息
	String description;
	private EnumVEAPMsgType(String des){
		this.description = des;
	}
	public String toString(){
		return this.description;
	}
}
