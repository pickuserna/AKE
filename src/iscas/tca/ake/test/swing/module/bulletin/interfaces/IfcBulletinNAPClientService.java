package iscas.tca.ake.test.swing.module.bulletin.interfaces;

import java.net.Socket;

public interface IfcBulletinNAPClientService {
	void service(Socket socket, String groupID)throws Exception;
}
