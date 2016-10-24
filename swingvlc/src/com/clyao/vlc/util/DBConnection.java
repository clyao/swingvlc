package com.clyao.vlc.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author clyao
 * @time：2015-11-16 15:00
 * @version 1.0v
 * @descriptiom 连接数据库的工具类
 */
public class DBConnection {

	String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
	String dbURL = "jdbc:sqlserver://192.168.15.2:1433; DatabaseName=Doss"; // 连接服务器和数据库test
	String userName = "sa"; // 默认用户名
	String userPwd = "iSeelgood"; // 密码
	Connection dbConn;

	// 连接数据库的工具类
	public Connection getConn() {
		try {
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("192.168.14.55数据库连接成功！");
			return dbConn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
