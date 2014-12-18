package iscas.tca.ake.test.swing.module.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class FileOperator {
	public static void writeInto(String fileName, String s)
	{
	
		//文件已经存在了
		try{
			File f = openOrCreateFile(fileName);
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
		catch(Exception e)
		{
			System.out.println("WriteIntoFile failed");
		}
		
		finally{
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static File openOrCreateFile(String fp)throws Exception{
		File f = new File(fp);
		if(!f.exists()){
			f.getParentFile().mkdirs();
			f.createNewFile();
		}
		return f;
	}
	//un done
	public static void deldeDir(){
		throw new UnsupportedOperationException();
	}
	public static void deleteFile(String filePath){
		File f = new File(filePath);
		if(f.exists()){
			f.delete();
		}
	}
	public static void writeObjectToFile(Object ob, String filePath)throws Exception{
		File f = openOrCreateFile(filePath);
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(ob);
	}
	//read one Object from file
	public static Object readObjectFromFile(String filePath)throws Exception{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filePath)));
		return ois.readObject();
	}
	public static void  writeObjects(Object[]obs, String filePath)throws Exception{
		File f = openOrCreateFile(filePath);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		for(Object ob : obs){
			oos.writeObject(ob);
		}
		oos.flush();
	}
	public static Object[] readObjects(int n, String filePath) throws Exception{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
		Object[] obs = new Object[n];
		for(int i=0;i<n ;i++)
			obs[i] = ois.readObject();
		return obs;
	}
	@Test
	public void testRWArr()throws Exception{
		String[] arr = {"1" , "2", "3"};
		writeObjects(arr, "\\test");
		writeObjectToFile(arr, "E:\\test\\file");
		UtilMy.print(((String[])readObjectFromFile( "E:\\test\\file"))[1]);
		//UtilMy.print(readObjects(3, "\\test"));
	}
}
