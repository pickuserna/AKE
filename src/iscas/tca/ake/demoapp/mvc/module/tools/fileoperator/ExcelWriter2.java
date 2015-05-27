package iscas.tca.ake.demoapp.mvc.module.tools.fileoperator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-24上午11:32:38
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
	 * TODO:<返回sheet的最后一行行标>
	 * @return sheet的最后一行行标
	 */
	public int getLastRowNum()
	{
		return this.sheet.getLastRowNum();
	}
	/**
	 * TODO:<得到row行的最后一列的列标>
	 * @param row
	 * @return 该行不存在则返回-1；否则col
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
			//判断Row是否存在
			if(sheetRow==null){
				sheetRow = this.sheet.createRow(row);
			}
			sheetRow.createCell(col).setCellValue(s);
			}
		else{
			System.out.print("没有初始化\n");
		}
	}
	@Override
	public void openWorkbook(String filePath) {
		// TODO Auto-generated method stub
		if(filePath.trim().toLowerCase().endsWith("xlsx")){
			this.filePath = filePath;//记录文件的路径
			try{
				File file = new File(filePath);
				if(file.exists()){//工作簿已经存在
					this.workbook = new XSSFWorkbook(new FileInputStream(filePath));
					this.sheet = this.workbook.getSheet(defaultSheet);
				}
				else{//工作簿不存在
					this.workbook = new XSSFWorkbook();
					this.sheet = this.workbook.createSheet(defaultSheet);
			
				}
			}catch(Exception e)
			{
				System.out.println("openWorkbook Error:"+filePath);
				e.printStackTrace();
			}
		}
		else {System.out.println("文件名称不对:"+filePath);}
		
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
