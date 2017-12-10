package xgu.jkx.fupeng.action;

import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PageInfo;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.Photo;
import xgu.jkx.fupeng.model.User;

public class ShowPhotosAction extends MySuperAction {

	private static final long serialVersionUID = 46301460566969681L;

	private int albumId; // 相册号
	private int allNum; // 总记录数
	private int num; // 每页个数
	private int pageNum; // 总页数
	private int currentPage; // 当前页码
	private int itemNum; // 当前页个数

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String execute() throws Exception {
	
		num = 8;
		allNum = PhotoBean.getPhotoNum(albumId);
		pageNum = (allNum - 1) / num + 1;

		User user = (User) session.get("user");
		List<Photo> myPhotos;
		if (user == null) {
			myPhotos = PhotoBean.getPhotos(currentPage * num, num,
					this.albumId, 0);
		} else {
			if (UserBean.holdTheAlbum(user.getId(), albumId)) {
				myPhotos = PhotoBean.getPhotos(currentPage * num, num,
						this.albumId, 1);
			} else {
				myPhotos = PhotoBean.getPhotos(currentPage * num, num,
						this.albumId, 0);
			}
		}
		request.setAttribute("topComment", CommentBean.getLastestComments(10)); // 取得10条最新评论
		if (albumId != 0) // 增加一次相册的浏览量
			AlbumBean.updateSkimnum(albumId, 1);
		this.itemNum = myPhotos.size();
		PageInfo pageInfo = new PageInfo(allNum, num, pageNum, currentPage,
				itemNum);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("myPhotos", myPhotos);

		if (albumId == 0) {
			if (user == null) {
				return "nologin";
			} else
				return "login";
		}

		if (myPhotos.size() == 0)
			request.setAttribute("myPhotos", null);
		if (user == null) {
			return "nologin";
		} else {
			if (UserBean.holdTheAlbum(user.getId(), albumId)) {
				return "login";
			} else {
				return "nologin";
			}
		}
	}
}
