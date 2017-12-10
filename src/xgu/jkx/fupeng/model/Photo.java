/**
 * 图片
 */
package xgu.jkx.fupeng.model;

import java.util.List;

/**
 * 
 * @author FuPeng
 * 
 * Jul 19, 2009
 */

public class Photo {
	private int id; // 图片id
	private String name; // 图片名称
	private String fromUser; // 图片所属的用户名
	private String fromAlbum; // 图片所属的相册名
	private String url; // 大图
	private String addTime; // 图片添加的时间
	private int skimNum; // 图片浏览次数
	private int albumId; // 图片类别id
	private int fine; // 图片被推荐次数
	private String descr; //图片描述
//	private Category category; // 图片类别
	private List<Comment> arrayComment; // 图片所有的评论

	public Photo(int id, String name, String url, String fromUser, int skimNum) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.fromUser = fromUser;
		this.skimNum = skimNum;
	}
	
	

	public String getDescr() {
		return descr;
	}



	public void setDescr(String descr) {
		this.descr = descr;
	}



	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}

	public String getFromAlbum() {
		return fromAlbum;
	}

	public void setFromAlbum(String fromAlbum) {
		this.fromAlbum = fromAlbum;
	}

	public List<Comment> getArrayComment() {
		return arrayComment;
	}

	public void setArrayComment(List<Comment> arrayList) {
		this.arrayComment = arrayList;
	}

//	public Category getCategory() {
//		return category;
//	}
//
//	public void setCategory(Category category) {
//		this.category = category;
//	}

	public Photo() {

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public int getSkimNum() {
		return skimNum;
	}

	public void setSkimNum(int skimNum) {
		this.skimNum = skimNum;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

}
