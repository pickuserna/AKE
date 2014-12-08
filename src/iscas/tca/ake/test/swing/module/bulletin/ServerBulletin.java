package iscas.tca.ake.test.swing.module.bulletin;

import iscas.tca.ake.napake.calculate.NAPCalculate;
import iscas.tca.ake.test.swing.module.Config;
import iscas.tca.ake.test.swing.module.EnumTags;
import iscas.tca.ake.test.swing.module.Response;
import iscas.tca.ake.test.swing.module.bulletin.csdata.BulletinNAPServerHashData;
import iscas.tca.ake.test.swing.module.bulletin.csimplements.BulletinNAPServerHash;
import iscas.tca.ake.test.swing.module.bulletin.csimplements.BulletinNAPServerSecurity;
import iscas.tca.ake.test.swing.module.bulletin.csimplements.BulletinVeapClient;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPServerHash;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPServerSecurity;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinVEAPClient;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinVEAPServer;
import iscas.tca.ake.test.swing.module.tools.SendAndRecv;
import iscas.tca.ake.veap.IfcGetUsers;
import iscas.tca.ake.veap.User;
import iscas.tca.ake.veap.calculate.GroupData;
import iscas.tca.ake.veap.calculate.GroupInput;

import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * des£º<ServerBulletin service concurrent executing at port 7070>
 * 
 * @author zn
 * @CreateTime 2014-10-13ÏÂÎç2:05:13
 */
// bulletin server
public class ServerBulletin implements Runnable, IfcBulletinVEAPServer, IfcBulletinNAPServerHash {
	@Override
	public String getConnectedPseudonyms(String groupID) throws Exception {
		// TODO Auto-generated method stub
		if(this.bulletinServer_Hash!=null){
			return bulletinServer_Hash.getConnectedPseudonyms(groupID);
		}
		else {
			return bulletinServerSecurity.getConnectedPseudonyms(groupID);
		}
	}

	int port = 7070;
	ServerSocket serverSocket;
	//
	private IfcGetUsers getUsers;
	private BigInteger g;
	private BigInteger q;
	private int lenMS;
	private String proType;
	// private String bulletinFilePath;
	private Response response;
	Long timeOut = 10000l;
	private Map<String, GroupData> groupDatas = new HashMap<String, GroupData>();
	private String napBulletinMode;
	//BulletinServer
	private IfcBulletinNAPServerHash bulletinServer_Hash;
	private IfcBulletinNAPServerSecurity bulletinServerSecurity;
	// ===================================== Constructor =====================================//
	private static ServerBulletin serverBulletin = null;
	private ServerBulletin(){
	}
	private void init(Config config, IfcGetUsers getUsers, int lenMS, Response response){
		this.port = config.getBulletinPort();
		this.g = config.getG();
		this.q = config.getQ();
		this.proType = config.getProType();//proType
		this.napBulletinMode = config.getBulletinMode();
		this.getUsers = getUsers;
		this.lenMS = lenMS;
		this.response = response;
	}
	public static synchronized ServerBulletin newInstance(Config config, IfcGetUsers getUsers, int lenMS, Response response){
		if(serverBulletin==null){
			serverBulletin = new ServerBulletin();
		}
		serverBulletin.init(config, getUsers, lenMS, response);
		return serverBulletin;
	}
	// =====================================public=====================================//
	
	@Override
	public GroupData getGroupData(String groupID) throws Exception {

		GroupData gd = null;
		// not the better way -> readers shouldnot wait for each others
		synchronized (this) {
			System.out.println("getGroupData In....\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\.");
			gd = groupDatas.get(groupID);
			if (!isValid(gd)) {
				System.out.println("waiting for VEAP bulletin...");
				this.wait();
				gd = groupDatas.get(groupID);
			}
		}
		return gd;
	}

	// return all the bulletin data
	public Map<String, GroupData> getAllGroupData() {
		return Collections.unmodifiableMap(groupDatas);
	}

