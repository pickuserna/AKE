package iscas.tca.ake.util.hashs;

import java.security.MessageDigest;

/** 
 *Algorithm :<SHA-256>
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
