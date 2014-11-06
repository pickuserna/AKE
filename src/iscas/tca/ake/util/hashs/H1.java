package iscas.tca.ake.util.hashs;
import iscas.tca.ake.util.Assist;

import java.security.MessageDigest;
/** 
 *�ļ�����[�ļ���]
 *������<MD5�㷨>
 * @author ��������:zn   
 * @version ����ʱ�䣺2014-7-31 ����1:42:30
 *�޸��ˣ�
 *�޸�ʱ�䣺YYYY��MM-DD
 *�޸����ݣ�<�޸�����>
 *��˵�� 
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


