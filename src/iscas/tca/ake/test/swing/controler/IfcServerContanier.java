package iscas.tca.ake.test.swing.controler;

import iscas.tca.ake.test.swing.module.Config;

/**
 * ������<>
 * @author zn
 * @CreateTime 2014-10-16����11:26:22
 */
public interface IfcServerContanier{
	public void init(Config conifg);
	public void service();
	public void close();
}
