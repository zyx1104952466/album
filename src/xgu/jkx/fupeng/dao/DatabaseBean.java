package xgu.jkx.fupeng.dao;

import java.sql.*;

public class DatabaseBean {
	private static String driverName = "com.mysql.jdbc.Driver";

	private static String username = "root";

	private static String password = "root";

	private static String databaseURL = "jdbc:mysql://localhost:3306/ajaxalbum?useUnicode=true&characterEncoding=UTF-8";
//	private static String databaseURL = "jdbc:mysql://localhost:3307/fupeng?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8"; //����eatj.com�ϵ����ݿ����Ӵ�

	/**
	 * ��ȡ���ݿ�����Connection����
	 * �޷���ȡ�򷵻�null
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// ������������
			Class.forName(driverName);
			// ��ȡ���ݿ�����
			conn = DriverManager.getConnection(databaseURL, username, password);
		} catch (ClassNotFoundException cnfe) {
			// �޷��ҵ���������
			cnfe.printStackTrace();
			conn = null;
		} catch (SQLException sqle) {
			// ���ݿⷢ���쳣
			sqle.printStackTrace();
			conn = null;
		}
		return conn;
	}
	

	/**
	 * �ر�����
	 * �������ͷ���Դ
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
	 * �ر�����
	 * �������ͷ���Դ
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
	 * ������ͨ
	 * @param args
	 */
	public static void main(String[] args){
		Connection conn=DatabaseBean.getConnection();
		System.out.println("���connection�ɹ�: "+conn);
		close(conn,null,null);
		System.out.println("�ͷ�����....");
	} 
}
