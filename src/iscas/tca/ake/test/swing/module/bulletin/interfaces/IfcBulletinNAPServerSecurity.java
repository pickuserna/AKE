package iscas.tca.ake.test.swing.module.bulletin.interfaces;

import iscas.tca.ake.napake.calculate.IfcNapAKECalculate;
import iscas.tca.ake.veap.IfcGetUsers;

import java.math.BigInteger;
import java.net.Socket;

public interface IfcBulletinNAPServerSecurity extends IfcBulletinNAPServerHash{
	void service(Socket socket,IfcGetUsers getUsers, BigInteger g, BigInteger q, IfcNapAKECalculate napCalculate)throws Exception;
	//public NAPS2CMsg getConfigMsg(String groupID);
	public BigInteger calAx(BigInteger A, BigInteger randx);
}
