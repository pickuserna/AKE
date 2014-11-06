package iscas.tca.ake.veap;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.hashs.Hash_VEAP;

import java.math.BigInteger;

/**
 * 描述：<用户结构，id-pw>
 * @author zn
 * @CreateTime 2014-9-11上午10:46:09
 */
public class User{
	public String user_id;
	public String user_pw;
	/**
	 * TODO:<获取用户对象的pvd>
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