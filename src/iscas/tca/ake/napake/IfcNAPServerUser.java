package iscas.tca.ake.napake;

import iscas.tca.ake.util.exceptions.CannotFindSuchIDException;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-22上午9:14:44
 */
public interface IfcNAPServerUser {
	/**
	 * TODO:<返回和ids对应的passwords>
	 * @param ids idiiowetoijtalgiob
	 * @return 
	 */
	public String[] getPasswords(String[] ids) throws CannotFindSuchIDException;
	
}
