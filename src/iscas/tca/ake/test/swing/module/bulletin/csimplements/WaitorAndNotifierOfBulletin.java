package iscas.tca.ake.test.swing.module.bulletin.csimplements;

import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcWaitorAndNotifier;

import java.util.HashMap;
import java.util.Map;
//synchronize mechanism
public class WaitorAndNotifierOfBulletin implements IfcWaitorAndNotifier {
	private static Map<String, WaitorAndNotifierOfBulletin> wnMap = new HashMap<String, WaitorAndNotifierOfBulletin>();
	private String groupID;
	private WaitorAndNotifierOfBulletin(){
	}
	//get the waitorAndNotifier for the specific groupID
	public static WaitorAndNotifierOfBulletin getWaitorAndNotifier(String groupID){
		WaitorAndNotifierOfBulletin wn =null;
		synchronized(groupID){
			wn = wnMap.get(groupID);
			if(wn==null){
				wn = new WaitorAndNotifierOfBulletin();
				wn.groupID = groupID;
			}
			wnMap.put(groupID, wn);
		}
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
