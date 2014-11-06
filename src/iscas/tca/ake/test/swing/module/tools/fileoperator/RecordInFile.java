package iscas.tca.ake.test.swing.module.tools.fileoperator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * ������<>
 * @author zn
 * @CreateTime 2014-8-22����7:15:54
 */
public class RecordInFile {

	public static void writeInto(String fileName, String s)
	{
		File f = new File(fileName);
		//�ļ��Ѿ�������
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
