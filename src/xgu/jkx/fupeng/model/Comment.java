/**
 * ����
 */
package xgu.jkx.fupeng.model;

/**
 * 
 * @author FuPeng
 * 
 * Jul 19, 2009
 */

public class Comment {
	private int id; // ����id
	private String content; // ��������
	private String addTime; // ����ʱ��
	private String fromUser; // �������۵��û���
	private int photoId; // ���۵�ͼƬid
	private String url; // �����ߵ�ͷ��
	private int userId; // �����ߵ��û�Id
	private Photo photo; // ͼƬ����

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
