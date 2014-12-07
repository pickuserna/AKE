package iscas.tca.ake.test.swing.module.bulletin.interfaces;

import java.net.Socket;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-11-13ÏÂÎç3:20:33
 */
public interface IfcBulletinNAPClient {
	//get the index of the id in the group
	int index(String id);
	//get the connected Pseudonyms of the group
	String getConnectedPseus();
	
}
