package iscas.tca.ake.test.swing.module.bulletin.interfaces;

import iscas.tca.ake.veap.IfcGetUsers;

import java.net.Socket;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-11-14ÏÂÎç4:36:59
 */
public interface IfcBulletinNAPServerHash{
	//get the connected pseudonyms of the group denoted by  the groupID
	String getConnectedPseudonyms(String groupID)  throws Exception;
	//TODO: interact with the client, offer the bulletin service to the client and supply the server with the bulletin Data
	void service(Socket socket,IfcGetUsers getUsers) throws Exception;
	//close the bulletin service, reset the bulletin
	void close()throws Exception;
}
