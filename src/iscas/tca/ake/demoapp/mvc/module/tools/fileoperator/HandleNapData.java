package iscas.tca.ake.demoapp.mvc.module.tools.fileoperator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-24下午2:00:54
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
public class HandleNapData {

	String sourceFile;
	String outputFile;
	ExcelWriter2 excelWriter2;
	int headRow;
	int headLableFlag = 0;
	//server和client数据项目的标记
	public static final String ServerFlag = "server";
	public static final String ClientFlag = "client";
	public static final String SplitString = ":";
	
	public static final int ServerRowDelta = 1;
	public static final int ClientRowDelta = 2;
	//指定文件名字
	public HandleNapData(String sourceFile, String outputFile)
	{
		this.sourceFile = sourceFile;
		this.outputFile = outputFile;
	}
	
	public void write()
	{
		try{
			//打开输入文件
			FileInputStream fis = new FileInputStream(this.sourceFile);
			//读字符
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			//打开输出文件
			excelWriter2 =  new ExcelWriter2();
			excelWriter2.openWorkbook(this.outputFile);
			String s=null;
			while(null!=(s=br.readLine()))
			{
				//空行代表下一个表开始了
				if(s.isEmpty())
				{
					headRow = 2 + excelWriter2.getLastRowNum();
				}
				else{
					formatWrite(s);
				}
			}
			excelWriter2.saveData();
		}
		catch(IOException e)
		{
			System.out.println("HandleData.formatWrite Error");
			e.printStackTrace();
		}
	}
	
	public void formatWrite(String s)
	{
		String sLower = s.toLowerCase();
		//ss[0]和ss[1]
		String[] ss = sLower.toLowerCase().split(SplitString);
		int col = excelWriter2.getLastColNum(headRow);
		//server 和服务器的数据
		if(sLower.contains(ServerFlag)
				|| sLower.contains(ClientFlag))
		{
			if(headLableFlag%10==0){
				excelWriter2.writeToCell(headRow, col, ss[1]);
			}
			if(sLower.contains(ServerFlag))
				excelWriter2.writeToCell(headRow + ServerRowDelta, col, ss[0]);
			else
				excelWriter2.writeToCell(headRow + ClientRowDelta, col, ss[0]);
		}	
		else{//其他的数据
			if(ss.length>=2)
			{
				excelWriter2.writeToCell(headRow+1, col, ss[1]);		
			}
			excelWriter2.writeToCell(headRow + 0, col, ss[0]);		
		}
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sourceFile = "F:\\napdata.txt";
		String outputFile = "F:\\handledata.xlsx";
		HandleNapData hnd= new HandleNapData(sourceFile, outputFile);
		hnd.write();
	}

}
