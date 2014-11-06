package iscas.tca.ake.test.swing.module;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.IfcInitData;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.napake.InitClientData;
import iscas.tca.ake.napake.NAPClient;
import iscas.tca.ake.test.swing.controler.ConfigInitData;
import iscas.tca.ake.test.swing.module.tools.SendAndRecv;
import iscas.tca.ake.test.swing.module.tools.TimeRecord;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.exceptions.IllegalMsgException;
import iscas.tca.ake.util.exceptions.InitializationException;
import iscas.tca.ake.veap.InitVEAPClientData;
import iscas.tca.ake.veap.VEAPClient;
import iscas.tca.ake.veap.VEAPConstants;
import iscas.tca.ake.veap.bulletin.IfcBulletinClient;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Map;

/**
 * 描述：<> 类名：<MyClient>
 * 
 * @author zn
 * @CreateTime 2014-8-19下午7:27:45
 */
public class MyClient {
	Socket socket;
	IfcAkeProtocol akeClient;
	public static long totalTime = 0;
	// Add
	String name;
	String password;
	String groupID;
	ConfigInitData cfgInitArgs;
	private TimeRecord timeRecord = new TimeRecord();
	private Response response;
	IfcBulletinClient bulletinClient = null;
	boolean isGetBulletin = false;
	/**
	 * TODO:<>
	 * 
	 * @param response
	 * @param socket
	 * @param proArgs
	 * @return
	 * @throws Exception
	 */
	public void bulletinService(){
		
	}
	
	public boolean prepareClient(Socket socket,
			IfcBulletinClient bulletinClient, 
			Map<String, Object> proArgs,
		  Response response) throws Exception {
		this.name = (String) proArgs.get("name");
		this.password = (String) proArgs.get("password");
		this.groupID = (String) proArgs.get("groupID");
		this.response = response;
		
		this.socket = socket;
		this.bulletinClient = bulletinClient;
		// set args
		SendAndRecv.sendMsg(groupID, socket);
		//receiving initData;
		System.out.println("receive configInitData...");
		cfgInitArgs = (ConfigInitData) SendAndRecv.recvMsg(socket);

		System.out.println("received configInitData...");

		System.out.println("preParaclient is OK...");
		response.setBit(cfgInitArgs.q.bitLength());
		response.setG(cfgInitArgs.g);
		response.setQ(cfgInitArgs.q);
		response.setProType(cfgInitArgs.proType);
		if(cfgInitArgs.groupUserIDs!=null){
			response.setIds(cfgInitArgs.groupUserIDs.length);
		}
		return true;
	}

	public void initClient(String type) throws Exception {
		try {
			System.out.println("client初始化");
			IfcInitData init = null;

			if (type.equals("NAP")) {
				this.akeClient = new NAPClient();
				init = new InitClientData(password, cfgInitArgs.groupUserIDs, this.name,
						cfgInitArgs.q, cfgInitArgs.g);
			} else if (type.equals("VEAP")) {
				this.akeClient = new VEAPClient();

				init = new InitVEAPClientData(VEAPConstants.LengthOfK,
						VEAPConstants.LengthOfSK, cfgInitArgs.g, cfgInitArgs.q,
						// 输入登陆参数
						this.name, this.password, this.groupID,
						this.bulletinClient//client bulletin
						);
			}
			akeClient.init(init);
		} catch (InitializationException ie) {
			System.out.println("have not initialized, protocolover");
			try {
				// 终止当前的线程
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
		if (cfgInitArgs.proType.equals("NAP")) {
			showResult((NAPClient) akeClient);
		} else if (cfgInitArgs.proType.equals("VEAP")) {
			showIsVerified(this.akeClient, "client");
		}
		this.timeRecord.showResult();
		this.response.putTimeRecord(this.timeRecord.getResult());
		this.response.putParameter("isVerified", this.akeClient.isVerified()+"");
		this.response.putParameter("sk", Assist.bytesToHexString(this.akeClient.getsk()));
	}

	/**
	 * TODO:<显示运行结果>
	 * 
	 * @param napClient
	 */

	public void showResult(NAPClient napClient) {
		String s = null;
		System.out.println("q  :" + napClient.getM_q());
		System.out.println("g  :" + napClient.getM_g());
		System.out.println("IDs :" + napClient.getM_IDs().length);
		if (akeClient.isVerified()) {
			s = "passed verify\r\nAuths:"
					+ Assist.bytesToHexString(napClient.getM_myAuths())
					+ "\r\n";
			s = s + "sk:   " + Assist.bytesToHexString(napClient.getM_sk())
					+ "\r\n";
			System.out.println(s);
		} else {
			System.out.println("did not passed \nClient Auths："
					+ Assist.bytesToHexString(napClient.getM_myAuths()));
			System.out.println("Server Auths："
					+ Assist.bytesToHexString(napClient.getM_Auths()));
			s = "did not passed \r\n";
		}

//		RecordInFile.writeInto(Constants.FileName, "q  :" + napClient.getM_q()
//				+ "\r\n" + "g  :" + napClient.getM_g() + "\r\n" + "IDs :"
//				+ napClient.getM_IDs().length + "\r\n" + s + "\r\n\r\n");
	}

	public static void showIsVerified(IfcAkeProtocol cs, String type) {
		System.out.println(type + "verify result：" + cs.isVerified() + "\n  SK:"
				+ Assist.bytesToHexString(cs.getsk()));

	}
}
