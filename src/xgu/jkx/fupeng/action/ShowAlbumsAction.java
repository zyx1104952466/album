package xgu.jkx.fupeng.action;

import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PageInfo;
import xgu.jkx.fupeng.model.Album;
import xgu.jkx.fupeng.model.User;

public class ShowAlbumsAction extends MySuperAction {

	private static final long serialVersionUID = 46301460566969681L;

	private int userId;
	private int allNum; // 总记录数
	private int num = 8; // 每页个数
	private int pageNum ; // 总页数
	private int currentPage; // 当前页码
	private int itemNum; // 当前页个数

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		User user = (User) session.get("user");
		allNum = AlbumBean.getAlbumNum(userId);
		pageNum = (allNum - 1) / num + 1;
		List<Album> myAlbums = AlbumBean.getAlbums(currentPage * num, num,
				this.userId);
		request.setAttribute("topComment", CommentBean.getLastestComments(10)); // 取得10条最新评论
		this.itemNum = myAlbums.size();
		PageInfo pageInfo = new PageInfo(allNum, num, pageNum, currentPage,
				itemNum);
		request.setAttribute("pageInfo", pageInfo);
//		session.put("pageInfo", pageInfo);
		request.setAttribute("myAlbums", myAlbums); // 取8个相册

		if (myAlbums.size() == 0)
			request.setAttribute("myAlbums", null);

		if (user == null) {
			return "nologin";
		} else {
			if (user.getId() == this.userId) { // 传入的userid和session中的userid相符才认为是自己的相册
				return "login";
			} else {
				return "nologin";
			}
		}
	}
}
