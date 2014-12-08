package iscas.tca.ake.test.swing.module;

import static iscas.tca.ake.test.swing.module.EnumTags.BitLength;
import static iscas.tca.ake.test.swing.module.EnumTags.GTag;
import static iscas.tca.ake.test.swing.module.EnumTags.PortMain;
import static iscas.tca.ake.test.swing.module.EnumTags.ProType_Arg;
import static iscas.tca.ake.test.swing.module.EnumTags.QTag;
import static iscas.tca.ake.test.swing.module.EnumTags.getGQPath;
import iscas.tca.ake.test.swing.module.tools.UsersManagement;
import iscas.tca.ake.test.swing.module.tools.UtilMy;
import iscas.tca.ake.test.swing.module.tools.XMLTools;
import iscas.tca.ake.veap.IfcGetUsers;
import iscas.tca.ake.veap.User;

import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;



/**
 * @author zn
 * @CreateTime 2014-10-11ÏÂÎç1:50:17
 */
class Group{
	String groupID;
	User[] users;
	public void reset(){
		this.groupID = null;
		this.users = null;
	}
}
public class Config implements  IfcGetUsers{
	//set if record the id and password in plain
	private boolean isPlainText = true;//
	public static boolean isPassed = true;
	public static final String ConfigPath = "settings\\config.xml";
	private static String  rootPath = null;
	//args from the framseting mapping 

	// g and q is according to the settings 
	private static Config config;
	//config setting operator
	private XMLTools xmlTool_Config;
	
	BigInteger g;
	BigInteger q;
	String proType;
	int portMain;
	String sid;
	private String bulletinMode;
	
	//dirs 
	private String bulletinDir;
	private String usersFilePath;
	private String groupsFilePath;
	private String logsDir;
	Map<String, String> configFile= new HashMap<String, String>();
	Map<String, Object> argsForTheContext;
	//set A cache ;
	private Group groupCache = new Group();
	private int bulletinPort;
	private Config(){
	}
	
	//bit length , proType and listenning port will not write to the file
	public void set_FrameArgs(Map<String, Object> settings){
		int bitLength = (Integer)settings.get(BitLength);
		this.g = this.readGQ(GTag, bitLength);
		this.q = this.readGQ(QTag, bitLength);
		this.proType = (String)settings.get(ProType_Arg);
		this.portMain = (Integer)settings.get(PortMain);
//		this.xmlTool_Config.writeToFile();;; write to File
//		this.xmlTool_Config.appendTextElement(pNode, tagName, value);
		String[] bitLengthPath = {EnumTags.ConfigRootTag, EnumTags.BitLength};
		String[] proTypePath = {EnumTags.ConfigRootTag, EnumTags.ProType_Arg};
		String[] portMainPath = {EnumTags.ConfigRootTag, EnumTags.PortMain};
		setNode( this.proType, proTypePath);
		setNode( this.portMain+"",portMainPath);
		setNode(bitLength+"", bitLengthPath);
	}
	
	//need to change
	private void setArgs(){
		Map<String, Object> settings = new HashMap<String, Object>();
		String proType = this.readProType();
		int portMain = this.readMainPort();
		int bitlength = this.readBitLength();
		settings.put(BitLength, bitlength);
		settings.put(ProType_Arg, proType);
		settings.put(PortMain, portMain);
		this.set_FrameArgs(settings);
	}
	
	//read configuration from xml files 
	public void initFromConfigFile(){
		setArgs();
		init();
	}
	public void init(){
		//read all the args from the file
		//change
//		setArgs();
		resetGroupCache();
		isPlainText = readIsPlainText();
		bulletinDir = readBulletinDir();
		this.bulletinMode = readBulletinMode();
		logsDir = readLogsDir();
		usersFilePath = readUsersFilePath(isPlainText);
		groupsFilePath = readGroupsFilePath(isPlainText);
		bulletinPort = readBulletinPort();
		sid = readSid();
	}
	//args from the frame settings
	public static synchronized Config newInstance_WebDeploy(String rt)throws Exception{
		String configPath = rt+File.separator+Config.ConfigPath;
		rootPath = rt;
		config = newInstance(configPath);
		config.init();
		return config;
	}
	
	public static synchronized Config newInstance(String configPath) throws Exception{
		if(config==null){
			config = new Config();
//			this.bulletinDirpath;
			config.xmlTool_Config = new XMLTools().openXML(configPath,"config");
			
			System.out.println(new File(configPath).getAbsolutePath());
			config.init();
		}
		return config;
	}
	//-----------------------------interface  port--------------------------------------//
	//-----------------------------interface  port--------------------------------------//
	public String getProType(){
		return this.proType;
	}
	public BigInteger getG(){
		return g;
	}
	public BigInteger getQ(){
		return q;
	}
	public int getPortMain(){
		return this.portMain;
	}
	public String getSid(){
		if(this.sid==null){
			sid = this.readSid();
		}
		return this.sid;
	}
	public boolean getIsTextPlain(){
		return this.isPlainText;
	}
	public int getBulletinPort(){
		return bulletinPort;
	}
	
//////--------------------------------------dirs getting --------------------------------------////
	public String getUsersFilePath() {
		return usersFilePath;
	}
	public String getGroupsFilePath() {
		return groupsFilePath;
	}
	public String getLogsDir() {
		return logsDir;
	}
	public String getBulletinMode(){
		return this.bulletinMode;
	}
	public String getBulletinDir(){
		return this.bulletinDir;
	}	
	
