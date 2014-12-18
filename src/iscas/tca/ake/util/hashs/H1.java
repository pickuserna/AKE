package iscas.tca.ake.util.hashs;
import iscas.tca.ake.util.Assist;

import java.security.MessageDigest;
/** 
 *ÃèÊö£º<SHA-256>
 * @author :zn   
 * @version £º2014-7-31 ÏÂÎç1:42:30

 */

public class H1 implements IfcHash {

	@Override
	public byte[] process(String s) {
		// TODO Auto-generated method stub
		byte[] bs = s.getBytes();
		try{
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update(bs);
			return sha.digest();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		String text = "abc";
		H1 h = new H1();
		
		byte[] resultData = h.process(text);
		for(byte b: resultData)
		{
			System.out.println(b);
		}
		System.out.println(Assist.bytesToHexString(resultData));
	}
}


