package iscas.tca.ake.demoapp.mvc.module;

import java.util.HashMap;
import java.util.Map;

import iscas.tca.ake.util.UtilMy;

import org.junit.Test;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-10-16ÏÂÎç12:26:11
 */
/*
 * Copyright (c) 20014-2041 Institute Of Software Chinese Academy Of Sciences
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @Organization: http://tca.iscas.ac.cn/
 * @author: Nan Zhou
 * @Aknowledge: Tutor Liwu Zhang , Alumnus Yan Zhang, Zhigang Gao
 * @Email: changchangge123@qq.com
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
	//nap bulletin model: hash or exponent
	public static final String NapBulletinModeTag="napBulletinMode";
	public static final String NapBulletinHashMode = "hash";
	public static final String NapBulletinSecurityMode = "security";
	String argTag;
	///
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
