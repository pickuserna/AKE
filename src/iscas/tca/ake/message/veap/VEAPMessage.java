package iscas.tca.ake.message.veap;

import iscas.tca.ake.message.IfcMessage;
import iscas.tca.ake.message.nap.NAPMessage.YAuthsData;
import iscas.tca.ake.util.Assist;

import java.math.BigInteger;
import java.util.Map;
import static iscas.tca.ake.message.veap.EnumVEAPMsgType.*;

/**
 * 描述：<>
 * 
 * @author zn
 * @CreateTime 2014-9-4上午10:17:35
 */
public class VEAPMessage implements IfcMessage {
	IfcMessage m_data;
	EnumVEAPMsgType m_msgType;

	@Override
	public boolean isMsgLegle() {
		// TODO Auto-generated method stub
		return true;
	}

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

	public void setVEAPMsg(EnumVEAPMsgType msgType, IfcMessage data) {
		this.m_data = data;
		this.m_msgType = msgType;
	}

	// 消息中包含信息的数据结构
	// 1
	public class UABData implements IfcMessage {
		BigInteger data_A;
		BigInteger data_B;
		String data_UID;

		@Override
		public String getMsgType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getMsgContent() {
			// TODO Auto-generated method stub
			String a = Assist.kvFormat("A", data_A.toString());
			String b = Assist.kvFormat("B", data_B.toString());
			String uid = Assist.kvFormat("UID", data_UID);
			return uid+a+b;
		}

		/**
		 * @param data_A
		 * @param data_B
		 * @param data_UID
		 */
		public UABData(String data_UID, BigInteger data_A, BigInteger data_B) {
			super();
			this.data_A = data_A;
			this.data_B = data_B;
			this.data_UID = data_UID;
		}

		@Override
		public boolean isMsgLegle() {
			// TODO Auto-generated method stub
			return true;
		}

		public BigInteger getData_A() {
			return data_A;
		}
		public BigInteger getData_B() {
			return data_B;
		}

		public String getData_UID() {
			return data_UID;
		}
	}
	// 2
	public class S2CData implements IfcMessage {
		// client查询的方式，服务器发布方式实现时：不需要 (U,C)中的C
		String data_sid;
		BigInteger data_Ax;
		BigInteger data_Y;
		
		byte[] data_Vs;

		@Override
		public String getMsgType() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String getMsgContent() {
			// TODO Auto-generated method stub
			String sid = Assist.kvFormat("sid", data_sid);
			String Ax = Assist.kvFormat("A^x", data_Ax.toString());
			String Y = Assist.kvFormat("Y", data_Y.toString());
			String Vs = Assist.kvFormat("Vs", Assist.bytesToHexString(data_Vs));
			return sid+Ax+Y+Vs;
		}
		public S2CData(String data_SID, 
				BigInteger data_Ax, 
				BigInteger data_Y,
				byte[] data_VS) {
			//super();
			this.data_sid = data_SID;
			this.data_Ax = data_Ax;
			this.data_Y = data_Y;
		
			this.data_Vs = data_VS;
		}
		@Override
		public boolean isMsgLegle() {
			// TODO Auto-generated method stub
			return true;
		}
		public String getData_SID() {
			return data_sid;
		}
		public BigInteger getData_Ax() {
			return data_Ax;
		}
		public BigInteger getData_Y() {
			return data_Y;
		}
		public byte[] getData_VS() {
			return data_Vs;
		}
	}
	// 3
	public class VerifyData implements IfcMessage {
		byte[] data_verify;
		@Override
		public String getMsgType() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String getMsgContent() {
			// TODO Auto-generated method stub
			String verify = Assist.kvFormat("verify", Assist.bytesToHexString(data_verify));
			return verify;
		}
		public VerifyData(byte[] data_verify) {
			super();
			this.data_verify = data_verify;
		}
		@Override
		public boolean isMsgLegle() {
			// TODO Auto-generated method stub
			return true;
		}
		public byte[] getData_verify() {
			return data_verify;
		}

	}

	public IfcMessage getM_data() {
		return m_data;
	}

	public EnumVEAPMsgType getM_msgType() {
		return m_msgType;
	}

}
