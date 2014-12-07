package iscas.tca.ake.test.swing.module.bulletin.csimplements;

import iscas.tca.ake.test.swing.module.EnumTags;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClientService;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcWaitorAndNotifier;
import iscas.tca.ake.test.swing.module.tools.SendAndRecv;

import java.net.Socket;

public class BulletinNAPClientService implements IfcBulletinNAPClient, IfcBulletinNAPClientService, IfcWaitorAndNotifier {
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

	@Override
	public void service(Socket socket, String groupID) throws Exception{
		// TODO Auto-generated method stub
		//first step : send the groupID to the server
		System.out.println("BulletinNAPClientService send groupID"+groupID);
		SendAndRecv.sendMsg(groupID, socket);
		
		synchronized(this){
			System.out.println("BulletinNAPClientService recv...."+groupID);
			Object msg = SendAndRecv.recvMsg(socket);
			System.out.println("BulletinNAPClientService recv over ...."+groupID+msg);
			//Hash Mode
			if(msg instanceof IfcBulletinNAPClient){
				this.bulletinMode = EnumTags.NapBulletinHashMode;
				this.bulletinNAPClient = (IfcBulletinNAPClient)msg;
				doneIt();
			}
			//security mode 
			else{
				this.bulletinMode = EnumTags.NapBulletinSecurityMode;
				
			}
		}
		
		System.out.println("NAP Bulletin done!!");
		System.out.println("admin's index is "+ bulletinNAPClient.index("admin"));
	}

}
