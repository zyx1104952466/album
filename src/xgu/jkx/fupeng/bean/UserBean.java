package xgu.jkx.fupeng.bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xgu.jkx.fupeng.dao.DatabaseBean;
import xgu.jkx.fupeng.model.User;
import xgu.jkx.fupeng.util.StringUtil;

public class UserBean {

	
	/**
	 * �õ��͹ؼ���ƥ��Ľ����
	 * @param keyword
	 * @return
	 */
	public static int countForKeyword(String keyword) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "select count(id) from user where username like '"+keyword+"%'";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				count = rs.getInt(1);
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			count = 0;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, stmt, null);
		}
		return count;
	}
	
	
	/**
	 * �����û�
	 * @param keyword
	 * @param start
	 * @param count
	 * @return List<User>
	 */
	public static List<User> search(int start, int count, String keyword) {
		List<User> users = new ArrayList<User>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select u.id,u.username,u.url,u.addtime,u.email,u.nickname,u.active from user u where u.username like '"+keyword+"%' order by username limit "+start+","+count;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			User user = null;
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setUrl((rs.getString(3)));
				user.setAddTime(StringUtil.dateToString(rs.getDate(4)));
				user.setEmail(rs.getString(5));
				user.setNickName(rs.getString(6));
				user.setActive(rs.getInt(7));
				users.add(user);
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			users = null;
		} finally {
			DatabaseBean.close(conn, stmt, rs);
		}
		if (users.size() == 0) {
			return null;
		}
		return users;
	}
	
	/**
	 * �û����������ܲ�����ʾ
	 * @param keyword
	 * @return
	 */
	public static List<String> searchSuggest(String keyword) {
		List<String> strList = new ArrayList<String>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select username from user where username like '"+keyword+"%' order by username";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				strList.add(rs.getString("username"));
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			strList.clear();
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, stmt, null);
		}
		return strList;
	}
	
	/**
	 * �û��Ƿ�ӵ���ض�����Ƭ
	 * 
	 * @param userId
	 * @param photoId
	 */
	public static boolean holdThePhoto(int userId, int photoId) {
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		boolean match = false;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			String sql = "select a.user_id from album a,photo p where a.id=p.album_id and p.id=? ;";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				match = (userId == rs.getInt(1));
			}
			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		if (match)
			System.out.println("Ҫɾ������Ƭ������Ȩ��������");
		return match;
	}

	/**
	 * �û��Ƿ�ӵ���ض������
	 * 
	 * @param userId
	 * @param photoId
	 */
	public static boolean holdTheAlbum(int userId, int albumId) {
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		boolean match = false;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			String sql = "select u.id from album a,user u where a.user_id=u.id and a.id=? ;";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, albumId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				System.out.println("userId: " + userId);
				System.out.println("albumId: " + albumId);
				match = (userId == rs.getInt(1));
			}
			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		if (match)
			System.out.println("��ѯ���������Ȩ��������");
		return match;
	}

	/**
	 * ��½�����û���Ծ��
	 */
	public static void addActive(int userId, int grade) {
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			String sql = "update user set active=active+? where id=?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, grade);
			prepstmt.setInt(2, userId);
			prepstmt.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
	}

	/**
	 * �õ��û�����
	 * 
	 * @return
	 */
	public static int getUserNum() {
		int num = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			String sql = "select count(*) from user";
			prepstmt = conn.prepareStatement(sql);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt(1);
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		// num = 20; // �����趨20���û�
		return num;
	}

	/**
	 * �õĻ�Ծ�û�
	 * 
	 * @param start
	 * @param count
	 * @return
	 */
	public static List<User> getHotUsers(int start, int count) {
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			String sql = "select u.id,u.username,u.url,u.addtime,u.email,u.nickname,u.active from user u order by u.active desc,u.id desc limit ?,?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, start);
			prepstmt.setInt(2, count);
			rs = prepstmt.executeQuery();
			User user = null;
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setUrl(rs.getString(3));
				user.setAddTime(StringUtil.dateToString(rs.getDate(4)));
				user.setEmail(rs.getString(5));
				user.setNickName(rs.getString(6));
				user.setActive(rs.getInt(7));
				userList.add(user);
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			userList = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		// for (int i = 0; i < 8; i++) {
		// userList.add(new User(i, "�û�" + i, "�ǳ�" + i, "2009/09/06",
		// "images/default/nopic.jpg", "admin@123.com", 150 - i * 3));
		// }
		return userList;
	}

	/**
	 * �ж��û����Ƿ����
	 */
	public static boolean isUsernameExist(String username) {
		boolean ok = false;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where username=?";
		conn = DatabaseBean.getConnection();
		try {
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, username);
			rs = prepstmt.executeQuery();
			if (rs.next()) {
				ok = true; // ��ѯ����¼
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ok = false; // �쳣����false
		} finally {
			DatabaseBean.close(conn, prepstmt, rs); // ��˳Ѱ�رո�����Դ
		}
		return ok;
	}

	/**
	 * ע��
	 * 
	 * @param user
	 * @return
	 */
	public static User addUser(User user) {
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "insert into user(username,password,nickname,addtime,email) values(?,?,?,?,?)";
		conn = DatabaseBean.getConnection();
		if (isUsernameExist(user.getUsername())) {
			user = null;
		} else {
			// �û���������,����ע��
			try {
				conn.setAutoCommit(false);
				prepstmt = conn.prepareStatement(sql);
				prepstmt.setString(1, user.getUsername());
				prepstmt.setString(2, user.getPassword());
				prepstmt.setString(3, user.getNickName());
				prepstmt.setDate(4, new Date(System.currentTimeMillis()));
				prepstmt.setString(5, user.getEmail());
				if (1 == prepstmt.executeUpdate()) {
					conn.commit();
				}
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				user = null;
			} finally {
				DatabaseBean.close(conn, prepstmt, null);
			}
		}
		return user;
	}

	/**
	 * ��ͨ�û���¼
	 */
	public static User Login(User user) {
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where username=? and password=?";
		conn = DatabaseBean.getConnection();
		try {
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, user.getUsername());
			prepstmt.setString(2, user.getPassword());
			rs = prepstmt.executeQuery();

			if (rs.next()) {
				/**
				 * userInfo.setId(rs.getInt("id"));
				 * userInfo.setAddTime(StringUtil.changeTimestamp(rs.getTimestamp("addTime")));
				 * userInfo.setNickName(rs.getString("nickname"));
				 * userInfo.setUsergroup(rs.getInt("usergroup"));
				 * userInfo.setUsergroup(rs.getString("email"));
				 * userInfo.setUrl(rs.getString("url"));
				 */
				user = getUserInfoById(rs.getInt("id"));
				addActive(user.getId(), 2);
			} else {
				user = null;
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			user = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return user;
	}

	
	/**
	 * ����Ա��¼
	 */
	public static User LoginAdmin(User user) {
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select * from admin where name=? and password=?";
		conn = DatabaseBean.getConnection();
		try {
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, user.getUsername());
			prepstmt.setString(2, user.getPassword());
			rs = prepstmt.executeQuery();

			if (rs.next()) {
				/**
				 * userInfo.setId(rs.getInt("id"));
				 * userInfo.setAddTime(StringUtil.changeTimestamp(rs.getTimestamp("addTime")));
				 * userInfo.setNickName(rs.getString("nickname"));
				 * userInfo.setUsergroup(rs.getInt("usergroup"));
				 * userInfo.setUsergroup(rs.getString("email"));
				 * userInfo.setUrl(rs.getString("url"));
				 */
			} else {
				user = null;
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			user = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return user;
	}
	
	
	/**
	 * ����id��ȡ�û���Ϣ
	 * 
	 */
	public static User getUserInfoById(int userId) {
		User user = null;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where id=?";
		conn = DatabaseBean.getConnection();
		try {
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, userId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setNickName(rs.getString("nickname"));
				user.setAddTime(StringUtil.dateToString(rs.getDate("addtime")));
				user.setActive(rs.getInt("active"));
				user.setUrl(rs.getString("url"));
				user.setEmail(rs.getString("email"));
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			user = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return user;
	}

	/**
	 * �޸�ͷ��
	 */
	public static boolean updateUserPhoto(String url, int userId) {
		System.out.println("����dao��url=" + url);
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "update user set url=? where id=?";
		conn = DatabaseBean.getConnection();
		try {
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, url);
			prepstmt.setInt(2, userId);
			ok = (1 == prepstmt.executeUpdate());
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return ok;
	}

	/**
	 * �ж��û������Ƿ���ȷ
	 */
	public static boolean isRightUsernamePassword(String username,
			String password) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select * from userinfo where username=? and password=?";
		try {
			conn = DatabaseBean.getConnection();
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, username);
			prepstmt.setString(2, password);
			// ok=(1==prepstmt.executeUpdate());

			rs = prepstmt.executeQuery();

			if (rs.next()) {
				// ������˵����ȷ
				ok = true;
			} else {
				ok = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return ok;
	}

	/**
	 * �޸��������Ϣ
	 */
	public static boolean updatePassword(int userId, String password,
			String email) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		// ResultSet rs=null;
		String sql = "update userinfo set password=? , email=? where id=?";
		try {
			conn = DatabaseBean.getConnection();
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, password);
			prepstmt.setString(2, email);
			prepstmt.setInt(3, userId);
			ok = (1 == prepstmt.executeUpdate());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return ok;
	}

	/**
	 * ��ȡ�û���
	 */
	public static int getAllUserNum() {
		// ArrayList<Integer> arr=new ArrayList<Integer>();
		int num = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select count(id) from userinfo";
		try {
			conn = DatabaseBean.getConnection();
			prepstmt = conn.prepareStatement(sql);
			rs = prepstmt.executeQuery();
			if (rs.next()) {
				// ������˵����ȷ
				num = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			num = 0;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return num;
	}

	/**
	 * ��ȡ�����û���ID
	 */
	public static int[] getAllUserId() {
		// ArrayList<Integer> arr=new ArrayList<Integer>();
		int[] arr = new int[getAllUserNum()];
		int i = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select id from userinfo";
		try {
			conn = DatabaseBean.getConnection();
			prepstmt = conn.prepareStatement(sql);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				// ������˵����ȷ
				arr[i] = rs.getInt("id");
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			arr = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return arr;
	}

	/**
	 * �����ȡʮ���û�����Ϣ
	 */
	public static List<User> getRandomUser() {
		ArrayList<User> userList = new ArrayList<User>();
		// ��ȡ�����û���ID
		int[] idList = getAllUserId();
		if (idList.length <= 10) {
			for (int i = 0; i < idList.length; i++) {
				userList.add(getUserInfoById(idList[i]));
			}
		} else {
			for (int i = 0; i < 10; i++) {
				// Random random=new Random(idList.length);
				int random = new Random().nextInt(idList.length);
				userList.add(getUserInfoById(idList[random]));
			}
		}
		return userList;

	}

	public static boolean updateUserinfo(int id, String newPassword,
			String email, String nickName) {
		boolean done = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "update user set password=?,email=?,nickname=? where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, newPassword);
			prepstmt.setString(2, email);
			prepstmt.setString(3, nickName);
			prepstmt.setInt(4, id);
			prepstmt.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			done = false;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return done;
	}

	
	/**
	 * ����idɾ���û�
	 * @param userId
	 * @return
	 */
	public static boolean deleteUserById(int userId) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "delete from user where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, userId);
			prepstmt.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return ok;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// UserInfo user = getUserInfoById(6);
		// System.out.print(u.getUrl()+u.getNickName()+u.getPassword()+u.getUsergroup()+u.getUsername()+u.getAddTime());;
		System.out.print(getRandomUser());
		/*
		 * (UserInfo u=new UserInfo(); u.setUsername("qiudawei");
		 * u.setPassword("851120"); u.setNickName("�����ѩ"); //u=new
		 * UserBean().Login(u); //System.out.print(new
		 * UserBean().isUsernameExist(u)); u=new UserBean().addUser(u);
		 * if(u==null){ System.out.print(u); }else{
		 * System.out.print(u.getId()+u.getNickName()+u.getPassword()+u.getUsergroup()+u.getUsername()+u.getAddTime());; }
		 * /*UserInfo u=new UserInfo(); u.setUsername("Dai");
		 * u.setPassword("asd"); u=new UserBean().Login(u);
		 * System.out.print(u.getId()+u.getNickName()+u.getPassword()+u.getUsergroup()+u.getUsername()+u.getAddTime());;
		 */
	}
}
