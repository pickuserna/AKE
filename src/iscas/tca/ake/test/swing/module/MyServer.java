package iscas.tca.ake.test.swing.module;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.napake.InitNAPAKEServerData;
import iscas.tca.ake.napake.NAPAKEServer;
import iscas.tca.ake.test.swing.controler.ProtocolConfigInitData;
import iscas.tca.ake.test.swing.controler.WaitorSession;
import iscas.tca.ake.test.swing.module.bulletin.ServerBulletin;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinVEAPServer;
import iscas.tca.ake.test.swing.module.tools.SendAndRecv;
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
						"" + cMsg.getMsgType(), cMsg.getMsgContent(), true);
				// process the received message
				session.getTimeRecord().startRec("server process");
				session.getTimeRecord().startRec(
						"server process " + cMsg.getMsgType());
				
				
				IfcMessage sMsg = akeServer.processMessage(cMsg);System.out.println("processing msg out2222");
				
				session.getTimeRecord().endRec(
						"server process " + cMsg.getMsgType());
				session.getTimeRecord().endRec("server process");

				if (null != sMsg) {
					
					session.getResponse().putExecutionStep(
							"" + sMsg.getMsgType(), sMsg.getMsgContent(), true);
					
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
