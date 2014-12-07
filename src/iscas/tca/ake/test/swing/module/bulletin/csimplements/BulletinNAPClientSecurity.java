package iscas.tca.ake.test.swing.module.bulletin.csimplements;

import java.math.BigInteger;

import iscas.tca.ake.test.swing.module.bulletin.csdata.NAPS2CMsg;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcBulletinNAPClient;
import iscas.tca.ake.test.swing.module.bulletin.interfaces.IfcWaitorAndNotifier;

public class BulletinNAPClientSecurity implements IfcBulletinNAPClient{

	NAPS2CMsg.ConfigMsg configMsg;
	BigInteger Ax;
	boolean isDone = false;
	BigInteger pvd;
	public BulletinNAPClientSecurity(NAPS2CMsg.ConfigMsg cfgMsg, BigInteger Ax, BigInteger pvd){
		this.configMsg = cfgMsg;
		this.Ax = Ax;
		this.pvd = pvd;
	}
	
	@Override
	public int index(String id) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public String getConnectedPseus() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}


}
