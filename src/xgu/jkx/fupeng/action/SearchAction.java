package xgu.jkx.fupeng.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PageInfo;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.http.ServletEncoding;
import xgu.jkx.fupeng.model.Album;
import xgu.jkx.fupeng.model.Photo;
import xgu.jkx.fupeng.model.User;

public class SearchAction extends MySuperAction {

	private static final long serialVersionUID = -6455644926598411615L;
	private int searchType;
	private String kword;

	private int allNum; // �ܼ�¼��
	private int num = 8; // ÿҳ����
	private int pageNum; // ��ҳ��
	private int currentPage; // ��ǰҳ��
	private int itemNum; // ��ǰҳ����

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public String getKword() {
		return kword;
	}

	public void setKword(String kword) throws UnsupportedEncodingException {
		this.kword = kword;
	}

	private void ceatePageInfo() {
		PageInfo pageInfo = new PageInfo(allNum, num, pageNum, currentPage,
				itemNum);
		request.setAttribute("pageInfo", pageInfo);
	}

	@Override
	public String execute() throws Exception {
		ServletEncoding.setEncoding(request, response);
		System.out.println("�����ؼ��� ��" + kword);
		System.out.println("�������� ��" + searchType);
		System.out.println("��ҳ�� ��" + currentPage);
		request.setAttribute("keyword", kword);
		request.setAttribute("searchType", searchType);
		request.setAttribute("topComment", CommentBean.getLastestComments(10)); // ȡ��10����������

		if (searchType == 1) {
			allNum = AlbumBean.countForKeyword(kword);
			pageNum = (allNum - 1) / num + 1;
			List<Album> albums = AlbumBean
					.search(currentPage * num, num, kword);
			if (albums != null) {
				itemNum = albums.size();
			}
			ceatePageInfo();
			request.setAttribute("albums", albums); // ȡ8�������
			return "SearchOnAlbum";
		} else if (searchType == 2) {
			allNum = PhotoBean.countForKeyword(kword);
			pageNum = (allNum - 1) / num + 1;
			List<Photo> photos = PhotoBean
					.search(currentPage * num, num, kword);
			if (photos != null) {
				itemNum = photos.size();
			}
			ceatePageInfo();
			request.setAttribute("photos", photos); // ȡ8�������
			return "SearchOnPhoto";
		} else if (searchType == 3) {
			allNum = UserBean.countForKeyword(kword);
			pageNum = (allNum - 1) / num + 1;
			List<User> users = UserBean
					.search(currentPage * num, num, kword);
			for(int i=0; i<users.size(); i++) {
				List<Photo> hotPhotos = new ArrayList<Photo>();
				hotPhotos = PhotoBean.getHotPhotosById(users.get(i).getId(), 0, 3);

				users.get(i).setHotPhotos(hotPhotos);
				users.get(i).setPhotoNum(PhotoBean.getPhotoNumByUserId(users.get(i).getId()));
				users.get(i).setAlbumNum(AlbumBean.getAlbumNum(users.get(i).getId()));
			}
			if (users != null) {
				itemNum = users.size();
			}
			ceatePageInfo();
			request.setAttribute("users", users); // ȡ8�������
			return "SearchOnUser";
		} else
			return null;
	}

}
