package Util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import Config.MySQLConfig;

public class SQLManager {
	static final Log logger = LogFactory.getLog(SQLManager.class);
	private static String url;
	private static String user;
	private static String pwd;
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	static {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url = MySQLConfig.URL;
		user = MySQLConfig.Username;
		pwd = MySQLConfig.Password;
	}

	public static Connection getConnection() throws Exception {
		try {
			Connection conn = DriverManager.getConnection(url, user, pwd);
			tl.set(conn);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void close(Connection conn) {
		try {
			conn = tl.get();
			if (conn != null) {
				conn.close();
				tl.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
