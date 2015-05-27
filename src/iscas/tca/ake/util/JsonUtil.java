package iscas.tca.ake.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;


/*
 * Copyright 2015 name changchangge123@qq.com
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 *
 * @Organization: http://tca.iscas.ac.cn/
 * @author: Nan Zhou
 * @Aknowledge: Tutor Liwu Zhang , Alumnus Yan Zhang, Zhigang Gao
 * @Email: changchangge123@qq.com
 */
public class JsonUtil {
	private static ObjectMapper objectMapper;

//	private static final String GENERIC_BINDING = "{\"key1\":{\"name\":\"name2\",\"type\":2},\"key2\":{\"name\":\"name3\",\"type\":3}}";
	static String charset = "utf-8";

	/**
	 * @param args
	 */
	// int and bytes
	public static void testOffline() throws Exception {
		String s = "{\"message\":{\"userID\":\"user\",\"password\":\"user1234\",\"groupID\":\"group_U\",\"host\":\"localhost\",\"port\":8007}}";
		ObjectMapper om = JsonUtil.getMapperInstance();
		JsonNode root = om.readTree(s);
		JsonNode msg = root.path("message");
		System.out.println(msg);

	}

	public static synchronized ObjectMapper getMapperInstance() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	private static void init() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
	}

	/**
	 * TODO:<把java 对象转化成json字符串>
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String beanToJson(Object obj) throws Exception { // json =
																	// mapper;
		init();
		String jsonString = objectMapper.writeValueAsString(obj);
		return jsonString;
	}

	// public static String beanToJson2(Object obj )throws Exception{
	// JSONPObject jsonpObject =new JSONPObject(function, value);
	// }
	public static <T> T jsonToBean(String jsonString, Class<T> beanClass)
			throws Exception {
		init();
		T jsonObj = objectMapper.readValue(jsonString, beanClass);
		return jsonObj;
	}

	/**
	 * TODO:<>
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String AccountBeanName = "xx小明";
		Book b = new Book("harry Patter", 100.212);
		Book a = new Book("Shuke and beida", 310.7);
		Book[] bs = {a, b};
		AccountBean acBean = new AccountBean(12333, AccountBeanName, "中山市上条路",
				b, bs);
		String jsonString = JsonUtil.beanToJson(acBean);
		UtilMy.print(jsonString = jsonString.replaceAll("pric\\w*", "price"));
		UtilMy.print(jsonString);
		UtilMy.print(JsonUtil.jsonToBean(jsonString, AccountBean.class));
		// testOffline();
	}

}
//-----------------------------------------------------------------test----------------------------------------------------------------------
class Book {
	String title;
	double price2;

	public Book() {

	}

	public Book(String title, double price) {
		this.title = title;
		this.price2 = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice2() {
		return price2;
	}

	public void setPrice2(double price) {
		this.price2 = price;
	}

	// String
	@Override
	public String toString() {
		return "book.title:\"" + title + "\"	book.price:" + price2;
	}

}

class AccountBean {
	private int m_id;
	private String m_name;
	private String email;
	private Book book;
	private Book[] books;

	// getter、setter

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("#books:\n");
		for (int i = 0; i < books.length; i++) {
			sb.append("  " + books[i] + "\n");
		}
		return this.m_name + "\n#" + this.m_id + "\n#" + this.email
				+ "\n#book.title:" + book.title + "\n#book.price:" + book.price2+"\n"+sb.toString();
	}

	/**
	 * @param id
	 * @param name
	 * @param email
	 * @param book
	 */
	public AccountBean(int id, String name, String email, Book book, Book[] bs) {
		super();
		this.m_id = id;
		this.m_name = name;
		this.email = email;
		this.book = book;
		this.books = bs;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public AccountBean() {

	}

	/**
	 * @param id
	 * @param name
	 * @param email
	 */
	public AccountBean(int id, String name, String email) {
		super();
		this.m_id = id;
		this.m_name = name;
		this.email = email;
	}

	public int getId() {
		return m_id;
	}

	public String getmName() {
		return m_name;
	}

	public String getEmail() {
		return email;
	}

	public Book[] getBooks() {
		return books;
	}

	public void setBooks(Book[] books) {
		this.books = books;
	}

	public void setId(int id) {
		this.m_id = id;
	}

	public void setmName(String name) {
		this.m_name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
