package xgu.jkx.fupeng.dao;

import java.sql.*;

public class DatabaseBean {
	private static String driverName = "com.mysql.jdbc.Driver";

	private static String username = "root";

	private static String password = "root";

	private static String databaseURL = "jdbc:mysql://localhost:3306/ajaxalbum?useUnicode=true&characterEncoding=UTF-8";
//	private static String databaseURL = "jdbc:mysql://localhost:3307/fupeng?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8"; //部署到eatj.com上的数据库连接串

	/**
	 * 获取数据库连接Connection对象
	 * 无法获取则返回null
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 加载驱动程序
			Class.forName(driverName);
			// 获取数据库连接
			conn = DriverManager.getConnection(databaseURL, username, password);
		} catch (ClassNotFoundException cnfe) {
			// 无法找到驱动程序
			cnfe.printStackTrace();
			conn = null;
		} catch (SQLException sqle) {
			// 数据库发生异常
			sqle.printStackTrace();
			conn = null;
		}
		return conn;
	}
	

	/**
	 * 关闭连接
	 * 按次序释放资源
	 * @param conn
	 * @param prepstmt
	 * @param rs
	 */
	public static void close(Connection conn, PreparedStatement prepstmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception rse) {
			rse.printStackTrace();
		}

		try {
			if (prepstmt != null)
				prepstmt.close(); 
		} catch (Exception stmte) {
			stmte.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close();
		} catch (Exception conne) {
			conne.printStackTrace();
		}
		
	}
	
	
	/**
	 * 关闭连接
	 * 按次序释放资源
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception rse) {
			rse.printStackTrace();
		}

		try {
			if (stmt != null)
				stmt.close(); 
		} catch (Exception stmte) {
			stmte.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close();
		} catch (Exception conne) {
			conne.printStackTrace();
		}
		
	}
	
	
	/**
	 * 测试连通
	 * @param args
	 */
	public static void main(String[] args){
		Connection conn=DatabaseBean.getConnection();
		System.out.println("获得connection成功: "+conn);
		close(conn,null,null);
		System.out.println("释放连接....");
	} 
}
