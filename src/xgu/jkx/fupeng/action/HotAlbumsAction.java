package xgu.jkx.fupeng.action;

import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PageInfo;
import xgu.jkx.fupeng.model.Album;

public class HotAlbumsAction extends MySuperAction {

	private static final long serialVersionUID = 46301460566969681L;
	private int allNum = AlbumBean.getAlbumNum(0); // �ܼ�¼��,0���������е�������
	private int num = 8; // ÿҳ����
	private int pageNum = (allNum - 1) / num + 1; // ��ҳ��
	private int currentPage; // ��ǰҳ��
	private int itemNum; // ��ǰҳ����

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String execute() throws Exception {
		List<Album> hotAlbum = AlbumBean.getHotAlbums(currentPage
				* num, num);
		request.setAttribute("hotAlbum", hotAlbum); // ȡ8���������������ҳչʾ
		this.itemNum = hotAlbum.size();
		PageInfo pageInfo = new PageInfo(allNum, num, pageNum, currentPage, itemNum);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("topComment", CommentBean.getLastestComments(10)); // ȡ��10����������
		return SUCCESS;
	}
}
