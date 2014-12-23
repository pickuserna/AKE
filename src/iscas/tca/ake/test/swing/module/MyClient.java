package iscas.tca.ake.test.swing.module;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.napake.InitNAPAKEClientData;
import iscas.tca.ake.napake.NAPAKEClient;
import iscas.tca.ake.test.swing.controler.ProtocolConfigInitData;
import iscas.tca.ake.test.swing.module.bulletin.ClientBulletin;
import iscas.tca.ake.test.swing.module.tools.SendAndRecv;
import iscas.tca.ake.test.swing.module.tools.TimeRecord;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.exceptions.InitializationException;
import iscas.tca.ake.veap.InitVEAPClientData;
import iscas.tca.ake.veap.VEAPClient;
import iscas.tca.ake.veap.VEAPConstants;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Map;

/**
 * @author zn
 * @CreateTime 2014-8-19ÏÂÎç7:27:45
 */
public class MyClient {
	Socket socket;
	IfcAkeProtocol akeClient;
	public static long totalTime = 0;
	// Add
	String name;
	String password;
	String groupID;
	ProtocolConfigInitData cfgInitArgs;
	private TimeRecord timeRecord = new TimeRecord();
	private Response response;

	ClientBulletin bulletinClient = null;
	boolean isGetBulletin = false;
	
	//set BulletinClient
	public void setBulletinClient(ClientBulletin bc){
		this.bulletinClient = bc;
	}
	public boolean prepareClient(Socket socket,
			ClientBulletin bulletinClient, 
			Map<String, Object> proArgs,
		  Response response) throws Exception {
		this.name = (String) proArgs.get("name");
		this.password = (String) proArgs.get("password");
		this.groupID = (String) proArgs.get("groupID");
		this.response = response;
		
		this.socket = socket;
		this.bulletinClient = bulletinClient;
		
		String httpSessionID=(String)proArgs.get("httpSessionID");
		//+++++++++++++++++++++++++++++++++++++++++++++++++
		if(httpSessionID!=null){
			SendAndRecv.sendMsg(new C2S_PreProData(groupID, httpSessionID),socket);
		}
		else{
			// send groupID
			SendAndRecv.sendMsg(groupID, socket);
		}
		//receiving protocol initData;
		System.out.println("receive configInitData...");
		cfgInitArgs = (ProtocolConfigInitData) SendAndRecv.recvMsg(socket);

		System.out.println("received configInitData...");

		System.out.println("prepareclient is OK...");
		response.setBit(cfgInitArgs.q.bitLength());
		response.setG(cfgInitArgs.g);
		response.setQ(cfgInitArgs.q);
		response.setProType(cfgInitArgs.proType);
//		if(cfgInitArgs.groupUserIDs!=null){
//			response.setIds(cfgInitArgs.groupUserIDs.length);
//		}
		return true;
	}

	public void initClient(String type) throws Exception {
		try {
			System.out.println("client initailization");
			IfcInitData init = null;

			if (type.equals("NAP")) {
				this.akeClient = new NAPAKEClient();
				init = new InitNAPAKEClientData(password, this.groupID, this.name,
						cfgInitArgs.q, cfgInitArgs.g, this.bulletinClient);
			} else if (type.equals("VEAP")) {
				this.akeClient = new VEAPClient();

				init = new InitVEAPClientData(VEAPConstants.LengthOfK,
						VEAPConstants.LengthOfSK, cfgInitArgs.g, cfgInitArgs.q,
						// login arguments
						this.name, this.password, this.groupID,
						this.bulletinClient//client bulletin
						);
			}
			akeClient.init(init);
		} catch (InitializationException ie) {
			System.out.println("have not initialized, protocolover");
			try {
				// terminate the thread current now
				if (this.socket != null)
					this.socket.close();
				Thread.currentThread().stop();
			} catch (IOException e) {
				System.out.println("IOException");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	public boolean service() throws Exception {

		System.out.println("service() start ..... runClient()");
			this.runClient();
			System.out.println("service() end .....");
		return this.akeClient.isVerified();
	}

	public void runClient() throws Exception {


		System.out.print("runClient() start.... ");

		initClient(cfgInitArgs.proType);//
		System.out.println(cfgInitArgs.proType);
		IfcMessage cMsg = null;
		IfcMessage sMsg = null;
		// start the  protocol
		
		cMsg = akeClient.startProtocol();
		this.response.getExecutionSteps();
		this.response.putExecutionStep(""+cMsg.getMsgType(), cMsg.getMsgContent(), true);
		System.out.print("sendMsg "+ "111111111----");
		SendAndRecv.sendMsg((Serializable) cMsg, socket);
		
		System.out.print("runClient ->while() ------");
		while (!akeClient.isProtocolOver()) {
			
			sMsg = (IfcMessage) SendAndRecv.recvMsg(socket);
			System.out.print("recv msg---------");
			this.response.putExecutionStep(""+sMsg.getMsgType(), sMsg.getMsgContent(), true);
			//time recording 
			this.timeRecord.startRec("client process");
			this.timeRecord.startRec("client process"+sMsg.getMsgType());
			System.out.print("process Msg" + sMsg+"start ....");
				cMsg = akeClient.processMessage(sMsg);
				System.out.print("process Msg" + sMsg+"end ....");
			this.timeRecord.endRec("client process");
			this.timeRecord.endRec("client process"+sMsg.getMsgType());
			
			if (cMsg != null) {// null is the signal of the end of protocol
				this.response.putExecutionStep(""+cMsg.getMsgType(), cMsg.getMsgContent(), true);
				SendAndRecv.sendMsg(cMsg, socket);
			}
		}
		this.timeRecord.showResult();
		this.response.setIdsNum(this.akeClient.getIDNum());
		this.response.putTimeRecord(this.timeRecord.getResult());
		this.response.putParameter("isVerified", this.akeClient.isVerified()+"");
		this.response.putParameter("sk", Assist.bytesToHexString(this.akeClient.getsk()));
	}
	public static void showIsVerified(IfcAkeProtocol cs, String type) {
		System.out.println(type + "verify result£º" + cs.isVerified() + "\n  SK:"
				+ Assist.bytesToHexString(cs.getsk()));

	}
	//return the client of akeProtocol 
	public IfcAkeProtocol getAkeProtocol(){
		return this.akeClient;
	}
}
