package iscas.tca.ake.test.swing.module.bulletin;

import iscas.tca.ake.util.Assist;
import iscas.tca.ake.util.hashs.H256;

import java.io.Serializable;
import java.util.Arrays;

/**
 * √Ë ˆ£∫<>
 * @author zn
 * @CreateTime 2014-11-13…œŒÁ10:07:58
 */
public class BulletinNAPClient implements Serializable, IfcBulletinNAPClient {
	public String addin;
	public byte[][] pseudonym;
	
	private BulletinNAPClient(){}
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
	public static BulletinNAPClient newInstance(String[] ids, String addin){
		BulletinNAPClient bn = new BulletinNAPClient();
		// create pseudonym
		bn.pseudonym = new byte[ids.length][];
		bn.addin = addin;
	
		for(int i=0; i<ids.length; i++){
			bn.pseudonym[i] = getPseudonym(ids[i], addin);
		}
		return bn;
	}	
}
