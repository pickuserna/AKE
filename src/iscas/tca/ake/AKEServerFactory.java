package iscas.tca.ake;

import iscas.tca.ake.napake.NAPServer;
import iscas.tca.ake.veap.VEAPServer;

/**
 * @author zn
 * @CreateTime 2014-11-18ионГ9:57:37
 */
public class AKEServerFactory {
	
	public static IfcAkeProtocol newInstance(String proType){
		if(proType.equals(AKEConstants.CT_ProType_NAPYZ)){
			return new NAPServer();
		}
		else if(proType.equals(AKEConstants.CT_ProType_SKI)){
			return new VEAPServer();
		}
		return null;
	}
}
