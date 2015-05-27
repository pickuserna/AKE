package iscas.tca.ake.demoapp.mvc.module.bulletin.csdata;

import iscas.tca.ake.demoapp.mvc.module.EnumTags;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.hashs.H256;

import java.io.Serializable;
import java.net.Socket;
import java.util.Arrays;

/**
 * √Ë ˆ£∫<>
 * @author zn
 * @CreateTime 2014-11-13…œŒÁ10:07:58
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
public class BulletinNAPClientHashData implements Serializable, IfcBulletinNAPClient {

	public String addin;
	public byte[][] pseudonym;
	
	@Override
	public String getMsgType() {
		// TODO Auto-generated method stub
		return EnumTags.NapBulletinHashMode;
	}
	private BulletinNAPClientHashData(){}
	//calculate the pseudonym with the addin of the given id
	private static byte[] getPseudonym( String id, String addin){
		String s = Assist.connectStrings(id, addin).toString();
		return new H256().process(s);
	}
	
	
	@Override
	public String getConnectedPseus() {
		// TODO Auto-generated method stub
		return Assist.Connectbytes(this.pseudonym).toString();
	}
	// find the index of id
	@Override
	public int index(String id){
		for(int i=0; i<pseudonym.length; i++){
			byte[] temp = getPseudonym(id, addin);
			if(Arrays.equals(temp, pseudonym[i])){
				return i;
			}
		}
		return -1;
	}
	public static BulletinNAPClientHashData newInstance(String[] ids, String addin){
		BulletinNAPClientHashData bn = new BulletinNAPClientHashData();
		// create pseudonym
		bn.pseudonym = new byte[ids.length][];
		bn.addin = addin;
	
		for(int i=0; i<ids.length; i++){
			bn.pseudonym[i] = getPseudonym(ids[i], addin);
		}
		return bn;
	}	
}
