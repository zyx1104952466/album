package xgu.jkx.fupeng.action;

import java.util.ArrayList;
import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PageInfo;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.Photo;
import xgu.jkx.fupeng.model.User;

public class HotUsersAction extends MySuperAction{
	
	private static final long serialVersionUID = -2700226829279640012L;

	private int allNum = UserBean.getUserNum(); // 总记录数
	private int num = 8; // 每页个数
	private int pageNum = (allNum - 1) / num + 1; // 总页数
	private int currentPage; // 当前页码
	private int itemNum; // 当前页个数

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String execute() {
		List<User> hotUser = UserBean.getHotUsers(currentPage
				* num, num);
		for(int i=0; i<hotUser.size(); i++) {
			List<Photo> hotPhotos = new ArrayList<Photo>();
			hotPhotos = PhotoBean.getHotPhotosById(hotUser.get(i).getId(), 0, 3);

			hotUser.get(i).setHotPhotos(hotPhotos);
			hotUser.get(i).setPhotoNum(PhotoBean.getPhotoNumByUserId(hotUser.get(i).getId()));
			hotUser.get(i).setAlbumNum(AlbumBean.getAlbumNum(hotUser.get(i).getId()));
		}

		request.setAttribute("hotUser", hotUser); // 取8条记录用于首页展示
		this.itemNum = hotUser.size();
		PageInfo pageInfo = new PageInfo(allNum, num, pageNum, currentPage, itemNum);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("topComment", CommentBean.getLastestComments(10)); // 取得10条最新评论
		return SUCCESS;
	}
}
