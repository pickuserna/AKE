package iscas.tca.ake.test.swing.module.bulletin.csdata;


public class BulletinNAPServerHashData {
	public String connectedPseudonyms;
	public BulletinNAPClientHashData bulletinNAPClientData;
	
	public BulletinNAPServerHashData(BulletinNAPClientHashData bn){
		this.connectedPseudonyms = bn.getConnectedPseus();
		this.bulletinNAPClientData = bn;
	}
}
