package iscas.tca.ake.demoapp.localtest;

import iscas.tca.ake.demoapp.mvc.controler.ClientControlor;
import iscas.tca.ake.demoapp.mvc.module.tools.SendAndRecv;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.message.nap.EnumNAPMsgType;
import iscas.tca.ake.message.nap.NAPMessage;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
public class TestDisturbance implements Runnable{

	/**
	 * TODO:<>
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestDisturbance().run();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			missedMsg();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//seding missed Message to the server 
	public void missedMsg()throws Exception{
		
		NAPMessage.GroupIDData gid = NAPMessage.GroupIDData.getGroupIDData("group_U");
		
		System.out.println("testMessage");
		
        IfcMessage m1 = new NAPMessage(gid, EnumNAPMsgType.GroupID);
        Socket s = new Socket("localhost", 8007);
        SendAndRecv.sendMsg(m1, s);
        SendAndRecv.recvMsg(s);
	}
	
	public void wrongMsg()throws Exception{
		
	}
}
