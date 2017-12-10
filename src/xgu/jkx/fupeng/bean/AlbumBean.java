package xgu.jkx.fupeng.bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import xgu.jkx.fupeng.dao.DatabaseBean;
import xgu.jkx.fupeng.model.Album;
import xgu.jkx.fupeng.util.StringUtil;

/**
 * ���DAO��impl
 * 
 * @author FuPeng
 * 
 */

public class AlbumBean {

	public AlbumBean() {
	}

	/**
	 * �������
	 * 
	 * @param keyword
	 * @param start
	 * @param count
	 * @return List<Album>
	 */
	public static List<Album> search(int start, int count, String keyword) {
		List<Album> albums = new ArrayList<Album>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select a.id,a.name,a.introduce,a.addtime,u.username,a.url,a.open,a.skimnum,a.photonum from album a,user u where u.id=a.user_id and a.name like '"
				+ keyword + "%' order by name limit " + start + "," + count;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Album album = null;
			while (rs.next()) {
				album = new Album();
				album.setId(rs.getInt(1));
				album.setName(rs.getString(2));
				album.setIntroduce((rs.getString(3)));
				album.setAddTime(StringUtil.dateToString(rs.getDate(4)));
				album.setUsername(rs.getString(5));
				album.setUrl(rs.getString(6));
				album.setOpen(rs.getInt(7));
				album.setSkimNum(rs.getInt(8));
				album.setPhotoNum(rs.getInt(9));
				albums.add(album);
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
			albums = null;
		} finally {
			DatabaseBean.close(conn, stmt, rs);
		}
		if (albums.size() == 0) {
			return null;
		}
		return albums;
	}

	/**
	 * ͨ���û�Id�����Ӧ������б�
	 * 
	 * @param userId
	 * @return albums
	 */
	public static List<Album> getAlbumByUserId(int userId) {
		List<Album> albums = new ArrayList<Album>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.id,a.name,a.introduce,a.addtime,a.url,a.open,a.skimnum,a.photonum from album a where a.user_id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			Album album = null;
			while (rs.next()) {
				album = new Album();
				album.setId(rs.getInt(1));
				album.setName(rs.getString(2));
				album.setIntroduce((rs.getString(3)));
				album.setAddTime(StringUtil.dateToString(rs.getDate(4)));
				album.setUrl(rs.getString(5));
				album.setOpen(rs.getInt(6));
				album.setSkimNum(rs.getInt(7));
				album.setPhotoNum(rs.getInt(8));
				albums.add(album);
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
			albums = null;
		} finally {
			DatabaseBean.close(conn, pstmt, rs);
		}

		return albums;
	}

