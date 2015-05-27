package iscas.tca.ake.demoapp.mvc.module.tools;

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
