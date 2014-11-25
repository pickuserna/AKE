package iscas.tca.ake.test.swing.module.bulletin.interfaces;

import iscas.tca.ake.veap.calculate.GroupData;
import iscas.tca.ake.veap.calculate.GroupInput;

/**
 * 描述：<将Group的计算结果进行公告和获取>
 * @author zn
 * @CreateTime 2014-9-11上午9:44:41
 */
public interface IfcBulletinVEAPServer {
	/**
	 * TODO:<向公告板公布数据，并返回公布的数据> 
	 */
//	public GroupData putonBoard(GroupData data);
//	
//	/**
//	 * TODO:<在服务器上寻找groupID所对应的GroupData>
//	 * @param groupID
//	 * @return null 没找到
//	 */
	public GroupData getGroupData(String groupID)throws Exception;
	
//	/**
//	 * TODO:<计算并发布GroupData>
//	 * @param gi Group的输入数据
//	 * @return 计算的结果
//	 */
//	GroupData calAndPutonBoard(GroupInput gi);
}
