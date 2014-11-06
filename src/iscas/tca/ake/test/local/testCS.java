//
//package iscas.tca.ake.test.local;
//
//import static java.lang.System.out;
//import iscas.tca.ake.IfcAkeProtocol;
//import iscas.tca.ake.message.IfcMessage;
//import iscas.tca.ake.message.nap.NAPMessage;
//import iscas.tca.ake.message.nap.NAPMessage.IDsData;
//import iscas.tca.ake.message.nap.NAPMessage.SAsData;
//import iscas.tca.ake.message.nap.NAPMessage.XstarBData;
//import iscas.tca.ake.message.nap.NAPMessage.YAuthsData;
//import iscas.tca.ake.napake.InitClientData;
//import iscas.tca.ake.napake.InitServerData;
//import iscas.tca.ake.napake.NAPClient;
//import iscas.tca.ake.napake.NAPServer;
//import iscas.tca.ake.util.Assist;
//
//import java.math.BigInteger;
//
//import junit.framework.TestCase;
///**
// * 描述：<>
// * 类名：<testCS>
// * @author zn
// * @CreateTime 2014-8-20上午9:12:02
// */
//public class testCS extends TestCase{
//
//	public void test()
//	{
//		//assertEquals(, actual)
//	}
//	
//	public static void main(String[] args) throws Exception{
//		// TODO Auto-generated method stub
//		BigInteger g = new BigInteger("2");
//		BigInteger q = new BigInteger("115701105422726514040638156162" +
//				"419691456577947418233606901093" +
//				"32135242374211361344848130502170" +
//				"13580621665036410054272" +
//				"4453576555630050667260490679134257474957");
//		//客户端初始化
//		print("客户端初始化");
//		IfcAkeProtocol client = new NAPClient();
//		InitClientData  init = new InitClientData();
//		init.setM_g(g);
//		init.setM_q(q);
//		init.setM_ID("Gandalf");
//		init.setM_IDs("Gandalf,Saruman,Baggins,Arwen".split(","));
//		init.setM_pw("123456");
//		
//		
//		client.init(init);
//		//服务器端初始化
//		print("服务器端初始化");
//		IfcAkeProtocol server = new NAPServer();
//		InitServerData  init2 = new InitServerData();
//		init2.setM_g(g);
//		init2.setM_q(q);
//		init2.setM_S("Ring");
//		
//		server.init(init2);
//		//协议开始了
//		print("协议开始了");
//		IfcMessage cMsg = null; 
//		IfcMessage sMsg  = null;
//		IfcMessage sMsg2 = null;
//		
//		cMsg = test_Start(client);//发送第一条消息
//		sMsg = test_SAs(server,cMsg);
////		sMsg2 = test_SAs(server, sMsg);
//		
//		cMsg = test_XstarB(client, sMsg2);
//		sMsg = test_YAuths(server, cMsg);
//		test_VerifyAuths(client, sMsg);
//		//测试模块
//		//init.setM_g(new BigIn)
//		printClient(client);
//		printServer(server);
//		byte[] cAuths = ((NAPClient)client).getM_myAuths();
//		byte[] sAuths = ((NAPServer)server).getM_Auths();
//		
//		System.out.print("Auths 是否相等：");
//		System.out.println(Assist.bytesToHexString(cAuths).equals(Assist.bytesToHexString(sAuths)));
//		System.out.print("sk是否相等:");
//		System.out.println(getClientSK(client).equals(getServerSK(server)));
//	}
//	
//	public static void initClient(IfcAkeProtocol c)
//	{
//		
//	}
//	public static void initServer(IfcAkeProtocol s)
//	{
//		
//	}
//////////////////////////////---------------------Client的消息------------------------///////////////////////////////
//	public static IfcMessage test_Start(IfcAkeProtocol c)
//	{
//		IfcMessage msg_start = c.startProtocol();
//		showNapMsg(msg_start);
//		return msg_start;
//	}
//	public static IfcMessage test_XstarB(IfcAkeProtocol c, IfcMessage msg)throws Exception
//	{
//
//		IfcMessage xbMsg = c.processMessage(msg);
//		showNapMsg(xbMsg);
//		return xbMsg;
//	}
//	public static IfcMessage test_VerifyAuths(IfcAkeProtocol c, IfcMessage msg)throws Exception
//	{
//		c.processMessage(msg);
//		return null;
//	}
//	////////------------------------Server的消息--------------------------------/////////
//	public static IfcMessage test_SAs(IfcAkeProtocol s, IfcMessage msg)throws Exception
//	{
//		IfcMessage askMsg = s.processMessage(msg);
//		showNapMsg(askMsg);
//		return askMsg;
//	}
//
//	
//	public static IfcMessage test_YAuths(IfcAkeProtocol s, IfcMessage msg)throws Exception
//	{
//		
//		IfcMessage yAuths = s.processMessage(msg);
//		
//		showNapMsg(yAuths);
//		return yAuths;
//	}
//	public static void printClient(IfcAkeProtocol client)
//	{
//		NAPClient c = (NAPClient)client;
//		print("------Client变量---------");
//		out.println("X: "+c.getM_X());
//		out.println("Z: "+c.getM_Z() );
//		out.println("Xstar" +c.getM_Xstar());
//		out.println("B: "+c.getM_B());
//		
//		out.println("K: "+c.getM_K());
//		out.println("Trans: "+c.getM_Trans());
//		out.println("myAuths: "+Assist.bytesToHexString(c.getM_myAuths()));
//		out.println("Client的SK:"+ getClientSK(c));
//	}
//	
//	public static void printServer(IfcAkeProtocol server)
//	{
//		NAPServer s = (NAPServer)server;
//		print("------Server变量---------");
//		out.println("Xp: "+s.getM_Xp());
//		out.println("Zp: "+s.getM_Zp() );
//		out.println("Xstar" +s.getM_Xstar());
//		out.println("B: "+s.getM_B());
//		
//		out.println("Kp: "+s.getM_Kp());
//		out.println("Trans: "+s.getM_Trans());
//		out.println("Auths: "+Assist.bytesToHexString(s.getM_Auths()));
//		out.println("Server的SK:"+getServerSK(s));
//		
//	}
//	public static String getClientSK(IfcAkeProtocol c)
//	{
//		return Assist.bytesToHexString(((NAPClient)c).getsk());
//	}
//	public static String getServerSK(IfcAkeProtocol s)
//	{
//		return Assist.bytesToHexString(((NAPServer)s).getsk());
//	}
//	//////////////
//	public static  void print(BigInteger[] bs)
//	{
//		for(BigInteger b:bs)
//		{
//			out.println("	"+b.toString());
//		}
//	}
//	public static void print(String[] ss)
//	{
//		for(String s:ss)
//		{
//			System.out.print("	"+s);
//		}
//		System.out.println();
//	}
//	public static void print(String s)
//	{
//		System.out.println(s);
//	}
//	public static void print(String s, Object o)
//	{
//		System.out.println(s+o.toString());
//	}
//	public static void showNapMsg(IfcMessage m)
//	{
//		//System.out.println("print NapMsg Infomation");
//		NAPMessage mNap = (NAPMessage)m;
//		out.println("\n----消息类型为"+mNap.getM_msgType());
//		IfcMessage data = mNap.getM_data();
//		switch(mNap.getM_msgType())
//		{
//		case IDs:
//			
//			IDsData ids = (IDsData)data;
//			out.print("IDs: ");
//			print(ids.getM_IDs());
//			break;
//		case SAs:
//			
//			SAsData sas = (SAsData)data;
//			out.println("SID: " + sas.getM_SID());
//			out.print("As: ");
//			print(sas.getM_As());
//			break;
//		case XstarB:
//			
//			XstarBData xb = (XstarBData)data;
//			out.print("Xstar = ");
//			out.println(" "+xb.getM_Xstar());
//			out.print("B = ");
//			out.println(" "+xb.getM_B());
//			break;
//		case YAuths:
//			
//			YAuthsData ya =(YAuthsData)data;
//			out.print(" Auths =");
//			out.println(" "+Assist.bytesToHexString(ya.getM_Auths()));
//		}
//
//	}
//	
//}
