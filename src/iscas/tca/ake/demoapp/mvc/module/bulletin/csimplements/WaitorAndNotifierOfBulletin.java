package iscas.tca.ake.demoapp.mvc.module.bulletin.csimplements;

import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcWaitorAndNotifier;

import java.util.HashMap;
import java.util.Map;
//synchronize mechanism
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
public class WaitorAndNotifierOfBulletin implements IfcWaitorAndNotifier {
	private static Map<String, WaitorAndNotifierOfBulletin> wnMap = new HashMap<String, WaitorAndNotifierOfBulletin>();
	private String groupID;
	private WaitorAndNotifierOfBulletin(){
	}
	//get the waitorAndNotifier for the specific groupID
	public static synchronized WaitorAndNotifierOfBulletin getWaitorAndNotifier(String groupID){
		WaitorAndNotifierOfBulletin wn =null;
			wn = wnMap.get(groupID);
			if(wn==null){
				wn = new WaitorAndNotifierOfBulletin();
				wn.groupID = groupID;
			}
			wnMap.put(groupID, wn);
		return wn;
	}
	
	public void waitForDone() throws Exception{
				this.wait();
	}
	public void doneIt(){
		synchronized(this){
			this.notifyAll();
		}
	}
	public static void remove(String groupID){
		wnMap.remove(groupID);
	}
	public void remove(){
		remove(groupID);
	}
}
