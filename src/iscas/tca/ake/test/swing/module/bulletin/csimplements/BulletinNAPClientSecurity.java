package iscas.tca.ake.test.swing.module.bulletin.csimplements;

import iscas.tca.ake.napake.calculate.IfcNapCalculate;
import iscas.tca.ake.test.swing.module.bulletin.csdata.NAPS2CMsg;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcWaitorAndNotifier;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.hashs.H256;
import iscas.tca.ake.util.rand.Rand;

import java.math.BigInteger;
import java.util.Arrays;

public class BulletinNAPClientSecurity implements IfcWaitorAndNotifier,  IfcBulletinNAPClient{

	NAPS2CMsg.ConfigMsg configMsg;
	IfcNapCalculate napCalculata;
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
	public BulletinNAPClientSecurity(NAPS2CMsg.ConfigMsg cfgMsg, String id, String password, IfcNapCalculate napCalculate){
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
	
	public void calculateIndex(BigInteger Ax){
		BigInteger q = configMsg.getQ();
		BigInteger X =configMsg.getX();
		BigInteger Xa = Assist.modPow(X, randa, q);		
		BigInteger pvdX = Assist.modDivision(Ax, Xa, q);
		
		String s = Assist.connectStrings(id, X.toString(), pvd.toString(), pvdX.toString()).toString();
		System.out.println("pvdX:"+pvdX +"\nAx:"+Ax+"\nXa:"+Xa+"\nranda:"+randa+"\nX:"+X+"\nq:"+q+"\ng:"+configMsg.getG());
		byte[] cj = new H256().process(s);
		this.index = indexOfArray(configMsg.getCjs(), cj);
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
