package xgu.jkx.fupeng.action;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.model.Album;
import xgu.jkx.fupeng.model.User;

public class EditAlbumAction extends MySuperAction {

	private static final long serialVersionUID = -67530859098539558L;
	private int albumId;
	
	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	@Override
	public String execute() throws Exception {
		@SuppressWarnings("unused")
		User user = (User)session.get("user");
		if (this.albumId == 0 || user == null) {
			request.setAttribute("returnUrl", "gethotalbums.action");
			request.setAttribute("errorMessage", "ÐÞ¸ÄÊ§°Ü²Ù×÷£¡");
			return ERROR;
		}
		Album album = AlbumBean.getAlbumById(this.albumId);
		request.setAttribute("album", album);
		return SUCCESS;
	}
}
