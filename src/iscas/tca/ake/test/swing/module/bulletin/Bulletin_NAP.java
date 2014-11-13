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
public class Bulletin_NAP implements Serializable, IfcBulletinNAP {
	public String addin;
	public byte[][] pseudonym;
	
	private Bulletin_NAP(){}
	//calculate the pseudonym with the addin of the given id
	private static byte[] getPseudonym( String id, String addin){
		String s = Assist.connectStrings(id, addin).toString();
		return new H256().process(s);
	}
	
	// find the index of id
	public int index(String id){
		for(int i=0; i<pseudonym.length; i++){
			byte[] temp = getPseudonym(id, addin);
			if(Arrays.equals(temp, pseudonym[i])){
				return i;
			}
		}
		return -1;
	}
	public static Bulletin_NAP newInstance(String[] ids, String addin){
		Bulletin_NAP bn = new Bulletin_NAP();
		// create pseudonym
		bn.pseudonym = new byte[ids.length][];
		bn.addin = addin;
	
		for(int i=0; i<ids.length; i++){
			bn.pseudonym[i] = getPseudonym(ids[i], addin);
		}
		return bn;
	}	
}
