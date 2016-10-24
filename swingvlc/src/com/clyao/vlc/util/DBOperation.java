package com.clyao.vlc.util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：clyao
 * @time：2015-11-16 15:00
 * @version 1.0v
 * @description 数据库的操作类
 */
public class DBOperation {

	private Statement statement;
	private DBConnection dbConnection;
	public List<String> list;

	/*
	 * 查询数据库所有数据的方法 return 成功返回数据的列表 失败返回null
	 */
	public List<String> getAllDate() {
		try {
			list = new ArrayList<>();
			dbConnection = new DBConnection();
			statement = dbConnection.getConn().createStatement();
			String sql = "SELECT * FROM LOC_Joinnote WHERE Comnm IS NOT NULL AND Comnm<>''";
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next()){
				while (resultSet.next()) { // 循环输出结果集
					String comnm = resultSet.getString("Comnm");
					String pewno = resultSet.getString("Pewno");
					list.add("展位号："+ pewno + "-" + comnm + "   ");
				}
			}else{
				list.add("请稍等,正在获取数据中......");
			}
			System.out.println("查询数据成功！");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
