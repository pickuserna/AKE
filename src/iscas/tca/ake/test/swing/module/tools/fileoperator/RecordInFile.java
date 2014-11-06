package iscas.tca.ake.test.swing.module.tools.fileoperator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * 描述：<>
 * @author zn
 * @CreateTime 2014-8-22下午7:15:54
 */
public class RecordInFile {

	public static void writeInto(String fileName, String s)
	{
		File f = new File(fileName);
		//文件已经存在了
		try{
			if(!f.exists())
			{
				f.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(f, true);
			fos.write(("\r\n").getBytes());
			fos.write(s.getBytes());
			fos.close();
			fos.flush();
		}
		catch(IOException e)
		{
			System.out.println("WriteIntoFile failed");
		}
		finally{
			
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Calendar.getInstance().getTime());

		writeInto("F:"+File.separator+"filedata.txt","filedata");
	}

}
