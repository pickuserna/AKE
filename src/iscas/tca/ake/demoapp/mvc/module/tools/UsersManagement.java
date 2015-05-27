package iscas.tca.ake.demoapp.mvc.module.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import iscas.tca.ake.demoapp.mvc.module.Config;
import iscas.tca.ake.demoapp.mvc.module.EnumTags;
import iscas.tca.ake.veap.User;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/*
 * Copyright (c) 20014-2041 Institute Of Software Chinese Academy Of Sciences
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @Organization: http://tca.iscas.ac.cn/
 * @author: Nan Zhou
 * @Aknowledge: Tutor Liwu Zhang , Alumnus Yan Zhang, Zhigang Gao
 * @Email: changchangge123@qq.com
 */
public class UsersManagement {
	static String ID = "id";
	static String PASSWORD = "password";
	static String GROUPID = "groupID";
	static String ISPASSED = "isPassed";
	static String ROOT  = "users";
	//record to file
	public static synchronized void recordUserToFile(String id, String password,String groupID, boolean isPassed, boolean isPlain, String filePath)throws Exception{
		if(!isPlain){
			//encode the plain text
			id= plainToCipher(id);
			password = plainToCipher(password);
			groupID  = plainToCipher(groupID);
		}
		XMLTools xml = new XMLTools();
		Document doc = xml.openXML(filePath, ROOT).getDocument();
		Node client = xml.appendElement(doc.getDocumentElement(), "client");
		xml.appendTextElement(client, ID, id);
		xml.appendTextElement(client, PASSWORD,password);
		xml.appendTextElement(client, GROUPID,groupID);
		xml.appendTextElement(client, ISPASSED, isPassed+"");
		xml.writeToFile(filePath);
	}
	@Test
	public void getUsers(){
		try{
			User[] users = readUsersOfGroup("group_U", true, "database\\users");
			System.out.println(users);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * TODO:<read users from database, if groupID==Null, then read users of all the groups>
	 * @param groupID <null -> all the groups>
	 * @param isPlain <the type of the storage ,either plain or encrypted>
	 * @param filePath <filepath of the database>
	 * @return the corresponding users of the giving group or all the groups
	 * @throws Exception 
	 */
	
	public static User[] readUsersOfGroup(String groupID, boolean isPlain, String filePath)throws Exception{
		XMLTools xmlTools = new XMLTools(filePath, EnumTags.UsersRootTag.toString());
		
		NodeList nl = xmlTools.getDocument().getElementsByTagName(EnumTags.UserTag.toString());
		List<User> listUser = new ArrayList<User>();
		for(int i=0; i<nl.getLength(); i++){
			//changed
			String id = xmlTools.getEndNodeValue(nl.item(i), EnumTags.UserIdTag.toString());
			String password =  xmlTools.getEndNodeValue(nl.item(i), EnumTags.UserPasswordTag.toString());
			String readGroupID = xmlTools.getEndNodeValue(nl.item(i), EnumTags.GroupIdTag);
			if(!isPlain){
				id = cipherToPlain(id);
				password = cipherToPlain(password);
				readGroupID = cipherToPlain(readGroupID);
			}
			if(groupID!=null){
				if(readGroupID.equals(groupID)){
//				users[j++] = new User(id, password);
					listUser.add(new User(id, password));
//				UtilMy.print(users[j]);
				}
			}
			else if(groupID==null){
				listUser.add(new User(id, password));
			}
		}
		User[] users = new User[listUser.size()];
		return (User[])listUser.toArray(users);
	}
	private static String plainToCipher(String plain){
			return UtilMy.base64_En(plain);
	}
	private static String cipherToPlain(String cipher){
		return UtilMy.base64_De(cipher);
	}
	@Test
	public void testUser()throws Exception {
		Config c = Config.newInstance(Config.ConfigPath);
		readUsersOfGroup("group_U", false, c.getUsersFilePath());
		readUsersOfGroup("group_U", true, c.getUsersFilePath());
		
	}
	
	public static boolean isLengthLegle(String userid, String password){
		if(userid.length()<3 || password.length()<8){
			return false;
		}
		return true;
	}
	public static boolean isRegist(String id, String groupID)throws Exception{
			Config c = Config.newInstance(Config.ConfigPath);
			User[] users = (c.getUsers(groupID));
			if(users!=null){
				for(User u :users){
					if(u.user_id.equals(id)){
						return true;
					}
				}
			}
			return false;
	}
	
	public static String[] getAllGroupID(String groupFilePath){
		XMLTools xmlTool = new XMLTools(groupFilePath, "groups");
		String[] ss = xmlTool.getAllTagValues(EnumTags.GroupIdTag);
		return ss;
	}
//	public static String[] getAllUsers(String groupID, String filePath){
//		XMLTools xml = new XMLTools(filePath, ROOT);
//		NodeList nl = xml.getDocument().getElementsByTagName(groupID);
//		Set<String> idSet = new TreeSet<String>();
//		for(int i=0; i<nl.getLength(); i++){
//			nl.item(i).normalize();
//			if(nl.item(i).getFirstChild().getNodeValue().equals(groupID)){
//				String idValue = xml.getSubValue(nl.item(i).getParentNode(), ID);
//				idSet.add(idValue);
//			}
//		}
//		//ugly
//		String[] arr = new String[idSet.toArray().length];
//		return idSet.toArray(arr);
//	}
}
