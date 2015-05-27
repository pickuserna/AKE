package iscas.tca.ake.demoapp.localtest;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import iscas.tca.ake.demoapp.mvc.module.Response;
import iscas.tca.ake.demoapp.mvc.module.bulletin.ClientBulletin;
import iscas.tca.ake.demoapp.mvc.view.observer.IfcObserver;
import iscas.tca.ake.napake.calculate.NAPAKECalculate;

/*文件名：TestBulletin.java
 *描述：<描述>
 *修改人：<zn>
 *修改时间：YYYY—MM-DD
 *修改单号:
 *修改内容：<修改内容>
 *创建时间：下午3:18:25
 */
class ObsBulletin implements IfcObserver{

	@Override
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
public void update(Response result) {
		// TODO Auto-generated method stub
		
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
public class TestBulletin implements Runnable{

	/**
	 * TODO:<>
	 * @param args 
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
	
		ScheduleTask st = new ScheduleTask();
//		st.executeSchedule(new TestMessage(), 1000L, 1000);
		st.executeSchedule(TestBulletin.class, 1000L, 5,new Response(new ObsBulletin()));
		System.out.println("Test Message over!!!");
	}
	public void run(){
		SocketAddress sa = new InetSocketAddress("localhost", 7070);
		ClientBulletin cb = new ClientBulletin("group_U", sa, "user", "user1234", new NAPAKECalculate());
		cb.setProType("NAP");
		cb.run();
	}
}
