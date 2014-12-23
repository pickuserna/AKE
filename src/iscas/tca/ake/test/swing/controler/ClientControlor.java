package iscas.tca.ake.test.swing.controler;

import iscas.tca.ake.napake.calculate.NAPAKECalculate;
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
 * @author zn
 * @CreateTime 2014-9-28����2:41:00
 */
public class ClientControlor implements IfcClientControler{
	MyClient m_myclient;
	Response m_response;
	Socket m_socket;
	Map<String, Object>m_frameArgs;
	ClientBulletin m_clientBulletin = null;
	ExecutorService m_executorService;// = Executors.newSingleThreadExecutor();
	String httpSessionID;
	/**
	 * TODO:<before the execution of the protocol,prepare for the protocol, > 
	 */
	//ClientControlor with web structure
	
	
	//frameArgs:name,password,groupID,host,port    ++++++++   httpSsessionID
	public ClientControlor(IfcObserver obs, Map<String, Object> frameArgs){
		this.m_frameArgs = frameArgs;
		m_response = new Response(obs);
	}
	public void runIt(){
		try{
			///prepare the bulletin and set the args
			m_response.setStatus("connecting...");
			
				this.preProtocol();
			//verifying user
			m_response.setStatus("verifying...");
			
			System.out.println("verifying...");	
				boolean isVerified = m_myclient.service();
				m_response.update(m_response);
						 
				m_response.setStatus("isVerified: "+isVerified+(isVerified?"@_@":"!!!"));
		}catch(ConnectException ce){
			ce.printStackTrace();
			m_response.setStatus("!!can not connect..");
		}
		catch(Exception e){
			e.printStackTrace();
			m_response.setStatus("Failed!!");
		}
		finally{
			try{
				if(this.m_socket!=null){
					this.m_socket.close();
				}
				this.m_executorService.shutdownNow();
			}catch(Exception e){
				System.out.println("cannot close the client socket");
				e.printStackTrace();
			}
		}
	}
	
	private void preProtocol()throws Exception{
		m_myclient = new MyClient();
		m_frameArgs.put("result", this.m_response);
		// start the connection c
		this.m_socket = new Socket((String)m_frameArgs.get("host"), (Integer)m_frameArgs.get("port"));
		System.out.println("connect is ok..."+m_frameArgs.get("name")+m_frameArgs.get("password"));
		
		this.m_clientBulletin = new ClientBulletin((String)this.m_frameArgs.get("groupID"), new InetSocketAddress((String)m_frameArgs.get("host"), 7070),
				(String)this.m_frameArgs.get("name"),
				(String)this.m_frameArgs.get("password"),
				new NAPAKECalculate());
		
		m_myclient.prepareClient(m_socket,  this.m_clientBulletin ,Collections.unmodifiableMap(m_frameArgs),this.m_response);
		// bulletin service 
		System.out.println("client booting");
		//set bulletin proType
		this.m_clientBulletin.setProType(this.m_response.getProType());
		m_executorService = Executors.newCachedThreadPool();
		m_executorService.execute(this.m_clientBulletin);
		
		this.m_response.setStatus("preProtocol is ok....");
	}
	@Override
	public void setArgs(IfcObserver obs, Map<String, Object> frameArgs) {
		// TODO Auto-generated method stub
		this.m_response = new Response(obs);
		this.m_frameArgs = frameArgs;
		
	}
	@Override
	public byte[] getSessionKey() {
		// TODO Auto-generated method stub
		return this.m_myclient.getAkeProtocol().getsk();
	}
}
