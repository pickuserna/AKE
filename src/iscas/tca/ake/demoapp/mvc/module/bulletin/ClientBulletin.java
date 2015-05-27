package iscas.tca.ake.demoapp.mvc.module.bulletin;

import iscas.tca.ake.demoapp.mvc.module.bulletin.csimplements.BulletinNAPClientService;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPClientService;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinVEAPClient;
import iscas.tca.ake.demoapp.mvc.module.tools.SendAndRecv;
import iscas.tca.ake.napake.calculate.IfcNapAKECalculate;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.veap.calculate.U_C;

import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author zn
 * @CreateTime 2014-10-13ÏÂÎç3:07:02
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
public class ClientBulletin extends IfcBulletinVEAPClient implements Runnable, IfcBulletinNAPClient{
	private boolean isDone = false;
	private IfcBulletinVEAPClient bulletinVEAPClient;
	private SocketAddress addr;
	String groupID;
	String proType;
	//
	String id;
	String password;
	private IfcNapAKECalculate napCalCulate;
	private  BulletinNAPClientService bulletinNAPClientService = new BulletinNAPClientService();
	
	//get index of the id
	public int index(String id){
		return bulletinNAPClientService.index(id);
	}
	//get connected String
	public String getConnectedPseus(){
		return bulletinNAPClientService.getConnectedPseus();
	}
	public void setProType(String proType){
		this.proType = proType ;
	}
	//set BulletinNAP
//	public ClientBulletin(String groupID, SocketAddress addr){
//		this.addr = addr;
//		isDone = false;
//		this.groupID = groupID;
//	}
	
	//the ClientBulletin for security mode
	public ClientBulletin (String groupID, SocketAddress addr, String id, String password, IfcNapAKECalculate napCal){
		this.addr = addr;
		this.isDone = false;
		this.groupID = groupID;
		this.id = id;
		this.password = password;
		this.napCalCulate = napCal;
	}
	
	
	public void run(){
		
		isDone = false;
		try{
			Socket socket = new Socket();
			socket.connect(addr);
			//++++++++++++nap+++++++++++++++++
			if(this.proType.equals("NAP")){
				//synchronized;  maybe nullPointer
				this.bulletinNAPClientService.service(socket, this.groupID, id, password, napCalCulate);
			}
			//------------nap-----------------
			//++++++++++++veap++++++++++++++++
			if(this.proType.equals("VEAP")){
				SendAndRecv.sendMsg(groupID, socket);
				this.bulletinVEAPClient = (IfcBulletinVEAPClient)SendAndRecv.recvMsg(socket);
				System.out.println("bulletinCLient receive 2"+this.bulletinVEAPClient);
				
				synchronized(this){
					isDone = true;
					this.notifyAll();
				//-------------veap----------------
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("existing the bulletin client");
	}
	
	@Override
	public String getMsgType() {
		// TODO Auto-generated method stub
		return null;
	}
	//a Future task
	@Override
	public IfcBulletinVEAPClient fetchData(String groupID) throws Exception {
		if(!isDone){
			synchronized(this){
				System.out.println("bulletin 3: waiting ");
				this.wait();
				System.out.println("bulletin 3: out of the waiting ");
				
			}
		}
		return this.bulletinVEAPClient;
	}

	@Override
	public String getGroupID() {
		// TODO Auto-generated method stub
		return this.bulletinVEAPClient.getGroupID();
	}

	@Override
	public BigInteger getX() {
		// TODO Auto-generated method stub
		return this.bulletinVEAPClient.getX();
	}

	@Override
	public U_C[] getU_Cs() {
		// TODO Auto-generated method stub
		return this.bulletinVEAPClient.getU_Cs();
	}

	@Override
	public Long getT() {
		// TODO Auto-generated method stub
		return this.bulletinVEAPClient.getT();
	}

	@Override
	public Long getT0() {
		// TODO Auto-generated method stub
		return this.bulletinVEAPClient.getT0();
	}

	@Override
	public void setGroupID(String groupID) {
		// TODO Auto-generated method stub
		this.bulletinVEAPClient.setGroupID(groupID);
	}

	@Override
	public void setU_Cs(U_C[] ucs) {
		// TODO Auto-generated method stub
		this.bulletinVEAPClient.setU_Cs(ucs);
	}

	@Override
	public void setX(BigInteger x) {
		// TODO Auto-generated method stub
		this.bulletinVEAPClient.setX(x);
	}

	@Override
	public void setT(Long t) {
		// TODO Auto-generated method stub
		this.bulletinVEAPClient.setT(t);
	}

	@Override
	public void setT0(Long t0) {
		// TODO Auto-generated method stub
		this.bulletinVEAPClient.setT0(t0);
	}	
}
