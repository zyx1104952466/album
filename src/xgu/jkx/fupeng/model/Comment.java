/**
 * 评论
 */
package xgu.jkx.fupeng.model;

/**
 * 
 * @author FuPeng
 * 
 * Jul 19, 2009
 */

public class Comment {
	private int id; // 评论id
	private String content; // 评论内容
	private String addTime; // 评论时间
	private String fromUser; // 发表评论的用户名
	private int photoId; // 评论的图片id
	private String url; // 评论者的头像
	private int userId; // 评论者的用户Id
	private Photo photo; // 图片对象

	public Comment(int id, String content, String addTime, String fromUser,
			int photoId, Photo photo) {
		super();
		this.id = id;
		this.content = content;
		this.addTime = addTime;
		this.fromUser = fromUser;
		this.photoId = photoId;
		this.photo = photo;
	}

	public Comment(int id, String content, String addTime, String fromUser,
			int photoId) {
		super();
		this.id = id;
		this.content = content;
		this.addTime = addTime;
		this.fromUser = fromUser;
		this.photoId = photoId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Photo getPhoto() {
		return photo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Comment() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
}
