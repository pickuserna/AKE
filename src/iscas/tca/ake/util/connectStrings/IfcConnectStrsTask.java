package iscas.tca.ake.util.connectStrings;

/**
 * @author zn
 * @CreateTime 2014-9-1����2:11:23
 */
public interface IfcConnectStrsTask extends Runnable{
	public StringBuilder get();
	public boolean isDone();
}
