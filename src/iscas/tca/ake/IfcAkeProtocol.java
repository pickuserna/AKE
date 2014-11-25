package iscas.tca.ake;

import iscas.tca.ake.message.IfcMessage;

/**
 * 描述：<AKE协议的使用接口>
 * @author zn
 * @CreateTime 2014-8-21下午3:16:06
 */
public interface IfcAkeProtocol {
	/**
	 * TODO:<使用init对client或server进行初始化>
	 * @param init:<初始化数据>
	 * @return 是否成功
	 */
	public boolean init(IfcInitData init)throws Exception;

	/**
	 * TODO:<协议的启动方实现,发送协议启动消息>
	 * @return IfcMessage要发送给服务器的消息
	 */

	public IfcMessage startProtocol();
	
	/**
	 * TODO:<处理消息m，并返回处理后的结果>
	 * @param m :要处理的消息
	 * @return IfcMessage处理的结果
	 */
	public IfcMessage processMessage(IfcMessage m)throws Exception;
	
	/**
	 * TODO:<消息的验证方实现，判断对方是否通过了验证>
	 * @return true 通过了验证，false没有通过
	 */
	public boolean isVerified();
	
	/**
	 * TODO:<判断协议是否正常结束了>
	 * @return 
	 */
	public boolean isProtocolOver();
	
	/**
	 * TODO:<获取sk,如果无法生成，返回null>
	 * @return 
	 */
	public byte[] getsk();
	
	public int getIDNum();
}
