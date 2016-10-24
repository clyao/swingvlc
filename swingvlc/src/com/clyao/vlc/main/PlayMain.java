package com.clyao.vlc.main;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.plaf.InsetsUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.clyao.vlc.views.MainWindow;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

/**
 * @author clyao
 * @time 2016-10-18 14:56
 * @version 1.0v
 * @description 程序主入口
 */
public class PlayMain {
	
	public static MainWindow frame;
	
	public static void main(String[] args) {
		if(RuntimeUtil.isWindows()){
			//winsows系统
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "D:\\CommonSoftware\\VideoLAN\\VLC");
		}else if(RuntimeUtil.isMac()){
			//mac系统
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "");
		}else if(RuntimeUtil.isNix()){
			//Linux系统
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "");
		}
		
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		
		//启动窗体
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
					BeautyEyeLNFHelper.launchBeautyEyeLNF();
					UIManager.put("RootPane.setupButtonVisible", false);
					UIManager.put("TabbedPane.tabAreaInsets", new InsetsUIResource(2,2,2,2));
					frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
