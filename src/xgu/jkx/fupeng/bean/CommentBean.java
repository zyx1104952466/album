package xgu.jkx.fupeng.bean;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

import xgu.jkx.fupeng.dao.*;
import xgu.jkx.fupeng.model.*;
import xgu.jkx.fupeng.util.*;

public class CommentBean {

	public CommentBean() {
	}

	public static List<Comment> getCommentsByUsername(String username, int start,
			int count) {
		List<Comment> commentList = new ArrayList<Comment>();
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select id,content,addtime,fromuser,photo_id, url from comment where photo_id in (select p.id from photo p, album a, user u where u.id=a.user_id and p.album_id=a.id and u.username=?) order by addtime desc limit ?,?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, username);
			prepstmt.setInt(2, start);
			prepstmt.setInt(3, count);
			rs = prepstmt.executeQuery();
			Comment comment;
			while (rs.next()) {
				comment = new Comment();
				comment.setId(rs.getInt(1));
				comment.setContent(rs.getString(2));
				comment.setAddTime(StringUtil.changeTimestamp(rs
						.getTimestamp(3)));
				comment.setFromUser(rs.getString(4));
				comment.setPhotoId(rs.getInt(5));
				comment.setUrl(rs.getString(6));
				commentList.add(comment);
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
			commentList = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return commentList;
	}

	public static List<Comment> getLastestComments(int count) {
		List<Comment> commentList = new ArrayList<Comment>();
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select id,content,addtime,fromuser,photo_id from comment order by addtime desc limit 0,?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, count);
			rs = prepstmt.executeQuery();
			Comment comment;
			while (rs.next()) {
				comment = new Comment();
				comment.setId(rs.getInt(1));
				comment.setContent(rs.getString(2));
				comment.setAddTime(StringUtil.changeTimestamp(rs
						.getTimestamp(3)));
				comment.setFromUser(rs.getString(4));
				comment.setPhotoId(rs.getInt(5));
				commentList.add(comment);
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
			commentList = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return commentList;
	}

	/**
	 * �������
	 */
	public static boolean addComment(Comment comment) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "insert into comment(fromuser,content,photo_id,url) values(?,?,?,?)";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, comment.getFromUser());
			prepstmt.setString(2, comment.getContent());
			prepstmt.setInt(3, comment.getPhotoId());
			prepstmt.setString(4, comment.getUrl());
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

	public static List<Comment> getCommentsByPhotoId(int photoId) {
		List<Comment> commentList = new ArrayList<Comment>();
		Comment comment = null;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			sql = "select c.id,c.content,c.addtime,c.fromuser,c.photo_id,c.url,u.id from comment c left join user u on c.fromuser=u.username where photo_id=?  order by c.addtime desc";
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setId(rs.getInt(1));
				comment.setContent(rs.getString(2));
				comment.setAddTime(StringUtil.changeTimestamp(rs
						.getTimestamp(3)));
				comment.setFromUser(rs.getString(4));
				comment.setPhotoId(rs.getInt(5));
				comment.setUrl(rs.getString(6));
				comment.setUserId(rs.getInt(7));
				commentList.add(comment);
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
			commentList = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return commentList;
	}

	/**
	 * ��ȡ��ƬID��Ӧ��start��ʼ��num������
	 */
	public static List<Comment> getComments(int start, int num, int photoId,
			int userId) {
		List<Comment> commentList = new ArrayList<Comment>();
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			if (userId == 0) {
				// ͨ����ƬID��ȡ����
				sql = "select * from comment where photoid=? order by addtime desc limit ?,?";
				prepstmt = conn.prepareStatement(sql);
				prepstmt.setInt(1, photoId);
				prepstmt.setInt(2, start);
				prepstmt.setInt(3, num);
				rs = prepstmt.executeQuery();
				while (rs.next()) {
					Comment comment = getCommentById(rs.getInt("id"));
					commentList.add(comment);
				}
			} else if (photoId == 0) {
				// ͨ���û�id����ȡ����
				sql = "select comment.id,comment.adduser,comment.content,comment.addtime,comment.photoid from comment,photo,category,userinfo where comment.photoid = photo.id and photo.categoryid = category.id and category.userid = userinfo.id and userinfo.id=? order by addtime desc limit ?,?";
				prepstmt = conn.prepareStatement(sql);
				prepstmt.setInt(1, userId);
				prepstmt.setInt(2, start);
				prepstmt.setInt(3, num);
				rs = prepstmt.executeQuery();
				while (rs.next()) {
					Comment comment = getCommentById(rs.getInt(1));
					commentList.add(comment);
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
			e.printStackTrace();
			commentList = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return commentList;
	}

	/**
	 * ͨ��id��ȡcomment
	 */
	public static Comment getCommentById(int id) {
		Comment comment = new Comment();
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select * from comment where id=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, id);
			rs = prepstmt.executeQuery();
			while (rs.next()) {
				comment.setId(rs.getInt("id"));
				comment.setFromUser(rs.getString("adduser"));
				comment.setContent(rs.getString("content"));
				comment.setAddTime(StringUtil.changeTimestamp(rs
						.getTimestamp("addtime")));
				comment.setPhotoId(rs.getInt("photoid"));
				// comment.setPhoto(PhotoBean.getPhoto(rs.getInt("photoid")));
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
			comment = null;
		} finally {
			DatabaseBean.close(conn, prepstmt, rs);
		}
		return comment;
	}

	/**
	 * ��ȡphotoId��Ӧ��������Ŀ
	 */
	public static int getCommentNumByPhotoId(int photoId) {
		int num = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from comment where photoId=?";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setInt(1, photoId);
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
	 * ��ѯ�û���õ�������Ŀ
	 */
	public static int getCommentNum(String username) {
		int num = 0;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		String sql = "select count(id) from comment where photo_id in (select p.id from photo p, album a, user u where u.id=a.user_id and p.album_id=a.id and u.username=?)";
		try {
			conn = DatabaseBean.getConnection();
			conn.setAutoCommit(false);
			prepstmt = conn.prepareStatement(sql);
			prepstmt.setString(1, username);
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
	 * �������۵�Idɾ��
	 */
	public static boolean deleteCommentById(int id) {
		boolean ok = true;
		Connection conn = null;
		PreparedStatement prepstmt = null;
		String sql = "delete from comment where id=?";
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
			ok = false;
		} finally {
			DatabaseBean.close(conn, prepstmt, null);
		}
		return ok;
	}


/**
 * ɾ���ض��û�������
 */
public static boolean deleteCommentByUsername(String username) {
	boolean ok = true;
	Connection conn = null;
	PreparedStatement prepstmt = null;
	String sql = "delete from comment where fromuser=?";
	try {
		conn = DatabaseBean.getConnection();
		conn.setAutoCommit(false);
		prepstmt = conn.prepareStatement(sql);
		prepstmt.setString(1, username);
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
		ok = false;
	} finally {
		DatabaseBean.close(conn, prepstmt, null);
	}
	if (ok) System.out.println("-----------����û���������۳ɹ�------------");
	return ok;
}

}
