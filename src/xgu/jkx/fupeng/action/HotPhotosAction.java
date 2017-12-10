package xgu.jkx.fupeng.action;

import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PageInfo;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.model.Photo;

public class HotPhotosAction extends MySuperAction {

	private static final long serialVersionUID = -6507449569703383053L;

	private int allNum ; // �ܼ�¼��,����0����������ͼƬ��Ŀ
	private int num = 8; // ÿҳ����
	private int pageNum ; // ��ҳ��
	private int currentPage; // ��ǰҳ��
	private int itemNum; // ��ǰҳ����

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String execute() {
		allNum = PhotoBean.getHotPhotoNum();
		List<Photo> hotPhoto = PhotoBean.getHotPhotos(currentPage * num, num);
		pageNum = (allNum - 1) / num + 1;
		this.itemNum = hotPhoto.size();
		request.setAttribute("hotPhoto", hotPhoto); // ȡ8��������Ƭ������ҳչʾ
		
		PageInfo pageInfo = new PageInfo(allNum, num, pageNum, currentPage,
				itemNum);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("topComment", CommentBean.getLastestComments(10)); // ȡ��10����������
		return SUCCESS;
	}
}
