package iscas.tca.ake.test.swing.module.tools;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * √Ë ˆ£∫<>
 * ¿‡√˚£∫<SendAndRecv>
 * @author zn
 * @CreateTime 2014-8-21œ¬ŒÁ1:17:32
 */
public class SendAndRecv {
	public static boolean sendMsg(Object so, Socket socket) throws Exception{

		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(so);
		return true;
		
	}
	public static Object recvMsg( Socket socket)throws Exception
	{
		InputStream is = socket.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		return ois.readObject();
	}

}
