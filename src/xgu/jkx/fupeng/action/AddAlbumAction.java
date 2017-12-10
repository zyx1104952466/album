package xgu.jkx.fupeng.action;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.Album;

public class AddAlbumAction extends MySuperAction {

	private static final long serialVersionUID = 1920093206465244521L;
	private int userId;
	private String name;
	private String desc;
	private int aStatus;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
		album.setUserid(this.userId);
		album.setName(this.name);
		album.setIntroduce(this.desc);
		album.setOpen(this.aStatus);
		if (AlbumBean.addAlbum(album)) {
			UserBean.addActive(this.userId, 6);
			return SUCCESS;
		} else
			request.setAttribute("returnUrl", "addalbum.jsp");
		 	request.setAttribute("errorMessage", "此相册名已经存在，请换一个相册名称！");
		return ERROR;
	}
}
