package iscas.tca.ake;

import iscas.tca.ake.message.IfcMessage;

/**
 * description£º<AKE interface >
 * @author zn
 * @CreateTime 2014-8-21ÏÂÎç3:16:06
 */
/*
 * Copyright (c) 20014-2041 Institute Of Software Chinese Academy Of Sciences
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @Organization: http://tca.iscas.ac.cn/
 * @author: Nan Zhou
 * @Aknowledge: Tutor Liwu Zhang , Alumnus Yan Zhang, Zhigang Gao
 * @Email: changchangge123@qq.com
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
