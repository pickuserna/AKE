package iscas.tca.ake.veap;


/**
 * 描述：<由服务器端的使用者实现，返回groupID对应的用户集合>
 * @author zn
 * @CreateTime 2014-9-12下午4:07:33
 */
public interface IfcGetUsers {
	public User[] getUsers(String groupID);
}
