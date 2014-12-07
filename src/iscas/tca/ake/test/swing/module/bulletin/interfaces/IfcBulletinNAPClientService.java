package iscas.tca.ake.test.swing.module.bulletin.interfaces;

import iscas.tca.ake.napake.calculate.IfcNapCalculate;

import java.net.Socket;

public interface IfcBulletinNAPClientService {
//	void service(Socket socket, String groupID)throws Exception;
	//offer the service of bulletinNAPClient
	void service(Socket socket ,String groupID, String id, String password, IfcNapCalculate napCalculate) throws Exception;
}
