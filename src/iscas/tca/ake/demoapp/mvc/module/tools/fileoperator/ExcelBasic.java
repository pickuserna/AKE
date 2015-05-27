package iscas.tca.ake.demoapp.mvc.module.tools.fileoperator;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-23下午2:42:24
 */
/*
 * Copyright (c) 20014-2041 Institute Of Software Chinese Academy Of Sciences
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @Organization: http://tca.iscas.ac.cn/
 * @author: Nan Zhou
 * @Aknowledge: Tutor Liwu Zhang , Alumnus Yan Zhang, Zhigang Gao
 * @Email: changchangge123@qq.com
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
