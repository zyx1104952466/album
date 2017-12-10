package xgu.jkx.fupeng.action;

import java.io.File;

import org.apache.struts2.ServletActionContext;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.User;
import xgu.jkx.fupeng.util.FileUtil;

public class DeletePhotoAction extends MySuperAction {

	private static final long serialVersionUID = -7212701556726684424L;

	private int photoId;
	private int albumId;

	public int getPhotoId() {
		return photoId;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String execute() throws Exception {
		User user = (User) session.get("user");
		String url = PhotoBean.getPhotoUrlById(photoId);
		if (user != null && UserBean.holdThePhoto(user.getId(), photoId)) {
			if (PhotoBean.isAlbumFace(this.photoId)) {
		System.err.println("��ͼƬ�Ƿ��棡");
				AlbumBean.updateFace(albumId, "images/default/album.jpg");
			}
			//ɾ����Ƭ���ݿ��е���Ϣ
			PhotoBean.deletePhotoById(photoId);
			//�û���Ծ�Ƚ���2��
			UserBean.addActive(user.getId(), -2);
			//���������������Ƭ��Ŀ
			AlbumBean.updatePhotonum(albumId, -1);
			//ɾ���������Ƭ
			String physPath = ServletActionContext.getServletContext().getRealPath("/")+url;
			File f = new File(physPath);
			FileUtil.deleteFile(f);
			return SUCCESS;
		}
		return ERROR;
	}
}
