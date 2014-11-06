/*
 * ServerFrame.java
 *
 * Created on __DATE__, __TIME__
 */

package iscas.tca.ake.test.swing.view;

import static iscas.tca.ake.test.swing.module.EnumTags.BitLength;
import static iscas.tca.ake.test.swing.module.EnumTags.PortMain;
import static iscas.tca.ake.test.swing.module.EnumTags.ProType_Arg;
import iscas.tca.ake.test.swing.controler.ServerContainer;
import iscas.tca.ake.test.swing.module.Config;
import iscas.tca.ake.test.swing.module.EnumTags;
import iscas.tca.ake.test.swing.module.Response;
import iscas.tca.ake.test.swing.module.bulletin.ServerBulletin;
import iscas.tca.ake.test.swing.module.tools.UsersManagement;
import iscas.tca.ake.test.swing.module.tools.UtilMy;
import iscas.tca.ake.test.swing.module.tools.XMLTools;
import iscas.tca.ake.test.swing.view.observer.IfcExecutionObserver;
import iscas.tca.ake.test.swing.view.observer.IfcObserver;
import iscas.tca.ake.util.Assist;
import iscas.tca.ake.veap.calculate.GroupData;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author  __USER__
 */
public class ServerFrame extends javax.swing.JFrame implements IfcObserver,
		IfcExecutionObserver {

	private static ServerFrame serverFrame;
	private String GroupIDTag = "groupID";
	private Map<String, String> exeListMap = new LinkedHashMap<String, String>();
	private final String PagesSparater = "/";
	private ServerContainer serverContainer;
	private ExecutorService executorService;
	private List<String> groupList = new ArrayList<String>();
	private Map<String, GroupData> bulletin;
	private Config config;

	//	private List listStartAndStop 

	public synchronized void setStatus(String status) {
		if (status.startsWith("isVerif")) {
			//this.label_result.setText(status);
		}
		if (status.startsWith("new")) {
			this.textArea_result.setBackground(Color.YELLOW);
		} else {
			this.textArea_result.setBackground(Color.white);
		}
		this.label_status.setText(status);
	}

	public synchronized void updateExecution(Response response) {
		this.exeListMap.clear();
		LinkedHashMap<String, String> exesteps = response.getExecutionSteps();
		UtilMy.print(Assist.traverseMap(exesteps));
		this.exeListMap = exesteps;
		this.updateList(this.list_excution, this.exeListMap);
	}

	private <K, V> void updateList(JList list, Map map) {
		DefaultListModel listValue = new DefaultListModel();
		list.removeAll();
		Iterator<Map.Entry> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = iter.next();
			listValue.addElement(entry.getKey());
		}
		list.setModel(listValue);
	}

	private void updateResultField(Response result) {
		StringBuilder sb = new StringBuilder();
		String display = Assist.traverseMap(result.getDisplayMap());
		String t = Assist.traverseMap(result.getTimeRecord());
		sb.append("display:\n" + display + "\n");
		sb.append("Time Cost:\n" + t + "\n");
		this.textArea_result.setText(sb.toString());
		this.textArea_result.setCaretPosition(0);
	}

	private void updateSettings(Response result) {
		this.cbx_bit.setSelectedItem(result.getBit());
		this.cbx_proType1.setSelectedItem(result.getProType());
		int order = (Integer) result.getArg("order");
		int onlineCount = (Integer) result.getArg("logonCount");
		this.jTextField1.setText(order + this.PagesSparater + onlineCount);
	}

	@Override
	public synchronized void update(Response result) {
		// TODO Auto-generated method stub
		updateSettings(result);
		this.updateExecution(result);
		updateResultField(result);
	}

	/** Creates new form ServerFrame */
	public static ServerFrame newServerFrameInstance() {
		if (serverFrame == null) {
			try {
				serverFrame = new ServerFrame();
				serverFrame.serverContainer = ServerContainer.newInstance(
						serverFrame.config, serverFrame,// mainObserver
						serverFrame.new ObserverBulletin());//bulletin Observer

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return serverFrame;
	}

	private ServerFrame() {
		try {
			this.config = Config.newInstantce(Config.ConfigPath);
			initComponents();

			//init the config instance
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("config init error!!");
		}

	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {
		bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

		jPanel2 = new javax.swing.JPanel();
		jPanel4 = new javax.swing.JPanel();
		jPanel5 = new javax.swing.JPanel();
		jPopupMenu1 = new javax.swing.JPopupMenu();
		jPopupMenu2 = new javax.swing.JPopupMenu();
		jFrame1 = new javax.swing.JFrame();
		jFrame2 = new javax.swing.JFrame();
		jLabel12 = new javax.swing.JLabel();
		jTabbedPane2 = new javax.swing.JTabbedPane();
		jPanel7 = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		list_excution = new javax.swing.JList();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jTextField1 = new javax.swing.JTextField();
		jScrollPane3 = new javax.swing.JScrollPane();
		textArea_content = new javax.swing.JTextArea();
		jPanel3 = new javax.swing.JPanel();
		cbx_proType1 = new javax.swing.JComboBox();
		cbx_bit = new javax.swing.JComboBox();
		txt_port = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		textArea_result = new javax.swing.JTextArea();
		jButton_start = new javax.swing.JButton();
		jButton_stop = new javax.swing.JButton();
		label_status = new java.awt.Label();
		jScrollPane5 = new javax.swing.JScrollPane();
		jList_BulExec = new javax.swing.JList();
		jScrollPane8 = new javax.swing.JScrollPane();
		txt_BulletingChange = new javax.swing.JTextArea();
		jPanel6 = new javax.swing.JPanel();
		jScrollPane4 = new javax.swing.JScrollPane();
		bul_list = new javax.swing.JList();
		jScrollPane7 = new javax.swing.JScrollPane();
		bul_textBoard = new javax.swing.JTextArea();
		bul_status = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jPanel8 = new javax.swing.JPanel();
		jTabbedPane3 = new javax.swing.JTabbedPane();
		jPanel11 = new javax.swing.JPanel();
		jLabel9 = new javax.swing.JLabel();
		reg_id_g = new javax.swing.JTextField();
		jLabel10 = new javax.swing.JLabel();
		reg_password_g = new javax.swing.JTextField();
		reg_group = new javax.swing.JButton();
		jPanel10 = new javax.swing.JPanel();
		reg_userid = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		reg_password = new javax.swing.JTextField();
		jButton4 = new javax.swing.JButton();
		reg_groupID = new javax.swing.JComboBox();
		jLabel8 = new javax.swing.JLabel();
		jPanel9 = new javax.swing.JPanel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		cbx_bitSetting = new javax.swing.JComboBox();
		txt_Seed = new javax.swing.JTextField();
		jScrollPane6 = new javax.swing.JScrollPane();
		txt_showGQ = new javax.swing.JTextArea();
		btn_config = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
				Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
				Short.MAX_VALUE));

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
				jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
				Short.MAX_VALUE));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
				Short.MAX_VALUE));

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(
				jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
				Short.MAX_VALUE));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
				Short.MAX_VALUE));

		javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(
				jFrame1.getContentPane());
		jFrame1.getContentPane().setLayout(jFrame1Layout);
		jFrame1Layout.setHorizontalGroup(jFrame1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400,
				Short.MAX_VALUE));
		jFrame1Layout.setVerticalGroup(jFrame1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300,
				Short.MAX_VALUE));

		javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(
				jFrame2.getContentPane());
		jFrame2.getContentPane().setLayout(jFrame2Layout);
		jFrame2Layout.setHorizontalGroup(jFrame2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400,
				Short.MAX_VALUE));
		jFrame2Layout.setVerticalGroup(jFrame2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300,
				Short.MAX_VALUE));

		jLabel12.setText("jLabel12");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		list_excution.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "1" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		list_excution.setToolTipText("12333");
		list_excution
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						list_excutionValueChanged(evt);
					}
				});
		jScrollPane1.setViewportView(list_excution);

		jTabbedPane1.addTab("tab1", jScrollPane1);

		jButton1.setText("up");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("down");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jTextField1.setText("0/0");
		jTextField1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField1ActionPerformed(evt);
			}
		});

		textArea_content.setColumns(20);
		textArea_content.setLineWrap(true);
		textArea_content.setRows(5);
		textArea_content.setText("excution content");
		textArea_content.setInheritsPopupMenu(true);
		jScrollPane3.setViewportView(textArea_content);

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
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jButton2)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jTextField1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				50,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jButton1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				58,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jTabbedPane1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																256,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												46, Short.MAX_VALUE)
										.addComponent(
												jScrollPane3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												260,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
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
														.addComponent(
																jScrollPane3,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																176,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jTabbedPane1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				143,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jButton2)
																						.addComponent(
																								jTextField1,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jButton1))))
										.addContainerGap()));

		jPanel3.setBackground(new java.awt.Color(204, 204, 204));

		cbx_proType1.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "NAP_YZ", "SKI" }));

		cbx_bit.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"512", "1024", "2048" }));
		cbx_bit.setSelectedIndex(1);
		cbx_bit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cbx_bitActionPerformed(evt);
			}
		});

		txt_port.setText("8007");

		jLabel1.setText("ProtocolType:");

		jLabel2.setText("bit:");

		jLabel3.setText("Port:");

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jLabel1)
														.addComponent(jLabel3)
														.addComponent(jLabel2))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																cbx_proType1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txt_port,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cbx_bit,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(92, 92, 92)));
		jPanel3Layout
				.setVerticalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(
																cbx_proType1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(14, 14, 14)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel2)
														.addComponent(
																cbx_bit,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel3)
														.addComponent(
																txt_port,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(60, Short.MAX_VALUE)));

		textArea_result.setColumns(20);
		textArea_result.setLineWrap(true);
		textArea_result.setRows(5);
		textArea_result.setText("result..");
		jScrollPane2.setViewportView(textArea_result);

		jButton_start.setText(" Start ");
		jButton_start.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_startActionPerformed(evt);
			}
		});

		jButton_stop.setText(" Stop ");
		jButton_stop.setEnabled(false);
		jButton_stop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_stopActionPerformed(evt);
			}
		});

		label_status.setText("Status..");

		jList_BulExec.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "bulletin Execution" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jScrollPane5.setViewportView(jList_BulExec);

		txt_BulletingChange.setColumns(20);
		txt_BulletingChange.setRows(5);
		txt_BulletingChange.setText("show change in time ");
		jScrollPane8.setViewportView(txt_BulletingChange);

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(
				jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout
				.setHorizontalGroup(jPanel7Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel7Layout
										.createSequentialGroup()
										.addGap(24, 24, 24)
										.addGroup(
												jPanel7Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jPanel1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																jPanel7Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel7Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								label_status,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jPanel3,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								268,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGroup(
																				jPanel7Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel7Layout
																										.createSequentialGroup()
																										.addGap(117,
																												117,
																												117)
																										.addComponent(
																												jButton_start)
																										.addGap(42,
																												42,
																												42)
																										.addComponent(
																												jButton_stop))
																						.addGroup(
																								jPanel7Layout
																										.createSequentialGroup()
																										.addGap(34,
																												34,
																												34)
																										.addComponent(
																												jScrollPane2,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												260,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(62,
																												62,
																												62)
																										.addGroup(
																												jPanel7Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																jScrollPane8,
																																javax.swing.GroupLayout.Alignment.TRAILING,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																251,
																																Short.MAX_VALUE)
																														.addComponent(
																																jScrollPane5,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																251,
																																Short.MAX_VALUE))))))
										.addContainerGap()));
		jPanel7Layout
				.setVerticalGroup(jPanel7Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel7Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel7Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane5,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																177,
																Short.MAX_VALUE)
														.addComponent(
																jPanel3,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane2,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																177,
																Short.MAX_VALUE))
										.addGroup(
												jPanel7Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel7Layout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jPanel1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel7Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								label_status,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								jPanel7Layout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.BASELINE)
																										.addComponent(
																												jButton_stop)
																										.addComponent(
																												jButton_start))))
														.addGroup(
																jPanel7Layout
																		.createSequentialGroup()
																		.addGap(24,
																				24,
																				24)
																		.addComponent(
																				jScrollPane8,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				173,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(17, 17, 17)));

		jTabbedPane2.addTab("MainCotrol", jPanel7);

		jPanel6.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentShown(java.awt.event.ComponentEvent evt) {
				jPanel6ComponentShown(evt);
			}
		});
		jPanel6.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				jPanel6FocusGained(evt);
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				jPanel6FocusLost(evt);
			}
		});

		bul_list.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "bulletin...." };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		bul_list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				bul_listValueChanged(evt);
			}
		});
		jScrollPane4.setViewportView(bul_list);

		bul_textBoard.setColumns(20);
		bul_textBoard.setLineWrap(true);
		bul_textBoard.setRows(5);
		bul_textBoard.setFocusCycleRoot(true);
		bul_textBoard.setFocusTraversalPolicyProvider(true);
		bul_textBoard.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent evt) {
				bul_textBoardCaretUpdate(evt);
			}
		});
		bul_textBoard.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				bul_textBoardFocusGained(evt);
			}
		});
		jScrollPane7.setViewportView(bul_textBoard);

		bul_status.setText("status...");

		jLabel11.setText("Bulletin here");

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(
				jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout
				.setHorizontalGroup(jPanel6Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel6Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel6Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel6Layout
																		.createSequentialGroup()
																		.addComponent(
																				bul_status,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				242,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addContainerGap())
														.addGroup(
																jPanel6Layout
																		.createSequentialGroup()
																		.addComponent(
																				jScrollPane4,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				258,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(52,
																				52,
																				52)
																		.addComponent(
																				jScrollPane7,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				543,
																				Short.MAX_VALUE)
																		.addGap(46,
																				46,
																				46))
														.addGroup(
																jPanel6Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel11)
																		.addContainerGap(
																				827,
																				Short.MAX_VALUE)))));
		jPanel6Layout
				.setVerticalGroup(jPanel6Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel6Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel6Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane7,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																175,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																jPanel6Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel11)
																		.addGap(14,
																				14,
																				14)
																		.addComponent(
																				jScrollPane4,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				144,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(182, 182, 182)
										.addComponent(bul_status)
										.addContainerGap(67, Short.MAX_VALUE)));

		jTabbedPane2.addTab("Bulletin", jPanel6);

		jLabel9.setText("GroupID:");

		reg_id_g.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				reg_id_gKeyReleased(evt);
			}
		});

		jLabel10.setText("Password:");

		reg_password_g.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				reg_password_gKeyReleased(evt);
			}
		});

		reg_group.setText("Regist");
		reg_group.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reg_groupActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(
				jPanel11);
		jPanel11.setLayout(jPanel11Layout);
		jPanel11Layout
				.setHorizontalGroup(jPanel11Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel11Layout
										.createSequentialGroup()
										.addContainerGap(179, Short.MAX_VALUE)
										.addGroup(
												jPanel11Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(jLabel10)
														.addComponent(jLabel9))
										.addGap(26, 26, 26)
										.addGroup(
												jPanel11Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																reg_group,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(reg_id_g)
														.addComponent(
																reg_password_g))
										.addContainerGap(549, Short.MAX_VALUE)));
		jPanel11Layout
				.setVerticalGroup(jPanel11Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel11Layout
										.createSequentialGroup()
										.addGap(45, 45, 45)
										.addGroup(
												jPanel11Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																reg_id_g,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel9))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel11Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																reg_password_g,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel10))
										.addGap(39, 39, 39)
										.addComponent(reg_group)
										.addContainerGap(58, Short.MAX_VALUE)));

		jTabbedPane3.addTab("GroupRegistion", jPanel11);

		jPanel10.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentShown(java.awt.event.ComponentEvent evt) {
				jPanel10ComponentShown(evt);
			}
		});
		jPanel10.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				jPanel10FocusGained(evt);
			}
		});

		reg_userid.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				reg_useridKeyReleased(evt);
			}
		});

		jLabel4.setText("UserID:");

		jLabel5.setText("Password:");

		reg_password.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				reg_passwordKeyReleased(evt);
			}
		});

		jButton4.setText("Regist");
		jButton4.setEnabled(false);
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});

		reg_groupID.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "group_U" }));

		org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings
				.createAutoBinding(
						org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
						textArea_result,
						org.jdesktop.beansbinding.ObjectProperty.create(),
						reg_groupID, org.jdesktop.beansbinding.BeanProperty
								.create("elements"));
		bindingGroup.addBinding(binding);

		reg_groupID.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reg_groupIDActionPerformed(evt);
			}
		});
		reg_groupID.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				reg_groupIDFocusGained(evt);
			}
		});

		jLabel8.setText("GroupID");

		javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(
				jPanel10);
		jPanel10.setLayout(jPanel10Layout);
		jPanel10Layout
				.setHorizontalGroup(jPanel10Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel10Layout
										.createSequentialGroup()
										.addGroup(
												jPanel10Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																jPanel10Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				jPanel10Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								jLabel5)
																						.addComponent(
																								jLabel4)
																						.addComponent(
																								jLabel8))
																		.addGap(27,
																				27,
																				27)
																		.addGroup(
																				jPanel10Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addComponent(
																								reg_userid)
																						.addComponent(
																								reg_password)
																						.addComponent(
																								reg_groupID,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								168,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																jPanel10Layout
																		.createSequentialGroup()
																		.addGap(185,
																				185,
																				185)
																		.addComponent(
																				jButton4,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				152,
																				Short.MAX_VALUE)))
										.addGap(545, 545, 545)));
		jPanel10Layout
				.setVerticalGroup(jPanel10Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel10Layout
										.createSequentialGroup()
										.addGap(28, 28, 28)
										.addGroup(
												jPanel10Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																reg_userid,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel4))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel10Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																reg_password,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel5))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel10Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																reg_groupID,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel8))
										.addGap(43, 43, 43)
										.addComponent(jButton4)
										.addContainerGap(40, Short.MAX_VALUE)));

		jTabbedPane3.addTab("UserRegistion", jPanel10);

		javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(
				jPanel8);
		jPanel8.setLayout(jPanel8Layout);
		jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanel8Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jTabbedPane3,
								javax.swing.GroupLayout.DEFAULT_SIZE, 887,
								Short.MAX_VALUE).addContainerGap()));
		jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel8Layout
						.createSequentialGroup()
						.addComponent(jTabbedPane3,
								javax.swing.GroupLayout.PREFERRED_SIZE, 252,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(203, Short.MAX_VALUE)));

		jTabbedPane2.addTab("RegistionSim", jPanel8);

		jLabel6.setText("bit Number ");

		jLabel7.setText("Random Seed");

		cbx_bitSetting.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "1024", "2048", "512" }));

		txt_Seed.setText("100");

		txt_showGQ.setColumns(20);
		txt_showGQ.setLineWrap(true);
		txt_showGQ.setRows(5);
		jScrollPane6.setViewportView(txt_showGQ);

		btn_config.setText("config ");
		btn_config.setEnabled(false);
		btn_config.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_configActionPerformed(evt);
			}
		});

		jButton5.setText("show");
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(
				jPanel9);
		jPanel9.setLayout(jPanel9Layout);
		jPanel9Layout
				.setHorizontalGroup(jPanel9Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel9Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel9Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jLabel7)
														.addComponent(jLabel6)
														.addComponent(
																btn_config))
										.addGap(7, 7, 7)
										.addGroup(
												jPanel9Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																false)
														.addComponent(
																cbx_bitSetting,
																javax.swing.GroupLayout.Alignment.LEADING,
																0, 62,
																Short.MAX_VALUE)
														.addGroup(
																jPanel9Layout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel9Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								txt_Seed)
																						.addComponent(
																								jButton5,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								72,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addGap(18, 18, 18)
										.addComponent(
												jScrollPane6,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												342,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(378, Short.MAX_VALUE)));
		jPanel9Layout
				.setVerticalGroup(jPanel9Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel9Layout
										.createSequentialGroup()
										.addContainerGap(311, Short.MAX_VALUE)
										.addGroup(
												jPanel9Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel9Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel9Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								cbx_bitSetting,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel6))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel9Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								txt_Seed,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel7))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel9Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jButton5)
																						.addComponent(
																								btn_config))
																		.addGap(53,
																				53,
																				53))
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel9Layout
																		.createSequentialGroup()
																		.addComponent(
																				jScrollPane6,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(38,
																				38,
																				38)))));

		jTabbedPane2.addTab("Configuration", jPanel9);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 916,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addComponent(jTabbedPane2,
								javax.swing.GroupLayout.PREFERRED_SIZE, 486,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(26, Short.MAX_VALUE)));

		bindingGroup.bind();

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void btn_config1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jPanel18FocusGained(java.awt.event.FocusEvent evt) {
		// TODO add your handling code here:
	}

	private void jPanel18ComponentShown(java.awt.event.ComponentEvent evt) {
		// TODO add your handling code here:
	}

	private void reg_groupID1FocusGained(java.awt.event.FocusEvent evt) {
		// TODO add your handling code here:
	}

	private void reg_groupID1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void reg_password1KeyReleased(java.awt.event.KeyEvent evt) {
		// TODO add your handling code here:
	}

	private void reg_userid1KeyReleased(java.awt.event.KeyEvent evt) {
		// TODO add your handling code here:
	}

	private void reg_group1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void reg_password_g1KeyReleased(java.awt.event.KeyEvent evt) {
		// TODO add your handling code here:
	}

	private void reg_id_g1KeyReleased(java.awt.event.KeyEvent evt) {
		// TODO add your handling code here:
	}

	private void jPanel15FocusLost(java.awt.event.FocusEvent evt) {
		// TODO add your handling code here:
	}

	private void jPanel15FocusGained(java.awt.event.FocusEvent evt) {
		// TODO add your handling code here:
	}

	private void jPanel15ComponentShown(java.awt.event.ComponentEvent evt) {
		// TODO add your handling code here:
	}

	private void bul_textBoard1FocusGained(java.awt.event.FocusEvent evt) {
		// TODO add your handling code here:
	}

	private void bul_textBoard1CaretUpdate(javax.swing.event.CaretEvent evt) {
		// TODO add your handling code here:
	}

	private void bul_list1ValueChanged(javax.swing.event.ListSelectionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton_stop1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton_start1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void cbx_bit1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void list_excution1ValueChanged(
			javax.swing.event.ListSelectionEvent evt) {
		// TODO add your handling code here:
	}

	private void reg_id_gKeyReleased(java.awt.event.KeyEvent evt) {
		// TODO add your handling code here:
	}

	private void ifCanRegGroup() {

	}

	private void reg_password_gKeyReleased(java.awt.event.KeyEvent evt) {
		// TODO add your handling code here:
	}

	private void regIt() {
		String id = this.reg_userid.getText();
		String password = this.reg_password.getText();
		String groupID = this.reg_groupID.getSelectedItem().toString();
		try {
			if (UsersManagement.isLengthLegle(id, password)
					&& !UsersManagement.isRegist(id, groupID)) {
				this.jButton4.setEnabled(true);
			} else {
				this.jButton4.setEnabled(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reg_passwordKeyReleased(java.awt.event.KeyEvent evt) {
		// TODO add your handling code here:
		regIt();
	}

	private void reg_useridKeyReleased(java.awt.event.KeyEvent evt) {
		regIt();
	}

	private void bul_textBoardFocusGained(java.awt.event.FocusEvent evt) {
		// TODO add your handling code here:
		this.bul_status.setText("focus" + n++);
	}

	private void bul_textBoardCaretUpdate(javax.swing.event.CaretEvent evt) {
		// TODO add your handling code here:
		this.bul_status.setText("caretUpdate" + n++);
	}

	private void bul_listValueChanged(javax.swing.event.ListSelectionEvent evt) {
		// TODO add your handling code here:
		String sel = (String) this.bul_list.getSelectedValue();
		String detail = this.bulletin.get(sel).toString();

		this.bul_textBoard.setText(detail);
		this.bul_textBoard.setCaretPosition(0);
	}

	private void jPanel6FocusLost(java.awt.event.FocusEvent evt) {
		// TODO add your handling code here:
		this.bul_textBoard.setText("lost Focus");
	}

	private void jPanel10ComponentShown(java.awt.event.ComponentEvent evt) {
		// TODO add your handling code here:

		XMLTools xmlTool = new XMLTools(this.config.getGroupsFilePath(),
				"groups");
		String[] ss = xmlTool.getAllTagValues(GroupIDTag);
		groupList = Arrays.asList(ss);

		this.reg_groupID.removeAllItems();
		for (String s : groupList) {
			this.reg_groupID.addItem(s);
		}
	}

	private void jPanel6FocusGained(java.awt.event.FocusEvent evt) {
		// TODO add your handling code here:
		this.bul_textBoard.setText("Focus Gained");
	}

	int n = 0;

	private void jPanel6ComponentShown(java.awt.event.ComponentEvent evt) {
		// TODO add your handling code here:
		this.bul_status.setText("componentShown");
		try {
			ServerBulletin sbulletin = this.serverContainer.getServerBulletin();
			if (sbulletin != null) {
				bulletin = sbulletin.getAllGroupData();
				if (bulletin != null) {
					updateList(bul_list, bulletin);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateBulList(Map<String, GroupData> bulletin) {

	}

	private void reg_groupActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code he
		String groupID = this.reg_id_g.getText();
		String groupPassword = this.reg_password_g.getText();
		try {
			UtilMy.registGroup(this.config.getGroupsFilePath(), groupID,
					groupPassword);
			//this.reg
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reg_groupIDActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void reg_groupIDFocusGained(java.awt.event.FocusEvent evt) {
		// TODO add your handling code here:

	}

	private void jPanel10FocusGained(java.awt.event.FocusEvent evt) {
		// TODO add your handling code here:
		if (groupList == null) {
			XMLTools xmlTool = new XMLTools(this.config.getGroupsFilePath(),
					EnumTags.GroupsRootTag);
			groupList = Arrays.asList(xmlTool.getAllTagValues(GroupIDTag));
		}
		this.reg_groupID.removeAll();
		for (String s : groupList) {
			this.reg_groupID.addItem(s);
		}
	}

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		String id = this.reg_userid.getText();
		String password = this.reg_password.getText();
		String groupID = this.reg_groupID.getSelectedItem().toString();
		try {
			UsersManagement.recordUserToFile(id, password, groupID,
					Config.isPassed, this.serverContainer.getConfig()
							.getIsTextPlain(), this.config.getUsersFilePath());
			this.reg_password.setText("");
			this.reg_userid.setText("");
			this.config.init();
			this.jButton4.setEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void btn_configActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		try {
			config.setGQToFile(gSetting, qSetting);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.btn_config.setEnabled(false);
	}

	private BigInteger gSetting = null;
	private BigInteger qSetting = null;

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		int bit = Integer.valueOf(this.cbx_bitSetting.getSelectedItem()
				.toString());
		int seednum = Integer.valueOf(this.txt_Seed.getText());
		qSetting = UtilMy.genQ(bit, seednum);
		gSetting = UtilMy.genG(bit, seednum, qSetting);
		this.txt_showGQ.setText("q:  " + qSetting.toString() + "\n" + "g:  "
				+ gSetting.toString() + "\n");
		this.btn_config.setEnabled(true);
	}
	
	private void jButton_stopActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		this.jButton_stop.setEnabled(false);
		this.jButton_start.setEnabled(true);
		this.cbx_bit.setEditable(true);
		this.cbx_proType1.setEnabled(true);
		this.txt_port.setEnabled(true);
		this.cbx_bit.setEnabled(true);
		//
		this.serverContainer.close();
		this.executorService.shutdownNow();
		this.setStatus("exsit");
	}

	private void jButton_startActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		//view change
		this.jButton_stop.setEnabled(true);
		this.jButton_start.setEnabled(false);
		this.cbx_bit.setEditable(false);
		this.cbx_proType1.setEnabled(false);
		this.txt_port.setEnabled(false);
		this.cbx_bit.setEnabled(false);
		//logic 
		this.executorService = Executors.newCachedThreadPool();
		//init the config files

		this.config.set_FrameArgs(this.getArgs());
		this.config.init();
		this.serverContainer.init();

		this.executorService.execute(this.serverContainer);
		this.setStatus("listing the port");
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		String sp = this.jTextField1.getText();
		int[] page_Display = this.get_PageInLabel(sp);
		if (page_Display[0] > 1) {
			try {
				Response history = this.serverContainer
						.getResponseMainHistory(--page_Display[0]);
				this.update(history);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private int[] get_PageInLabel(String sPage) {
		String[] ps = sPage.split(this.PagesSparater);
		int[] pages = { Integer.valueOf(ps[0]), Integer.valueOf(ps[1]) };
		return pages;
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		String sp = this.jTextField1.getText();
		int[] page_Display = get_PageInLabel(sp);
		if (page_Display[0] < page_Display[1]) {
			try {
				Response history = this.serverContainer
						.getResponseMainHistory(++page_Display[0]);
				this.update(history);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:

		int order = Integer.valueOf(this.jTextField1.getText());
		try {
			Response res = this.serverContainer.getResponseMainHistory(order);
			if (res != null)
				this.update(res);
			else {
				//not handled
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void list_excutionValueChanged(
			javax.swing.event.ListSelectionEvent evt) {
		// TODO add your handling code here:
		Object selValue = this.list_excution.getSelectedValue();
		if (selValue != null) {

			String content = this.exeListMap.get(selValue.toString());
			this.textArea_content.setText(content);
			this.textArea_content.setCaretPosition(0);
		}

	}

	private void cbx_bitActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	//get the settings of args
	private Map<String, Object> getArgs() {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put(PortMain, Integer.valueOf(this.txt_port.getText()));////!!!!!!!!!!!!!!!!!!!!
		argMap.put(BitLength,
				Integer.valueOf(this.cbx_bit.getSelectedItem().toString()));
		if (this.cbx_proType1.getSelectedItem().toString() == "NAP_YZ")
			argMap.put(ProType_Arg, "NAP");
		else {
			argMap.put(ProType_Arg, "VEAP");
		}
		return argMap;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ServerFrame.newServerFrameInstance().setVisible(true);
			}
		});
	}

	class ObserverBulletin implements IfcObserver, Serializable {
		public static final String bulletinDataFlag = "bulletinData";

		public ObserverBulletin() {

		}

		@Override
		public void update(Response response) {
			// TODO Auto-generated method stub
			updateExecution(response);
			txt_BulletingChange.setText(Assist.traverseMap(response
					.getDisplayMap()));
		}

		@Override
		public void setStatus(String status) {

		}

		@Override
		public void updateExecution(Response response) {
			// TODO Auto-generated method stub
			UtilView.updateList(jList_BulExec, response.getExecutionSteps());
		}

	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btn_config;
	private javax.swing.JList bul_list;
	private javax.swing.JLabel bul_status;
	private javax.swing.JTextArea bul_textBoard;
	private javax.swing.JComboBox cbx_bit;
	private javax.swing.JComboBox cbx_bitSetting;
	private javax.swing.JComboBox cbx_proType1;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton_start;
	private javax.swing.JButton jButton_stop;
	private javax.swing.JFrame jFrame1;
	private javax.swing.JFrame jFrame2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JList jList_BulExec;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JPopupMenu jPopupMenu1;
	private javax.swing.JPopupMenu jPopupMenu2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JScrollPane jScrollPane7;
	private javax.swing.JScrollPane jScrollPane8;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTabbedPane jTabbedPane2;
	private javax.swing.JTabbedPane jTabbedPane3;
	private javax.swing.JTextField jTextField1;
	private java.awt.Label label_status;
	private javax.swing.JList list_excution;
	private javax.swing.JButton reg_group;
	private javax.swing.JComboBox reg_groupID;
	private javax.swing.JTextField reg_id_g;
	private javax.swing.JTextField reg_password;
	private javax.swing.JTextField reg_password_g;
	private javax.swing.JTextField reg_userid;
	private javax.swing.JTextArea textArea_content;
	private javax.swing.JTextArea textArea_result;
	private javax.swing.JTextArea txt_BulletingChange;
	private javax.swing.JTextField txt_Seed;
	private javax.swing.JTextField txt_port;
	private javax.swing.JTextArea txt_showGQ;
	private org.jdesktop.beansbinding.BindingGroup bindingGroup;
	// End of variables declaration//GEN-END:variables

}