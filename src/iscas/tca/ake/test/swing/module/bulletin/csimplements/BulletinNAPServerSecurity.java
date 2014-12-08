package iscas.tca.ake.test.swing.module.bulletin.csimplements;

import iscas.tca.ake.napake.calculate.IfcNapCalculate;
import iscas.tca.ake.test.swing.module.bulletin.csdata.NAPS2CMsg;
import iscas.tca.ake.test.swing.module.bulletin.csdata.NAPS2CMsg.ConfigMsg;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPServerSecurity;
import iscas.tca.ake.test.swing.module.tools.SendAndRecv;
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
public class BulletinNAPServerSecurity implements IfcBulletinNAPServerSecurity {
	BigInteger g;
	BigInteger q;
	
	//cal variables
	BigInteger X;
	IfcGetUsers getUsers;
	private Map<String, ServerData> serverDataLib = new HashMap<String, ServerData>();
	private IfcNapCalculate napCalculate;
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
	public void service(Socket socket, IfcGetUsers getUsers, BigInteger g, BigInteger q, IfcNapCalculate napCalculate) throws Exception {
		// TODO Auto-generated method stub
		
		ServerData sd = null;	
		String groupID = null;
		this.getUsers = getUsers;
		this.g = g;
		this.q = q;
		this.napCalculate = napCalculate;
		
		try{
			//<---groupID
			groupID = (String) SendAndRecv.recvMsg(socket);
			sd  = serverDataLib.get(groupID);
			if(sd==null){
				sd = this.calServerData(groupID);
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
