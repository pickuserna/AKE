package iscas.tca.ake.util.hashs;

import java.security.MessageDigest;

/** 
 *������<��h0һ��������MD5�㷨>
 *�ļ�����<H2.java>
 * @author ��������:zn   
 * @version ����ʱ�䣺2014-8-13 ����3:14:53
 *�޸��ˣ�
 *�޸�ʱ�䣺YYYY��MM-DD
 *�޸����ݣ�<�޸�����> 
 */

public class H256 implements IfcHash {

	@Override
	public byte[] process(String s) {
		// TODO Auto-generated method stub
	
		try{
			byte[] bs = s.getBytes("UTF8");
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update(bs);

			return (sha.digest());
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}

}
