package iscas.tca.ake.message;

import java.io.Serializable;

/**
 * ������<>
 * ������<IfcMessage>
 * @author zn
 * @CreateTime 2014-8-16����11:05:51
 */
public interface IfcMessage extends Serializable{
	/**
	 * TODO:<�����Ϣ�Ƿ�Ϸ�����������ԣ����ݷ�Χ>
	 * @param m
	 * @return 
	 */
	public boolean isMsgLegle();
	//public IfcMessage getMessage();
	public String getMsgType();
	public String getMsgContent();
}
