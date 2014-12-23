package iscas.tca.ake.test.swing.controler;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.util.rand.Rand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class WaitorSession {
	private String httpSessionID;
	private boolean isOver;//is verification Protocol over
	private IfcAkeProtocol akeServer;
	private static int LenOfRandSuffix = 8;
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
	//get the WaitorSession by httpSessionID,if there does not exist the WaitorSession with the specific ID,then create a new one
	public static  WaitorSession getInstanceById(String id)throws Exception{
		WaitorSession ws = null;
		synchronized(id){//synchronize on the same sessionID 
			ws = sessionMap.get(id);
			if(ws==null){
				ws = new WaitorSession(id, false);
				sessionMap.put(id, ws);
			}
		}
		return ws;
	}
	public static void remove(String id){
		synchronized(id){
			sessionMap.remove(id);
		}
	}
	public void remove(){
		synchronized(this.httpSessionID){
			remove(this.httpSessionID);
		}
	}
	//Create the httpSessionID when the Browser first request the login Page;
	public static String createHttpSessionID()throws Exception{
		StringBuilder sb= new StringBuilder();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String dateString = formatter.format(currentTime);
		
		sb.append(dateString+"_"+new HexBinaryAdapter().marshal(new Rand().nextBytes(LenOfRandSuffix)));
		return sb.toString();
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
	//AKE protocol over and notify all the waiters
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
