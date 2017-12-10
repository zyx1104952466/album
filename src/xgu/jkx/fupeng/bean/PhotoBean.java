/**
 * ��Ƭ
 */
package xgu.jkx.fupeng.bean;

import java.util.*;
import java.sql.*;
import java.sql.Date;

import xgu.jkx.fupeng.dao.*;
import xgu.jkx.fupeng.model.*;
import xgu.jkx.fupeng.util.*;

/**
 * 
 * @author FuPeng
 * 
 * Jul 19, 2009
 */

public class PhotoBean {

	/**
	 * ������Ƭ
	 * 
	 * @param keyword
	 * @param start
	 * @param count
	 * @return List<Photo>
	 */
	public static List<Photo> search(int start, int count, String keyword) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Photo> photoList = new ArrayList<Photo>();
System.out.println("Start Number:"+start);
System.out.println("Length:"+count);
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			String sql = "select p.id,p.url,u.username,p.skimnum,p.name,p.descr from photo p,album a,user u where p.name like '"
					+ keyword
					+ "%' and p.album_id=a.id and a.user_id=u.id and p.album_id not in (select id from album where open=0) order by p.name limit "
					+ start + "," + count;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Photo photo = null;
			while (rs.next()) {
				photo = new Photo();
				photo.setId(rs.getInt(1));
				photo.setUrl(rs.getString(2));
				photo.setFromUser(rs.getString(3));
				photo.setSkimNum(rs.getInt(4));
				photo.setName(rs.getString(5));
				photo.setDescr(rs.getString(6));
				photoList.add(photo);
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
			photoList = null;
		} finally {
			DatabaseBean.close(conn, stmt, rs);
		}
		if (photoList.size() == 0) {
			return null;
		} else
			return photoList;
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
		String sql = "select count(id) from photo where name like '" + keyword
				+ "%' and album_id not in (select id from album where open=0)";
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
	 * ��Ƭ���������ܲ�����ʾ
	 * 
	 * @param keyword
	 * @return
	 */
	public static List<String> searchSuggest(String keyword) {
		List<String> strList = new ArrayList<String>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select name from photo where name like '" + keyword
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

	public static boolean updatePhoto(String name, String descr, int photoId) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "update photo set name=?,descr=? where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, name);
			prepstmt.setString(2, descr);
			prepstmt.setInt(3, photoId);
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
			e.printStackTrace();
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return ok;

	}

	public static String getPhotoUrlById(int photoId) {
		String url = null;
		String sql = "select url from photo where id = ?";
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				url = rs.getString(1);
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
			url = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return url;
	}

