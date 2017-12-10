package xgu.jkx.fupeng.action;

import java.io.File;

import org.apache.struts2.ServletActionContext;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.model.User;
import xgu.jkx.fupeng.util.FileUtil;

public class DelAlbumAction extends MySuperAction {

	private static final long serialVersionUID = 7745544846601637074L;

	private int albumId;

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	@Override
	public String execute() throws Exception {
		User user = (User) session.get("user");
		if (0 == PhotoBean.getPhotoNum(albumId)) {
			if (AlbumBean.delAlbumById(this.albumId, user.getId())) {
				request.setAttribute("returnUrl", "goindex.action");
				request.setAttribute("errorMessage", "���ɾ���ɹ���");
				String physPath = ServletActionContext.getServletContext()
						.getRealPath("/")
						+ "Upload_Images/"
						+ "userid_"
						+ user.getId()
						+ "/"
						+ "albumid_" + albumId;
				System.out.println(physPath);
				File f = new File(physPath);
				FileUtil.deleteFile(f);

				return SUCCESS;
			} else {
				request.setAttribute("returnUrl", "gethotusers.action");
				request.setAttribute("errorMessage", "�������Ȩ���쳣������ɾ����ᣡ");
				return ERROR;
			}
		} else {
			request.setAttribute("returnUrl", "showAlbums.action?userId="
					+ user.getId());
			request.setAttribute("errorMessage", "���ǿգ�����ɾ����ᣡ");
			return ERROR;
		}

	}
}
