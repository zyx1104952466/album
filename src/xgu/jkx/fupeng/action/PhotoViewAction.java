package xgu.jkx.fupeng.action;

import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.Album;
import xgu.jkx.fupeng.model.Comment;
import xgu.jkx.fupeng.model.Photo;
import xgu.jkx.fupeng.model.User;

public class PhotoViewAction extends MySuperAction {

	private static final long serialVersionUID = 7464977599944652174L;
	private int photoId;

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String execute() {
		int albumId = AlbumBean.getAlbumIdByPhotoId(photoId);
		Photo photo = PhotoBean.getPhoto(photoId);
		Album album = AlbumBean.getAlbumById(albumId);
		int userId = album.getUserid();
		User user = UserBean.getUserInfoById(userId);
		List<Comment> comment = CommentBean.getCommentsByPhotoId(photoId);
		
		request.setAttribute("photo", photo);
		request.setAttribute("album", album);
		request.setAttribute("user", user);
		request.setAttribute("comment", comment);
		
		PhotoBean.updatePhotoSkim(photoId);
		return SUCCESS;
	}

}
