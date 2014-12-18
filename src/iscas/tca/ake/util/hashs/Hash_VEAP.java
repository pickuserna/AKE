package iscas.tca.ake.util.hashs;

import iscas.tca.ake.veap.VEAPConstants;

import java.security.MessageDigest;

/**
 * Algorithm£º<SHA-256 Hash>
 * @author zn
 * @CreateTime 2014-9-11ÉÏÎç10:50:13
 */
public class Hash_VEAP implements IfcHash {

	@Override
	public byte[] process(String s) {
		// TODO Auto-generated method stub
		
			try{
				byte[] bs = s.getBytes("UTF8");
				MessageDigest sha = MessageDigest.getInstance(VEAPConstants.AlgorithmOfHash);
				sha.update(bs);

				return (sha.digest());
			}
			catch(Exception e){
				System.out.println(e);
			}
			return null;
	}

}
