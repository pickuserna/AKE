package iscas.tca.ake.test.swing.controler;

import iscas.tca.ake.test.swing.module.Config;
import iscas.tca.ake.test.swing.module.MyServer;
import iscas.tca.ake.test.swing.module.Response;
import iscas.tca.ake.test.swing.module.Session;
import iscas.tca.ake.test.swing.module.bulletin.ServerBulletin;
import iscas.tca.ake.test.swing.view.observer.IfcObserver;
import iscas.tca.ake.veap.VEAPConstants;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author zn
 * @CreateTime 2014-10-9ÉÏÎç9:29:34
 */
public class ServerContainer implements Runnable, IfcServerContanier, IfcRecSession{
	private static ServerContainer serverContainer;
	
	private  ServerSocket serverSocket;// serverSocket
	//observer of main control panel
	private IfcObserver observerMain;
	//observer of bulletin panel
	private IfcObserver bulletinObserver;
	private Socket socket;
	ServerBulletin serverBulletin;
	Config config;// = Config.newInstantce();	
	ExecutorService exeService = Executors.newCachedThreadPool();
	//protocol execution servlet
//	private MyServer serverProtocol;
	int logonCount = 0;
	int current = 0;

	Map<Integer, String> historySessionID = new TreeMap<Integer, String>();
	
	private boolean isVerified =false;
	private boolean isDone = false;//is Done
	
	public int getOnlineCount(){
		return this.logonCount;
	}
	
	public Response getResponseMainHistory(int logOrder) throws Exception{
		if(logOrder >0 && logOrder<=logonCount){
			String sessionID = historySessionID.get(logOrder);
			Response res = Session.readResponse(config.getLogsDir(), sessionID);
			res.setArg("order", logOrder);
			res.setArg("logonCount", logonCount);
			return res;
		}
		return null;
	}
	//record the session
	public void recSession(Session session)throws Exception{
		session.recSession(config.getLogsDir());
		//synchronized to update the view
		synchronized(this){
			this.historySessionID.put(++logonCount, session.getSessionID());
			session.getResponse().setArg("order", logonCount);
			session.getResponse().setArg("logonCount", logonCount);
		}
	}
	public void showSession(Session session){
		session.getResponse().update(null);
	}
	//singleton
	private ServerContainer(){
		this.isDone = false;
		this.isVerified = false;
	}
	//newInstance->init->service->close()
	public static ServerContainer newInstance(Config config, IfcObserver mainObsv, IfcObserver bulletinObsv){
		if(serverContainer==null){
			serverContainer = new ServerContainer();
		}
		serverContainer.config = config;
		serverContainer.registBulltinObserver(bulletinObsv);
		serverContainer.registMainObserver(mainObsv);
		//serverContainer.init();
		return serverContainer;
	}
	public void registMainObserver(IfcObserver mainObsv){
		this.observerMain= mainObsv;
	}
	public void registBulltinObserver(IfcObserver bulletinObsv){
		this.bulletinObserver = bulletinObsv;
	}
	
	//using xml to init the protocol
	 public void initFromConfigFile(){
		 this.config.initFromConfigFile();
		 init();
	 }
	public void init(Config cng){
		this.config = cng;
		init();
	}
	//configurate args, and initialize the bulletin
	public void init(){
		this.close();
		this.isDone = false;
		this.isVerified = false;
		//
		this.serverBulletin = ServerBulletin.newInstance(config,
				config,
				VEAPConstants.LengthOfMS,
				new Response(bulletinObserver));		
		this.exeService = Executors.newCachedThreadPool();
		this.exeService.execute(serverBulletin);
	}
	
	public void run(){
		try {
			service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("unknown Exception occurred");
			e.printStackTrace();
		}
		finally{
			try{
				this.close();
			}catch(Exception e){
				System.out.println("can not close the socket!!");
				e.printStackTrace();
			}
		}
		System.out.println("exit the ServerControler!");
		this.observerMain.setStatus("Exit the ServerContainer!!!");
	}
	
