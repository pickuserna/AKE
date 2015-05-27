package iscas.tca.ake.demoapp.mvc.module;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.demoapp.mvc.controler.ProtocolConfigInitData;
import iscas.tca.ake.demoapp.mvc.controler.WaitorSession;
import iscas.tca.ake.demoapp.mvc.module.bulletin.ServerBulletin;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinVEAPServer;
import iscas.tca.ake.demoapp.mvc.module.tools.SendAndRecv;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.napake.InitNAPAKEServerData;
import iscas.tca.ake.napake.NAPAKEServer;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.exceptions.CannotFindSuchIDException;
import iscas.tca.ake.veap.IfcGetUsers;
import iscas.tca.ake.veap.InitVEAPServerData;
import iscas.tca.ake.veap.User;
import iscas.tca.ake.veap.VEAPConstants;
import iscas.tca.ake.veap.VEAPServer;

import java.io.EOFException;
import java.math.BigInteger;
import java.net.Socket;

/**
 * Description£º<use the protocol to verify> 
 * ÀàÃû£º<MyServer>
 * 
 * @author zn
 * @CreateTime 2014-8-19ÏÂÎç7:41:31
 */
// Servlet

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
public class MyServer implements /*IfcNAPServerUser,*/ IfcGetUsers {

	private Session session;
	public Socket socket;//
	IfcAkeProtocol akeServer;
	ServerBulletin bulletinServer;
	String httpSessionID;
	WaitorSession waitorSession;
	
	public MyServer(Session session) {
		this.session = session;
	}
	public IfcAkeProtocol getAkeServer (){
		return this.akeServer;
	}
	@Override
	public User[] getUsers(String groupID) {
		// TODO Auto-generated method stub
		return session.getUsers();
	}

	// init the protocol 
	private void initProtocol() {
		BigInteger g = this.session.getConfig().getG();
		;
		BigInteger q = this.session.getConfig().getQ();
		String sid = this.session.getConfig().getSid();
		try {
			if (this.session.getProType() != null) {
				if (this.session.getProType().equals("NAP")) {
					InitNAPAKEServerData init = new InitNAPAKEServerData(q, g, sid, this, this.bulletinServer);
					this.akeServer = new NAPAKEServer();
					this.akeServer.init(init);
				} else if (this.session.getProType().equals("VEAP")) {
					InitVEAPServerData init = new InitVEAPServerData(
							VEAPConstants.LengthOfMS,
							VEAPConstants.LengthOfVerify,
							VEAPConstants.LengthOfSK, g, q, sid, this,
							this.bulletinServer);

					this.akeServer = new VEAPServer();
					this.akeServer.init(init);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	//
	private void runProtocol() throws Exception {
		initProtocol();
//		try {
			while (!this.akeServer.isProtocolOver()) {
				// receive the message
				
				IfcMessage cMsg = (IfcMessage) SendAndRecv.recvMsg(socket);
			
				session.getResponse().putExecutionStep(
						"" + cMsg.sGetMsgType(), cMsg.sGetMsgContent(), true);
				// process the received message
				session.getTimeRecord().startRec("server process");
				session.getTimeRecord().startRec(
						"server process " + cMsg.sGetMsgType());
				
				
				IfcMessage sMsg = akeServer.processMessage(cMsg);System.out.println("processing msg out2222");
				
				session.getTimeRecord().endRec(
						"server process " + cMsg.sGetMsgType());
				session.getTimeRecord().endRec("server process");

				if (null != sMsg) {
					
					session.getResponse().putExecutionStep(
							"" + sMsg.sGetMsgType(), sMsg.sGetMsgContent(), true);
					
					//bulletin
					SendAndRecv.sendMsg(sMsg, socket);
				}
			}
//		} catch (EOFException e) {
//			e.printStackTrace();
//			System.out.println("Maybe the remote terminate the connection!!");
//		}
		//protocol over
		
		this.session.getResponse().putParameter("isVerified", this.akeServer.isVerified() + "");
		if (this.akeServer.isVerified()) {
			this.session.getResponse().putParameter("sk",
					Assist.bytesToHexString(this.akeServer.getsk()));
		}
		this.session.getTimeRecord().showResult();
		this.session.getResponse().putTimeRecord(this.session.getTimeRecord().getResult());		
	}
	
	public String getHttpSessionID(){
		return this.httpSessionID;
	}
	
	public void preProServer(Socket s, ProtocolConfigInitData cid, ServerBulletin bulletinServer) throws Exception {
		this.socket = s;
		this.session.setProType(cid.proType);
		Object obj = SendAndRecv.recvMsg(s);
		//old CS module 
		String groupID=null;
		if(obj instanceof String){
			System.out.println("CS module ");
			groupID = (String) obj;
		}
		//web BS module
		else if(obj instanceof C2S_PreProData){
			groupID = ((C2S_PreProData)obj).groupID;
			httpSessionID = ((C2S_PreProData)obj).httpSessionID;
			System.out.println("web BS module ++ receiving httpSessionID"+httpSessionID);
		}
		//bulletin 
		this.bulletinServer = bulletinServer;
		this.session.setGroupID(groupID);
		this.session.getResponse().putParameter("groupID",groupID);
		
		this.session.getResponse().putParameter("ids",session.getUserIds().length+"");
		this.session.getResponse().setBit(cid.q.bitLength());
		this.session.getResponse().setProType(cid.proType);
		SendAndRecv.sendMsg(cid, s);

	}

	// protocol service entrance
	public boolean service(Socket sct) throws Exception {
		this.socket = sct;
		this.runProtocol();
		System.out.println("exist service  !!!!!new new new!!");
		this.socket.close();
		return this.akeServer.isVerified();
	}
	public byte[] getSessionKey(){
		return this.akeServer.getsk();
	}
}
