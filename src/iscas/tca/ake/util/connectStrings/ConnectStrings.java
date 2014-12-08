package iscas.tca.ake.util.connectStrings;

/**
 * @author zn
 * @CreateTime 2014-9-1ионГ9:01:35
 */
public class ConnectStrings implements IfcConnectStrings {
	//@SuppressWarnings("unused")
	
	/**
	 * TODO:<>
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public StringBuilder getConnectedString(String[] ss) {
		// TODO Auto-generated method stub
		if(ss==null)
			return null;
		int totalCapacity =  0;
		StringBuilder sb = new StringBuilder();
		
		for(String s :ss){
			totalCapacity += s.length();
		}
		sb.ensureCapacity(totalCapacity);	
		
		for(int i=0; i<ss.length; i++){
			sb.append(ss[i]);
		}
		return sb;
	}
}
