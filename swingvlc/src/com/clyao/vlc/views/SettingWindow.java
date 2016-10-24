package com.clyao.vlc.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.clyao.vlc.util.XMLUtil;

import javax.swing.ListSelectionModel;

/**
 * @author clyao
 * @time 2016-10-18 15:00
 * @version 1.0v
 * @description 设置界面
 */
public class SettingWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static Vector<String> vector = new Vector<String>();
	
	public JList<String> list = null;

	/**
	 * Create the dialog.
	 * @throws Exception 
	 */
	public SettingWindow() throws Exception {
		setResizable(false);
		setTitle("\u7CFB\u7EDF\u8BBE\u7F6E");
		setBounds(100, 100, 531, 336);
		setLocationRelativeTo(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel jpVideoList = new JPanel();
		tabbedPane.addTab("视频列表", null, jpVideoList, null);

		JScrollPane scrollPane = new JScrollPane();

		JButton btnAddVideo = new JButton("\u6DFB\u52A0\u89C6\u9891");
		btnAddVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser chooser = new JFileChooser();
					int v = chooser.showOpenDialog(null);
					if(v == JFileChooser.APPROVE_OPTION){
						File file = chooser.getSelectedFile();
						String videoPath = new String(file.getAbsolutePath().toString()).replaceAll("\\\\", "\\\\\\\\");
						XMLUtil.add(videoPath);
						Vector<String> vector2 = new Vector<String>();
						vector2 = XMLUtil.readXML();
						list.setListData(vector2);
						JOptionPane.showMessageDialog(null, "添加成功！");
						System.out.println("添加了视频：" + videoPath);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton btnRemoveVideo = new JButton("\u5220\u9664\u89C6\u9891");
		btnRemoveVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(list.getSelectedIndex()<=-1){
						JOptionPane.showMessageDialog(null, "至少选择一项");
					}else{
						System.out.println("删除了视频：" + list.getSelectedValue());
						XMLUtil.delete(list.getSelectedValue());
						Vector<String> vector2 = new Vector<String>();
						vector2 = XMLUtil.readXML();
						list.setListData(vector2);
						JOptionPane.showMessageDialog(null, "删除成功！");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_jpVideoList = new GroupLayout(jpVideoList);
		gl_jpVideoList.setHorizontalGroup(
				gl_jpVideoList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jpVideoList.createSequentialGroup()
						.addGroup(gl_jpVideoList.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_jpVideoList.createSequentialGroup()
										.addComponent(btnAddVideo)
										.addGap(18)
										.addComponent(btnRemoveVideo))
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 456, GroupLayout.PREFERRED_SIZE))
										.addContainerGap(78, Short.MAX_VALUE))
				);
		gl_jpVideoList.setVerticalGroup(
				gl_jpVideoList.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_jpVideoList.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_jpVideoList.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAddVideo)
								.addComponent(btnRemoveVideo))
								.addContainerGap())
				);
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel<String>() {

			private static final long serialVersionUID = 1L;
			public int getSize() {
				return vector.size();
			}
			public String getElementAt(int index) {
				return vector.get(index);
			}
		});
		list.setListData(XMLUtil.readXML());
		scrollPane.setViewportView(list);
		jpVideoList.setLayout(gl_jpVideoList);
	}
}
