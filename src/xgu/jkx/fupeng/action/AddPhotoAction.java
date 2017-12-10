package xgu.jkx.fupeng.action;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.model.Album;

public class AddPhotoAction extends MySuperAction {

	private static final long serialVersionUID = -3062017942281670560L;
	private int albumId;

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		Album album = AlbumBean.getAlbumById(albumId);
		session.put("album", album);
		return SUCCESS;
	}

}
