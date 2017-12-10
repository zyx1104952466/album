package xgu.jkx.fupeng.action;

import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PageInfo;
import xgu.jkx.fupeng.model.Album;

public class HotAlbumsAction extends MySuperAction {

	private static final long serialVersionUID = 46301460566969681L;
	private int allNum = AlbumBean.getAlbumNum(0); // 总记录数,0代表查出所有的相册个数
	private int num = 8; // 每页个数
	private int pageNum = (allNum - 1) / num + 1; // 总页数
	private int currentPage; // 当前页码
	private int itemNum; // 当前页个数

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String execute() throws Exception {
		List<Album> hotAlbum = AlbumBean.getHotAlbums(currentPage
				* num, num);
		request.setAttribute("hotAlbum", hotAlbum); // 取8个热门相册用于首页展示
		this.itemNum = hotAlbum.size();
		PageInfo pageInfo = new PageInfo(allNum, num, pageNum, currentPage, itemNum);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("topComment", CommentBean.getLastestComments(10)); // 取得10条最新评论
		return SUCCESS;
	}
}
