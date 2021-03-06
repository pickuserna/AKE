/*
 * SettingArgs.java
 *
 * Created on __DATE__, __TIME__
 */

package iscas.tca.ake.demoapp.mvc.view;

import iscas.tca.ake.demoapp.mvc.module.Config;
import iscas.tca.ake.util.UtilMy;

import java.math.BigInteger;

/**
 *
 * @author  __USER__
 */
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
public class SettingArgs extends javax.swing.JFrame {

	/** Creates new form SettingArgs */
	public SettingArgs() {
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jScrollPane6 = new javax.swing.JScrollPane();
		txt_showGQ = new javax.swing.JTextArea();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		btn_config = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		txt_Seed = new javax.swing.JTextField();
		cbx_bitSetting = new javax.swing.JComboBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		txt_showGQ.setColumns(20);
		txt_showGQ.setLineWrap(true);
		txt_showGQ.setRows(5);
		jScrollPane6.setViewportView(txt_showGQ);

		jLabel6.setText("bit Number ");

		jLabel7.setText("Random Seed");

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

		txt_Seed.setText("100");

		cbx_bitSetting.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1024", "2048", "512" }));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel7).addComponent(jLabel6).addComponent(btn_config))
						.addGap(7, 7, 7)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(cbx_bitSetting, javax.swing.GroupLayout.Alignment.LEADING, 0, 62, Short.MAX_VALUE)
										.addGroup(
												layout.createSequentialGroup()
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(
																layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txt_Seed)
																		.addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(7, 7, 7)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												layout.createSequentialGroup()
														.addGap(18, 18, 18)
														.addGroup(
																layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(cbx_bitSetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jLabel6)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(txt_Seed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel7))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton5).addComponent(btn_config)))
										.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private BigInteger gSetting = null;
	private BigInteger qSetting = null;

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		int bit = Integer.valueOf(this.cbx_bitSetting.getSelectedItem().toString());
		int seednum = Integer.valueOf(this.txt_Seed.getText());
		qSetting = UtilMy.genQ(bit, seednum);
		gSetting = UtilMy.genG(bit, seednum, qSetting);
		this.txt_showGQ.setText("q:  " + qSetting.toString() + "\n" + "g:  " + gSetting.toString() + "\n");
		this.btn_config.setEnabled(true);
	}

	private void btn_configActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		try {
			Config config = Config.newInstance(Config.ConfigPath);
			config.setGQToFile(gSetting, qSetting);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.btn_config.setEnabled(false);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SettingArgs().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btn_config;
	private javax.swing.JComboBox cbx_bitSetting;
	private javax.swing.JButton jButton5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JTextField txt_Seed;
	private javax.swing.JTextArea txt_showGQ;
	// End of variables declaration//GEN-END:variables

}