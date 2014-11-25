package iscas.tca.ake.test.swing.module;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.napake.InitServerData;
import iscas.tca.ake.napake.NAPServer;
import iscas.tca.ake.test.swing.controler.ProtocolConfigInitData;
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
	public MyServer(Session session) {
		this.session = session;
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
					InitServerData init = new InitServerData(q, g, sid, this, this.bulletinServer);
					this.akeServer = new NAPServer();
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
		try {
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
		} catch (EOFException e) {
			e.printStackTrace();
			System.out.println("Maybe the remote terminate the connection!!");
		}

//		if (this.session.getProType().equals("NAP")) {
//			showResult((NAPServer) akeServer);
//		} else if (this.session.getProType().equals("VEAP")) {
//			showIsVerified(this.akeServer, "server");
//		}
		//
		this.session.getResponse().putParameter("isVerified", this.akeServer.isVerified() + "");
		if (this.akeServer.isVerified()) {
			this.session.getResponse().putParameter("sk",
					Assist.bytesToHexString(this.akeServer.getsk()));
		}
		this.session.getTimeRecord().showResult();
		this.session.getResponse().putTimeRecord(this.session.getTimeRecord().getResult());		
	}

	public void preProServer(Socket s, ProtocolConfigInitData cid, ServerBulletin bulletinServer) throws Exception {
		this.socket = s;
		this.session.setProType(cid.proType);
		String groupID = (String) SendAndRecv.recvMsg(s);
		//bulletin 
	//,,,,
		this.bulletinServer = bulletinServer;
		this.session.setGroupID(groupID);
//		cid.setGroupUserIDs(session.getUserIds());
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

	/*public void cleanBoard() {
		Constants.groupData = null;
		Constants.clientBulletin = null;
	}*/

//	private void showResult(NAPServer napServer) {
//		System.out.println("q  :" + napServer.getM_q());
//		System.out.println("g  :" + napServer.getM_g());
////		System.out.println("IDs :" + napServer.getM_IDs().length);
//		System.out.println("Auths: "
//				+ Assist.bytesToHexString(napServer.getM_Auths()));
//		System.out.println("sk:   "
//				+ Assist.bytesToHexString(napServer.getM_sk()));
//		System.out.println("rS  :" + napServer.getM_rS());
//		System.out.println("ry  :" + napServer.getM_randy());
//	}

	private String findPws(String id) {
		User[] users = session.getUsers();
		for (int i = 0; i < users.length; i++) {
			if (id.equals(users[i].user_id)) {
				return users[i].user_pw;
			}
		}
		return null;
	}


	public static void showIsVerified(IfcAkeProtocol cs, String type) {
		System.out.println(type + "Verify result£º" + cs.isVerified() + "\n  SK:"
				+ Assist.bytesToHexString(cs.getsk()));

	}
}
