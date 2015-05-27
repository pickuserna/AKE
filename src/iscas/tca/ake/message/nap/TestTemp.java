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
	// static 方法不是 属性
	public static String getS() {
		return "s";
	}

	public static void setS() {
	}

	// get()方法包含参数
	// 正常构造不受影响
	public String getNihao(int a) {
		return "get 有参数";
	}

	// get() void 返回值
	// BasicSerializerFactory can not access a member of class
	// org.codehaus.jackson.map.ser.std.NullSerializer with modifiers "private"
//	public void getVoid() {
//
//	}

	/**<TODO> :返回对象，此对象实现了接口，接口中定义了get
	 *<result>:OK!!,返回对象的json格式{"b":"b","a":-1}
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
