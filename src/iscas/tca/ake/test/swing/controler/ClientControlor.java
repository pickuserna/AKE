package iscas.tca.ake.test.swing.controler;

import iscas.tca.ake.test.swing.module.MyClient;
import iscas.tca.ake.test.swing.module.Response;
import iscas.tca.ake.test.swing.module.bulletin.ClientBulletin;
import iscas.tca.ake.test.swing.view.observer.IfcObserver;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ������<>
 * @author zn
 * @CreateTime 2014-9-28����2:41:00
 */
public class ClientControlor {
	MyClient myclient;
	Response response;
	Socket socket;
	Map<String, Object>frameArgs;
	ClientBulletin clientBulletin = null;
	ExecutorService executorService;// = Executors.newSingleThreadExecutor();
	/**
	 * TODO:<Э��ִ��ǰ��׼���׶� ,�������ò���> 
	 * bitλ����Э�����ͣ�ID������groupID, ��Ҫ��server���ɣ���server��ע��
	 */
	//frameArgs:name,password,groupID,host,port;
	public ClientControlor(IfcObserver obs, Map<String, Object> frameArgs){
		this.frameArgs = frameArgs;
		response = new Response(obs);
	}
	public void runIt(){
		try{
			///prepare the bulletin and  set the args 
			response.setStatus("connecting...");
			
				this.preProtocol();
			//verifying user
			response.setStatus("verifying...");
			
			System.out.println("verifying...");	
				boolean isVerified = myclient.service();
				response.update(response);
						 
				response.setStatus("isVerified: "+isVerified+(isVerified?"@_@":"!!!"));
		}catch(ConnectException ce){
			ce.printStackTrace();
			response.setStatus("!!can not connect..");
		}
		catch(Exception e){
			e.printStackTrace();
			response.setStatus("Failed!!");
		}
		finally{
			try{
				if(this.socket!=null){
					this.socket.close();
				}
				this.executorService.shutdownNow();
			}catch(Exception e){
				System.out.println("cannot close the client socket");
				e.printStackTrace();
			}
		}
	}
	
	private void preProtocol()throws Exception{
		myclient = new MyClient();
		frameArgs.put("result", this.response);
		// start the connection c
		this.socket = new Socket((String)frameArgs.get("host"), (Integer)frameArgs.get("port"));
		System.out.println("connect is ok...");
		
		this.clientBulletin = new ClientBulletin((String)this.frameArgs.get("groupID"), new InetSocketAddress((String)frameArgs.get("host"), 7070));
		myclient.prepareClient(socket,  this.clientBulletin ,Collections.unmodifiableMap(frameArgs),this.response);
		// bulletin service 
		System.out.println("client booting");
		//set bulletin proType
		this.clientBulletin.setProType(this.response.getProType());
		executorService = Executors.newCachedThreadPool();
		executorService.execute(this.clientBulletin);
		
		this.response.setStatus("preProtocol is ok....");
	}
}
