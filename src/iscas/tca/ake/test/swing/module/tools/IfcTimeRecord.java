package iscas.tca.ake.test.swing.module.tools;

import java.util.Set;

public interface IfcTimeRecord {
	//默认的记录时间函数
	//start record the time 
	void startRec(String tagName);
	Long endRec(String tagName);
	//记录某个位置的code   运行的次数
	//record the frequency of execution of one point
	int hitRec(String tagName);
	void showResult();
	void recToFile(String fileName);
	void clear();
}