	public boolean getIsVerified() throws Exception{
		System.out.println("isVerified!!!!!");
		if(this==null){
			System.out.println("ServerContainer==null");
		}

		synchronized(this){
			if(!this.isDone){
				System.out.println("isVerified waiting ......==========");
				this.wait();
				System.out.println("isVerified outoutoutoutoutoutotuoutoutout......==========");
				//set the done flag
				this.isDone = false;
				return this.isVerified;
			}
		}
		this.isDone = false;
		return this.isVerified;
	}
	public void service() throws Exception{	
		
			this.startServerSocket(config.getPortMain());
			while (!Thread.interrupted()) {
				try{
				//listenning the port
					System.out.println("listenning the port...");
					this.observerMain.setStatus("listenning the port...");
					socket = this.serverSocket.accept();
					System.out.println("receive a connection");
					this.observerMain.setStatus("new connection...");
					//start a session ,session settings 
					Response mainResponse = new Response(this.observerMain);
					Session session = new  Session(config,mainResponse);
					
					session.setSocket(socket);
					//start the verification
					VerifyService vs = new VerifyService(session,this.serverBulletin, this);
					this.exeService.execute(vs);

				}catch(SocketException se){
					se.printStackTrace();
					this.observerMain.setStatus("Exception occured!!");
					System.out.println("maybe the client terminated the Connection reset!!");
				}
				catch(java.io.NotSerializableException nse){
					nse.printStackTrace();
					System.out.println("not serializable");
				}
				finally{
//					synchronized (this) {
//						endVerify();
//						System.out.println("notify all ===========");
//						notifyAll();
//					}
				}
			}
		
	}
	//-------------------------------------------socket starting and closing---------------------------------------------------//

	private void startServerSocket(int port) throws Exception {
		serverSocket = new ServerSocket(port);
	}
	public void close(){
		try{
			//Thread.currentThread().interrupt();
			if(this.socket!=null){
				this.socket.close();
			}
			if(this.serverSocket!=null)
				this.serverSocket.close();
			if(this.serverBulletin!=null)
				this.serverBulletin.close();
			
			this.serverBulletin = null;
			this.exeService.shutdownNow();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public ServerBulletin getServerBulletin()throws Exception{
		return this.serverBulletin;
	}
	//////---------------------------------------- return the unique runtime Config --------------------------------------//
	public Config getConfig(){
		return this.config;
	}
}

enum Enum_VerifyStatus{
	Verification_Passed("OK!!!"),
	Verification_Failed("Verify Failed!!!");
	String introductions;
	private Enum_VerifyStatus(String intro){
		this.introductions = intro;
	}
}
interface IfcRecSession{
	public void recSession(Session session)throws Exception;
	public void showSession(Session session);
}
class VerifyService implements Runnable{
	
	//input args
	Session session;
	ServerBulletin serverBulletin;
	IfcRecSession recSession;
	//variables for web application
	private MyServer serverProtocol;
	public VerifyService(Session sesn,ServerBulletin sb, IfcRecSession recSe){
		this.session = sesn;
		this.serverBulletin = sb;
		this.recSession = recSe;
	}
	
	public void run(){
		try{
			service();
		}catch(Exception e){
			e.printStackTrace();
			endVerify();
		}
	}
	// notify All the waiters of the specific httpSessionID
	private void endVerify(){
		String httpSessionID = serverProtocol.getHttpSessionID();
		System.out.println("endVerify():::"+httpSessionID);
		if(httpSessionID!=null){
			try{
				WaitorSession ws = WaitorSession.getInstanceById(httpSessionID);
				synchronized(ws){
					ws.setAkeProtocol(serverProtocol.getAkeServer());
					System.out.println("waitorSession id:"+httpSessionID+" notifyAll===================");
					ws.overAndNotify();
				}
				}
				catch(Exception e){
					System.out.println("endVerify() Exception!!!!");
					e.printStackTrace();
				}
		}
		System.out.println("isVerified:!!!!!!"+serverProtocol.getAkeServer().isVerified());
	}

	public void service() throws Exception{
		serverProtocol = new MyServer(session);
		serverProtocol.preProServer(session.getSocket(), new ProtocolConfigInitData(session.getConfig().getG(), session.getConfig().getQ(), session.getConfig().getProType()), this.serverBulletin);
		serverProtocol.service(session.getSocket());
		//end the verification, notify the waiters;
		
		this.endVerify();
		//record and show the result of session
		this.recSession.recSession(this.session);
		this.recSession.showSession(this.session);
	}
	
}