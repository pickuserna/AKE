package iscas.tca.ake.demoapp.mvc.module.bulletin.csimplements;

import iscas.tca.ake.demoapp.mvc.module.EnumTags;
import iscas.tca.ake.demoapp.mvc.module.bulletin.csdata.NAPS2CMsg;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPClientService;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcWaitorAndNotifier;
import iscas.tca.ake.demoapp.mvc.module.tools.SendAndRecv;
import iscas.tca.ake.napake.calculate.IfcNapAKECalculate;

import java.math.BigInteger;
import java.net.Socket;

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
public class BulletinNAPClientService implements IfcBulletinNAPClient, IfcBulletinNAPClientService, IfcWaitorAndNotifier {
	@Override
	public String getMsgType(){
		throw new UnsupportedOperationException();
	}
	@Override
	public void service(Socket socket, String groupID, String id, String password, IfcNapAKECalculate napCalculate ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("BulletinNAPClientService send groupID: "+groupID);
		SendAndRecv.sendMsg(groupID, socket);
		
		synchronized(this){
			System.out.println("BulletinNAPClientService recv...."+groupID);
			IfcBulletinNAPClient msg =(IfcBulletinNAPClient) SendAndRecv.recvMsg(socket);
			System.out.println("BulletinNAPClientService recv over ...."+groupID+msg);
			
			//Hash Mode
			if(msg.getMsgType().equals(EnumTags.NapBulletinHashMode)){
				this.bulletinMode = EnumTags.NapBulletinHashMode;
				this.bulletinNAPClient = (IfcBulletinNAPClient)msg;
				doneIt();
			}
			//security mode
			else {
				System.out.println("security mode!!");
				BulletinNAPClientSecurity bncs = new BulletinNAPClientSecurity((NAPS2CMsg.ConfigMsg)msg, id, password, napCalculate);
				//calculate A
				BigInteger A = bncs.getA();
				//sending A to the server
				SendAndRecv.sendMsg(A, socket);
				//receive Ax from the server
				BigInteger Ax = (BigInteger)SendAndRecv.recvMsg(socket);
				bncs.calculateIndex(Ax);
				this.bulletinNAPClient = bncs;
				//set done flag;
				doneIt();
			}
		}
		
		System.out.println("NAP Bulletin done!!");
		System.out.println("index is "+ bulletinNAPClient.index("user"));
		//security  Mode
			//serviceSecurityMode(socket, groupID, id, password, napCalculate);
	}

	IfcBulletinNAPClient bulletinNAPClient;
	
	boolean isDone = false;
	private String bulletinMode;//"hash" or "security"
	
	
	@Override
	public int index(String id) {
		// TODO Auto-generated method stub
		waitForDone();
		return bulletinNAPClient.index(id);
	}
	
	@Override
	public  void waitForDone(){
		try{
			synchronized(this){
				if(isDone==false){
					this.wait();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void doneIt(){
		synchronized(this){
			isDone = true;
			this.notifyAll();
		}
	}
	@Override
	public String getConnectedPseus() {
		// TODO Auto-generated method stub
		waitForDone();
		return bulletinNAPClient.getConnectedPseus();
	}
}
