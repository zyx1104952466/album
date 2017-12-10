package xgu.jkx.fupeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import net.sf.json.JSONObject;
import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.http.ServletEncoding;
import xgu.jkx.fupeng.model.Comment;
import xgu.jkx.fupeng.util.StringUtil;

public class PubCommentAction extends MySuperAction {

	private static final long serialVersionUID = 4755246116349335897L;
	private int photoId;
	private String content;
	private String url;
	private String fromuser;
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFromuser() {
		return fromuser;
	}

	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String execute() {
		ServletEncoding.setEncoding(request, response);
		
		System.out.println("fromuser=" + fromuser);
		System.out.println("photoId=" + photoId);
		System.out.println("content=" + content);
		System.out.println("url=" + url);

		Comment comment = new Comment();
		comment.setFromUser(fromuser);
		comment.setContent(content);
		comment.setPhotoId(photoId);
		comment.setUrl(url);
		comment.setUserId(userId);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		comment.setAddTime(StringUtil.changeTimestamp(time));
		
		CommentBean.addComment(comment);
		if (userId != 0)
			UserBean.addActive(userId, 2);
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.put("comment", comment);
		out.print(json.toString());
		out.flush();
		out.close();
		return null;
	}
}
