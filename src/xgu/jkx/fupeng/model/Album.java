package xgu.jkx.fupeng.model;

/**
 * 相册类
 * 
 * @author FuPeng
 * 
 */
public class Album {
	private int id; // 相册id
	private String name; // 相册名称
	private String introduce; // 相册介绍
	private String addTime; // 相册创建时间
	private String username; // 相册主人的username
	private int userid; // 相册主人的userid
	private String url; // 相册封面图片的url
	private int open; // 相册浏览权限，1：公开 0：私有
	private int skimNum; // 相册被浏览次数
	private int photoNum; // 相册里面图片的数量

	public Album() {
	}

	public Album(int id, String name, String introduce, String addTime,
			String username, String url, int open, int skimNum, int photoNum) {
		this.id = id;
		this.name = name;
		this.introduce = introduce;
		this.addTime = addTime;
		this.username = username;
		this.url = url;
		this.open = open;
		this.skimNum = skimNum;
		this.photoNum = photoNum;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public int getSkimNum() {
		return skimNum;
	}

	public void setSkimNum(int skimNum) {
		this.skimNum = skimNum;
	}

	public int getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(int photoNum) {
		this.photoNum = photoNum;
	}

}
