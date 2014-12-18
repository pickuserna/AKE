package iscas.tca.ake.test.swing.view.observer;

import iscas.tca.ake.test.swing.module.Response;


/**
 * @author zn
 * @CreateTime 2014-10-9ионГ10:58:27
 */
public interface IfcObserver extends IfcExecutionObserver{
	public void update(Response result);
	public void setStatus(String status);
	public void updateExecution(Response response);
}
