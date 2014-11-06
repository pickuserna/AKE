package iscas.tca.ake.test.swing.controler;

import iscas.tca.ake.test.swing.module.Config;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-10-16ÉÏÎç11:26:22
 */
public interface IfcServerContanier{
	public void init(Config conifg);
	public void service();
	public void close();
}
