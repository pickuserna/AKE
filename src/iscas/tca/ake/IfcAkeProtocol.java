package iscas.tca.ake;

import iscas.tca.ake.message.IfcMessage;

/**
 * description£º<AKE interface >
 * @author zn
 * @CreateTime 2014-8-21ÏÂÎç3:16:06
 */
public interface IfcAkeProtocol {
	/**
	 * TODO:<use the initial data to initialize the client or server of the protocol>
	 * @param init:<initial data>
	 * @return true if the initialization is passed
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
	 * @return <IfcMessage> response of the received Message 
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
	 * TODO:<get the sessionKey, if error occurred or failed to pass the verification then return null>
	 * @return sessionKey(passed), null failed
	 */
	public byte[] getsk();
	
	public int getIDNum();
}
