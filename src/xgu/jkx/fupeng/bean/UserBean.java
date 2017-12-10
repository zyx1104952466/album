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
	 * 得到和关键字匹配的结果数
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
	 * 搜索用户
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
	 * 用户搜索的智能补齐提示
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
	 * 用户是否拥有特定的相片
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
			System.out.println("要删除的相片，所属权限正常！");
		return match;
	}

	/**
	 * 用户是否拥有特定的相册
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
			System.out.println("查询的相册所属权限正常！");
		return match;
	}

	/**
	 * 登陆增加用户活跃度
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
	 * 得到用户数量
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
		// num = 20; // 测试设定20个用户
		return num;
	}

	/**
	 * 得的活跃用户
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
		// userList.add(new User(i, "用户" + i, "昵称" + i, "2009/09/06",
		// "images/default/nopic.jpg", "admin@123.com", 150 - i * 3));
		// }
		return userList;
	}

	/**
	 * 判断用户名是否存在
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
				ok = true; // 查询到记录
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
			ok = false; // 异常返回false
		} finally {
			DatabaseBean.close(conn, prepstmt, rs); // 按顺寻关闭各种资源
		}
		return ok;
	}

	/**
	 * 注册
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
			// 用户名不存在,可以注册
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
	 * 普通用户登录
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
	 * 管理员登录
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
	 * 根据id获取用户信息
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
	 * 修改头像
	 */
	public static boolean updateUserPhoto(String url, int userId) {
		System.out.println("进入dao，url=" + url);
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
	 * 判断用户密码是否正确
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
				// 查出结果说明正确
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
	 * 修改密码等信息
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
	 * 获取用户数
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
				// 查出结果说明正确
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
	 * 获取所有用户的ID
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
				// 查出结果说明正确
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
	 * 随机获取十个用户的信息
	 */
	public static List<User> getRandomUser() {
		ArrayList<User> userList = new ArrayList<User>();
		// 获取所有用户的ID
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
	 * 根据id删除用户
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
		 * u.setPassword("851120"); u.setNickName("满天飞雪"); //u=new
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
