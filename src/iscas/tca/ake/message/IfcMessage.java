package iscas.tca.ake.message;

import java.io.Serializable;

/**
 * ClassName£º<IfcMessage>
 * @author zn
 * @CreateTime 2014-8-16ÉÏÎç11:05:51
 */
public interface IfcMessage extends Serializable{
	/**
	 * TODO: verify the validity of the message 
	 * @param m
	 * @return 
	 */
	public boolean isMsgLegle();
	public String getMsgType();
	public String getMsgContent();
}
