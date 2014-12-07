package iscas.tca.ake.test.swing.module.bulletin.interfaces;

public interface IfcWaitorAndNotifier {
	//waiting for something to be done
	void waitForDone()throws Exception;
	//have something done and notify the waitors
	void doneIt();
}	
