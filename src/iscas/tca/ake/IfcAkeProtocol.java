package iscas.tca.ake;

import iscas.tca.ake.message.IfcMessage;

/**
 * description£º<AKE interface >
 * @author zn
 * @CreateTime 2014-8-21ÏÂÎç3:16:06
 */
public interface IfcAkeProtocol {
	/**
	 * TODO:<use the init data to init the client or server of the protocol>
	 * @param init:<init data>
	 * @return if the initialization is successed
	 */
	public boolean init(IfcInitData init)throws Exception;

	/**
	 * TODO:<implemented by the starter of the protocol>
	 * @return IfcMessage message to send to the other part
	 */

	public IfcMessage startProtocol();
	
	/**
	 * TODO:<process the message m and get the response >
	 * @param m :message need to be processed 
	 * @return IfcMessage the response 
	 */
	public IfcMessage processMessage(IfcMessage m)throws Exception;
	
	/**
	 * TODO:<if the other part passed the verification>
	 * @return true yes, false no
	 */
	public boolean isVerified();
	
	/**
	 * TODO:<if the protocol is over >
	 * @return 
	 */
	public boolean isProtocolOver();
	
	/**
	 * TODO:<get the sk, if error occurred or failed to pass the verification return null>
	 * @return 
	 */
	public byte[] getsk();
	
	public int getIDNum();
}
