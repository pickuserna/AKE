package iscas.tca.ake.napake;

import iscas.tca.ake.util.exceptions.CannotFindSuchIDException;

/**
 * ������<>
 * @author zn
 * @CreateTime 2014-8-22����9:14:44
 */
public interface IfcGetUsers {
	/**
	 * TODO:<���غ�ids��Ӧ��passwords>
	 * @param ids idiiowetoijtalgiob
	 * @return 
	 */
	public String[] getPasswords(String[] ids) throws CannotFindSuchIDException;
	//public String[] getPasswords(String groupID) throws Exception;
	
}