	/**
	 * ������������ܲ�ȫ��ʾ
	 * 
	 * @param keyword
	 * @return
	 */
	public static List<String> searchSuggest(String keyword) {
		List<String> strList = new ArrayList<String>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select name from album where name like '" + keyword
				+ "%' order by name";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				strList.add(rs.getString("name"));
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
	 * �õ��͹ؼ���ƥ��Ľ����
	 * 
	 * @param keyword
	 * @return
	 */
	public static int countForKeyword(String keyword) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "select count(id) from album where name like '" + keyword
				+ "%'";
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
	 * �������ķ���
	 * 
	 * @param albumId
	 * @param photoId
	 * @return
	 */
	public static boolean updateFace(int albumId, int photoId) {
		boolean done = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "update album set url = (select url from photo where id=?) where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
			prepstmt.setInt(2, albumId);
			done = (1 == prepstmt.executeUpdate());
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			done = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return done;
	}

	/**
	 * �������ķ���
	 * 
	 * @param albumId
	 * @param url
	 * @return
	 */

	public static boolean updateFace(int albumId, String url) {
		boolean done = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "update album set url = ? where id = ?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, url);
			prepstmt.setInt(2, albumId);
			done = (1 == prepstmt.executeUpdate());
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			done = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return done;
	}

	/**
	 * ��������������Ƭ����
	 * 
	 * @param albumId
	 * @param count
	 */
	public static boolean updatePhotonum(int albumId, int count) {
		boolean done = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "update album set photonum = photonum+? where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, count);
			prepstmt.setInt(2, albumId);
			done = (1 == prepstmt.executeUpdate());
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			done = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return done;
	}

	/**
	 * ���������������
	 * 
	 * @param albumId
	 * @param count
	 */
	public static boolean updateSkimnum(int albumId, int count) {
		boolean done = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "update album set skimnum = skimnum+? where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, count);
			prepstmt.setInt(2, albumId);
			done = (1 == prepstmt.executeUpdate());
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			done = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return done;
	}

	/**
	 * ��ѯһ����Ŀ������ᣬ��������������������
	 * 
	 * @param ��ʼλ��
	 * @param ��Ŀ��
	 * @return ����б�
	 */

	public static List<Album> getHotAlbums(int start, int count) {
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;

		List<Album> albumList = new ArrayList<Album>();
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			String sql = "select a.id,a.name,a.introduce,a.addtime,u.username,a.url,a.open,a.skimnum,a.photonum from album a,user u where u.id=a.user_id order by a.skimnum desc,a.id desc limit ?,?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, start);
			prepstmt.setInt(2, count);
			rs = prepstmt.executeQuery();

			Album album = null;
			while (rs.next()) {
				album = new Album();
				album.setId(rs.getInt(1));
				album.setName(rs.getString(2));
				album.setIntroduce((rs.getString(3)));
				album.setAddTime(StringUtil.dateToString(rs.getDate(4)));
				album.setUsername(rs.getString(5));
				album.setUrl(rs.getString(6));
				album.setOpen(rs.getInt(7));
				album.setSkimNum(rs.getInt(8));
				album.setPhotoNum(rs.getInt(9));
				albumList.add(album);
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
			albumList = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		// for (int i = 0; i < 8; i++) {
		// albumList.add(new
		// Album(i,"���"+i,"���"+i,"2009/9/5","admin","images/default/album.jpg",1,100,358));
		// }
		return albumList;
	}

	/**
	 * ��ѯ����Ƿ����
	 * 
	 * @param category
	 * @return boolean
	 */
	public static boolean isAlbumExist(String name) {
		boolean exist = false;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select * from album where name = ?";

		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, name);
			rs = prepstmt.executeQuery();
			conn.commit();
			conn.setAutoCommit(true);
			if (rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			exist = true;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return exist;
	}

	/**
	 * �������
	 * 
	 * @param category
	 * @return boolean
	 */
	public static boolean addAlbum(Album album) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		System.out.println("�������" + album.getName());
		System.out.println("�û�id��" + album.getUserid());
		String sql = "insert into album(name,introduce,addtime,user_id,open) values(?,?,?,?,?)";
		if (isAlbumExist(album.getName())) {
			ok = false; // ���ʧ��
		} else {
			try {
				conn = DatabaseBean.getConnection();
				conn.setAutoCommit(false);
				prepstmt = conn.prepareStatement(sql);
				prepstmt.setString(1, album.getName());
				prepstmt.setString(2, album.getIntroduce());
				prepstmt.setDate(3, new Date(System.currentTimeMillis()));
				prepstmt.setInt(4, album.getUserid());
				prepstmt.setInt(5, album.getOpen());
				ok = (1 == prepstmt.executeUpdate());
				conn.commit();
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				ok = false;
				System.out.println("�������ʧ�ܣ����ݿ���������쳣��");
			} finally {
				DatabaseBean.close(conn, prepstmt, null);
			}
		}
		return ok;
	}

	/**
	 * �����û�id��ȡһ�����������
	 * 
	 * @param start
	 * @param num
	 * @param userId
	 * @return List<Category>
	 */
	public static List<Album> getAlbums(int start, int count, int userId) {
		List<Album> albumList = new ArrayList<Album>();
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select a.id,a.name,a.introduce,a.addtime,a.user_id,u.username,a.url,a.open,a.skimnum,a.photonum from album a,user u where u.id=a.user_id and a.user_id=? order by a.addtime desc limit ?,?";
		if (userId == 0)
			sql = "select a.id,a.name,a.introduce,a.addtime,a.user_id,a.url,a.open,a.skimnum,a.photonum from album a order by a.addtime desc limit ?,?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			if (userId != 0) {
				prepstmt.setInt(1, userId);
				prepstmt.setInt(2, start);
				prepstmt.setInt(3, count);
			} else {
				prepstmt.setInt(1, start);
				prepstmt.setInt(2, count);
			}
			rs = prepstmt.executeQuery();
			Album album = null;
			while (rs.next()) {
				album = new Album();
				album.setUserid(rs.getInt("user_id"));
				album.setId(rs.getInt("id"));
				album.setName(rs.getString("name"));
				album.setIntroduce((rs.getString("introduce")));
				album
						.setAddTime(StringUtil.dateToString(rs
								.getDate("addtime")));
				if (userId != 0)
					album.setUsername(rs.getString("username"));
				album.setUrl(rs.getString("url"));
				album.setOpen(rs.getInt("open"));
				album.setSkimNum(rs.getInt("skimnum"));
				album.setPhotoNum(rs.getInt("photonum"));
				albumList.add(album);
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
			albumList = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return albumList;
	}

	/**
	 * �����û�id��������б�
	 * 
	 * @param userId
	 * @return
	 */
//	public static List<Category> getCategory(int userId) {
//		List<Category> arrayList = new ArrayList<Category>();
//		Connection conn = null;
//		PreparedStatement prepstmt = null;
//		ResultSet rs = null;
//		String sql = "select * from category where userid=?";
//		try {
//			conn = DatabaseBean.getConnection();
//			conn.setAutoCommit(false);
//			prepstmt = conn.prepareStatement(sql);
//			prepstmt.setInt(1, userId);
//			rs = prepstmt.executeQuery();
//			Category category = null;
//			while (rs.next()) {
//				category = new Category();
//				category = getCategoryById(rs.getInt("id"));
//				arrayList.add(category);
//			}
//			conn.commit();
//			conn.setAutoCommit(true);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			arrayList = null;
//		} finally {
//			DatabaseBean.close(conn, prepstmt, rs);
//		}
//		return arrayList;
//	}

	/**
	 * �����û�id��ȡ������
	 * 
	 * @param userId
	 * @return int
	 */
	public static int getCategoryNum(int userId) {
		int num = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from category where userid=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, userId);
			rs = prepstmt.executeQuery();
			// Category category=null;
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
			num = 0;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return num;
	}

