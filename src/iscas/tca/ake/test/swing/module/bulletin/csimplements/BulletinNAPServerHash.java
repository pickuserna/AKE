package iscas.tca.ake.test.swing.module.bulletin.csimplements;

import iscas.tca.ake.test.swing.module.bulletin.csdata.BulletinNAPClientHashData;
import iscas.tca.ake.test.swing.module.bulletin.csdata.BulletinNAPServerHashData;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPServerHash;
import iscas.tca.ake.test.swing.module.tools.SendAndRecv;
import iscas.tca.ake.veap.IfcGetUsers;
import iscas.tca.ake.veap.User;

import java.net.Socket;
import java.util.HashMap;

public class BulletinNAPServerHash implements IfcBulletinNAPServerHash{
	
	//outside resource
	private String groupID;
	
	//store all the server bulletin data for every calculated groupID
	private HashMap<String,BulletinNAPServerHashData> bulletinServerLib = new HashMap<String, BulletinNAPServerHashData>();
	private static BulletinNAPServerHash staticBulletinServer;
	//==========================constructor====================================//
	private BulletinNAPServerHash(){
		
	}
	public static synchronized BulletinNAPServerHash newInstance(){
		if(staticBulletinServer==null){
			staticBulletinServer = new BulletinNAPServerHash();
		}
		return staticBulletinServer;
	}
	
	//=============================service===================================//
	//TODO: interact with the client, offer the bulletin service to the client and supply the server with the bulletin Data
	//---------need to change
	@Override
	public void service(Socket socket,IfcGetUsers getUsers) throws Exception{
		BulletinNAPServerHashData bsd = null;
		try{
			groupID = (String) SendAndRecv.recvMsg(socket);
			bsd = bulletinServerLib.get(groupID);
			if(bsd==null){
				// if there exists no serverData of the groupID, recalculate the data for the groupID;
				BulletinNAPClientHashData bn = BulletinNAPClientHashData.newInstance(getIDs(getUsers.getUsers(groupID)), this.getAddin());
				bsd = new BulletinNAPServerHashData(bn);
			}
			SendAndRecv.sendMsg(bsd.bulletinNAPClientData, socket);	
			//record the bulletin data of the groupID in the bulletinServerLib
			System.out.println("NAP Bulletin send message over!");
		}
		finally{
			WaitorAndNotifierOfBulletin wnb = WaitorAndNotifierOfBulletin.getWaitorAndNotifier(groupID);
			synchronized (wnb){
				bulletinServerLib.put(groupID, bsd);
				System.out.println("BulletinNAPServerHash notify all +++ "+ groupID);
				wnb.notifyAll();
			}
		}
	}
	
	@Override
	public String getConnectedPseudonyms(String groupID) throws Exception{
		// TODO Auto-generated method stub
		BulletinNAPServerHashData serverData =null;
		WaitorAndNotifierOfBulletin wnb = WaitorAndNotifierOfBulletin.getWaitorAndNotifier(groupID);
		synchronized(wnb){
			serverData = bulletinServerLib.get(groupID);
			//synchronized with the calculation module
			if(serverData==null){
				wnb.waitForDone();
			}
		}
		serverData = bulletinServerLib.get(groupID);
		return serverData.connectedPseudonyms;
	}
	
	///===========================NAP Bulletin operation assistance============================//
		public String getAddin(){
			return "Addin";
		}
		private String[] getIDs(User[] users){
			String[] ids = new String[users.length];
			for(int i=0; i<users.length; i++){
				ids[i] = users[i].user_id;
			}
			return ids;
		}
		@Override
		public void close() throws Exception {
			// TODO Auto-generated method stub
			bulletinServerLib.clear();
			staticBulletinServer = null;
		}
}
