package iscas.tca.ake.test.swing.module.tools;

import java.util.Set;

public interface IfcTimeRecord {
	//Ĭ�ϵļ�¼ʱ�亯��
	void startRec(String tagName);
	Long endRec(String tagName);
	//��¼ĳ��λ�õ�code   ���еĴ���
	int hitRec(String tagName);
	void showResult();
	void recToFile(String fileName);
	void clear();
}
