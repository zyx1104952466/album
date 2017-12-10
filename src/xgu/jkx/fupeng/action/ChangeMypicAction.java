package xgu.jkx.fupeng.action;

import java.io.File;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.User;
import xgu.jkx.fupeng.util.FileUtil;
import xgu.jkx.fupeng.util.ImageZoom;

public class ChangeMypicAction extends MySuperAction {

	private static final long serialVersionUID = -2874133080462353190L;

	private File mypic;
	private String mypicContentType;
	private String mypicFileName;
	private String msg;

	public File getMypic() {
		return mypic;
	}

	public void setMypic(File mypic) {
		this.mypic = mypic;
	}

	public String getMypicContentType() {
		return mypicContentType;
	}

	public void setMypicContentType(String mypicContentType) {
		this.mypicContentType = mypicContentType;
	}

	public String getMypicFileName() {
		return mypicFileName;
	}

	public void setMypicFileName(String mypicFileName) {
		this.mypicFileName = mypicFileName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@SuppressWarnings( { "unchecked", "deprecation" })
	public String execute() throws Exception {
		//得到图片的名称
		String imageFileName = new Date().getTime()
				+ FileUtil.getExtention(this.mypicFileName);
		
		// 创建图片保存目录
		FileUtil.changePathToAbsol(request, "/Upload_Images/"
				 + "userid_"
				+((User) session.get("user")).getId() + "/" + "person/");
		//创建图片存储的物理路径
		String physPath = ServletActionContext.getServletContext().getRealPath(
				"Upload_Images/")
				+ "/"
				+ "userid_"
				+ ((User) session.get("user")).getId()
				+ "/person/"
				+ imageFileName;
		
		File imageFile = new File(physPath);
		FileUtil.copy(mypic, imageFile);
		ImageZoom.reduceImg(physPath, physPath, 100);
		String updateUrl = "Upload_Images/" + "userid_"
				+ ((User) session.get("user")).getId() + "/person/"
				+ imageFileName;
		UserBean.updateUserPhoto(updateUrl, ((User) session.get("user"))
				.getId());
		User user = (User) session.get("user");
		user.setUrl(updateUrl);
		session.put("user", user);
		return SUCCESS;
	}
}
