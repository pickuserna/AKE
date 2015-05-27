package iscas.tca.ake.demoapp.mvc.module.bulletin.csimplements;

import iscas.tca.ake.demoapp.mvc.module.bulletin.csdata.NAPS2CMsg;
import iscas.tca.ake.demoapp.mvc.module.bulletin.csdata.NAPS2CMsg.ConfigMsg;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPServerSecurity;
import iscas.tca.ake.demoapp.mvc.module.tools.SendAndRecv;
import iscas.tca.ake.napake.calculate.IfcNapAKECalculate;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.hashs.H256;
import iscas.tca.ake.util.rand.Rand;
import iscas.tca.ake.veap.IfcGetUsers;
import iscas.tca.ake.veap.User;

import java.math.BigInteger;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class ServerData{
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
public NAPS2CMsg.ConfigMsg configMsg;
	public BigInteger randx;
	/**
	 * @param configMsg
	 * @param randx
	 */
	public ServerData(ConfigMsg configMsg, BigInteger randx) {
		super();
		this.configMsg = configMsg;
		this.randx = randx;
	}
	
}
//singleton pattern
public class BulletinNAPServerSecurity implements IfcBulletinNAPServerSecurity {
	BigInteger g;
	BigInteger q;
	
	//cal variables
	BigInteger X;
	IfcGetUsers getUsers;
	private Map<String, ServerData> serverDataLib = new HashMap<String, ServerData>();
	private IfcNapAKECalculate napCalculate;
	private static BulletinNAPServerSecurity bulletinNAPServerSecurity;
	//===========================constructors==================================
	public static BulletinNAPServerSecurity newInstance(){
		if(bulletinNAPServerSecurity==null){
			bulletinNAPServerSecurity = new BulletinNAPServerSecurity();
		}
		return bulletinNAPServerSecurity;
	}
	private BulletinNAPServerSecurity(){
		
	}
	//===========================constructors==================================
	private ServerData calServerData(String groupID){
		User[] users = getUsers.getUsers(groupID);
		//this is for test
//		BigInteger randx = new BigInteger("2");
		BigInteger randx = (new Rand()).randOfMax(q);
		
		X = Assist.modPow(g, randx, q);
		byte[][] cjs = new byte[users.length][];
		for(int i=0; i<users.length; i++){
			String id =  users[i].user_id; 
			String password = users[i].user_pw;
			BigInteger pvd = napCalculate.getPW(id, password, q);
			
			BigInteger pvdX = Assist.modPow(pvd, randx, q);
			if(i==0)
				System.out.println(i+"::::"+users[i].user_id +"\npvdX:"+pvdX +"\npvd:"+pvd+"\nrandx:"+ randx+"\nX:"+X+"\ng:"+g+"\nq:"+q);
			String s = Assist.connectStrings(id, X.toString(),pvd.toString(), pvdX.toString()).toString();
			cjs[i] = new H256().process(s);
		}
		return new ServerData(new NAPS2CMsg.ConfigMsg(g, q, X, cjs),randx);
	}
	
	
	public BigInteger calAx(BigInteger A, BigInteger randx){
		return Assist.modPow(A, randx, q);
	}
	@Override
	public void service(Socket socket, IfcGetUsers getUsers, BigInteger g, BigInteger q, IfcNapAKECalculate napCalculate) throws Exception {
		// TODO Auto-generated method stub
		
		ServerData sd = null;	
		String groupID = null;
		this.getUsers = getUsers;
		this.g = g;
		this.q = q;
		this.napCalculate = napCalculate;
		
		try{
			//receiving groupID from client
			groupID = (String) SendAndRecv.recvMsg(socket);
			//!!!!!--------------there is a bug --------------------!!!!!!!!!!!
			//1 号线程 和 2 ， 3 号线程同时进入到该代码段， 发现sd==null
			//开始3个计算sd 的线程并依次覆盖先计算好的，这样导致先计算好的程序发送给客户端的X 与服务端再次使用的X不一致
			//adjusting 
			synchronized(this){
				sd  = serverDataLib.get(groupID);
				if(sd==null){
					//only should to be executed once
					sd = this.calServerData(groupID);
				}
			}
			//--->ConfigMsg
			System.out.println("sending ConfigMsg____+++++++++++++++");
			
			SendAndRecv.sendMsg(sd.configMsg, socket);
			
			System.out.println("sending ConfigMsg over >>> recv A....");
			
			BigInteger A = (BigInteger)SendAndRecv.recvMsg(socket);
			
			System.out.println("A:"+A);
			
			System.out.println("Sending Ax to Client ");
			//sending Ax to Client;
			SendAndRecv.sendMsg(this.calAx(A, sd.randx), socket);
			System.out.println("Sending Ax Over!!!");
			//record the bulletin data of the groupID in the bulletinServerLib
		}
		finally{
			WaitorAndNotifierOfBulletin wnb = WaitorAndNotifierOfBulletin.getWaitorAndNotifier(groupID);
			synchronized (wnb){
				serverDataLib.put(groupID, sd);
				System.out.println("BulletinNAPServerHash notify all +++ "+ groupID);
				wnb.doneIt();
			}
		}
	}
	@Override
	public String getConnectedPseudonyms(String groupID) throws Exception {
		// TODO Auto-generated method stub
		ServerData sd = this.serverDataLib.get(groupID);
		
		WaitorAndNotifierOfBulletin wnd = WaitorAndNotifierOfBulletin.getWaitorAndNotifier(groupID);
		synchronized(wnd){
			if(sd==null){
				wnd.waitForDone();
			}
		}
		sd = this.serverDataLib.get(groupID);
		return sd.configMsg.getConnectedPseus();
	}
	@Override
	public void service(Socket socket, IfcGetUsers getUsers) throws Exception {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		this.serverDataLib.clear();
		bulletinNAPServerSecurity = null;
		
	}
}
