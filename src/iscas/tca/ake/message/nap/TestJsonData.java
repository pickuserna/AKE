package iscas.tca.ake.message.nap;

import iscas.tca.ake.demoapp.mvc.module.tools.UtilMy;
import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.util.JsonUtil;
import iscas.tca.ake.veap.calculate.GroupData;

public class TestJsonData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		NAPMessage.GroupIDData gid = new NAPMessage.GroupIDData("groupID");
		NAPMessage gMsg = new NAPMessage(gid, EnumNAPMsgType.GroupID); 
		String jsonGMsg = Nap2Json(gMsg);
		UtilMy.print(jsonGMsg);
	}

	public static IfcMessage json2NapMsg(String jsonMsg){
		
//		JsonUtil.jsonToBean(jsonMsg, beanClass);
		return null;
	}
	
	//为了使用Json set 和 get ，需要对msg 的类型进行严格限NKGG6-WBPCC-HXWMY-6DQGJ-CPQVG定
	public static String Nap2Json(NAPMessage msg){
		String jsonMsg = null;
		try {
			jsonMsg = JsonUtil.beanToJson(msg);
			System.out.println(jsonMsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			UtilMy.print("Nap2Json Error!!");
			e.printStackTrace();
		}
		return jsonMsg;
	}
}
