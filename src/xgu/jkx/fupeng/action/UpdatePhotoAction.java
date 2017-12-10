package xgu.jkx.fupeng.action;


import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.User;

public class UpdatePhotoAction extends MySuperAction {

	private static final long serialVersionUID = 1008019280999083138L;
	private int photoId;
	private int albumId;
	private String name;
	private String descr;

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
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

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String execute() throws Exception {
		User user = (User) session.get("user");
		if (!UserBean.holdThePhoto(user.getId(), photoId)) {
			return "nologin";
		}
		if (PhotoBean.updatePhoto(name, descr, photoId))
			return SUCCESS;
		else {
			request.setAttribute("returnUrl", "gethotusers.action");
			request.setAttribute("errorMessage", "相片信息修改失败！");
			return ERROR;
		}
	}

}
