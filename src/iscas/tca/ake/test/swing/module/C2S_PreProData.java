package iscas.tca.ake.test.swing.module;

import java.io.Serializable;

public class C2S_PreProData implements Serializable{
	String groupID;
	String httpSessionID;
	/**
	 * @param groupID
	 * @param sessionID
	 */
	public C2S_PreProData(String groupID, String sessionID) {
		super();
		this.groupID = groupID;
		this.httpSessionID = sessionID;
	}
	
}	
