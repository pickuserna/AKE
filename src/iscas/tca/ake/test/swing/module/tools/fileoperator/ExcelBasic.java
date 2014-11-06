package iscas.tca.ake.test.swing.module.tools.fileoperator;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-23下午2:42:24
 */
public class ExcelBasic {
	static String filePath = "f:"+File.separator+"workbook.xlsx";
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
//		int i = 1;
//		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(filePath));
//		Sheet sheet1 = xwb.getSheet("Sheet1");
//		
//		Row r1 = sheet1.getRow(1);
//		//Row r1 = sheet1.createRow(1);
//		//r1.createCell(1).setCellValue("[1,1]");
//		
//		System.out.println("sheet physicalcol"+r1.getPhysicalNumberOfCells());
//		System.out.println("sheet lastcell"+r1.getLastCellNum());
//		
//		assert i==1;
//		
//		System.out.println("sheet physicalRow"+sheet1.getPhysicalNumberOfRows());
//		
//		xwb.write(new FileOutputStream(filePath));
//		String filePath = "f:"+File.separator+"wb2.xlsx";
//		File file = new File(filePath);
//		System.out.println(file.exists());

		XSSFWorkbook wb = new XSSFWorkbook();
		FileOutputStream fos = new FileOutputStream(filePath);
		wb.write(fos);
		fos.close();
	
	}
		
		
/**
 * TODO:<判断指定名字的单元表是否存在>
 * @param xwb
 * @param sheetName
 * @return 
 */
public static boolean isSheetExists(XSSFWorkbook xwb, String sheetName)
{
	int num = xwb.getNumberOfSheets();
	for(int i=0; i<num; i++)
	{
		if(xwb.getSheetName(i).equals(sheetName))
			return true;
	}
	return false;

}
}
