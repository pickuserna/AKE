package iscas.tca.ake.demoapp.mvc.module;

import iscas.tca.ake.demoapp.mvc.module.tools.FileOperator;
import iscas.tca.ake.demoapp.mvc.module.tools.TimeRecord;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.rand.Rand;
import iscas.tca.ake.veap.User;

import java.io.File;
import java.math.BigInteger;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;


/**
 * ������<>
 * @author zn
 * @CreateTime 2014-10-11����1:31:49
 */
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
public class Session {
	User[] users;
	String groupID;
	Config config;
	BigInteger g;
	BigInteger q;
	String proType;
	Response response;
	private Socket socket;
	
	//date_time_rand
	private String sessionID;
	//before the constructor
	TimeRecord timeRecord = new TimeRecord();
	private String createSessionID(){
		StringBuilder sb= new StringBuilder();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String dateString = formatter.format(currentTime);

		sb.append(dateString+"_"+new HexBinaryAdapter().marshal(new Rand().nextBytes(16)));
		return sb.toString();
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Assist.kvFormat("sessionID", sessionID));
		sb.append(Assist.kvFormat("proType", proType));
		sb.append("bit"+q.bitLength());
		sb.append(response.toString());
		return sb.toString();
	}
	public String getSessionID(){
		return this.sessionID;
	}
	public Session(Config config, Response result){
		this.config = config;
		this.response = result;
		this.sessionID = createSessionID();
	}
	public String getGroupID() {
		return groupID;
	}
	public TimeRecord getTimeRecord() {
		return timeRecord;
	}
	public Config getConfig(){
		return this.config;
	}
	
	public User[] getUsers(){
		if(this.users==null)
			this.users = this.config.getUsers(groupID);
		return this.users;
	}
	public String[] getUserIds(){
		getUsers();                    
		String[] ids = new String[users.length];
		for(int i=0; i<users.length; i++)
		{
			ids[i] = users[i].user_id;
		}
		return ids;
	}public BigInteger getG(){
		return this.g;
	}
	public BigInteger getQ() {
		return q;
	}
	public void setGroupID(String gid){
		this.groupID = gid;
	}
	public void setProType(String proType){
		this.proType = proType;
		
	}
	public String getProType(){
		return proType;
	}
	//add socket
	public void setSocket(Socket sock){
		this.socket = sock;
	}
	public Socket getSocket(){
		return this.socket;
	}
	public Response getResponse(){
		return this.response;
	}
	//session file Operator
	private static String getSessionFilePath(String logDir, String sessionID){
		return logDir+ File.separator+"sessionObs"+File.separator+sessionID;
	}
	
	private String getTextFilePath(){
		return this.config.getLogsDir()+ File.separator+"sessionTxts"+File.separator+new SimpleDateFormat("yyyyMMdd_HH").format(new Date())+"log.txt";
	}
	
	public void recSession(String logDir) throws Exception{
		String filePath = getSessionFilePath(logDir, this.sessionID);
		this.response.writeResponseToFile(filePath);
		FileOperator.writeInto(getTextFilePath(), response.toString());
	}
	public static Response readResponse(String logDir, String sessionID)throws Exception{
		String filePath = getSessionFilePath(logDir, sessionID);
		return Response.readResponseFromFile(filePath);
	}
}
