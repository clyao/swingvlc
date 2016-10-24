package com.clyao.vlc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author clyao
 * @time 2016-10-18 15:00
 * @version 1.0v
 * @description 设置界面的xml解析
 */
public class XMLUtil {
	
	//添加xml节点
	@SuppressWarnings("unchecked")
	public static void add(String elementValue) throws Exception{
		File file = createFile();
        BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
        SAXReader reader = new SAXReader();
        Document document = reader.read(bufferedreader);  
          
        Element videolist = document.getRootElement().element("videolist");  
       
		List<Object> list = videolist.elements();  
          
        Element video = DocumentHelper.createElement("video");  
        video.setText(elementValue);  
        list.add(video);  
          
        OutputFormat former =OutputFormat.createPrettyPrint();//设置格式化输出器  
        former.setEncoding("UTF-8");  
          
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"),former);  
        writer.write(document);  
        writer.close();  
    }  
    
	//删除xml节点
    @SuppressWarnings("unchecked")
	public static void delete(String elementValue) throws Exception{
    	File file = createFile();
        BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
        SAXReader reader = new SAXReader();  
        Document document = reader.read(bufferedreader);  
          
        Element videolist = document.getRootElement().element("videolist");
        List<Element> video = videolist.elements("video");  
		for (int i=0; i<video.size(); i++) {  
			Element elm = (Element) video.get(i);  
			if(elm.getText().equals(elementValue)){
				video.remove(elm);
			}
	    }
          
        OutputFormat format = OutputFormat.createPrettyPrint();  
        format.setEncoding("UTF-8");  
          
        XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);  
        writer.write(document);  
        writer.close();  
    } 
    
    //解析xml
    @SuppressWarnings("unchecked")
	public static Vector<String> readXML() throws Exception{
    	Vector<String> list =  new Vector<String>();
    	File file = createFile();
		BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
        SAXReader reader = new SAXReader();  
        Document document = reader.read(bufferedreader);  
          
        Element videolist = document.getRootElement().element("videolist");  
       
		List<Element> video = videolist.elements("video");  
		for (Iterator<Element> it = video.iterator(); it.hasNext();) {  
			Element elm = (Element) it.next();  
			list.add(elm.getText());       
	    }
    	
		return list;
    }
    
    //创建文件
    public static File createFile(){
    	try {
			File file=new File("res/setting/setting.xml");
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			    file.createNewFile();
			    
			    Element root = DocumentHelper.createElement("settings");
			    root.addElement("videolist");
			    Document document = DocumentHelper.createDocument(root);

			    OutputFormat former =OutputFormat.createPrettyPrint();//设置格式化输出器  
		        former.setEncoding("UTF-8");
		        
		        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"),former);  
		        writer.write(document);  
		        writer.close();
		        
			    return file;
			}else{
				return file;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
}