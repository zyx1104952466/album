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
		System.err.println("此图片是封面！");
				AlbumBean.updateFace(albumId, "images/default/album.jpg");
			}
			//删除相片数据库中的信息
			PhotoBean.deletePhotoById(photoId);
			//用户活跃度降低2点
			UserBean.addActive(user.getId(), -2);
			//更改相册表里面的相片数目
			AlbumBean.updatePhotonum(albumId, -1);
			//删除物理的相片
			String physPath = ServletActionContext.getServletContext().getRealPath("/")+url;
			File f = new File(physPath);
			FileUtil.deleteFile(f);
			return SUCCESS;
		}
		return ERROR;
	}
}
