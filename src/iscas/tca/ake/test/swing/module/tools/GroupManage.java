package iscas.tca.ake.test.swing.module.tools;

import org.w3c.dom.NodeList;

public class GroupManage {
	String filePath = "E:\\test\\xml\\group.xml";
	final String rootTag = "groups";
	final String groupTag = "group";
	final String idTag = "groupID";
	final String passwordTag = "password";
	
	public GroupManage(String filePath){
		this.filePath = filePath;
	}
	public void addGroup(String groupID, String password)throws Exception{
		XMLTools xml = new XMLTools(filePath, rootTag);
		String[] tagPath = {groupTag, idTag};
		xml.setNode(tagPath, groupID);
		xml.setNode(tagPath, password);
		xml.writeToFile(filePath);
	}
	public String[] getAllGroupIDs(){
		XMLTools xml = new XMLTools();
		NodeList nl = xml.getDocument().getElementsByTagName(idTag);
		String[] groupIDs = new String[nl.getLength()];
		for(int i = 0; i<nl.getLength(); i++){
			groupIDs[i] = nl.item(i).getFirstChild().getNodeValue();
		}
		return groupIDs;
	}
}
