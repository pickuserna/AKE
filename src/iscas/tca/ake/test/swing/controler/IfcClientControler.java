package iscas.tca.ake.test.swing.controler;

import iscas.tca.ake.test.swing.module.MyClient;
import iscas.tca.ake.test.swing.view.observer.IfcObserver;

import java.util.Map;

public interface IfcClientControler {
	void setArgs(IfcObserver obs, Map<String, Object> frameArgs);
	void runIt();
	byte[] getSessionKey();
}
