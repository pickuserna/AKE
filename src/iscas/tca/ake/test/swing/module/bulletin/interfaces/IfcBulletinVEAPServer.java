package iscas.tca.ake.test.swing.module.bulletin.interfaces;

import iscas.tca.ake.veap.calculate.GroupData;
import iscas.tca.ake.veap.calculate.GroupInput;

/**
 * ������<��Group�ļ��������й���ͻ�ȡ>
 * @author zn
 * @CreateTime 2014-9-11����9:44:41
 */
public interface IfcBulletinVEAPServer {
	/**
	 * TODO:<�򹫸�幫�����ݣ������ع���������> 
	 */
//	public GroupData putonBoard(GroupData data);
//	
//	/**
//	 * TODO:<�ڷ�������Ѱ��groupID����Ӧ��GroupData>
//	 * @param groupID
//	 * @return null û�ҵ�
//	 */
	public GroupData getGroupData(String groupID)throws Exception;
	
//	/**
//	 * TODO:<���㲢����GroupData>
//	 * @param gi Group����������
//	 * @return ����Ľ��
//	 */
//	GroupData calAndPutonBoard(GroupInput gi);
}
