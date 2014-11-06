package iscas.tca.ake.veap;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.hashs.Hash_VEAP;

import java.math.BigInteger;

/**
 * ������<�û��ṹ��id-pw>
 * @author zn
 * @CreateTime 2014-9-11����10:46:09
 */
public class User{
	public String user_id;
	public String user_pw;
	/**
	 * TODO:<��ȡ�û������pvd>
	 * @return 
	 */
	public User(String id, String pw){
		this.user_id = id;
		this.user_pw = pw;
	}
	public BigInteger getPvd(){
		String ss[] ={user_id, user_pw};
		String str = Assist.connectStrings(ss).toString();
		byte[] bPvd =  new Hash_VEAP().process(str);
		
		return new BigInteger(bPvd).abs();
	}
	public String toString(){
		return this.user_id+":"+this.user_pw;
	}
}