	/**
	 * �û�idΪ0���ѯ�������û��������Ŀ�������ѯ��ָ���û��������Ŀ
	 * 
	 * @param userid
	 * @return
	 */
	public static int getAlbumNum(int userid) {
		int num = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql;
		if (userid == 0)
			sql = "select count(*) from album";
		else
			sql = "select count(*) from album where user_id=" + userid;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
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
		return num;
	}

	/**
	 * ͨ��ͼƬid��ѯ��albumId
	 * 
	 * @param photoId
	 * @return
	 */
	public static int getAlbumIdByPhotoId(int photoId) {
		int albumId = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select a.id from album a, photo p where a.id=p.album_id and p.id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
			rs = prepstmt.executeQuery();
			conn.commit();
			while (rs.next()) {
				albumId = rs.getInt(1);
			}
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			albumId = 0;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return albumId;
	}

	/**
	 * �����û�id�����idɾ����Ӧ�����
	 * 
	 * @param id
	 * @param userId
	 * @return boolean
	 */
	public static boolean deleteCategoryById(int id, int userId) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "delete from category where id=? and userid=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);

			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, id);
			prepstmt.setInt(2, userId);
			ok = (1 == prepstmt.executeUpdate());
			conn.commit();
			conn.setAutoCommit(true);
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
	 * �����û�id�����idɾ�����
	 * 
	 * @param albumId
	 * @param userId
	 * @return
	 */
	public static boolean delAlbumById(int albumId, int userId) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "delete from album where id=? and user_id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, albumId);
			prepstmt.setInt(2, userId);
			ok = (1 == prepstmt.executeUpdate());
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
		if (ok) {
			UserBean.addActive(userId, -2);
			return ok;
		} else
			return ok;
	}
	
	
	/**
	 * ��������idɾ�����
	 * @param albumId
	 * @return boolean
	 */
	public static boolean deleteAlbumById(int albumId) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "delete from album where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, albumId);
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
	 * �������id��ȡ���Ӧ�����
	 * 
	 * @param albumId
	 * @return
	 */
	public static Album getAlbumById(int albumId) {
		Album album = null;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select * from album where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, albumId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				album = new Album();
				album.setId(rs.getInt("id"));
				album.setName(rs.getString("name"));
				album.setIntroduce(rs.getString("introduce"));
				album
						.setAddTime(StringUtil.dateToString(rs
								.getDate("addtime")));
				album.setUserid(rs.getInt("user_id"));
				album.setUrl(rs.getString("url"));
				album.setOpen(rs.getInt("open"));
				album.setSkimNum(rs.getInt("skimnum"));
				album.setPhotoNum(rs.getInt("photonum"));
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			album = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return album;
	}

	/**
	 * ����id��ȡ���Ӧ�����
	 * 
	 * @param categoryId
	 * @return Category
	 */
