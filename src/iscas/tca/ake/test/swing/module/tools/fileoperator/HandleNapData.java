package iscas.tca.ake.test.swing.module.tools.fileoperator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-24下午2:00:54
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
