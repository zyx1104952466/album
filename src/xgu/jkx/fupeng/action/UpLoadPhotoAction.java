package xgu.jkx.fupeng.action;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.Album;
import xgu.jkx.fupeng.model.User;
import xgu.jkx.fupeng.util.FileUtil;
import xgu.jkx.fupeng.util.ImageZoom;

public class UpLoadPhotoAction extends MySuperAction {

	private static final long serialVersionUID = -2874133080462353190L;

	private List<File> upload;
	private List<String> uploadContentType;
	private List<String> uploadFileName;
	private String msg;
	private int albumId;
	private int userId;

	public int getUserId() {
		return userId;
	}

	public List<File> getUpload() {
		return upload;
	}

	public void setUpload(List<File> upload) {
		this.upload = upload;
	}

	public List<String> getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(List<String> uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public List<String> getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(List<String> uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getAlbumId() {
		return albumId;
	}

	@SuppressWarnings( { "unchecked", "deprecation" })
	public String execute() throws Exception {
		this.albumId = ((Album) session.get("album")).getId();  
		this.userId = ((User) session.get("user")).getId();
		session.remove("album");
		// 创建图片保存目录
		FileUtil.changePathToAbsol(request, "/Upload_Images/" + "userid_"
				+ this.userId + "/" + "albumid_" + albumId + "/");
		String basePath = ServletActionContext.getServletContext().getRealPath(
				"Upload_Images/")
				+ "/"
				+ "userid_"
				+ this.userId
				+ "/"
				+ "albumid_"
				+ getAlbumId() + "/";

		for (int i = 0; i < upload.size(); i++) {
			String fileName = new Date().getTime()
					+ FileUtil.getExtention(this.uploadFileName.get(i));
			String physPath = basePath + fileName;
			
//			String smallFileName = "pre_" + new Date().getTime()
//					+ FileUtil.getExtention(this.uploadFileName.get(i));
//			String smallPhotoPath = basePath + smallFileName;
			
			File imageFile = new File(physPath);
//			File preImageFile = new File(smallPhotoPath);
			FileUtil.copy(upload.get(i), imageFile);
//			FileUtil.copy(upload.get(i), preImageFile);
			
			ImageZoom.reduceImg(physPath, physPath, 600);
//			ImageZoom.reduceImg(smallPhotoPath, smallPhotoPath, 120);
			// 数据库图片保存地址
			String updateUrl = "Upload_Images/" + "userid_" + this.userId + "/"
					+ "albumid_" + getAlbumId() + "/" + fileName;

			PhotoBean.addPhoto(FileUtil.getFileName(uploadFileName.get(i)),
					updateUrl, albumId);
			UserBean.addActive(userId, 3);
		}
		return SUCCESS;
	}

	@Override
	public void addActionError(String anErrorMessage) {
		if (anErrorMessage
				.startsWith("the request was rejected because its size")) {
			Matcher m = Pattern.compile("d+").matcher(anErrorMessage);
			String s1 = "";
			if (m.find())
				s1 = m.group();
			String s2 = "";
			if (m.find())
				s2 = m.group();
			super.addActionError("你上传的文件(" + s1 + ")超过了允许的大小(" + s2 + ")");
		} else {
			super.addActionError(anErrorMessage);
		}
	}

}
