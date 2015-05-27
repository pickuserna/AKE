

package iscas.tca.ake.demoapp.mvc.view;

import iscas.tca.ake.demoapp.mvc.controler.ClientControlor;
import iscas.tca.ake.demoapp.mvc.module.EnumTags;
import iscas.tca.ake.demoapp.mvc.module.Response;
import iscas.tca.ake.demoapp.mvc.module.tools.UtilMy;
import iscas.tca.ake.demoapp.mvc.view.observer.IfcExecutionObserver;
import iscas.tca.ake.demoapp.mvc.view.observer.IfcObserver;
import iscas.tca.ake.util.Assist;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.DefaultListModel;


/*
 * Copyright (c) 20014-2041 Institute Of Software Chinese Academy Of Sciences
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @Organization: http://tca.iscas.ac.cn/
 * @author: Nan Zhou
 * @Aknowledge: Tutor Liwu Zhang , Alumnus Yan Zhang, Zhigang Gao
 * @Email: changchangge123@qq.com
 */
public class ClientFrame extends javax.swing.JFrame implements IfcObserver,
		IfcExecutionObserver {
	private LinkedHashMap<String, String> dataExecution = new LinkedHashMap<String, String>();

	@Override
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException();
		this.label_result.setText(status);
		if (status.contains("true")) {
			this.label_result.setBackground(Color.green);
		} else if (status.contains("false")) {
			this.label_result.setBackground(Color.red);
		} else {
			this.label_result.setBackground(Color.white);
		}

	}

	private void updateResultField(Response result) {
		StringBuilder sb = new StringBuilder();
		String display = Assist.traverseMap(result.getDisplayMap());
		String t = Assist.traverseMap(result.getTimeRecord());
		sb.append("display:\n" + display + "\n");
		sb.append("Time Cost:\n" + t + "\n");
		this.jTextArea_result.setText(sb.toString());
		this.jTextArea_result.setCaretPosition(0);

	}

	private void updateSettings(Response result) {
		String bit = result.getBit();
		String proType = result.getProType();
		if(proType.equals("NAP")){
			proType = "NAP_YZ";
		}
		else{
			proType = "SKI";
		}
		String ids = result.getIDNum();
		this.txt_bit.setText(bit);
		this.txt_proType.setText(proType);
		this.txt_ids.setText(ids);
	}

	public void updateExecution(Response response) {
		DefaultListModel listValue = new DefaultListModel();
		this.list_excution.removeAll();
		this.dataExecution = response.getExecutionSteps();
		Iterator<Map.Entry<String, String>> iter = this.dataExecution
				.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			listValue.addElement(entry.getKey());
		}

		list_excution.setModel(listValue);
		//		list_excution.setSelectedIndex(0);
	}

	@Override
	public void update(Response result) {
		// TODO Auto-generated method stub
		this.updateExecution(result);
		updateResultField(result);
		updateSettings(result);
	}

	/** Creates new form SwingFrame */
	public ClientFrame() {
		initComponents();
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jFrame1 = new javax.swing.JFrame();
		jPanel1 = new javax.swing.JPanel();
		UserName = new java.awt.Label();
		GroupID = new java.awt.Label();
		Password = new java.awt.Label();
		textUserName = new java.awt.TextField();
		textGroupID = new java.awt.TextField();
		textPassword = new java.awt.TextField();
		label1 = new java.awt.Label();
		label2 = new java.awt.Label();
		label3 = new java.awt.Label();
		txt_ids = new java.awt.TextField();
		label_result = new java.awt.Label();
		btn_login = new java.awt.Button();
		txt_bit = new java.awt.TextField();
		txt_proType = new java.awt.TextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		list_excution = new javax.swing.JList();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea_result = new javax.swing.JTextArea();
		jScrollPane3 = new javax.swing.JScrollPane();
		jTextArea_content = new javax.swing.JTextArea();
		txtURL = new java.awt.TextField();
		label4 = new java.awt.Label();

		javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(
				jFrame1.getContentPane());
		jFrame1.getContentPane().setLayout(jFrame1Layout);
		jFrame1Layout.setHorizontalGroup(jFrame1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400,
				Short.MAX_VALUE));
		jFrame1Layout.setVerticalGroup(jFrame1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300,
				Short.MAX_VALUE));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("client");
		setResizable(false);

		jPanel1.setName("Panel1");

		UserName.setText("UserName");

		GroupID.setText("GroupID");

		Password.setText("Password");

		textUserName.setText("user");
		textUserName.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	//			textUserNameActionPerformed(evt);
			}
		});

		textGroupID.setText("group_U");

		textPassword.setText("user1234");

		label1.setFont(new java.awt.Font("Arial", 0, 12));
		label1.setText("ptlType:");

		label2.setText("bit:");

		label3.setText("IDs:");

		txt_ids.setText("256");
		txt_ids.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txt_idsActionPerformed(evt);
			}
		});

		label_result.setText("waiting..");

		btn_login.setLabel("Login");
		btn_login.setName("name_Login");
		btn_login.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_loginActionPerformed(evt);
			}
		});

		txt_bit.setEditable(false);
		txt_bit.setText("512...");

		txt_proType.setEditable(false);
		txt_proType.setText("n or s");

		list_excution.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "Execution" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		list_excution
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						list_excutionValueChanged(evt);
					}
				});
		jScrollPane1.setViewportView(list_excution);

		jTextArea_result.setColumns(20);
		jTextArea_result.setLineWrap(true);
		jTextArea_result.setRows(5);
		jTextArea_result.setText("result");
		jScrollPane2.setViewportView(jTextArea_result);

		jTextArea_content.setColumns(20);
		jTextArea_content.setLineWrap(true);
		jTextArea_content.setRows(5);
		jTextArea_content.setText("content");
		jTextArea_content.setCaretPosition(0);
		jScrollPane3.setViewportView(jTextArea_content);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jScrollPane1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				183,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(7,
																				7,
																				7)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jScrollPane3,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								212,
																								Short.MAX_VALUE)
																						.addComponent(
																								jScrollPane2,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								212,
																								Short.MAX_VALUE)))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addGroup(
																												jPanel1Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																UserName,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addGroup(
																																jPanel1Layout
																																		.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.TRAILING,
																																				false)
																																		.addComponent(
																																				Password,
																																				javax.swing.GroupLayout.Alignment.LEADING,
																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																				Short.MAX_VALUE)
																																		.addComponent(
																																				GroupID,
																																				javax.swing.GroupLayout.Alignment.LEADING,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				javax.swing.GroupLayout.DEFAULT_SIZE,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												jPanel1Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																textGroupID,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																184,
																																Short.MAX_VALUE)
																														.addComponent(
																																textUserName,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																184,
																																Short.MAX_VALUE)
																														.addComponent(
																																textPassword,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																184,
																																Short.MAX_VALUE)))
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												label_result,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												146,
																												Short.MAX_VALUE)
																										.addGap(113,
																												113,
																												113)))
																		.addGap(28,
																				28,
																				28)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addGroup(
																												jPanel1Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																label2,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																label3,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																label1,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												jPanel1Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																txt_proType,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																txt_ids,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																txt_bit,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)))
																						.addComponent(
																								btn_login,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								115,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												textUserName,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												UserName,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												22,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								GroupID,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textGroupID,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								Password,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textPassword,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								label1,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								txt_proType,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGap(11,
																				11,
																				11)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								label2,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								txt_bit,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								txt_ids,
																								0,
																								0,
																								Short.MAX_VALUE)
																						.addComponent(
																								label3,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addGap(17, 17, 17)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																label_result,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btn_login,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(52, 52, 52)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																false)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jScrollPane3,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				87,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jScrollPane2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				88,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jScrollPane1))
										.addContainerGap()));

		GroupID.getAccessibleContext().setAccessibleDescription("asdfasdfas");
		txt_ids.getAccessibleContext().setAccessibleName("nameOfIDNum");

		txtURL.setText("localhost:8007");

		label4.setText("ServerURL");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														javax.swing.GroupLayout.Alignment.LEADING,
														layout.createSequentialGroup()
																.addContainerGap()
																.addComponent(
																		jPanel1,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))
												.addGroup(
														javax.swing.GroupLayout.Alignment.LEADING,
														layout.createSequentialGroup()
																.addGap(21, 21,
																		21)
																.addComponent(
																		label4,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		txtURL,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		343,
																		Short.MAX_VALUE)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(25, 25, 25)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														label4,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														txtURL,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		jPanel1.getAccessibleContext().setAccessibleName("Panel1");

		getAccessibleContext().setAccessibleName("frame1");

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void list_excutionValueChanged(
			javax.swing.event.ListSelectionEvent evt) {
		// TODO add your handling code here:
		Object selValue = this.list_excution.getSelectedValue();
		if (selValue != null) {

			String content = this.dataExecution.get(selValue.toString());
			this.jTextArea_content.setText(content);
			jTextArea_content.setCaretPosition(0);
		}
	}

	private void txt_idsActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private Map<String, Object> getFrameArgs() {
		String name = this.textUserName.getText();
		String groupID = this.textGroupID.getText();
		String password = this.textPassword.getText();
		String[] url = this.txtURL.getText().split(":");
		String host = url[0];
		int port = Integer.valueOf(url[1]);
		Map<String, Object> frameArgs = new HashMap<String, Object>();
		frameArgs.put("name", name);
		frameArgs.put("groupID", groupID);
		frameArgs.put("password", password);
		frameArgs.put("host", host);
		frameArgs.put("port", port);
		frameArgs.put(EnumTags.PortBulletin, 7070);
		//·µ»Ø
		return frameArgs;
	}

	private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:

		Map<String, Object> frameArgs = getFrameArgs();
		ClientControlor controler = new ClientControlor(this, frameArgs);
		controler.runIt();

	}

	private void textField1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ClientFrame().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private java.awt.Label GroupID;
	private java.awt.Label Password;
	private java.awt.Label UserName;
	private java.awt.Button btn_login;
	private javax.swing.JFrame jFrame1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JTextArea jTextArea_content;
	private javax.swing.JTextArea jTextArea_result;
	private java.awt.Label label1;
	private java.awt.Label label2;
	private java.awt.Label label3;
	private java.awt.Label label4;
	private java.awt.Label label_result;
	private javax.swing.JList list_excution;
	private java.awt.TextField textGroupID;
	private java.awt.TextField textPassword;
	private java.awt.TextField textUserName;
	private java.awt.TextField txtURL;
	private java.awt.TextField txt_bit;
	private java.awt.TextField txt_ids;
	private java.awt.TextField txt_proType;
	// End of variables declaration//GEN-END:variables

}