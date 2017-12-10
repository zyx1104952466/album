/**
 * �û���Ϣ
 */
package xgu.jkx.fupeng.model;

import java.util.List;


/**
 * 
 * @author FuPeng
 * 
 * Jul 19, 2009
 */

public class User {
	private int id; // �û�id
	private String username; // �û�����
	private String password; // ����
	private String nickName; // �ǳ�
	private String addTime; // ����ʱ��
	private int active; // �û���Ծ��
	private String url; // �û�ͷ��
	private String email; // �ʼ���ַ
	private int albumNum; // �û�ӵ��������
	private int photoNum; // �û�ӵ����Ƭ����
	private List<Photo> hotPhotos; // ������Ƭ

	public User(int id, String username, String nickName, String addTime,
			String url, String email, int active) {
		super();
		this.id = id;
		this.username = username;
		this.nickName = nickName;
		this.addTime = addTime;
		this.url = url;
		this.email = email;
		this.active = active;
	}

	public List<Photo> getHotPhotos() {
		return hotPhotos;
	}

	public void setHotPhotos(List<Photo> hotPhotos) {
		this.hotPhotos = hotPhotos;
	}

	public int getAlbumNum() {
		return albumNum;
	}

	public void setAlbumNum(int albumNum) {
		this.albumNum = albumNum;
	}

	public int getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(int photoNum) {
		this.photoNum = photoNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
