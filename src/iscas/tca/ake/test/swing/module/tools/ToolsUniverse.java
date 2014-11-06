package iscas.tca.ake.test.swing.module.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-10-13ÏÂÎç7:50:54
 */
public class ToolsUniverse {

	/**
	 * TODO:<>
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String formatNowTime(String formate){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(formate);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

}
