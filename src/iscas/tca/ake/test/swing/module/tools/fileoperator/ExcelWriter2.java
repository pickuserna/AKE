package iscas.tca.ake.test.swing.module.tools.fileoperator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ������<>
 * @author zn
 * @CreateTime 2014-8-24����11:32:38
 */
public class ExcelWriter2 extends IfcExcelWriter{
	
	

	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		try{
			FileOutputStream fos = new FileOutputStream(this.filePath);
			this.workbook.write(fos);
			fos.flush();
		}catch(Exception e)
		{
			System.out.println("saveData error:"+this.filePath);
			e.printStackTrace();
		}
	}
	/**
	 * TODO:<����sheet�����һ���б�>
	 * @return sheet�����һ���б�
	 */
	public int getLastRowNum()
	{
		return this.sheet.getLastRowNum();
	}
	/**
	 * TODO:<�õ�row�е����һ�е��б�>
	 * @param row
	 * @return ���в������򷵻�-1������col
	 */
	public int getLastColNum(int row){
		Row sheetRow = this.sheet.getRow(row);
		if(sheetRow==null){
			return 0;
		}
		else{
			return sheetRow.getLastCellNum();
		}
	}
	@Override
	public void writeToCell(String s) {
		// TODO Auto-generated method stub
		System.out.print("ignore(not implements)");
	}

	@Override
	public void writeToCell(int row, int col, String s) {
		// TODO Auto-generated method stub
		if(isInit()){
			Row sheetRow = this.sheet.getRow(row);
			//�ж�Row�Ƿ����
			if(sheetRow==null){
				sheetRow = this.sheet.createRow(row);
			}
			sheetRow.createCell(col).setCellValue(s);
			}
		else{
			System.out.print("û�г�ʼ��\n");
		}
	}
	@Override
	public void openWorkbook(String filePath) {
		// TODO Auto-generated method stub
		if(filePath.trim().toLowerCase().endsWith("xlsx")){
			this.filePath = filePath;//��¼�ļ���·��
			try{
				File file = new File(filePath);
				if(file.exists()){//�������Ѿ�����
					this.workbook = new XSSFWorkbook(new FileInputStream(filePath));
					this.sheet = this.workbook.getSheet(defaultSheet);
				}
				else{//������������
					this.workbook = new XSSFWorkbook();
					this.sheet = this.workbook.createSheet(defaultSheet);
			
				}
			}catch(Exception e)
			{
				System.out.println("openWorkbook Error:"+filePath);
				e.printStackTrace();
			}
		}
		else {System.out.println("�ļ����Ʋ���:"+filePath);}
		
	}

	@Override
	public boolean isInit() {
		// TODO Auto-generated method stub
		return super.isInit();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExcelWriter2 ew2 = new ExcelWriter2();
		ew2.openWorkbook("F:\\data.xlsx");
		ew2.writeToCell(2, 2, "excelwriter2");
		ew2.saveData();
	}

}
