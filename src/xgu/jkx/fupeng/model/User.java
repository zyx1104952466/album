/**
 * 用户信息
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
	private int id; // 用户id
	private String username; // 用户名称
	private String password; // 密码
	private String nickName; // 昵称
	private String addTime; // 增加时间
	private int active; // 用户活跃度
	private String url; // 用户头像
	private String email; // 邮件地址
	private int albumNum; // 用户拥有相册个数
	private int photoNum; // 用户拥有相片张数
	private List<Photo> hotPhotos; // 热门相片

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