	/////-----------------------------args translation through the application--------------------------------------//
	public void setArg(String argName, Object arg){
		this.argsForTheContext.put(argName, arg);
	}
	public Object getArg(String argName) {
		// TODO Auto-generated method stub		
		return this.argsForTheContext.get(argName);
	}
	
	
////----------------------------------Usersdatabase--------------------------------------------///
	private boolean isInGroupCache(String groupID){
	if(this.groupCache==null || this.groupCache.groupID==null)
		return false;
	return this.groupCache.groupID.equals(groupID);
}
private void setIntoGroupCache(String groupID, User[] users){
	this.groupCache.groupID = groupID;
	this.groupCache.users = users;
}
public User[] getUsers(String groupID){	
	//if not in the cache then read the file
	if(! isInGroupCache(groupID)){
		try{
		User[] users = UsersManagement.readUsersOfGroup(groupID, isPlainText, usersFilePath);
		setIntoGroupCache(groupID, users);
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}		
	}
	System.out.println("ok!");
	return this.groupCache.users;
}
private void resetGroupCache(){
	this.groupCache.reset();
}
//test 
	@Test
	public static void testGetUsers(){
		try{
			Config config = Config.newInstance(Config.ConfigPath);
			User[] users = config.getUsers("group_U");
			for(User u :users){
				
				UtilMy.print(u.user_id+" "+u.user_pw);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//--------------------------------------set Node-----------------------------
	private void setNode(String value, String... tagPath){
		this.xmlTool_Config.setNode(tagPath, value);
	}
	//--------------------------------------read config from file--------------------------------------//
	private void readAllConfig(){
		throw new UnsupportedOperationException();
	}
	//--------------------dir config -------------//
	
	private String readUsersFilePath(boolean isPlain){
		String dir = getFilePath("usersDir");
		String fileName = isPlain ? "users_plainDB.xml":"users_cypherDB.xml";
		return dir+File.separator+fileName;
	}
	private String readGroupsFilePath(boolean isPlain){
		String dir =  getFilePath("groupsDir");
		String fileName = isPlain ? "groups_plain_DB.xml":"groups_cypherDB.xml";
		return dir+File.separator+fileName;
	}
	private String readBulletinDir(){
		return 	getFilePath("bulletinDir");
	}

	private String readLogsDir(){
		return getFilePath( "logsDir");
	}
	private String getFilePath(String tagPath){
		String relativePath = this.xmlTool_Config.getEndNodeValue(tagPath);
		if(rootPath==null){
			return relativePath;
		}
		if(rootPath.endsWith(File.separator)){
			return rootPath+relativePath;
		}
		else
			return rootPath+File.separator+relativePath;
	}
	//-------------args config ----------------//
	private String readBulletinMode(){
		return (String)this.readTag(EnumTags.NapBulletinModeTag);
	}
	
	private boolean readIsPlainText(){
		return (Boolean.valueOf(this.readTag("isPlainText")));
	}
	private BigInteger readGQ( String gq, int bit){
		System.out.println(gq+bit);
		String value = this.readTag(getGQPath(gq, bit));
		System.out.println(value);
		return new BigInteger(value);
	}
	private int readBulletinPort(){
		return Integer.valueOf(this.xmlTool_Config.getEndNodeValue(EnumTags.PortBulletin));
	}
	private String readSid(){
		return this.xmlTool_Config.getEndNodeValue("sid");
	}
	//Tags 
	private String readProType(){
		return this.xmlTool_Config.getEndNodeValue(EnumTags.ProType_Arg);
	}
	private int readMainPort(){
		return Integer.valueOf(readTag(EnumTags.PortMain));
	}
	private int readBitLength(){
		return Integer.valueOf(readTag(EnumTags.BitLength));
	}
	private String readTag(String... endPointTagName){
		return this.xmlTool_Config.getEndNodeValue(endPointTagName);
	}
	//--------------------------------------write config to file--------------------------------------//
	public void setArgsToFile(String argName, String argValue){
		throw new UnsupportedOperationException("hai mieyou  shixian ne ");
	}
	public void setGQToFile(BigInteger gSetting, BigInteger qSetting){
		int len = qSetting.bitLength();
		String[] path = getGQPath(GTag, len);
		this.xmlTool_Config.setNode(path, gSetting.toString());
		
		path = EnumTags.getGQPath(QTag, len);
		this.xmlTool_Config.setNode(path, qSetting.toString());
		try{
			this.xmlTool_Config.writeToFile();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