//	public static Category getCategoryById(int categoryId) {
//		Category category = new Category();
//		Connection conn = null;
//		PreparedStatement prepstmt = null;
//		ResultSet rs = null;
//		String sql = "select * from category where id=?";
//		try {
//			conn = DatabaseBean.getConnection();
//			conn.setAutoCommit(false);
//			prepstmt = conn.prepareStatement(sql);
//			prepstmt.setInt(1, categoryId);
//			rs = prepstmt.executeQuery();
//			while (rs.next()) {
//				category.setId(rs.getInt("id"));
//				category.setName(rs.getString("name"));
//				category.setIntroduce(rs.getString("introduce"));
//				category.setOpen(rs.getInt("open"));
//				category.setUrl(rs.getString("url"));
//				category.setUserId(rs.getInt("userid"));
//				category.setAddTime(StringUtil.changeTimestamp(rs
//						.getTimestamp("addtime")));
//				category.setPhotoNum(PhotoBean.getPhotoNum(categoryId, 0));
//			}
//			conn.commit();
//			conn.setAutoCommit(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			category = null;
//		} finally {
//			DatabaseBean.close(conn, prepstmt, rs);
//		}
//		return category;
//	}

	/**
	 * �޸�ĳ������Ĵ�����Ƭ
	 * 
	 * @param int
	 *            id
	 * @param int
	 *            categoryId
	 * @return boolean
	 */
	public static boolean updateCategoryUrl(int id, int categoryId) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "update category set url=? where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, PhotoBean.getPhoto(id).getUrl());
			prepstmt.setInt(2, categoryId);
			ok = (1 == prepstmt.executeUpdate());
			conn.commit();
			conn.setAutoCommit(true);
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
	 * �������
	 * 
	 * @param album
	 * @return
	 */
	public static boolean updateAlbum(Album album) {
		System.out.println("Ҫ���µ����id: " + album.getId());
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "update album set name=?,introduce=?,open=? where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, album.getName());
			prepstmt.setString(2, album.getIntroduce());
			prepstmt.setInt(3, album.getOpen());
			prepstmt.setInt(4, album.getId());
			ok = (1 == prepstmt.executeUpdate());
			conn.commit();
			conn.setAutoCommit(true);
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
	 * �������
	 * 
	 * @param Category
	 * @return boolean
	 */
//	public static boolean updateCategory(Category category) {
//		boolean ok = true;
//		Connection conn = null;
//		PreparedStatement prepstmt = null;
//		String sql = "update category set name=?,introduce=?,open=? where id=?";
//		try {
//			conn = DatabaseBean.getConnection();
//			conn.setAutoCommit(false);
//			prepstmt = conn.prepareStatement(sql);
//			prepstmt.setString(1, category.getName());
//			prepstmt.setString(2, category.getIntroduce());
//			prepstmt.setInt(3, category.getOpen());
//			prepstmt.setInt(4, category.getId());
//			ok = (1 == prepstmt.executeUpdate());
//			conn.commit();
//			conn.setAutoCommit(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			ok = false;
//		} finally {
//			DatabaseBean.close(conn, prepstmt, null);
//		}
//		return ok;
//	}

	public static boolean clearAlbums(List<Album> albums) {
		boolean ok = true;
		for (int i = 0; i < albums.size(); i++) {
			ok = PhotoBean.deletePhotoByAlbumId(albums.get(i).getId());
			if (ok) System.out.println("�ɹ�������albumId="+albums.get(i).getId()); 
			else return false;
		}
		return ok;
	}
}
