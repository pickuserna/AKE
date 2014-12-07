package iscas.tca.ake.test.swing.module.bulletin.csimplements;

import iscas.tca.ake.napake.calculate.IfcNapCalculate;
import iscas.tca.ake.test.swing.module.bulletin.csdata.NAPS2CMsg;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.hashs.H256;
import iscas.tca.ake.util.rand.Rand;
import iscas.tca.ake.veap.IfcGetUsers;
import iscas.tca.ake.veap.User;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class BulletinNAPServerSecurity  {
	BigInteger g;
	BigInteger q;
	
	//cal variables
	BigInteger randx;
	BigInteger X;
	IfcGetUsers getUsers;
	private Map<String, NAPS2CMsg.ConfigMsg> configMsgLib = new HashMap<String, NAPS2CMsg.ConfigMsg>();
	private IfcNapCalculate napCalculate;
	
	public BulletinNAPServerSecurity(IfcGetUsers getUsers, BigInteger g, BigInteger q, IfcNapCalculate napCalculate){
		this.g = g;
		this.q = q;
		this.getUsers = getUsers;
		this.napCalculate = napCalculate;
	}
	private NAPS2CMsg.ConfigMsg calConfigMsg(String groupID){
		User[] users = this.getUsers.getUsers(groupID);
		randx = (new Rand()).randOfMax(q);
		X = Assist.modPow(g, randx, q);
		byte[][] cjs = new byte[users.length][];
		for(int i=0; i<users.length; i++){
			String id =  users[i].user_id; 
			String password = users[i].user_pw;
			BigInteger pvd = this.napCalculate.getPW(id, password, q);
			
			BigInteger pvdX = Assist.modPow(pvd, randx, q);
			String s = Assist.connectStrings(id, X.toString(),pvd.toString(), pvdX.toString()).toString();
			cjs[i] = new H256().process(s);
		}
		return new NAPS2CMsg.ConfigMsg(g, q, X, cjs);
	}
	
	public NAPS2CMsg getConfigMsg(String groupID){
		NAPS2CMsg.ConfigMsg cfgMsg = configMsgLib.get(groupID);
		if(cfgMsg==null){
			cfgMsg = calConfigMsg(groupID);
		}
		return new NAPS2CMsg<NAPS2CMsg.ConfigMsg>(cfgMsg, NAPS2CMsg.BulletinNAPMsgType.MSGBulltinTypeConfigSecurity);
	}
	
	public BigInteger calAx(BigInteger A){
		return Assist.modPow(A, randx, q);
	}
}
