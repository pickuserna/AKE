package iscas.tca.ake.demoapp.mvc.controler;

import iscas.tca.ake.IfcAkeProtocol;
import iscas.tca.ake.util.rand.Rand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/*
 * Copyright (c) 20014-2041 Institute Of Software Chinese Academy Of Sciences
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @Organization: http://tca.iscas.ac.cn/
 * @author: Nan Zhou
 * @Aknowledge: Tutor Liwu Zhang , Alumnus Yan Zhang, Zhigang Gao
 * @Email: changchangge123@qq.com
 */
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
