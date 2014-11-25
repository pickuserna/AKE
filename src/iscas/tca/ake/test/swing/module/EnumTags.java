package iscas.tca.ake.test.swing.module;

import java.util.HashMap;
import java.util.Map;

import iscas.tca.ake.test.swing.module.tools.UtilMy;

import org.junit.Test;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-10-16ÏÂÎç12:26:11
 */
public class EnumTags {
	public static final String GTag = ("g"),
	QTag = ("q"),
	PortMain= ("portMain"),
	PortBulletin=("portBulletin"),
	ProType_Arg= ("proType"),
	//ProType_NAP_YZ("nap_yz"),
	//ProType_SKI("ski"),
	BitLength= ("bitLength"),
	//whether the database stored in plainText
	IsPlainText = ("isPlainText"),
	//dirs
	bulletinDirTag= ("bulletinDir"),
	usersFilePathTag= ("usersFilePath"),
	groupsFilePathTag= ("groupsFilePath"),
	logsDirTag= ("logsDir"),
	ConfigRootTag = ("config"),
	//users and groups tag mapping
	UsersRootTag= ("users"),
	UserTag = ("client"),
	UserIdTag = ("id");
	
	public static final String UserPasswordTag = "password";
	
	public static final String GroupsRootTag = "groups";
	public static final String GroupTag = "group";
	public static final String GroupIdTag = "groupID";
	public static final String GroupPasswordTag = "password";;
	
	String argTag;
	
	private EnumTags(String tag){
		argTag = tag;
	}
	public String toString(){
		return this.argTag;
	}
	public static String[] getGQPath(String gq, int bitlen){
		String[] path = new String[2];
		path[0] = BitLength+bitlen;
		path[1] = gq.toString();
		return path;
	}
	public static  void main(String[] args){
		UtilMy.print(ConfigRootTag);
		UtilMy.print(ConfigRootTag.toString());
		Map<String, Object>map = new HashMap<String, Object>();
		map.put(ConfigRootTag.toString(), ConfigRootTag);
		UtilMy.print(map.get(ConfigRootTag.toString()));
	}
}
