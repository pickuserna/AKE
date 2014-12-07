package iscas.tca.ake.test.swing.module.bulletin.interfaces;

public interface IfcWaitorAndNotifier {
	//waiting for someting to be done
	void waitForDone()throws Exception;
	//have done something(denoted by a flag ), and notify the waitors 
	void doneIt();
	// remove the waitorAndNotifier
	void remove();
}	
