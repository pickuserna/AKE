package iscas.tca.ake.demoapp.localtest;

import iscas.tca.ake.demoapp.mvc.controler.ClientControlor;
import iscas.tca.ake.demoapp.mvc.module.EnumTags;
import iscas.tca.ake.demoapp.mvc.module.Response;
import iscas.tca.ake.demoapp.mvc.module.tools.FileOperator;
import iscas.tca.ake.demoapp.mvc.view.observer.IfcObserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
class ObsPerformance implements IfcObserver{
	FileWriter fileWriter;
	
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
public ObsPerformance(String recordFilepath){
	
		try {
			File f = FileOperator.openOrCreateFile(recordFilepath);
			fileWriter  = new FileWriter(f,true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HH:mm:ss");
			Date d = new java.util.Date();
			String now = sdf.format(d);
			fileWriter.append("Test Performance :"+now);
			fileWriter.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Response result) {
		
		// TODO Auto-generated method stub
		Boolean isRec = (Boolean)result.getArg("isRec");
		if(!isRec){
			return ;
		}
		else{
			try {
	//			System.out.println(result);
				fileWriter.append(result.toString());
				fileWriter.flush();
				close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void close(){
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateExecution(Response response) {
		// TODO Auto-generated method stub
		
	}
	
}
public class TestMessage implements Runnable{

	/**
	 * TODO:<>
	 * @param args 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		String hostname = "localhost";
//		int port = 8007;
//		
//		NAPMessage.GroupIDData gid = NAPMessage.GroupIDData.getGroupIDData("group_U");
//		
//        IfcMessage m1 = new NAPMessage(gid, EnumNAPMsgType.GroupID);
//        Socket s = new Socket(hostname, port);
//        SendAndRecv.sendMsg(m1, s);
//        SendAndRecv.recvMsg(s);
//		testMessage();
		ScheduleTask st = new ScheduleTask();
//		st.executeSchedule(new TestMessage(), 1000L, 1000);
		Response resp = new Response(new ObsPerformance("F:\\ÈÎÎñ\\studio\\AKE-document\\performance.txt"));
		resp.setArg("isRec", false);
		st.executeSchedule(TestMessage.class, 36000000L, 10, resp);
		System.out.println("Test Message over!!!");
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			testMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testMessage()throws Exception{
		System.out.println("testMessage");
		Map<String, Object>frameArgs = new HashMap<String, Object>();
		setArgs(frameArgs);
		ClientControlor cc = new ClientControlor(new ObsMsg(), frameArgs);
		cc.runIt();
	}
	public static void setArgs(Map<String, Object> frameArgs)throws Exception
	{
		frameArgs.put("name","user");
		frameArgs.put("groupID", "group_U");
		frameArgs.put("password", "user1234");
		frameArgs.put("host", "localhost");
		frameArgs.put("port", 8007);
		frameArgs.put(EnumTags.PortBulletin, 7070);
	}
	}


class ObsMsg implements IfcObserver{

	@Override
	public void update(Response result) {
		// TODO Auto-generated method stub
		//System.out.println(result);
	}

	@Override
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		System.out.println(status);
	}

	@Override
	public void updateExecution(Response response) {
		// TODO Auto-generated method stub
		//System.out.println(response);
	}
	
}
