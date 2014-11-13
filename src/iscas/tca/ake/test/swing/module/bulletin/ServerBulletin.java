package iscas.tca.ake.test.swing.module.bulletin;

import iscas.tca.ake.test.swing.module.Config;
import iscas.tca.ake.test.swing.module.Response;
import iscas.tca.ake.test.swing.module.tools.SendAndRecv;
import iscas.tca.ake.veap.IfcGetUsers;
import iscas.tca.ake.veap.User;
import iscas.tca.ake.veap.bulletin.IfcBulletinClient;
import iscas.tca.ake.veap.bulletin.IfcBulletinServer;
import iscas.tca.ake.veap.calculate.GroupData;
import iscas.tca.ake.veap.calculate.GroupInput;

import java.io.File;
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
//bulletin server 
public class ServerBulletin implements Runnable ,IfcBulletinServer{
	int port = 7070;
	ServerSocket serverSocket;
	//
	IfcGetUsers getUsers;
	BigInteger g;
	BigInteger q;
	int lenMS;
	String bulletinFilePath;
	private Response response;
	Long timeOut = 100000000l;
	private Map<String, GroupData>groupDatas = new HashMap<String, GroupData>();
//===================================== Constructor =====================================//
	public ServerBulletin(Config config, IfcGetUsers getUsers, int lenMS, Response response) {
		this.port = config.getBulletinPort();
		this.g = config.getG();
		this.q = config.getQ();
		this.bulletinFilePath = this.getBulletinPath(config.getBulletinDir());
		
		this.getUsers = getUsers;
		this.lenMS = lenMS;
		this.response = response;
	}
//=====================================public=====================================//
	@Override
	public GroupData getGroupData(String groupID)throws Exception{
		
		GroupData gd = null;
		//not the better way -> readers shouldnot wait for each others
		synchronized(this){
			System.out.println("getGroupData In....\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\.");
			gd = groupDatas.get(groupID);
			if(!isValid(gd)){
				System.out.println("waiting for ....");
				System.out.println(System.currentTimeMillis()-gd.getM_publishTime());
				isValid(gd);
				this.wait();
				gd = groupDatas.get(groupID);
			}
		}
		return gd;
	}
	//return all the bulletin data
	public Map<String, GroupData> getAllGroupData(){
		return Collections.unmodifiableMap(groupDatas);
	}
	
