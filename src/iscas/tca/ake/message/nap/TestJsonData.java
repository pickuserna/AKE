package iscas.tca.ake.message.nap;
import static iscas.tca.ake.message.nap.EnumNAPMsgType.*;

import org.codehaus.jackson.JsonNode;

import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.message.nap.NAPMessage.GroupIDData;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.JsonUtil;
import iscas.tca.ake.util.UtilMy;
import iscas.tca.ake.veap.calculate.GroupData;
import static iscas.tca.ake.message.nap.NAPMessage.*;

public class TestJsonData {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		NAPMessage.GroupIDData gid = new NAPMessage.GroupIDData("groupID");
		NAPMessage gMsg = new NAPMessage(gid, EnumNAPMsgType.GroupID); 
		//NAPMsg 2 Json
		String jsonGMsg = NapMsg2Json(gMsg);
		UtilMy.print(jsonGMsg);
		//json 2 NAPMSG
			NAPMessage napMsg = json2NapMsg(jsonGMsg); 
			UtilMy.print(napMsg.toString());
		//array property
		//AuthcMsg 2 JSON
		byte[] authc = {123,23,21,25};
		UtilMy.print(Assist.bytesToHexString(authc));
		NAPMessage.AuthcData authcData = new NAPMessage.AuthcData(authc);
		NAPMessage aMsg = new NAPMessage(authcData, EnumNAPMsgType.Authc);
		String jsonAMsg = NapMsg2Json(aMsg);
		UtilMy.print(jsonAMsg);
		//Json 2 AuthcMsg
			NAPMessage authcMsg = json2NapMsg(jsonAMsg);
			UtilMy.print(authcMsg);
}
	private static IfcMessage deserializeM_data(String jsonM_data, EnumNAPMsgType msgType) throws Exception{
		IfcMessage m_data = null;
		Class[] dataClasses = {GroupIDData.class, SAsData.class, XstarBData.class, YAuthsData.class, AuthcData.class};
		EnumNAPMsgType[] types = {EnumNAPMsgType.GroupID, SAs,XstarB, YAuths, Authc};
		for(int i=0;i<types.length; i++)
		{
			if(msgType.equals(types[i])){
				m_data = JsonUtil.jsonToBean(jsonM_data, dataClasses[i]);
			}
		}
		return m_data;
		
	}
	public static NAPMessage json2NapMsg(String jsonMsg) throws Exception{
		
//		JsonUtil.jsonToBean(jsonMsg, beanClass);
		JsonNode tree = JsonUtil.getMapperInstance().readTree(jsonMsg);
		String jsonData= tree.get("m_data").toString();
		String msgType = tree.get("m_msgType").getTextValue();
		EnumNAPMsgType enumMsgType = UtilMy.getEnumFromString(EnumNAPMsgType.class, msgType);
		IfcMessage m_data = deserializeM_data(jsonData, enumMsgType);
		return new NAPMessage(m_data, enumMsgType);
	}
	
	//为了使用Json set 和 get ，需要对msg 的类型进行严格限NKGG6-WBPCC-HXWMY-6DQGJ-CPQVG定
	public static String NapMsg2Json(NAPMessage msg){
		String jsonMsg = null;
		try {
			jsonMsg = JsonUtil.beanToJson(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			UtilMy.print("Nap2Json Error!!");
			e.printStackTrace();
		}
		return jsonMsg;
	}
}
