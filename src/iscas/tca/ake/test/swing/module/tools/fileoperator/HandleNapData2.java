package iscas.tca.ake.test.swing.module.tools.fileoperator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-26下午7:41:19
 */
public class HandleNapData2 {

	/**
	 * TODO:<>
	 * @param args 
	 */
	public static String dirPath = "F:\\result";
	public static void recInExcel()throws Exception
	{
		File dir  = new File(HandleNapData2.dirPath);
		File[] files = dir.listFiles();
		int idNum;
		int headRow;
		int headCol;
		ExcelWriter2 ew2 = new ExcelWriter2();
		ew2.openWorkbook("F:\\result\\statistics.xlsx");
		
		for(File afile:files)
		{
			if(afile.isFile() && afile.getName().contains("+")){
				String fileName = afile.getName();//记录文件名
				String[] nameParams = fileName.split("[+]");
				String[] idParams = nameParams[1].split("[.]");
				idNum = Integer.valueOf(idParams[0]);
				headRow = (int)(Math.log(idNum)/Math.log(2));
				headCol = 2;
				
				int row=0;
				int col=0;
				//读取文件
				FileInputStream fis = new FileInputStream(afile);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String line = null;
				int i = 0;
				while((line=br.readLine())!=null){
					if(line.isEmpty()){
						continue;
					}
						String[] lineParams = line.split("次");
						int ci = Integer.valueOf(lineParams[0].trim());
						String[] timeParams = lineParams[1].trim().split(":");
						String[] runTime = timeParams[1].split("ID");
						int t = Integer.valueOf(runTime[0].trim());
						if(ci==30){
							col = i+headCol;
							if(fileName.toLowerCase().contains("server")){
								row = headRow*2+1;
							}
							else if(fileName.toLowerCase().contains("client")){
								row =headRow*2+2;
							}
							ew2.writeToCell(row, col, String.valueOf(t));
							System.out.println("["+row+","+col+"]"+" t:"+t+" ci:"+ci+" idFre:"+idNum);
							i++;
						}
						if(ci==1000){
							break;
						}
				}
			}
		}
		ew2.saveData();
	}
	

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		recInExcel();
	}

}
