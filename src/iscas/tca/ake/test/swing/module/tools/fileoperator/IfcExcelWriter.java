package iscas.tca.ake.test.swing.module.tools.fileoperator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-24上午11:32:55
 */
//sheet表的名字就使用默认的名字了
public abstract class IfcExcelWriter {
	Workbook workbook;//Interfaces
	String filePath;//打开的路径
	Sheet sheet;
	public static String defaultSheet = "Sheet1";
	
	/**
	 * TODO:<保存数据> 
	 */
	public abstract void saveData();
	/**
	 * TODO:<写入到文件中> 
	 */
	public abstract void writeToCell(String s);//写入到cell中
	public abstract void writeToCell(int row, int col, String s);//写入到cell中
	
	/**
	 * TODO:<打开工作簿>
	 * @param filePath ：工作簿的路径
	 */
	public abstract void openWorkbook(String filePath);
	/**
	 * TODO:<判断程序writer是否进行了初始化>
	 * @return 
	 */
	public boolean isInit(){
		if(workbook!=null)
			return true;
		return false;
	}
}
