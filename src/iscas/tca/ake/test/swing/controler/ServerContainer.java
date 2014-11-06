package iscas.tca.ake.test.swing.controler;

import iscas.tca.ake.test.swing.module.Config;
import iscas.tca.ake.test.swing.module.MyServer;
import iscas.tca.ake.test.swing.module.Response;
import iscas.tca.ake.test.swing.module.Session;
import iscas.tca.ake.test.swing.module.bulletin.ServerBulletin;
import iscas.tca.ake.test.swing.view.observer.IfcObserver;
import iscas.tca.ake.veap.User;
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
 * 描述：<>
 * 
 * @author zn
 * @CreateTime 2014-10-9上午9:29:34
 */
// 控制，负责接收请求，返回响应
public class ServerContainer implements Runnable {
	private static ServerContainer serverContainer;
	
	private  ServerSocket serverSocket;// serverSocket
	//observer of main contorl panel
	private IfcObserver observerMain;
	//observer of bulletin pannel
	private IfcObserver bulletinObserver;
	private Socket socket;
	ServerBulletin serverBulletin;
	Config config;// = Config.newInstantce();	
	ExecutorService exeService = Executors.newCachedThreadPool();
	
	//protocol execution servlet
	private MyServer serverProtocol;
	int logonCount = 0;
	int current = 0;

	Map<Integer, String> historySessionID = new TreeMap<Integer, String>();
	
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
	
	private void recSession(Session session)throws Exception{
		session.recSession(config.getLogsDir());
		this.historySessionID.put(++logonCount, session.getSessionID());
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////xuyao gai de /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//path to Bulletin !!
	
	//path to save sessions
	
	//path to save text Response
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////xuyao gai de /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//session operation

	List<OneService> serviceList = new LinkedList<OneService>();

	//singleton
	private ServerContainer(){
	}
	
	public static ServerContainer newInstance(Config config, IfcObserver mainObsv, IfcObserver bulletinObsv){
		if(serverContainer==null){
			serverContainer = new ServerContainer();
			serverContainer.config = config;
			serverContainer.registBulltinObserver(bulletinObsv);
			serverContainer.registMainObserver(mainObsv);
		}
		return serverContainer;
	}
	public void registMainObserver(IfcObserver mainObsv){
		this.observerMain= mainObsv;
	}
	public void registBulltinObserver(IfcObserver bulletinObsv){
		this.bulletinObserver = bulletinObsv;
	}
	public void init(Config cng){
		this.config = cng;
		init();
	}
	//config, and init the bulletin
	public void init(){

		this.serverBulletin = new ServerBulletin(config,
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
		this.observerMain.setStatus("Exception!! Exit the ServerContainer!");
	}

	private void service() throws Exception{	
		
			this.startServerSocket(config.getPortMain());
			while (!Thread.interrupted()) {
				try{
				//listenning the port
					System.out.println("listenning the port...");
					this.observerMain.setStatus("listennign the port...");
					socket = this.serverSocket.accept();
					System.out.println("receive a connection");
					this.observerMain.setStatus("new connection...");
				
					//prepare for the execution of the Verify protocol
					Response mainResponse = new Response(this.observerMain);
					Session session = new  Session(config,mainResponse);
					serverProtocol = new MyServer(session);
					serverProtocol.prepareServer(socket, new ConfigInitData(config.getG(), config.getQ(), config.getProType()), this.serverBulletin);
					//start a session 
					boolean isVerified = serverProtocol.service(socket);
					this.observerMain.setStatus("isVerified:"+isVerified);
				//end of one service()
					this.recSession(session);
				    //use mainResponse to update the observer
					mainResponse.setArg("order", logonCount);
					mainResponse.setArg("logonCount", logonCount);
					mainResponse.update(mainResponse);

				}catch(SocketException se){
					se.printStackTrace();
					this.observerMain.setStatus("reset: Exception可能客户端终止了连接");
					System.out.println("能客户端终止了连接Connection reset!!");
				}
				catch(java.io.NotSerializableException nse){
					nse.printStackTrace();
					System.out.println("not serializable");
				}
			}
		
	}
	//-------------------------------------------socket starting and closing---------------------------------------------------//

	private void startServerSocket(int port) throws Exception {
		serverSocket = new ServerSocket(port);
	}
	private void reset(){
		
	}
	public void close(){
		try{
			//Thread.currentThread().interrupt();
			this.socket.close();
			this.serverSocket.close();
			
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

//暂时没有用到
// 每次接到请求 启动的线程
class OneService implements Runnable {
	MyServer myserver;
	Socket socket;
	// 存放结果
	Map<String, String> result;
	public boolean isDone = false;

	public OneService(MyServer myserver, Socket socket) {
		this.myserver = myserver;
		this.socket = socket;
		result = new HashMap<String, String>();
		isDone = false;
	}

	public void run() {
		try {
			myserver.service( socket);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			synchronized (this) {
				isDone = true;
				notifyAll();
			}
		}
	}

	public boolean isDone() {
		return this.isDone;
	}
	
	public Map<String, String> getResult() throws Exception {
		// /如果没有完成，那么等待
		synchronized (this) {
			if (!isDone) {
				wait();
			}
		}
		return result;
	}
	
}
