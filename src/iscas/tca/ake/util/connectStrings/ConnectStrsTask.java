package iscas.tca.ake.util.connectStrings;

import java.math.BigInteger;

import iscas.tca.ake.util.Assist;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-9-1上午10:44:04
 */
//类似Future的一个类，需要更显式的调用run
public class ConnectStrsTask implements Runnable{
	private StringBuilder sb;
	private String[] ss;
	private boolean isDone = false;
	public ConnectStrsTask(final String[] ss)
	{
		this.ss = ss;
	}
	/**
	 * 连接BigInteger形式的数组
	 * @param bs
	 */
	public ConnectStrsTask(final BigInteger[] bs){
		this.ss = new String[bs.length];
		for(int i=0; i<bs.length; i++)
		{
			ss[i] = bs[i].toString();
		}
	}
	//merge string task
	public void run()
	{
		sb = Assist.connectStrings(ss);
		synchronized (this){
			isDone = true;
			notifyAll();
		}
	}
	//获取合并后的结果
	public StringBuilder get()
	{
		synchronized(this){
			if(!this.isDone){
				try{
					wait();
				}catch(Exception e)
				{
					System.out.println("get() Error!");
					e.printStackTrace();
				}
			}
		}
		return this.sb;
	}
	public boolean isDone()
	{
		return this.isDone;
	}
	/**
	 * TODO:<>
	 * @param args 
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
		String[] ss={"1", "2","3"};
		ConnectStrsTask cst = new ConnectStrsTask(ss);
		new Thread(cst ).start();
		System.out.println(cst.get().toString());
	}

}
