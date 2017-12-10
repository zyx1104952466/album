package xgu.jkx.fupeng.action;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.model.Album;
import xgu.jkx.fupeng.model.User;

public class ChangeAlbumInfoAction extends MySuperAction {

	private static final long serialVersionUID = 314213176417478106L;
	private int userId;
	private int albumId;
	private String name;
	private String desc;
	private int aStatus;


	public int getUserId() {
		return userId;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getAStatus() {
		return aStatus;
	}

	public void setAStatus(int status) {
		aStatus = status;
	}

	public String execute() {
		Album album = new Album();
		album.setId(this.albumId);
		album.setName(this.name);
		album.setIntroduce(this.desc);
		album.setOpen(this.aStatus);

		if (AlbumBean.updateAlbum(album)) {
			this.userId = ((User)session.get("user")).getId();
			return SUCCESS;
		} else {
			request.setAttribute("returnUrl", "gethotalbums.action");
			request.setAttribute("errorMessage", "œ‡≤·–ﬁ∏ƒ ß∞‹£°");
			return ERROR;

		}
	}

}