	public static int getHotPhotoNum() {

		String sql = "select count(*) from photo p,album a,user u where p.album_id=a.id and a.user_id=u.id and p.album_id not in (select id from album where open=0) ";
		int num = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
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
	 * ���idΪ0���ѯ�������û�ͼƬ��������������albumId��ѯ�ƶ���������ͼƬ����
	 * 
	 * @param albumId
	 * @return
	 */
	public static int getPhotoNum(int albumId) {
		int num = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql;
		if (albumId == 0) {
			sql = "select count(*) from photo";
		} else
			sql = "select count(*) from photo where album_id=" + albumId;
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
		} catch (Exception e) {
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

	public static int getPhotoNumByUserId(int userId) {
		int num = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from photo p,album a,user u where p.album_id=a.id and a.user_id=u.id and u.id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, userId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt(1);
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
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return num;
	}

	/**
	 * ͨ���û�id�õ�������Ƭ
	 * 
	 * @param statr
	 * @param count
	 * @return
	 */
	public static List<Photo> getHotPhotosById(int userId, int start, int count) {
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		List<Photo> photoList = new ArrayList<Photo>();
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			String sql = "select p.id,p.url,p.name "
					+ "from photo p,album a,user u "
					+ "where p.album_id=a.id "
					+ "and a.user_id=u.id "
					+ "and p.album_id not in (select id from album where open=0) "
					+ "and u.id=? "
					+ "order by p.fine desc,p.skimnum desc limit ?,?";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, userId);
			prepstmt.setInt(2, start);
			prepstmt.setInt(3, count);
			rs = prepstmt.executeQuery();

			Photo photo = null;
			while (rs.next()) {
				photo = new Photo();
				photo.setId(rs.getInt(1));
				photo.setUrl(rs.getString(2));
				photo.setName(rs.getString(3));
				photoList.add(photo);
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
			photoList = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		// for (int i = 0; i < 3; i++) {
		// photoList.add(new Photo(i, "��Ƭ" + i, "images/default/nopic.jpg",
		// "admin", 63));
		// }
		return photoList;
	}

	/**
	 * �õ�������Ƭ
	 * 
	 * @param statr
	 * @param count
	 * @return
	 */
	public static List<Photo> getHotPhotos(int start, int count) {
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		List<Photo> photoList = new ArrayList<Photo>();
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			String sql = "select p.id,p.url,u.username,p.skimnum,p.name from photo p,album a,user u where p.album_id=a.id and a.user_id=u.id and p.album_id not in (select id from album where open=0) order by p.skimnum desc,p.fine desc limit ?,?";

			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, start);
			prepstmt.setInt(2, count);
			rs = prepstmt.executeQuery();

			Photo photo = null;
			while (rs.next()) {
				photo = new Photo();
				photo.setId(rs.getInt(1));
				photo.setUrl(rs.getString(2));
				photo.setFromUser(rs.getString(3));
				photo.setSkimNum(rs.getInt(4));
				photo.setName(rs.getString(5));
				photoList.add(photo);
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
			photoList = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return photoList;
	}

	/**
	 * �����Ƭ
	 */
	public static boolean addPhoto(Photo photo) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "insert into photo(name,introduce,url,addtime,album_id) values(?,?,?,?)";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, photo.getName());
			prepstmt.setString(2, photo.getUrl());
			prepstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			prepstmt.setInt(4, photo.getAlbumId());
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
	 * �����Ƭ
	 */
	public static boolean addPhoto(String name, String url, int albumId) {
		System.out.println(name);
		System.out.println(url);
		System.out.println("album_id=" + albumId);
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "insert into photo(name,url,addtime,album_id) values(?,?,?,?)";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, name);
			prepstmt.setString(2, url);
			prepstmt.setDate(3, new Date(System.currentTimeMillis()));
			prepstmt.setInt(4, albumId);
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

		if (ok)
			AlbumBean.updatePhotonum(albumId, 1);

		return ok;
	}

