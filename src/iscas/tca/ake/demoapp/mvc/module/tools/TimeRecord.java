package iscas.tca.ake.demoapp.mvc.module.tools;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
