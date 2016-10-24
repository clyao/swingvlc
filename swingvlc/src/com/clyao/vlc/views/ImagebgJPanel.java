package com.clyao.vlc.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.clyao.vlc.ninepatch.SwingNinePatch;

/**
 * @author clyao
 * @time 2015-11-18 15:00
 * @version 1.0v
 * @description 给JPanel添加背景图片
 */
public class ImagebgJPanel extends JPanel { 
	
	private static final long serialVersionUID = 1L;
	
	private SwingNinePatch swingNinePath = null;
	private BufferedImage bufferedImage = null;  
  
    public ImagebgJPanel(InputStream imageInputStream) {
    	try {
			this.bufferedImage = ImageIO.read(imageInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}  
    }  
    
    //重新绘制JPanel的背景图片
    public void setBackgroundImage(InputStream imageInputStream){
    	try {
			this.bufferedImage = ImageIO.read(imageInputStream);
			this.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //重新写JPanel
    @Override
    protected void paintComponent(Graphics g) {
    	try {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			swingNinePath = new SwingNinePatch(bufferedImage);
			swingNinePath.drawNinePatch(g2d, 0, 0, this.getWidth(), this.getHeight());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "此图片不是.9图片！");
			e.printStackTrace();
		}
    }  
}  