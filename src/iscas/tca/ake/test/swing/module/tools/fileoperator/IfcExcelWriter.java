package iscas.tca.ake.test.swing.module.tools.fileoperator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * ������<>
 * @author zn
 * @CreateTime 2014-8-24����11:32:55
 */
//sheet������־�ʹ��Ĭ�ϵ�������
public abstract class IfcExcelWriter {
	Workbook workbook;//Interfaces
	String filePath;//�򿪵�·��
	Sheet sheet;
	public static String defaultSheet = "Sheet1";
	
	/**
	 * TODO:<��������> 
	 */
	public abstract void saveData();
	/**
	 * TODO:<д�뵽�ļ���> 
	 */
	public abstract void writeToCell(String s);//д�뵽cell��
	public abstract void writeToCell(int row, int col, String s);//д�뵽cell��
	
	/**
	 * TODO:<�򿪹�����>
	 * @param filePath ����������·��
	 */
	public abstract void openWorkbook(String filePath);
	/**
	 * TODO:<�жϳ���writer�Ƿ�����˳�ʼ��>
	 * @return 
	 */
	public boolean isInit(){
		if(workbook!=null)
			return true;
		return false;
	}
}