	/**
	 * �ж���Ƭ���� id��userid�Ƿ��Ӧ
	 */
	public static boolean isRightUser(int id, int userId) {
		@SuppressWarnings("unused")
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select count(photo.id) from photo,category,userinfo where photo.id=? photo.categoryid=category.id and category.userid=userinfo.id and userinfo.id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, id);
			prepstmt.setInt(2, userId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 0) {
					// û�н��,��ƥ��
					ok = false;
				}
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
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return false;
	}

	public static List<Photo> getPhotos(int start, int num, int categoryId,
			int userId, int a) {
		List<Photo> list = new ArrayList<Photo>();
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			if (userId == 0) {
				sql = "select * from photo where categoryid=? order by addtime desc limit ?,? ";
				prepstmt = conn.prepareStatement(sql);
				prepstmt.setInt(1, categoryId);
				prepstmt.setInt(2, start);
				prepstmt.setInt(3, num);
				rs = prepstmt.executeQuery();
				Photo photo;
				while (rs.next()) {
					photo = new Photo();
					photo.setId(rs.getInt("id"));
					photo.setName(rs.getString("name"));
					photo.setUrl(rs.getString("url"));
					photo.setAddTime(StringUtil.changeTimestamp(rs
							.getTimestamp("addtime")));
					photo.setAlbumId(rs.getInt("categoryid"));
					photo.setSkimNum(rs.getInt("skimnum"));
//					photo.setCategory(CategoryBean.getCategoryById(categoryId));
					list.add(photo);
				}
			} else {
				sql = "select photo.id,photo.name,photo.url,photo.addtime,photo.categoryid,photo.skimnum from photo,category,userinfo where photo.categoryid=category.id and category.userid=userinfo.id and userinfo.id=? order by addtime desc limit ?,? ";
				prepstmt = conn.prepareStatement(sql);
				prepstmt.setInt(1, userId);
				prepstmt.setInt(2, start);
				prepstmt.setInt(3, num);
				rs = prepstmt.executeQuery();
				Photo photo;
				while (rs.next()) {
					photo = new Photo();
					photo.setId(rs.getInt(1));
					photo.setName(rs.getString(2));
					photo.setUrl(rs.getString(3));
					photo.setAddTime(StringUtil.changeTimestamp(rs
							.getTimestamp(4)));
					photo.setAlbumId(rs.getInt(5));
					photo.setSkimNum(rs.getInt(6));
//					photo.setCategory(CategoryBean
//							.getCategoryById(rs.getInt(5)));
					/*
					 * photo.setId(rs.getInt("photo.id"));
					 * photo.setName(rs.getString("photo.name"));
					 * photo.setUrl(rs.getString("photo.url"));
					 * photo.setAddTime(StringUtil.changeTimestamp(rs.getTimestamp("photo.addtime")));
					 * photo.setCategoryId(rs.getInt("photo.categoryid"));
					 * photo.setSkimNum(rs.getInt("photo.skimnum"));
					 * photo.setCategory(CategoryBean.getCategoryById(rs.getInt("photo.categoryid")));
					 */
					list.add(photo);
				}
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
			list = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return list;
	}

	/**
	 * ��ȡͼƬ�б�
	 * 
	 * @param start
	 * @param num
	 * @param albumId
	 * @return
	 */
	public static List<Photo> getPhotos(int start, int count, int albumId,
			int type) {
		// typeΪ0��ʱ�򲻻�ȡ˽��ͼƬ�������ȡȫ��ͼƬ
		String sql;
		List<Photo> photoList = new ArrayList<Photo>();
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			if (type == 0) {
				sql = "select p.descr,a.id album_id,a.name album_name,u.username,p.id,p.name,p.url,p.addtime,p.skimnum from photo p,album a,user u where p.album_id=a.id and a.user_id=u.id and p.album_id=? and p.album_id not in (select id from album where open=0) order by addtime desc limit ?,?";
				prepstmt = conn.prepareStatement(sql);
				prepstmt.setInt(1, albumId);
				prepstmt.setInt(2, start);
				prepstmt.setInt(3, count);
			} else {
				sql = "select p.descr,a.id album_id,a.name album_name,u.username,p.id,p.name,p.url,p.addtime,p.skimnum from photo p,album a,user u where p.album_id=a.id and a.user_id=u.id and album_id = ? order by addtime desc limit ?,?";
				prepstmt = conn.prepareStatement(sql);
				prepstmt.setInt(1, albumId);
				prepstmt.setInt(2, start);
				prepstmt.setInt(3, count);
			}
			rs = prepstmt.executeQuery();
			Photo photo;
			while (rs.next()) {
				photo = new Photo();
				photo.setDescr(rs.getString("descr"));
				photo.setFromAlbum(rs.getString("album_name"));
				photo.setFromUser(rs.getString("username"));
				photo.setAlbumId(rs.getInt("album_id"));
				photo.setId(rs.getInt("id"));
				photo.setUrl(rs.getString("url"));
				photo.setName(rs.getString("name"));
				photo
						.setAddTime(StringUtil.dateToString(rs
								.getDate("addtime")));

				photo.setSkimNum(rs.getInt("skimnum"));
				photoList.add(photo);
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
			photoList = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return photoList;
	}

//	public static List<Photo> getPhotos(int start, int num) {
//		List<Photo> list = new ArrayList<Photo>();
//		Connection conn = null;
//		PreparedStatement prepstmt = null;
//		ResultSet rs = null;
//		String sql = "select * from photo order by addtime desc limit ?,? ";
//		try {
//			conn = DatabaseBean.getConnection();
//			conn.setAutoCommit(false);
//			prepstmt = conn.prepareStatement(sql);
//			prepstmt.setInt(1, start);
//			prepstmt.setInt(2, num);
//			rs = prepstmt.executeQuery();
//			Photo photo;
//			while (rs.next()) {
//				photo = new Photo();
//				photo.setId(rs.getInt("id"));
//				photo.setName(rs.getString("name"));
//				photo.setUrl(rs.getString("url"));
//				photo.setAddTime(StringUtil.changeTimestamp(rs
//						.getTimestamp("addtime")));
//				photo.setAlbumId(rs.getInt("categoryid"));
//				photo.setSkimNum(rs.getInt("skimnum"));
//				photo.setCategory(CategoryBean.getCategoryById(rs
//						.getInt("categoryid")));
//				list.add(photo);
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
//			list = null;
//		} finally {
//			DatabaseBean.close(conn, prepstmt, rs);
//		}
//		return list;
//	}

	/**
	 * ��ȡ��Ƭ����,����categoryID��userID��ȡ��Ƭ����
	 */
	public static int getPhotoNum(int categoryId, int userId) {
		int num = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);

			if (categoryId == 0) { // ���id��Ч�����û�id�����е�ͼƬ����
				sql = "select count(photo.id) from photo,category,userinfo where photo.categoryid=category.id and category.userid=userinfo.id and userinfo.id=?";
				prepstmt = conn.prepareStatement(sql);
				prepstmt.setInt(1, userId);
			} else {
				if (userId == 0) { // �û�id��Ч�������id������ͼƬ����
					// sql="select count(photo.id) from photo,category,userinfo
					// where photo.categoryid=category.id and
					// category.userid=userinfo.id and and category.id=?";
					sql = "select count(id) from photo where categoryid=?";
					prepstmt = conn.prepareStatement(sql);
					prepstmt.setInt(1, categoryId);
				} else { // ���id���û�id����Ч�Ͳ�ѯ�û���Ӧ�����������ͼƬ����
					sql = "select count(photo.id) from photo,category,userinfo where photo.categoryid=category.id and category.userid=userinfo.id and userinfo.id=? and category.id=?";
					prepstmt = conn.prepareStatement(sql);
					prepstmt.setInt(1, userId);
					prepstmt.setInt(2, categoryId);
				}
			}
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt(1);
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
			num = 0;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return num;
	}

	/**
	 * ������Ƭidɾ����Ƭ
	 * @param photoId
	 * @return
	 */
	public static boolean deletePhotoById(int photoId) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "delete from photo where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
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
			e.printStackTrace();
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return ok;
	}
	
	/**
	 * ɾ��ָ�����id�µ�ͼƬ
	 */
	public static boolean deletePhotoByAlbumId(int albumId) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "delete from photo where album_id=?";
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
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return ok;
	}

	/**
	 * ��Ƭ�Ƿ�Ϊ������
	 * 
	 * @return
	 */
	public static boolean isAlbumFace(int photoId) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs;
		System.out.println("Ҫ�жϵ���Ƭid�ǣ�" + photoId);
		String sql = "select a.url a_url,p.url p_url from photo p,album a where a.id = p.album_id and p.id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
			rs = prepstmt.executeQuery();
			conn.commit();
			while (rs.next()) {
				System.err.println("album ����url:" + rs.getString("a_url"));
				System.out.println("��Ƭurl:" + rs.getString("p_url"));
				if (rs.getString("a_url").equals(rs.getString("p_url"))) {
					flag = true;
				} else
					flag = false;
			}
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			flag = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return flag;
	}

	/**
	 * ͨ��id��ȡPhoto����
	 * 
	 * @param args
	 */
	public static Photo getPhoto(int photoId) {
		Photo photo = null;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select id,name,url,addtime,album_id,skimnum,fine from photo where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				photo = new Photo();
				photo.setId(rs.getInt("id"));
				photo.setName(rs.getString("name"));
				photo.setUrl(rs.getString("url"));
				photo
						.setAddTime(StringUtil.dateToString(rs
								.getDate("addtime")));
				photo.setAlbumId(rs.getInt("album_id"));
				photo.setSkimNum(rs.getInt("skimnum"));
				photo.setFine(rs.getInt("fine"));
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
			e.printStackTrace();
			photo = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return photo;
	}

	/**
	 * ����ͼƬid��ȡ��һ��ͼƬ����id����ȡ������Ϊ1
	 * 
	 * @param photoId
	 * @return Photo
	 */
	public static Photo getNextPhoto(int categoryId, int photoId) {
		Photo photo = null;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select * from photo where categoryid=? and id>? order by id limit 0,1";
		// System.out.println(sql);
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, categoryId);
			prepstmt.setInt(2, photoId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				photo = new Photo();
				photo.setId(rs.getInt("id"));
				photo.setName(rs.getString("name"));
				photo.setUrl(rs.getString("url"));
				photo.setAddTime(StringUtil.changeTimestamp(rs
						.getTimestamp("addtime")));
				photo.setAlbumId(rs.getInt("categoryid"));
				photo.setSkimNum(rs.getInt("skimnum"));
//				photo.setCategory(CategoryBean.getCategoryById(rs
//						.getInt("categoryid")));
				// PhotoBean.updatePhotoSkim(photo.getId());
				// photo.setArrayComment(CommentBean.getComments(0, 5,
				// rs.getInt("id"), 0));
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
			e.printStackTrace();
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return photo;
	}

	/**
	 * ����ͼƬphotoId������albumId��ȡ��һ��ͼƬ
	 * 
	 * @param albumId
	 * @param photoId
	 * @return
	 */
	public static Photo getNextPic(int albumId, int photoId) {
		Photo photo = null;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select p.id,p.name,p.url,p.addtime,p.skimnum,p.fine from album a,photo p where a.id=p.album_id and p.id in (select p.id from photo p,album a where p.album_id=a.id and p.id>? and "
				+ "a.id=? order by p.id ) limit 0,1";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
			prepstmt.setInt(2, albumId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				photo = new Photo();
				System.out.println("��һ�ŵ�photoId ---" + rs.getInt("id") + "---");
				photo.setId(rs.getInt("id"));
				photo.setName(rs.getString("name"));
				photo.setUrl(rs.getString("url"));
				photo
						.setAddTime(StringUtil.dateToString(rs
								.getDate("addtime")));

				photo.setSkimNum(rs.getInt("skimnum"));
				photo.setFine(rs.getInt("fine"));
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			photo = null;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return photo;
	}

	/**
	 * �޸���Ƭ�������
	 * 
	 * @param args
	 */
	public static boolean updatePhotoSkim(int id) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "update photo set skimnum=skimnum+1 where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, id);
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
			e.printStackTrace();
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return ok;
	}

	/**
	 * �޸���Ƭ��֧�ֵĴ���
	 * 
	 * @param args
	 */
	public static boolean updateFine(int photoId) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "update photo set fine=fine+1 where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
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
			e.printStackTrace();
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return ok;
	}

	/**
	 * �޸���Ƭ��Ϣ
	 * 
	 * @param args
	 */
	public static boolean updatePhoto(Photo photo) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "update photo set name=?,categoryid=? where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, photo.getName());
			prepstmt.setInt(2, photo.getAlbumId());
			prepstmt.setInt(3, photo.getId());
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
			e.printStackTrace();
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return ok;
	}

}
