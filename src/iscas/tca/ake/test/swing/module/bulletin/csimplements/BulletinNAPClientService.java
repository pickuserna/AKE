package iscas.tca.ake.test.swing.module.bulletin.csimplements;

import iscas.tca.ake.napake.calculate.IfcNapAKECalculate;
import iscas.tca.ake.test.swing.module.EnumTags;
import iscas.tca.ake.test.swing.module.bulletin.csdata.NAPS2CMsg;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClientService;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcWaitorAndNotifier;
import iscas.tca.ake.test.swing.module.tools.SendAndRecv;

import java.math.BigInteger;
import java.net.Socket;

public class BulletinNAPClientService implements IfcBulletinNAPClient, IfcBulletinNAPClientService, IfcWaitorAndNotifier {
	@Override
	public String getMsgType(){
		throw new UnsupportedOperationException();
	}
	@Override
	public void service(Socket socket, String groupID, String id, String password, IfcNapAKECalculate napCalculate ) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("BulletinNAPClientService send groupID"+groupID);
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
				BigInteger A = bncs.getA();
				System.out.println("A:"+A.toString());
				SendAndRecv.sendMsg(A, socket);
				BigInteger Ax = (BigInteger)SendAndRecv.recvMsg(socket);
				bncs.calculateIndex(Ax);
				this.bulletinNAPClient = bncs;
				//set done flag;
				doneIt();
			}
		}
		
		System.out.println("NAP Bulletin done!!");
		System.out.println("admin's index is "+ bulletinNAPClient.index("admin"));
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
