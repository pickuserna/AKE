package iscas.tca.ake.demoapp.mvc.module.tools.fileoperator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * ������<>
 * @author zn
 * @CreateTime 2014-8-24����11:32:55
 */
//sheet������־�ʹ��Ĭ�ϵ�������
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
