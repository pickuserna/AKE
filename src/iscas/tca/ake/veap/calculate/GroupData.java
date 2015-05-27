package iscas.tca.ake.veap.calculate;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.rand.Rand;
import iscas.tca.ake.veap.User;
import iscas.tca.ake.veap.edcoder.EDCoder;
import iscas.tca.ake.veap.edcoder.IfcEDCoder;

import java.io.Serializable;
import java.math.BigInteger;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * description£º<calculate the data of a group>
 * @author zn
 * @CreateTime 2014-9-11ÉÏÎç10:34:59
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
public class GroupData implements Serializable{
	BigInteger m_randx;
	BigInteger m_X;//
	String m_groupID;//
	U_C[] m_ucs;//
	String m_sUCs;//connected string
	byte[] m_MS;
	BigInteger m_q;
	long m_timeOut;//
	long m_publishTime;//
	BigInteger m_g ;
	String groupDataID;
	/**
	 * TODO:<calculate and get the groupData >
	 * @param input
	 * @return 
	 */
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("published InfoMation:\r\n");
		sb.append("groupID = "+m_groupID+"\r\n");
		sb.append("publish Time =" +m_publishTime+"\r\n");
		sb.append("timeOut = "+m_timeOut+"\r\n");
		sb.append("MS = " +new HexBinaryAdapter().marshal(m_MS)+"\r\n");
		sb.append("randx = "+ m_randx+"\r\n");
		sb.append("X = "+m_X+"\r\n");
		sb.append("UC = "+ new HexBinaryAdapter().marshal(m_sUCs.getBytes())+"\r\n");
		return sb.toString();
	}
	
	public GroupData calGroupData(GroupInput input){
		m_groupID = input.getM_groupID();
		
		m_q = input.getM_q();
		m_g = input.getM_g();
		
		int lenMS= input.getM_lenMS();
		Rand rnd = new Rand();
		m_MS = rnd.nextBytes(lenMS);
		m_randx = rnd.randOfMax(m_q);
		m_X = Assist.modPow(m_g, m_randx, m_q);
		m_ucs = calU_Cs(input.getM_users(), lenMS);
		//connect to String
		m_sUCs = new VEAPCalculate().connectUcs(m_ucs);
		m_timeOut = input.timeOut;
		//System.out.println("MS:"+Assist.bytesToHexString(m_MS));
		return this;
	}
	/**
	 * TODO:<calculate the U_Cs>
	 * @return 
	 */
	private U_C[] calU_Cs(User[] users, int lenU){		
		byte[][] u_k = new byte[2][];
		int[] lens ={lenU, lenU};
		
		U_C[] u_cs = new U_C[users.length];
		
		IfcVEAPCalculate cal = new VEAPCalculate();
		IfcEDCoder edcoder = new EDCoder();

		for(int i=0; i<users.length; i++){
			BigInteger pvd = users[i].getPvd();
			BigInteger pvdX = Assist.modPow(pvd, m_randx, m_q);
			byte[] uk = cal.getU_K(users[i].user_id, m_X, pvd, 
					pvdX);
			Assist.divideBytes(uk, u_k, lens) ; 
			//System.out.println("X:"+m_X+"\tpvd:"+pvd+"\tpvdX:"+pvdX+"\trandx:"+m_randx);
			byte[] c = edcoder.encrypto(m_MS, u_k[1]);//
			//¼ÇÂ¼uc
			u_cs[i] = new U_C();
			System.out.println(u_cs[i]);
			u_cs[i].setM_u(u_k[0]);
			u_cs[i].setM_c(c);
		}
		return u_cs;
	}
	public void setPublishTime(Long publishTime){
		this.m_publishTime = publishTime;
	}
	public BigInteger getM_randx() {
		return m_randx;
	}
	public BigInteger getM_X() {
		return m_X;
	}
	public String getM_groupID() {
		return m_groupID;
	}
	public U_C[] getM_ucs() {
		return m_ucs;
	}
	public String getM_sUCs() {
		return m_sUCs;
	}
	public byte[] getM_MS() {
		return m_MS;
	}
	public BigInteger getM_q() {
		return m_q;
	}
	public long getM_timeOut() {
		return m_timeOut;
	}
	public long getM_publishTime() {
		return m_publishTime;
	}
	public String getGroupDataID() {
		return groupDataID;
	}
	
	////////////////////////////////////////////////////////////////
	
}