	//fresh: delete the data of overdue
	public Map<String, GroupData> fresh(){
		Iterator<Map.Entry<String, GroupData>> iter = this.groupDatas.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, GroupData> next = iter.next();
			GroupData gd = next.getValue();
			if(!isValid(gd)){
				deleteData(next.getKey());
			}
		}
		return getAllGroupData();
	}
	//connection management
	public void close() throws Exception {
		System.out.println("close Bulletin");
		this.serverSocket.close();
	}
	
	private void startSocket() throws Exception {
		this.serverSocket = new ServerSocket(port);
	}
	
	//===========================Bulletin operate===========================//
	//judge if the gd is valid
	private boolean isValid(GroupData gd) {
		if(gd==null){
			return false;
		}
		Long now = System.currentTimeMillis();
		if(now<=(gd.getM_timeOut()+gd.getM_publishTime())){
			return true;
		}
		return false;
	}
	//delete the groupID Data
	private void deleteData(String groupID){
		this.groupDatas.remove(groupID);
		//String filePath = this.getFilePath(groupID);
		//FileOperator.deleteFile(filePath);
	}
	//calculate and record to board
	private GroupData calAndPutonBoard(GroupInput gi) {
		GroupData gd = new GroupData();
				
		gd.calGroupData(gi);
		gd.setPublishTime(System.currentTimeMillis());
		System.out.println("not");
		//put on board
		synchronized(this){
			//putonBoard and notify  the group waitors
			recordToBoard(gd);
			this.notifyAll();
		}
		return gd;
	}
	//record to board
		private GroupData recordToBoard(GroupData data){
			// TODO Auto-generated method stub
			try{
				//this.writeToFile(data.getM_groupID(), data);//file 
				this.groupDatas.put(data.getM_groupID(), data);// save in cache
				return data;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		private IfcBulletinClient getBulletinClient(GroupData gd) {
			// ///time setting
			return new S2CBulletinData(gd.getM_X(), gd.getM_ucs(), gd.getM_publishTime(), gd.getM_timeOut(),
					gd.getM_groupID());
		}
//============================file operate============================//
	
		private String getBulletinPath(String root){
			return root+File.separator+"Bulletin_"+new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		}
//	private String getFilePath( String groupID) {
//		return this.bulletinFilePath + File.separator + groupID;
//	}
//	private void writeToFile(String groupID, GroupData gd) throws Exception {
//		String filePath = this.getFilePath( groupID);
//		FileOperator.writeObjectToFile(gd, filePath);
//	}
//	private GroupData readFromFile(String groupID) throws Exception {
//		String filePath = this.getFilePath(groupID);
//		GroupData gd = (GroupData) FileOperator.readObjectFromFile(filePath);
//		return gd;
//	}
//	private boolean isFileExist(String groupID) {
//		String filePath = this.getFilePath(groupID);
//		File f = new File(filePath);
//		return f.exists();
//	}
//	private GroupData readGroupData(String groupID) {
//		try {
//			if (isFileExist(groupID)) {
//				GroupData gd = this.readFromFile(groupID);
//				if (isValid(gd)) {
//					return gd;
//				}
//				else{
//					///delete invalid data
//					deleteData(groupID);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
//================================ run =======================================//
	private final String prefix = new SimpleDateFormat("HH-mm-ss").format(new Date())+System.currentTimeMillis()%1000+" := ";
	private final String content = new SimpleDateFormat("HH-mm-ss").format(new Date());
	private final String step_listenPort = "Listenning port...";
	private final String step_recvAndValid = "GroupID is Valid fetch immediately";
	private final String step_recvAndCal = "GroupID is past due and calculating";
	private final String step_calOverAndNotifySend = "Cal over.notify waitors sending bulletin";
	private final String step_SendBulleFinished = "Send finished";
	
	public void run() {
		try {
			System.out.println("start the bulletin");
			this.startSocket();
			while (!Thread.interrupted()) {
				
				Socket socket = this.serverSocket.accept();
				// receive groupID from user
				this.response.putExecutionStep(prefix +step_listenPort, content, true);
				
				String groupID = (String) SendAndRecv.recvMsg(socket);
				synchronized(this){
				System.out.println("bulletin : received request"+groupID);
				//look up in cache
				GroupData gd = this.groupDatas.get(groupID);
					System.out.println("//////////////////////////////////////calculate In.....");
				if (true) {////////////////////////need to change
					// look up in dataBase
					//gd = this.readGroupData(groupID);
					if(!isValid(gd)){
						this.response.putExecutionStep(prefix +step_recvAndCal, content, true);
						//calulate the result
						User[] users = this.getUsers.getUsers(groupID);
						GroupInput gi = new GroupInput(groupID, users, lenMS, g, q, this.timeOut);
						users = this.getUsers.getUsers(groupID);
						gd = this.calAndPutonBoard(gi);
						this.response.putExecutionStep(prefix +step_calOverAndNotifySend, content, true);
					}
				}
				// send response back to user
					this.notifyAll();
					
					System.out.println("bulletin : notify all the waitors  get the"+groupID+" data");
					SendAndRecv.sendMsg(getBulletinClient(gd), socket);
					this.response.putExecutionStep(prefix +step_SendBulleFinished, content, true);
				}
			}
		} catch (Exception e) {
			this.response.putExecutionStep("bulletin Exception!!! ", "^ ,^",  true);
			System.out.println("bulletin error!!");
			e.printStackTrace();
		}
	}
	
}
