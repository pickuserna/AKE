package iscas.tca.ake.demoapp.mvc.module.tools.fileoperator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-24上午11:32:55
 */
//sheet表的名字就使用默认的名字了
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
