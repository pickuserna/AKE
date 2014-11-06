package iscas.tca.ake.test.local;

import iscas.tca.ake.veap.User;
import iscas.tca.ake.veap.VEAPConstants;
import iscas.tca.ake.veap.calculate.GroupData;
import iscas.tca.ake.veap.calculate.GroupInput;

import java.math.BigInteger;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-10-16ÏÂÎç8:24:06
 */
public class testGroup {

	/**
	 * TODO:<>
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User u = new User("id", "pw");
		User[] users ={u, u};
		GroupInput gi = new GroupInput("group", users, VEAPConstants.LengthOfMS, new BigInteger("100"), new BigInteger("100"), 100000l);
		GroupData gd = new GroupData();
		gd.calGroupData(gi);
		System.out.println(gd.getM_sUCs());
	}

}
