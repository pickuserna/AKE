package iscas.tca.ake.test.swing.module.bulletin;

import iscas.tca.ake.test.swing.module.tools.SendAndRecv;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.veap.bulletin.IfcBulletinVEAPClient;
import iscas.tca.ake.veap.calculate.U_C;

import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-10-13ÏÂÎç3:07:02
 */
public class ClientBulletin extends IfcBulletinVEAPClient implements Runnable, IfcBulletinNAPClient{
	private boolean isDone = false;
	private IfcBulletinVEAPClient bulletinClient;
	private SocketAddress addr;
	String groupID;
	String proType;
	private IfcBulletinNAPClient bulletinNAP;
	
	//get index of the id
	public int index(String id){
		try{
			synchronized(this){
				if(bulletinNAP==null){
					this.wait();
				}
			}
		}catch(Exception e){
			System.out.println("error at the synchronized index!!");
			e.printStackTrace();
		}
		return bulletinNAP.index(id);
	}
	//get connected String
	public String getConnectedPseus(){
		return bulletinNAP.getConnectedPseus();
	}
	public void setProType(String proType){
		this.proType = proType ;
	}
	//set BulletinNAP
	public ClientBulletin(String groupID, SocketAddress addr){
		this.addr = addr;
		isDone = false;
		this.groupID = groupID;
	}
	
	public void run(){
		
		isDone = false;
		try{
			Socket socket = new Socket();
			socket.connect(addr);
			//send groupID
			System.out.println("bulletin 1 :"+"send groupID");
			SendAndRecv.sendMsg(groupID, socket);
			
			//++++++++++++nap+++++++++++++++++
			if(this.proType.equals("NAP")){
				//asynchronized maybe nullPointer
				
				synchronized(this){
					this.bulletinNAP = (IfcBulletinNAPClient)SendAndRecv.recvMsg(socket);
					this.notifyAll();
				}
				
				System.out.println("NAP Bulletin done!!");
				System.out.println("admin's index is "+ bulletinNAP.index("admin"));
			}
			//------------nap-----------------
			//++++++++++++veap++++++++++++++++
			if(this.proType.equals("VEAP")){
				this.bulletinClient = (IfcBulletinVEAPClient)SendAndRecv.recvMsg(socket);
				System.out.println("bulletinCLient receive 2"+this.bulletinClient);
				
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
		return this.bulletinClient;
	}

	@Override
	public String getGroupID() {
		// TODO Auto-generated method stub
		return this.bulletinClient.getGroupID();
	}

	@Override
	public BigInteger getX() {
		// TODO Auto-generated method stub
		return this.bulletinClient.getX();
	}

	@Override
	public U_C[] getU_Cs() {
		// TODO Auto-generated method stub
		return this.bulletinClient.getU_Cs();
	}

	@Override
	public Long getT() {
		// TODO Auto-generated method stub
		return this.bulletinClient.getT();
	}

	@Override
	public Long getT0() {
		// TODO Auto-generated method stub
		return this.bulletinClient.getT0();
	}

	@Override
	public void setGroupID(String groupID) {
		// TODO Auto-generated method stub
		this.bulletinClient.setGroupID(groupID);
	}

	@Override
	public void setU_Cs(U_C[] ucs) {
		// TODO Auto-generated method stub
		this.bulletinClient.setU_Cs(ucs);
	}

	@Override
	public void setX(BigInteger x) {
		// TODO Auto-generated method stub
		this.bulletinClient.setX(x);
	}

	@Override
	public void setT(Long t) {
		// TODO Auto-generated method stub
		this.bulletinClient.setT(t);
	}

	@Override
	public void setT0(Long t0) {
		// TODO Auto-generated method stub
		this.bulletinClient.setT0(t0);
	}	
}
