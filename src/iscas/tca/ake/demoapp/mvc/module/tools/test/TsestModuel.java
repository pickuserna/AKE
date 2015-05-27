package iscas.tca.ake.demoapp.mvc.module.tools.test;

import iscas.tca.ake.demoapp.mvc.module.tools.GroupManage;
import iscas.tca.ake.demoapp.mvc.module.tools.UsersManagement;
import iscas.tca.ake.demoapp.mvc.module.tools.UtilMy;
import iscas.tca.ake.demoapp.mvc.module.tools.XMLTools;

import java.math.BigInteger;

import org.w3c.dom.Document;

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
public class TsestModuel {
	static String userPath = "E:\\test\\xml\\users.xml";
	static String groupPath = "E:\\test\\xml\\group.xml";
//	static String configPath = "E:\\test\\xml\\config.xml";
	static String testPath = "E:\\test\\xml\\testXML.xml";
	/**
	 * @param args
	 */
	public static void testConfig(int bit, int seednum)throws Exception{
	//	BigInteger g = UtilMy.genG(bit, seednum);
		BigInteger q = UtilMy.genQ(bit, seednum);
		String[] tagPath = {"bit_"+bit, "g"};
		
		//UtilMy.recordConfigToFile(configPath, tagPath, g.toString());
		tagPath[1] ="q";
		UtilMy.recordConfigToFile(testPath, tagPath, q.toString());
	}
	public static void testUser()throws Exception{
		UsersManagement.recordUserToFile("xiaoming", "12345","meiguoren",  true, true, userPath);
	}
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
//		testUser();
//		testGroupManage();
//		testConfig(128, 100);
		testSetNode();
		testGetNode();
		testTraverse();
//		testShowAllUsers();
//		XMLTools xml2 = new XMLTools("E:\\test\\xml\\groups.xml", "groups");
//		String[] ss = xml2.getAllTagValues("groupID");
//		UtilMy.print(ss);
		
	}
	public static void testTraverse()throws Exception{
		XMLTools xmlTool = new XMLTools(testPath, "config");
		
		xmlTool.showXML();
	}
	public static void testSetNode()throws Exception{
		XMLTools xml = new XMLTools();
		xml.openXML(testPath, "config");
		//1
		String[] tagPath = {"serverConfig","sid"};
		xml.setNode(tagPath, "SID");
		//4
				String[] tagPath4 = { "filePath", "bulletinPath", };
				xml.setNode(tagPath4, "444444444444444444");
		//2
		String[] tagPath2 = {"serverConfig", "filePath", "bulletinPath", };
		xml.setNode(tagPath2, "E:\\test\\bulletinDir");
		//3
		String[] tagPath3 = {"serverConfig", "filePath", "logsPath", };
		xml.setNode(tagPath3, "logsPath");
		xml.writeToFile(testPath);
		
		xml.writeToFile();
	}
	public static void testGetNode()throws Exception{
		String[] tagPath = { "filePath", "logsPath", };
		XMLTools xml = new XMLTools(testPath, "config");
		
		String value = xml.getEndNodeValue(tagPath);
		System.out.println("=++++++++++====="+value);
	}
	public static void testGroupManage()throws Exception{
		GroupManage gm = new GroupManage(testPath);
		gm.addGroup("group_U", "12345678");
	}
	public static void testShowAllUsers()throws Exception{
		//String []ids =  UsersManagement.getAllUsers("meiguoren", userPath);
		//UtilMy.print(ids);
	}
	
}
