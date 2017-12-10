package xgu.jkx.fupeng.model;

/**
 * �����
 * 
 * @author FuPeng
 * 
 */
public class Album {
	private int id; // ���id
	private String name; // �������
	private String introduce; // ������
	private String addTime; // ��ᴴ��ʱ��
	private String username; // ������˵�username
	private int userid; // ������˵�userid
	private String url; // ������ͼƬ��url
	private int open; // ������Ȩ�ޣ�1������ 0��˽��
	private int skimNum; // ��ᱻ�������
	private int photoNum; // �������ͼƬ������

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
