package xgu.jkx.fupeng.action;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.User;

public class SetAlbumFaceAction extends MySuperAction {

	private static final long serialVersionUID = 553306605571828588L;
	private int albumId;
	private int photoId;
	private int userId;

	public int getUserId() {
		return userId;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String execute() {
		User user = (User) session.get("user");
		userId = user.getId();
		if (this.userId == 0) {
			return ERROR;
		}
		if (UserBean.holdTheAlbum(userId, albumId)
				&& UserBean.holdThePhoto(userId, photoId)) {
			AlbumBean.updateFace(albumId, photoId);
			return SUCCESS;
		}
		return ERROR;
	}
}
