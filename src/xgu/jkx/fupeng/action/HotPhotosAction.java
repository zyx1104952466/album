package xgu.jkx.fupeng.action;

import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PageInfo;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.model.Photo;

public class HotPhotosAction extends MySuperAction {

	private static final long serialVersionUID = -6507449569703383053L;

	private int allNum ; // 总记录数,参数0代表查出所有图片数目
	private int num = 8; // 每页个数
	private int pageNum ; // 总页数
	private int currentPage; // 当前页码
	private int itemNum; // 当前页个数

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String execute() {
		allNum = PhotoBean.getHotPhotoNum();
		List<Photo> hotPhoto = PhotoBean.getHotPhotos(currentPage * num, num);
		pageNum = (allNum - 1) / num + 1;
		this.itemNum = hotPhoto.size();
		request.setAttribute("hotPhoto", hotPhoto); // 取8张热门照片用于首页展示
		
		PageInfo pageInfo = new PageInfo(allNum, num, pageNum, currentPage,
				itemNum);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("topComment", CommentBean.getLastestComments(10)); // 取得10条最新评论
		return SUCCESS;
	}
}
