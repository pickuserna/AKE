package iscas.tca.ake.test.swing.module.tools;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TimeRecord implements IfcTimeRecord {
	Map<String, Long>mapResult = new HashMap<String, Long>();
	private Map<String, Long>mapTemp = new HashMap<String, Long>();
	
	//确保tagName增加mapResult中
	@Override
	public void startRec(String tagName) {
		// TODO Auto-generated method stub
		tagName = formatTag(tagName);
		if(!mapResult.containsKey(tagName)){
			mapResult.put(tagName, 0l);
		}
		mapTemp.put(tagName, System.currentTimeMillis());
	}

	public Map<String ,Long> getResult(){
		return Collections.unmodifiableMap(this.mapResult);
	}
	@Override
	public Long endRec(String tagName) {
		// TODO Auto-generated method stub
		tagName = formatTag(tagName);
		if(mapTemp.containsKey(tagName)){
			Long period = System.currentTimeMillis()-mapTemp.get(tagName);
			mapTemp.remove(tagName);
			Long sum = mapResult.get(tagName)+period;
			mapResult.put(tagName, sum);
			return sum;
		}
		else {
			System.out.println("标记不存在");
		}
		return null;
	}
	public String getTag(String tagName){
		String formateTag =this.formatTag(tagName);
		System.out.println(tagName + ":"+this.mapResult.get(formateTag));
		return this.mapResult.get(formateTag).toString();
	}
	public void recTofile(String filePath){
		
	}
	
	@Override
	public int hitRec(String tagName) {
		// TODO Auto-generated method stub
		return 0;
	}
	public void recToFile(String filePath){
		
	}
	public void showResult(){
		for(Map.Entry<String, Long> e:mapResult.entrySet()){
			System.out.println(e.getValue()+"\t:"+e.getKey());
		}
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.mapResult.clear();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		TimeRecord tr = new TimeRecord();
		for(int i=0 ;i<2; i++){
			tr.startRec("A");
				TimeUnit.MILLISECONDS.sleep(100);
			tr.startRec("A  B C");
			tr.endRec("A");
				TimeUnit.MILLISECONDS.sleep(5);
			tr.endRec("ABC");
		}
		tr.showResult();
	}
	//private 
	private String formatTag2(String tagName){
		return tagName.trim().toLowerCase().replaceAll("[' ''\t']", "");
	}
	private String formatTag(String tagName){
		return tagName.trim();
	}
}