	// fresh: delete the data of overdue
	public Map<String, GroupData> freshVEAP() {
		Iterator<Map.Entry<String, GroupData>> iter = this.groupDatas.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, GroupData> next = iter.next();
			GroupData gd = next.getValue();
			if (!isValid(gd)) {
				deleteData(next.getKey());
			}
		}
		return getAllGroupData();
	}

	// connection management
	public void close() throws Exception {
		System.out.println("\n\n==================close Bulletin");
		this.groupDatas.clear();
		this.serverSocket.close();
		if(this.bulletinServer_Hash!=null){
			this.bulletinServer_Hash.close();
		}
		if(this.bulletinServerSecurity!=null){
			this.bulletinServerSecurity.close();
		}
		serverBulletin = null;
	}

	private void startSocket() throws Exception {
		this.serverSocket = new ServerSocket(port);
	}
	
	
	// ===========================Veap Bulletin operate===========================//
	// judge if the gd is valid
	private boolean isValid(GroupData gd) {
		if (gd == null) {
			return false;
		}
		Long now = System.currentTimeMillis();
		if (now <= (gd.getM_timeOut() + gd.getM_publishTime())) {
			return true;
		}
		return false;
	}

	// delete the groupID Data
	private void deleteData(String groupID) {
		this.groupDatas.remove(groupID);
		// String filePath = this.getFilePath(groupID);
		// FileOperator.deleteFile(filePath);
	}

	// calculate and record to board
	private GroupData calAndPutonBoard(GroupInput gi) {
		GroupData gd = new GroupData();

		gd.calGroupData(gi);
		gd.setPublishTime(System.currentTimeMillis());
		System.out.println("not");
		// put on board
		synchronized (this) {
			// putonBoard and notify the group waitors
			recordToBoard(gd);
			this.notifyAll();
		}
		return gd;
	}

	// record to board
	private GroupData recordToBoard(GroupData data) {
		// TODO Auto-generated method stub
		try {
			// this.writeToFile(data.getM_groupID(), data);//file
			this.groupDatas.put(data.getM_groupID(), data);// save in cache
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private IfcBulletinVEAPClient getBulletinClient(GroupData gd) {
		// ///time setting
		return new BulletinVeapClient(gd.getM_X(), gd.getM_ucs(), gd.getM_publishTime(), gd.getM_timeOut(), gd.getM_groupID());
	}

// ================================ run ==================================
	// veap status
	private final String prefix = new SimpleDateFormat("HH-mm-ss").format(new Date()) + System.currentTimeMillis() % 1000 + " := ";
	private final String content = new SimpleDateFormat("HH-mm-ss").format(new Date());
	private final String step_listenPort = "Listenning port...";
	private final String step_recvAndCal = "GroupID is past due and calculating";
	private final String step_calOverAndNotifySend = "Cal over.notify waitors sending bulletin";
	private final String step_SendBulleFinished = "Send finished";
	
	private GroupData veap_Bulleint(String groupID){
		User[] users = this.getUsers.getUsers(groupID);
		GroupInput gi = new GroupInput(groupID, users, lenMS, g, q, this.timeOut);
		users = this.getUsers.getUsers(groupID);
		GroupData gd = this.calAndPutonBoard(gi);
		return gd;
	}
	
	// run the bulletin
	public void run() {
		try {
			System.out.println("start the bulletin");
			this.startSocket();
			while (!Thread.interrupted()) {
                   
				Socket socket = this.serverSocket.accept();
				serverSocket.isClosed();
				// receive groupID from user
				this.response.putExecutionStep(prefix + step_listenPort, content, true);
				final Socket innerSocket = socket;
				//++++++++++++++++++ nap ++++++++++++++++++
				System.out.println("new Thread:"+socket.getRemoteSocketAddress());
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try{
						if(proType.equals("NAP")){
							//Hash 
							System.out.println("napBulletinMode++++++++++++++ "+ napBulletinMode);
							if(ServerBulletin.this.napBulletinMode.equals(EnumTags.NapBulletinHashMode)){
								bulletinServer_Hash = BulletinNAPServerHash.newInstance();
								bulletinServer_Hash.service(innerSocket, getUsers);
							}
							else {
								bulletinServerSecurity = BulletinNAPServerSecurity.newInstance();
								bulletinServerSecurity.service(innerSocket, getUsers, g, q, new NAPCalculate());
							}
						}
						}catch(Exception  e){
							e.printStackTrace();
						}
					}
				}).start();
				
				//------------------nap --------------------
				
				//++++++++++++++++++ veap ++++++++++++++++++
				if(this.proType.equals("VEAP")){
					String groupID = (String) SendAndRecv.recvMsg(socket);
					synchronized (this) {
						System.out.println("bulletin : received request" + groupID);
						GroupData gd = this.groupDatas.get(groupID);
						if (!isValid(gd)) {
							this.response.putExecutionStep(prefix + step_recvAndCal, content, true);
							//veap calculate
							gd = veap_Bulleint(groupID);
							this.response.putExecutionStep(prefix + step_calOverAndNotifySend, content, true);
						}
						this.notifyAll();
					
						SendAndRecv.sendMsg(getBulletinClient(gd), socket);
						this.response.putExecutionStep(prefix + step_SendBulleFinished, content, true);
					}
				}
				//--------------- veap ---------------
			}
		} catch (Exception e) {
			this.response.putExecutionStep("bulletin Exception!!! ", "^ ,^", true);
			System.out.println("bulletin error!!");
			e.printStackTrace();
		}
	}
	//provide service by the bulletinServer_Hash
	@Override
	public void service(Socket socket, IfcGetUsers getUsers) throws Exception {
		// TODO Auto-generated method stub
		this.bulletinServer_Hash.service(socket, getUsers);
	}
	
	//============================ assist =================================


	
}
