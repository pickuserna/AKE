package iscas.tca.ake.test.swing.controler;

import iscas.tca.ake.IfcAkeProtocol;

import java.util.HashMap;
import java.util.Map;

public class WaitorSession {
	private String httpSessionID;
	private boolean isOver;//is verification Protocol over
	private IfcAkeProtocol akeServer;
	
	private static Map<String, WaitorSession> sessionMap = new HashMap<String, WaitorSession>();
	
	/**
	 * @param httpSessionID
	 * @param isOver
	 */
	private WaitorSession(String httpSessionID, boolean isOver) {
		super();
		this.httpSessionID = httpSessionID;
		this.isOver = isOver;
	}
	public static  WaitorSession getInstanceById(String id)throws Exception{
		WaitorSession ws = null;
		synchronized(id){//synchronize the same sessionID 
			ws = sessionMap.get(id);
			if(ws==null){
				ws = new WaitorSession(id, false);
				sessionMap.put(id, ws);
			}
		}
		return ws;
	}
	//set IfcAkeProtocol to the akeServer
	public void setAkeProtocol(IfcAkeProtocol serverProtocol){
		this.akeServer = serverProtocol;
	}
	//get result of the session with the 'id'
	public IfcAkeProtocol getResultAkeProtocol() throws Exception{
		synchronized(this){
			if(this.isOver==false){
				this.wait();
			}
		}
		return this.akeServer;
	}
	public boolean isOver(){
		return this.isOver;
	}
	
	public String getHttpSessionID(){
		return this.httpSessionID;
	}
	//ake protocol over and notify all the waitors
	public void overAndNotify(){
		synchronized(this){
			this.isOver = true;
			this.notifyAll();
		}
	}
	public void invalidWaitor(){
		sessionMap.remove(this.httpSessionID);
	}
}
