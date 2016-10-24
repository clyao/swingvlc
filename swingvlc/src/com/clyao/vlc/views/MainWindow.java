package com.clyao.vlc.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

import com.clyao.vlc.main.PlayMain;
import com.clyao.vlc.util.DBOperation;
import com.clyao.vlc.util.Weather;
import com.clyao.vlc.util.XMLUtil;

import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author clyao
 * @time 2016-10-18 14:56
 * @version 1.0v
 * @description 程序主界面
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	public ImagebgJPanel toppanel;
	public ImagebgJPanel leftpanel;
	public ImagebgJPanel rightpanel;
	private JPanel footerpanel;
	private JLabel footerlabel;
	
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem mnFile;
	private JMenuItem mnExit;
	private JMenu mnPlayOption;
	private JMenuItem mnPause;
	private JMenuItem mnPlay;
	private JMenuItem mnStop;
	private JMenu mnPictures;
	private JMenuItem mnToppic;
	private JMenuItem mnLeftpic;
	private JMenuItem mnRightpic;
	private JMenu mnSetting;
	private JMenuItem mnFullScreen;
	private JMenuItem mnExitScreen;
	
	private FontMetrics fontMetrics;
	
	private int moveLength;
	private JLabel lblDate;
	private JLabel lblDayPicture;
	private JLabel lblTemperature;
	private JLabel lblWeather;
	private JLabel lblWind;
	private JLabel lblWeek;
	private JLabel lblRealWeather;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JMenuItem mntmUseHelp;
	private JMenuItem mnPlayList;
	private JMenuItem mnSettingSystem;
	private JPanel controlpanel;
	private JToggleButton btnstop;
	private JToggleButton btnplay;
	private JToggleButton btnpause;
	private JToggleButton btnvolume;
	private JProgressBar progressBar;
	private JSlider slider;
	private JPanel progressbarpanel;
	private JPanel mediabtnpanel;
	
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private DBOperation dbOperation;
	private List<String> videoList;
	private JPanel showpanel;

	public MainWindow() {
		initUI();
		videoControlWorker();
	}
	
	// 初始化UI界面
	public void initUI(){
		setTitle("新南粤人才市场投影程序V1.0 by:clyao QQ:837904664");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1024, 768);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		menuUI();
		northUI();
		westUI();
		centerUI();
		eastUI();
		southUI();
	}
	
	//菜单UI布局
	public void menuUI(){
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menu = new JMenu("File");
		menuBar.add(menu);
		
		mnFile = new JMenuItem("Open File...");
		menu.add(mnFile);
		
		mnExit = new JMenuItem("Exit");
		menu.add(mnExit);
		
		mnPlayOption = new JMenu("Play");
		menuBar.add(mnPlayOption);
		
		mnPause = new JMenuItem("pause");
		mnPlayOption.add(mnPause);
		
		mnPlay = new JMenuItem("play");
		mnPlayOption.add(mnPlay);
		
		mnStop = new JMenuItem("stop");
		mnPlayOption.add(mnStop);
		
		mnPlayList = new JMenuItem("play list");
		mnPlayOption.add(mnPlayList);
		
		mnPictures = new JMenu("Pictures");
		menuBar.add(mnPictures);
		
		mnToppic = new JMenuItem("topPic");
		mnPictures.add(mnToppic);
		
		mnLeftpic = new JMenuItem("leftPic");
		mnPictures.add(mnLeftpic);
		
		mnRightpic = new JMenuItem("rightPic");
		mnPictures.add(mnRightpic);
		
		mnSetting = new JMenu("Setting");
		menuBar.add(mnSetting);
		
		mnFullScreen = new JMenuItem("Full Screen");
		mnSetting.add(mnFullScreen);
		
		mnExitScreen = new JMenuItem("Exit Screen");
		mnSetting.add(mnExitScreen);
		
		mnSettingSystem = new JMenuItem("SettingSystem");
		mnSetting.add(mnSettingSystem);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmUseHelp = new JMenuItem("UseHelp");
		mnHelp.add(mntmUseHelp);
		
		mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		//监听打开视频文件按钮
		mnFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openVideo();
			}
		});
		
		//监听退出按钮
		mnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exitSystem();
			}
		});
		
		//监听视频播放的暂停按钮
		mnPause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseVideo();
			}
		});
		
		//监听视频播放的播放按钮
		mnPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playVideo();
			}
		});
		
		//监听视频播放的停止按钮
		mnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stopVideo();
			}
		});
		
		//监听列表播放的按钮
		mnPlayList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listPlayVideo();
			}
		});
		
		//监听顶部广告图片更换按钮
		mnToppic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeTopPic();
			}
		});
		
		//监听左边广告图片的更换按钮
		mnLeftpic.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLeftPic();
			}
		});
		
		//监听右边广告图片的更换按钮
		mnRightpic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeRightPic();
			}
		});
		
		//监听全屏显示按钮
		mnFullScreen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openFullScreen();
			}
		});
		
		//监听退出全屏显示按钮
		mnExitScreen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exitFullScreen();
			}
		});
		
		//监听使用帮助按钮
		mntmUseHelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainWindow.this, "欢迎使用新南粤人才市场投影程序V1.0\n使用方法请咨询技术员", "使用帮助", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//监听关于我们按钮
		mntmAbout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainWindow.this, "作者：clyao\n日期：2015-11-26 \n版本：V1.0正式版\n版权：新南粤人才市场所有", "关于我们", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//监听弹出设置窗体
		mnSettingSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSettingWindow();
			}
		});
	}
	
	//上边UI布局
	public void northUI(){
		toppanel = new ImagebgJPanel(getClass().getResourceAsStream("/images/top.9.png"));
		toppanel.setBorder(null);
		toppanel.setPreferredSize(new Dimension(1024, 80));
		contentPane.add(toppanel, BorderLayout.NORTH);
		toppanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));
	}
	
	//左边UI布局
	public void westUI(){
		leftpanel = new ImagebgJPanel(getClass().getResourceAsStream("/images/left.9.png"));
		leftpanel.setPreferredSize(new Dimension(100, 618));
		contentPane.add(leftpanel, BorderLayout.WEST);
	}
	
	//中间UI布局
	public void centerUI(){
		JPanel videopanel = new JPanel();
		contentPane.add(videopanel, BorderLayout.CENTER);
		videopanel.setLayout(new BorderLayout(0, 0));
		
		showpanel = new JPanel();
		videopanel.add(showpanel, BorderLayout.CENTER);
		showpanel.setLayout(new BorderLayout(0, 0));
		
		controlpanel = new JPanel();
		controlpanel.setBorder(null);
		controlpanel.setBackground(Color.BLACK);
		videopanel.add(controlpanel, BorderLayout.SOUTH);
		controlpanel.setLayout(new BorderLayout(0, 0));
		
		progressbarpanel = new JPanel();
		progressbarpanel.setBackground(Color.BLACK);
		controlpanel.add(progressbarpanel, BorderLayout.CENTER);
		
		progressBar = new JProgressBar();
		progressBar.setOpaque(true);
		progressBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				jump((float)x/progressBar.getWidth());
			}
		});
		progressbarpanel.setLayout(new BorderLayout(0, 0));
		progressBar.setStringPainted(true);
		progressBar.setBorderPainted(false);
		progressBar.setBorder(null);
		progressBar.setBackground(Color.DARK_GRAY);
		progressbarpanel.add(progressBar);
		
		mediabtnpanel = new JPanel();
		mediabtnpanel.setBackground(Color.BLACK);
		controlpanel.add(mediabtnpanel, BorderLayout.NORTH);
		
		btnstop = new JToggleButton();
		btnstop.setFocusPainted(false);
		btnstop.setContentAreaFilled(false);
		btnstop.setOpaque(false);
		btnstop.setBorderPainted(false);
		btnstop.setIcon(new ImageIcon(getClass().getResource("/images/stopnormal.png")));
		btnstop.setRolloverIcon(new ImageIcon(getClass().getResource("/images/stoprollover.png")));
		btnstop.setSelectedIcon(new ImageIcon(getClass().getResource("/images/stopselected.png")));
		mediabtnpanel.add(btnstop);
		btnstop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stopVideo();
			}
		});
		
		btnplay = new JToggleButton();
		btnplay.setFocusPainted(false);
		btnplay.setContentAreaFilled(false);
		btnplay.setOpaque(false);
		btnplay.setBorderPainted(false);
		btnplay.setIcon(new ImageIcon(getClass().getResource("/images/playnormal.png")));
		btnplay.setRolloverIcon(new ImageIcon(getClass().getResource("/images/playrollover.png")));
		btnplay.setSelectedIcon(new ImageIcon(getClass().getResource("/images/playselected.png")));
		mediabtnpanel.add(btnplay);
		btnplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				playVideo();
			}
		});
		
		btnpause = new JToggleButton();
		btnpause.setFocusPainted(false);
		btnpause.setContentAreaFilled(false);
		btnpause.setOpaque(false);
		btnpause.setBorderPainted(false);
		btnpause.setIcon(new ImageIcon(getClass().getResource("/images/pausenormal.png")));
		btnpause.setRolloverIcon(new ImageIcon(getClass().getResource("/images/pauserollover.png")));
		btnpause.setSelectedIcon(new ImageIcon(getClass().getResource("/images/pauseselected.png")));
		mediabtnpanel.add(btnpause);
		btnpause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pauseVideo();
			}
		});
		
		btnvolume = new JToggleButton();
		btnvolume.setFocusPainted(false);
		btnvolume.setContentAreaFilled(false);
		btnvolume.setOpaque(false);
		btnvolume.setBorderPainted(false);
		btnvolume.setIcon(new ImageIcon(getClass().getResource("/images/volumenormal.png")));
		btnvolume.setRolloverIcon(new ImageIcon(getClass().getResource("/images/volumerollover.png")));
		btnvolume.setSelectedIcon(new ImageIcon(getClass().getResource("/images/volumeselected.png")));
		mediabtnpanel.add(btnvolume);
		btnvolume.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					volume(0);
				}
				if(e.getStateChange() == ItemEvent.DESELECTED){
					volume(slider.getValue());
				}
			}
		});
		
		slider = new JSlider();
		slider.setOpaque(false);
		mediabtnpanel.add(slider);
		
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		showpanel.add(mediaPlayerComponent, BorderLayout.CENTER);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				volume(slider.getValue());
			}
		});
	}
	
	//右边UI布局
	public void eastUI(){
		rightpanel = new ImagebgJPanel(getClass().getResourceAsStream("/images/right.9.png"));
		rightpanel.setPreferredSize(new Dimension(100, 618));
		contentPane.add(rightpanel, BorderLayout.EAST);
		
		lblWeek = new JLabel();
		lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeek.setForeground(Color.WHITE);
		lblWeek.setFont(new Font("Dialog", Font.PLAIN, 48));
		
		lblDate = new JLabel();
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblDate.setForeground(Color.WHITE);
		
		lblDayPicture = new JLabel();
		lblDayPicture.setHorizontalAlignment(SwingConstants.CENTER);
		lblDayPicture.setSize(80, 80);
		
		lblTemperature = new JLabel();
		lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemperature.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblTemperature.setForeground(Color.WHITE);
		
		lblWeather = new JLabel();
		lblWeather.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeather.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblWeather.setForeground(Color.WHITE);
		
		lblWind = new JLabel();
		lblWind.setHorizontalAlignment(SwingConstants.CENTER);
		lblWind.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblWind.setForeground(Color.WHITE);	
		
		lblRealWeather = new JLabel();
		lblRealWeather.setHorizontalAlignment(SwingConstants.CENTER);
		lblRealWeather.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblRealWeather.setForeground(Color.YELLOW);
		
		GroupLayout gl_rightpanel = new GroupLayout(rightpanel);
		gl_rightpanel.setHorizontalGroup(
			gl_rightpanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rightpanel.createSequentialGroup()
					.addGroup(gl_rightpanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDayPicture, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(lblDate, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(lblWeek, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(lblTemperature, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(lblWind, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(lblRealWeather, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(lblWeather, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_rightpanel.setVerticalGroup(
			gl_rightpanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rightpanel.createSequentialGroup()
					.addComponent(lblWeek)
					.addGap(10)
					.addComponent(lblDate)
					.addGap(18)
					.addComponent(lblDayPicture, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblTemperature)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblWeather)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblWind)
					.addGap(9)
					.addComponent(lblRealWeather)
					.addContainerGap(234, Short.MAX_VALUE))
		);
		rightpanel.setLayout(gl_rightpanel);
		weatherTash();
	}
	
	//底部UI布局
	public void southUI(){
		footerpanel = new JPanel();
		footerlabel = new JLabel();
		//给footerlable设置text
		setFooterLabelText();
		footerpanel.add(footerlabel);
		contentPane.add(footerpanel, BorderLayout.SOUTH);
		new Thread(new MoveThread()).start();
	}
	
	//从网络获取天气
	public void weather(){
		try {
			Weather w = new Weather();
			w = w.resolveJSON();
			lblWeek.setText(w.getWeek());
			lblDate.setText(w.getDate());
			lblDayPicture.setIcon(new ImageIcon(getClass().getResource("/images/weather2.png")));
			lblTemperature.setText(w.getTemperature());
			lblWeather.setText(w.getWeather());
			lblWind.setText(w.getWind());
			lblRealWeather.setText(w.getRealWeather());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "网络连接失败，无法获取天气！");
			e.printStackTrace();
		}
	}
	
	//更新右边天气的定时器
	public void weatherTash(){
		//定时更新天气
		TimerTask task = new TimerTask() {
		    @Override
		    public void run() {
		    	weather();
		    }
		};
		Calendar calendar = Calendar.getInstance();
		Timer timer = new Timer();
		timer.schedule(task, calendar.getTime(), 60*60*1000);
	}
	
	//从数据库中获取数据，并设置到JLabel上
	public void setFooterLabelText(){
		try {
			dbOperation = new DBOperation();
			List<String> joinnoteList = dbOperation.getAllDate();
			StringBuffer joinnoteText = new StringBuffer();
			for(String joinnote:joinnoteList){
				joinnoteText.append(joinnote);
			}
			String text = joinnoteText.toString().replaceAll("\\s*", "");
			footerlabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
			footerlabel.setForeground(Color.RED);
			
			//获取JLabel里字符串的像素（这个要放在setFont之后才有效，否则无法获取正确的像素长度）
			fontMetrics = footerlabel.getFontMetrics(footerlabel.getFont());
			moveLength = fontMetrics.stringWidth(text);
			
			footerlabel.setPreferredSize(new Dimension(moveLength, 50));
			footerlabel.setText(text);
			//输出JLabel移动的像素
			System.out.println("JLabel移动的像素为："+moveLength);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "数据库连接失败！");
			e.printStackTrace();
		}
	}
	
	//底部滚动文字的实现类
	private class MoveThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				for (int i = 1024; i >-moveLength; i--) {
					try {
						Thread.sleep(30);// 线程休眠0.01秒
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					footerlabel.setLocation(i, 5);// 移动标签
					//判断是否移动到最后
					if(i == -moveLength+1){
						//更新给footerlable的text
						setFooterLabelText();
					}
				}
			}
		}
		
	}
	
	public EmbeddedMediaPlayer getMediaPlayer(){
		return mediaPlayerComponent.getMediaPlayer();
	}
	
	public JProgressBar getpJProgressBar(){
		return progressBar;
	}
	
	//暂停视频播放
	public void pauseVideo(){
		PlayMain.frame.getMediaPlayer().pause();
	}
	
	//开始视频播放
	public void playVideo(){
		PlayMain.frame.getMediaPlayer().play();
	}
	
	//停止视频播放
	public void stopVideo(){
		PlayMain.frame.getMediaPlayer().stop();
	}
	
	//退出程序
	public void exitSystem(){
		PlayMain.frame.getMediaPlayer().release();
		System.exit(0);
	}
	
	//开启全屏模式
	public void openFullScreen(){
		PlayMain.frame.getGraphicsConfiguration().getDevice().setFullScreenWindow(PlayMain.frame);
	}
	
	//退出全屏模式
	public void exitFullScreen(){
		PlayMain.frame.getGraphicsConfiguration().getDevice().setFullScreenWindow(null);
	}
	
	//弹出设置窗体
	public void openSettingWindow(){
		try {
			SettingWindow dialog = new SettingWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//点击进度条跳转到对应的播放时间
	public void jump(float to){
		PlayMain.frame.getMediaPlayer().setTime((long)(to*PlayMain.frame.getMediaPlayer().getLength()));
	}
	
	//设置音量大小
	public void volume(int v){
		PlayMain.frame.getMediaPlayer().setVolume(v);
	}
	
	//视频列表播放
	public void listPlayVideo(){
		try {
			videoList = new ArrayList<String>();
			MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
			MediaListPlayer mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();
			mediaListPlayer.setMediaPlayer(PlayMain.frame.getMediaPlayer()); 
			MediaList mediaList = mediaPlayerFactory.newMediaList();
			videoList = XMLUtil.readXML();
			for(String list:videoList){
				mediaList.addMedia(list);
				System.out.println("播放列表文件：" + list);
			}
			mediaListPlayer.setMediaList(mediaList);
			//MediaListPlayerMode.LOOP重复播放   MediaListPlayerMode.DEFAULT
			mediaListPlayer.setMode(MediaListPlayerMode.DEFAULT);
			mediaListPlayer.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//选择视频文件
	public static void openVideo(){
		try {
			JFileChooser chooser = new JFileChooser();
			int v = chooser.showOpenDialog(null);
			if(v == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				String videoPath = file.getAbsolutePath();
				PlayMain.frame.getMediaPlayer().playMedia(videoPath);
				System.out.println(videoPath);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "对不起，打开文件出错了！");
			e.printStackTrace();
		}
	}
	
	//改变顶部的广告图片
	public void changeTopPic(){
		try {
			JFileChooser chooser = new JFileChooser();
			int v = chooser.showOpenDialog(null);
			if(v == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				InputStream inputStream = new FileInputStream(file);
				PlayMain.frame.toppanel.setBackgroundImage(inputStream);
				PlayMain.frame.toppanel.repaint();
				System.out.println(file.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//改变左边的广告图片
	public static void changeLeftPic(){
		try {
			JFileChooser chooser = new JFileChooser();
			int v = chooser.showOpenDialog(null);
			if(v == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				InputStream inputStream = new FileInputStream(file);
				PlayMain.frame.leftpanel.setBackgroundImage(inputStream);
				PlayMain.frame.leftpanel.repaint();
				System.out.println(file.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//改变右边的广告图片
	public static void changeRightPic(){
		try {
			JFileChooser chooser = new JFileChooser();
			int v = chooser.showOpenDialog(null);
			if(v == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				InputStream inputStream = new FileInputStream(file);
				PlayMain.frame.rightpanel.setBackgroundImage(inputStream);
				PlayMain.frame.rightpanel.repaint();
				System.out.println(file.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//视频进度条处理方法
	public void videoControlWorker(){
		new SwingWorker<String, Integer>() {

			@Override
			protected String doInBackground() throws Exception {
				while (true) {
					long total = PlayMain.frame.getMediaPlayer().getLength();
					long curr = PlayMain.frame.getMediaPlayer().getTime();
					float percent = (float)curr/total;
					publish((int)(percent*100));
					Thread.sleep(100);
				}
			}
			
			protected void process(java.util.List<Integer> chunks) {
				for (int v:chunks) {
					PlayMain.frame.getpJProgressBar().setValue(v);
				}
			};
			
		}.execute();
	}
	
}
