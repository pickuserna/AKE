package iscas.tca.ake.util.hashs;

import java.security.MessageDigest;

/** 
 *描述：<和h0一样都用了MD5算法>
 *文件名：<H2.java>
 * @author 作者姓名:zn   
 * @version 创建时间：2014-8-13 下午3:14:53
 *修改人：
 *修改时间：YYYY—MM-DD
 *修改内容：<修改内容> 
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
