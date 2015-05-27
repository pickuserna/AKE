package iscas.tca.ake.demoapp.mvc.module.bulletin.csdata;

import iscas.tca.ake.demoapp.mvc.module.EnumTags;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPServerSecurity;
import iscas.tca.ake.util.Assist;

import java.io.Serializable;
import java.math.BigInteger;

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
public class NAPS2CMsg<T> implements Serializable{
	T msg;
	BulletinNAPMsgType msgType;
	public enum BulletinNAPMsgType implements Serializable{
		MSGBulletinTypeHash,
		MSGBulltinTypeConfigSecurity,
		MSGBulltinTypeResponseSecurity;
	}
	public NAPS2CMsg(T msg, BulletinNAPMsgType msgType){
		this.msg = msg;
		this.msgType = msgType;
	}
	//1st msg
	public static class ConfigMsg implements Serializable, IfcBulletinNAPClient {
		@Override
		public int index(String id) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
		@Override
		public String getMsgType() {
			// TODO Auto-generated method stub
			return EnumTags.NapBulletinSecurityMode;
		}
		@Override
		public String getConnectedPseus() {
			// TODO Auto-generated method stub
			return Assist.Connectbytes(this.cjs).toString();
		}
		BigInteger g;
		BigInteger q;
		byte[][] cjs;
		BigInteger X;
		
		//toString()
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append("NAPS2CMsg:::\n");
			sb.append("g:"+g+"\nq:"+q+"\nX:"+X+"\ncjs[]: \n");
			for(int i=0; i<cjs.length; i++){
				sb.append(i+ " :" + Assist.bytesToHexString(cjs[i])+"\n");
			}
			return sb.toString();
		}
		public BigInteger getX(){
			return X;
		}
		public BigInteger getG() {
			return g;
		}
		public BigInteger getQ() {
			return q;
		}
		public byte[][] getCjs() {
			return cjs;
		}
		/**
		 * @param g
		 * @param q
		 * @param cjs
		 */
		public ConfigMsg(BigInteger g, BigInteger q, BigInteger X, byte[][] cjs) {
			super();
			this.g = g;
			this.q = q;
			this.cjs = cjs;
			this.X = X;
		}
	
		
	}
//	//2nd msg
//	public static class ResponseMsg implements Serializable {
//		BigInteger Ax;
//	}
	
}
