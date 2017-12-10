package xgu.jkx.fupeng.action;

import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PageInfo;
import xgu.jkx.fupeng.model.Comment;
import xgu.jkx.fupeng.model.User;

public class CommentAction extends MySuperAction {

	private static final long serialVersionUID = 2822200419777857810L;
	private int cid;
	private List<Comment> comments;
	private PageInfo pageInfo;
	private int allNum; // 总记录数,参数0代表查出所有图片数目
	private int num = 2; // 每页个数
	private int pageNum; // 总页数
	private int currentPage; // 当前页码
	private int itemNum; // 当前页个数

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public String getCommentList() {
		User user = (User) session.get("user");
		this.allNum = CommentBean.getCommentNum(user.getUsername());
		this.pageNum = (allNum - 1) / num + 1;
		this.comments = CommentBean.getCommentsByUsername(user.getUsername(),
				currentPage * num, num);
		this.itemNum = comments.size();
		this.pageInfo = new PageInfo(allNum, num, pageNum, currentPage, itemNum);
		request.setAttribute("pageInfo", pageInfo);

		System.out.println("评论个数：" + comments.size());

		return "listComment";
	}

	public String deleteComment() {

		System.out.println("删除评论后返回的页码：" + currentPage);
		System.out.println("要删除评论的id:" + cid);
		CommentBean.deleteCommentById(cid);
		return "delComment";
	}
}
