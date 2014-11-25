package iscas.tca.ake.test.swing.module;

import iscas.tca.ake.test.swing.controler.ProtocolConfigInitData;
import iscas.tca.ake.test.swing.module.tools.FileOperator;
import iscas.tca.ake.test.swing.module.tools.UtilMy;
import iscas.tca.ake.test.swing.view.observer.IfcObserver;
import iscas.tca.ake.util.Assist;

import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-10-11ÏÂÎç7:51:57
 */
//response need to be a singleton 
public class Response implements Serializable, IfcObserver{
	// set and get can access a argMap
	public static final String Separator = "-_-!-_-";
	private Map<String, Object> argMap = new LinkedHashMap<String, Object>();
	//display directly in the result area
	private Map<String, String> resultDisplayMap = new LinkedHashMap<String, String>();
	private IfcObserver observer;
	
		
	//only for your self  ---->   resume from the file
	private Response(){
	}
	//fileOperate --package
	void writeResponseToFile(String filePath) throws Exception{
		Object[] obs = {argMap, resultDisplayMap};
		FileOperator.writeObjectToFile(obs, filePath);
	
	}
	static Response readResponseFromFile(String filePath)throws Exception{
		Response r = new Response();
		Object[] ob = (Object[])FileOperator.readObjectFromFile(filePath);
		r.argMap = (Map<String, Object>)ob[0];
		r.resultDisplayMap = (Map<String, String>)ob[1];
		return r;
	}
	//record the text
	void recordTxt(String logPath) throws Exception{
		FileOperator.writeInto(logPath, this.toString());
	}
	
	
	//in response only two map exists
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n\r\nArgs:\r\n");
		sb.append(Assist.traverseMap(argMap));
		sb.append("result:\r\n");
		sb.append(Assist.traverseMap(resultDisplayMap));
		return sb.toString();
	}
	
	public Response(IfcObserver observer){
		this.observer = observer;
	}
	
	@Override
	public void update(Response result) {
		// TODO Auto-generated method stub
		this.observer.update(this);
	}



	@Override
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		this.observer.setStatus(status);
	}



	@Override
	public void updateExecution(Response response) {
		// TODO Auto-generated method stub
		this.observer.updateExecution(this);
	}



	public boolean isVerified(){
		return this.resultDisplayMap.get("isVerified").equals(true+"");
	}
	public void setVerified(boolean r){
		this.resultDisplayMap.put("isVerified",r+"");
	}
	public String getProType(){
		return argMap.get("proType").toString();
	}
	public void setBit(int bit){
		this.argMap.put("bit", bit+"");
	}
	public String getBit(){
		return this.argMap.get("bit").toString();
	}
	public String getG(){
		return argMap.get("g").toString();
	}
	public String getQ(){
		return argMap.get("q").toString();
	}
	public String getIDNum(){
		return argMap.get("ids").toString();
	}
	public void setProType(String proType){
		this.argMap.put("proType", proType);
	}
	public void setG(BigInteger g){
		this.argMap.put("g",g.toString());
	}
	public void setQ(BigInteger q){
		this.argMap.put("q",q.toString());
	}
	public void setIdsNum(int ids){
		this.argMap.put("ids",ids+"");
	}
	public void setConfigInitData(ProtocolConfigInitData cid){
		
	}
	//config the args
	public void setArg(String key, Object value){
		this.argMap.put(key, value);
	}
	public Object getArg(String key){
		return this.argMap.get(key);
	}
	private void clearArgs(){
		this.argMap.clear();
	}
	@Test
	public void testTreeMap(){
		Map<String, String> tm = new LinkedHashMap<String, String>();
		tm.put("c",	 "c");
		tm.put("a",	 "a");
		tm.put("b",	 "b");
		Iterator<Map.Entry<String, String>> iter = tm.entrySet().iterator();
		while(iter.hasNext()){
			UtilMy.print(iter.next());
		}
	}
	public LinkedHashMap<String, String> getExecutionSteps(){
		Iterator<Map.Entry<String, Object>> iter = this.argMap.entrySet().iterator();
		LinkedHashMap<String, String> exeStep = new LinkedHashMap<String, String>();
		while(iter.hasNext()){
			Map.Entry<String, Object > next  = iter.next();
			String key = next.getKey();
			if(key.toLowerCase().startsWith("step")){
				String[] arr = next.getKey().split(Response.Separator);
				exeStep.put(arr[arr.length-1],next.getValue().toString());
			}
		}
		//System.out.println(Assist.traverseMap(exeStep));
		return exeStep;
	}
	public void putExecutionStep(String step, String content, boolean isUpdate){
		this.argMap.put("step"+ Response.Separator +step, content);
		if(isUpdate){
			this.updateExecution(this);
		}
	}
	
	public void putTimeRecord(Map<String, Long> timeRecord){
		this.argMap.put("timeRecord", timeRecord);
	}
	public Map<String, Long>getTimeRecord(){
		Object tr = this.argMap.get("timeRecord");
		return (Map<String, Long>)tr;
	}
	
	public void putParameter(String key, String value){
		this.resultDisplayMap.put(key, value);
	}
	public String getParameter(String key){
		return this.resultDisplayMap.get(key);
	}
	public Map<String, String>getDisplayMap(){
		return Collections.unmodifiableMap(this.resultDisplayMap);
	}
//	static String input()throws Exception{
//		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
//		StringBuffer sb = new StringBuffer();
//		String s = null;
//		while(!(s=br.readLine()).equals("#")){
//			sb.append(s+"\n");
//		}
//		return sb.toString();
//	}
//	public static void main(String[] args)throws Exception{
//		
//		String s = input();
//		s = s.replaceAll("return.*?;", "");
//		s = s.replaceAll("get", "set");
//		s = s.replaceAll("String", "void");
//		System.out.println(s.replaceAll("return.*?;", ""));
//	}
}
