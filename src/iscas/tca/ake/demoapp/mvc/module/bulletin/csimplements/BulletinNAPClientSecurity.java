package iscas.tca.ake.demoapp.mvc.module.bulletin.csimplements;

import iscas.tca.ake.demoapp.mvc.module.bulletin.csdata.NAPS2CMsg;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces.IfcWaitorAndNotifier;
import iscas.tca.ake.napake.calculate.IfcNapAKECalculate;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.hashs.H256;
import iscas.tca.ake.util.rand.Rand;

import java.math.BigInteger;
import java.util.Arrays;

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
public class BulletinNAPClientSecurity implements IfcWaitorAndNotifier,  IfcBulletinNAPClient{

	NAPS2CMsg.ConfigMsg configMsg;
	IfcNapAKECalculate napCalculata;
	boolean isDone = false;
	String id;
	String password;
	//
	BigInteger Ax;
	BigInteger randa;
	BigInteger Ap;
	BigInteger pvd;
	BigInteger A;
	//result
	private int index = -1;
	public BulletinNAPClientSecurity(NAPS2CMsg.ConfigMsg cfgMsg, String id, String password, IfcNapAKECalculate napCalculate){
		this.configMsg = cfgMsg;
		this.id = id;
		this.password = password;
		this.napCalculata = napCalculate;
	}
	
	@Override
	public String getMsgType() {
		// TODO Auto-generated method stub
		return this.configMsg.getMsgType();
	}
	//calculate A 
	public BigInteger getA(){
		BigInteger g = configMsg.getG();
		BigInteger q = configMsg.getQ();
		
		randa = (new Rand()).randOfMax(q);
		Ap = Assist.modPow(g, randa, q);
		pvd = napCalculata.getPW(id, password, q);
		
		System.out.println("pvd:"+pvd.toString());
		
		A =	Assist.modMutiply(Ap, pvd, q);
		return A;
	}
	//should be private , how ???
	public void calculateIndex(BigInteger Ax){
		BigInteger q = configMsg.getQ();
		BigInteger X =configMsg.getX();
		BigInteger Xa = Assist.modPow(X, randa, q);		
		BigInteger pvdX = Assist.modDivision(Ax, Xa, q);
		
		String s = Assist.connectStrings(id, X.toString(), pvd.toString(), pvdX.toString()).toString();
		
		
		byte[] cj = new H256().process(s);
		this.index = indexOfArray(configMsg.getCjs(), cj);
		System.out.println("pvdX:"+pvdX +"\nAx:"+Ax+"\nXa:"+Xa+"\nranda:"+randa+"\nX:"+X+"\nq:"+q+"\ng:"+configMsg.getG()+"\nCj:"+Assist.bytesToHexString(cj)+ "\nindex is "+index);
		
		doneIt();
	}
	
	private int indexOfArray(byte[][] cjArr, byte[] cj){
		for(int i=0; i<cjArr.length; i++){
			if(Arrays.equals(cjArr[i], cj)){
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public int index(String id) {
		// TODO Auto-generated method stub
		try{
			waitForDone();
		}catch(Exception e){
			e.printStackTrace();
		}
		return index;
	}

	@Override
	public String getConnectedPseus() {
		// TODO Auto-generated method stub
		return 	this.configMsg.getConnectedPseus();
	}

	@Override
	public void waitForDone() throws Exception {
		// TODO Auto-generated method stub
		synchronized(this){
		if(this.isDone==false){
			this.wait();
		}
		}
	}

	@Override
	public void doneIt() {
		// TODO Auto-generated method stub
		synchronized(this){
			this.isDone = true;
			this.notifyAll();
		}
	}


}
