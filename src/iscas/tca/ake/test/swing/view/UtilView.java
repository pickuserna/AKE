package iscas.tca.ake.test.swing.view;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-10-15ÏÂÎç1:17:18
 */
public class UtilView {
	public static <K, V>void updateList(JList list , LinkedHashMap<K, V> map){
		DefaultListModel listValue = new DefaultListModel();
		list.removeAll();
		Iterator<Map.Entry<K, V>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = iter.next();
			listValue.addElement(entry.getKey().toString());
		}
		list.setModel(listValue);
	}
}
