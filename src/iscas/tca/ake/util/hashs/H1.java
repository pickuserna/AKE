package iscas.tca.ake.util.hashs;
import iscas.tca.ake.util.Assist;

import java.security.MessageDigest;
/** 
 *文件名：[文件名]
 *描述：<MD5算法>
 * @author 作者姓名:zn   
 * @version 创建时间：2014-7-31 下午1:42:30
 *修改人：
 *修改时间：YYYY—MM-DD
 *修改内容：<修改内容>
 *类说明 
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


