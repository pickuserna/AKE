package iscas.tca.ake.message;

import java.io.Serializable;

/**
 * 描述：<>
 * 类名：<IfcMessage>
 * @author zn
 * @CreateTime 2014-8-16上午11:05:51
 */
public interface IfcMessage extends Serializable{
	/**
	 * TODO:<检查消息是否合法，检查完整性，数据范围>
	 * @param m
	 * @return 
	 */
	public boolean isMsgLegle();
	//public IfcMessage getMessage();
	public String getMsgType();
	public String getMsgContent();
}
