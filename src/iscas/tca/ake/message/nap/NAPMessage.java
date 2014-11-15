package iscas.tca.ake.message.nap;

import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.util.Assist;

import java.math.BigInteger;

/**
 * 描述：<>
 * 类名：<NAPMessage>
 * @author zn
 * @CreateTime 2014-8-16上午11:06:37
 */
public class NAPMessage implements IfcMessage {

	IfcMessage m_data;//消息体
    EnumNAPMsgType m_msgType;//
	public static EnumNAPMsgType m_endMsgType;//最后一条消息的类型
	public NAPMessage(IfcMessage m_data, EnumNAPMsgType m_msgType) {
		super();
		this.m_data = m_data;
		this.m_msgType = m_msgType;
	}
	@Override
	public boolean isMsgLegle() {
		// TODO Auto-generated method stub
//     switch(this.m_msgType){}
		if(this.m_msgType!=null)//msgType不是为空
		{
			return this.m_data.isMsgLegle();
		}
		return false;
	}
	//使用统一接口获取消息
	
	@Override
	public String getMsgType() {
		// TODO Auto-generated method stub
		return this.m_msgType.toString();
	}
	@Override
	public String getMsgContent() {
		// TODO Auto-generated method stub
		
		return this.m_data.getMsgContent();
	}
	//用于自己构造data，然后获取NAPMessage
	public static NAPMessage getNAPMessage(IfcMessage data,EnumNAPMsgType type)
	{
		return new NAPMessage(data, type);
	}
	//消息类
	//第一条消息的类?length 是需要,设置getIDsData的作用是进行类型检查
	public static class GroupIDData implements IfcMessage{
//		String[] m_IDs;
		String m_groupID;
		public GroupIDData(String groupID) {
			super();
			this.m_groupID = groupID;
		}
		@Override
		public boolean isMsgLegle() {
			// TODO Auto-generated method stub
			if(m_groupID==null){
				return false;
			}
			return true;
		}
		@Override
		public String getMsgType() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}

		@Override
		public String getMsgContent() {
			// TODO Auto-generated method stub
			return Assist.kvFormat("groupID", m_groupID);
		}
		public static GroupIDData getGroupIDData(String groupID)
		{
			return new GroupIDData(groupID);
		}
		public String getM_groupID() {
			return m_groupID;
		}
		
	}
	//2
	public static class SAsData implements IfcMessage{
		String m_SID;//S的名字
		BigInteger[] m_As;//A[]
		
		public SAsData(String m_SID, BigInteger[] m_As) {
			super();
			this.m_SID = m_SID;
			this.m_As = m_As;
		}
		@Override
		public boolean isMsgLegle() {
			// TODO Auto-generated method stub
			if(m_SID==null ||
					 m_As.length<=0)
			{
				return false;
			}
			return true;
		}
		@Override
		public String getMsgType() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String getMsgContent() {
			// TODO Auto-generated method stub
			StringBuilder sb = new StringBuilder();
			String as = Assist.arrayFormatToString("As-", "\r\n", m_As);
			sb.append(as);
			return sb.toString();
		}
		public static SAsData getSAsData(String sID, BigInteger[] as)
		{
			return new SAsData(sID, as);
		}
		//Getters and Setters
		public String getM_SID() {
			return m_SID;
		}
		public void setM_SID(String m_SID) {
			this.m_SID = m_SID;
		}
		
		public BigInteger[] getM_As() {
			return m_As;
		}

		public void setM_As(BigInteger[] m_As) {
			this.m_As = m_As;
		}
	}
	public static class XstarBData implements IfcMessage{
		BigInteger m_Xstar;
		BigInteger m_B;
		public XstarBData(BigInteger m_Xstar, BigInteger m_B) {
			super();
			this.m_Xstar = m_Xstar;
			this.m_B = m_B;
		}
		
		@Override
		public boolean isMsgLegle() {
			// TODO Auto-generated method stub
			if(m_Xstar.compareTo(BigInteger.ZERO)<=0 ||
					m_B.compareTo(BigInteger.ZERO)<=0){
				return false;
			}
			return true;
		}
		@Override
		public String getMsgType() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String getMsgContent() {
			// TODO Auto-generated method stub
			String xStart = "X' := "+m_Xstar.toString();
			String b = "B := "+m_B.toString();
			return xStart+"\n"+b+"\n";
		}
		//构造一条XstarB类型的消息
		public static XstarBData getXstarBData(BigInteger xStar, BigInteger b)
		{
			XstarBData xbData = new XstarBData(xStar, b);
			return xbData;
		}
		//Getters and Setters
		public BigInteger getM_Xstar() {
			return m_Xstar;
		}
		public void setM_Xstar(BigInteger m_XStar) {
			this.m_Xstar = m_XStar;
		}
		public BigInteger getM_B() {
			return m_B;
		}
		public void setM_B(BigInteger m_B) {
			this.m_B = m_B;
		}
	}
	//4
	public static class YAuthsData  implements IfcMessage{
		BigInteger m_Y;
		byte[] m_Auths;
		
		@Override
		public String getMsgType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getMsgContent() {
			// TODO Auto-generated method stub
			String auths = Assist.kvFormat("Auths", Assist.bytesToHexString(m_Auths));
			String y = Assist.kvFormat("Y", m_Y.toString());
			return auths+y;
		}

		public YAuthsData(BigInteger m_Y, byte[] m_Auths) {
			super();
			this.m_Y = m_Y;
			this.m_Auths = m_Auths;
		}

		@Override
		public boolean isMsgLegle() {
			// TODO Auto-generated method stub
			if(m_Y.compareTo(BigInteger.ZERO)<=0 ||
					m_Auths.length<=0){					
			return false;
			}
			return true;
		}
		public static YAuthsData getYAuthsData(BigInteger Y, byte[] auths)
		{
			return new YAuthsData(Y,auths);
		}
		public BigInteger getM_Y() {
			return m_Y;
		}
		public void setM_Y(BigInteger m_Y) {
			this.m_Y = m_Y;
		}
		public byte[] getM_Auths() {
			return m_Auths;
		}
		public void setM_Auths(byte[] m_Auths) {
			this.m_Auths = m_Auths;
		}
	}
	public static class AuthcData implements IfcMessage{
		byte[] authc;
		public AuthcData(byte[] authc){
			this.authc = authc;
		}
		public byte[] getAuthc(){
			return this.authc;
		} 
		@Override
		public boolean isMsgLegle() {
			// TODO Auto-generated method stub
			return true;
		}
		@Override
		public String getMsgType() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String getMsgContent() {
			// TODO Auto-generated method stub
			return Assist.kvFormat("Authc", Assist.bytesToHexString(authc));//"authc = "+ Assist.bytesToHexString(authc)+"\r\n";
		}
	}
	//Getters and Setters
    public IfcMessage getM_data() {
		return m_data;
	}
	public void setM_data(IfcMessage m_data) {
		this.m_data = m_data;
	}
	public EnumNAPMsgType getM_msgType() {
		return m_msgType;
	}
	public void setM_msgType(EnumNAPMsgType m_msgType) {
		this.m_msgType = m_msgType;
	}
}
