package iscas.tca.ake.test.swing;

import iscas.tca.ake.test.swing.module.Config;
import iscas.tca.ake.test.swing.module.tools.XMLTools;

/**
 * ������<>
 * @author zn
 * @CreateTime 2014-10-15����8:34:47
 */
public class testConfig {

	/**
	 * TODO:<>
	 * @param args 
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Config c = Config.newInstantce(Config.ConfigPath);
		System.out.println(c.getBulletinDir());
	}

}
