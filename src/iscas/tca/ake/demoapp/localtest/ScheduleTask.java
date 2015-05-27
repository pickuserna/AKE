package iscas.tca.ake.demoapp.localtest;

import iscas.tca.ake.demoapp.mvc.module.Response;
import iscas.tca.ake.demoapp.mvc.view.observer.IfcObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
public class ScheduleTask {

	public ScheduleTask(){
		
	}
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ScheduleTask st  = new ScheduleTask();
		st.executeSchedule(TestTask.class, 2000L, 1000, new Response(new ObsBulletin()));
	}
//	
//	public void setSchedule(Runnable t, Long duration, int interval){
//		this.duration = duration;
//		this.task = t;
//		this.interval = interval;
//	}
	
//	public void executeSchedule(){
//		
//	}
	public void executeSchedule(Class<? extends Runnable> t, Long duration, int interval, Response resp) throws Exception{
		Long preTime = System.currentTimeMillis();
		ExecutorService es = Executors.newCachedThreadPool();
		
		Long now = 0L;
		int i =0;
		
		//record the interval between two tasks
		//List<Long> listInterval = new ArrayList<Long>();
		Long LastTime, tempTime;
		LastTime = now = System.currentTimeMillis();
		while(now-preTime<=duration){
			tempTime = System.currentTimeMillis();
			//listInterval.add(tempTime-LastTime);
			LastTime = tempTime;
			es.execute(t.newInstance());
			System.out.println("\n++++++++++++++++++/////++++++   "+i+++"   +++++/////+++++++++++++++++++");
			TimeUnit.MILLISECONDS.sleep(interval);
			now = System.currentTimeMillis();
		}
		//waiting for the task to be done!!
		TimeUnit.SECONDS.sleep(3);
		//resp.putParameter("Interval List :", Arrays.toString(listInterval.toArray()));
		resp.putParameter("interval:", interval+"");
		resp.putParameter("duration:", duration+"");
		resp.putParameter("now-preTime:", (now-preTime)+"");
		resp.putParameter("frequency:", i+"");
		
		resp.update(resp);
		//System.out.println(Arrays.toString(listInterval.toArray()));
		System.out.println("shutdownnow:"+i+" interval:"+interval+" time:"+(now-preTime));
		//shutdown the task 
		
//		es.shutdown();//this code will keep the thread active 
		es.shutdownNow();// this code caused throwing interrupted Exception ,because of the client terminating the task
	}
	public void executeSchedule(Runnable t, Long duration, int interval) throws Exception{
		Long preTime = System.currentTimeMillis();
		ExecutorService es = Executors.newCachedThreadPool();
		
		Long now = 0L;
		now = System.currentTimeMillis();
		int i =0;
		while(now-preTime<=duration){
			es.execute(t);
			System.out.println(i++);
			TimeUnit.MILLISECONDS.sleep(interval);
			now = System.currentTimeMillis();
		}
		System.out.println("shutdownnow");
		es.shutdownNow();
	}
}
class TestTask implements Runnable {
	String id = null;
	//Exception in thread "main" java.lang.InstantiationException:
	//the class has no nullary constructor
	public TestTask(){
		
	}
	public TestTask(String id){
		this. id = id;
	}
	public void run() {
		System.out.println(System.currentTimeMillis()+"  task"+id);
	}
}
