package iscas.tca.ake.message.nap;

import iscas.tca.ake.util.JsonUtil;
import iscas.tca.ake.util.UtilMy;

interface I {
	public int geta();

	public String getb();
}

class A implements I {

	@Override
	public int geta() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public String getb() {
		// TODO Auto-generated method stub
		return "b";
	}

}

class Bean {
	// static is a property??
	public String getN() {
		return "1";
	}

	public void setN(String N) {

	}

	public String getLonely() {
		return "lonely";
	}

	// None Operate S
	// static �������� ����
	public static String getS() {
		return "s";
	}

	public static void setS() {
	}

	// get()������������
	// �������첻��Ӱ��
	public String getNihao(int a) {
		return "get �в���";
	}

	// get() void ����ֵ
	// BasicSerializerFactory can not access a member of class
	// org.codehaus.jackson.map.ser.std.NullSerializer with modifiers "private"
//	public void getVoid() {
//
//	}

	/**<TODO> :���ض��󣬴˶���ʵ���˽ӿڣ��ӿ��ж�����get
	 *<result>:OK!!,���ض����json��ʽ{"b":"b","a":-1}
	*/
	public A getObject() {
		return new A();
	}

}

public class TestTemp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bean b = new Bean();
		try {
			String json = JsonUtil.beanToJson(b);
			UtilMy.print(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
