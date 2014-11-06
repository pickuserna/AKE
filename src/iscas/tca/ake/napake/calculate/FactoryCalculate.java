package iscas.tca.ake.napake.calculate;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-8-21ÏÂÎç6:40:10
 */
public class FactoryCalculate {

	/**
	 * TODO:<>
	 * @param args 
	 */
	public static IfcNapCalculate getCalculate(String name)
	{
		if(name.toLowerCase().equals("napcalculate"))
		{
			return new NAPCalculate();
		}
		else {
			return null;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
