package xgu.jkx.fupeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONObject;
import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.http.ServletEncoding;
import xgu.jkx.fupeng.model.Comment;
import xgu.jkx.fupeng.model.Photo;

public class GetNextPhotoAction extends MySuperAction {

	private static final long serialVersionUID = 984898785748624987L;
	private int photoId;
	private int albumId;

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

	public String execute() {
		ServletEncoding.setEncoding(request, response);
		System.out.println("photoId=" + photoId);
		System.out.println("albumId=" + albumId);
		JSONObject json = new JSONObject();
		Photo photo = PhotoBean.getNextPic(albumId, photoId);
		List<Comment> commentList = null;
		if (photo == null) {
			json.put("lastOne", "YES");
		} else {
			commentList = CommentBean.getCommentsByPhotoId(photo.getId());
			JSONObject photo_json = new JSONObject();
			photo_json.put("photoId", photo.getId());
			photo_json.put("name", photo.getName());
			photo_json.put("url", photo.getUrl());
			photo_json.put("addTime", photo.getAddTime());
			photo_json.put("skimNum", photo.getSkimNum());
			photo_json.put("fine", photo.getFine());

			JSONObject comments_json = new JSONObject();
			if (commentList.size() == 0) {
				json.put("comments", "null");
			} else {
				for (int i = 0; i < commentList.size(); i++) {
					JSONObject oneComment = new JSONObject();
					oneComment.put("userId", commentList.get(i).getUserId());
					oneComment
							.put("fromUser", commentList.get(i).getFromUser());
					oneComment.put("addTime", commentList.get(i).getAddTime());
					oneComment.put("content", commentList.get(i).getContent());
					oneComment.put("url", commentList.get(i).getUrl());
					comments_json.put(String.valueOf(i), oneComment);
				}
				json.put("comments", comments_json);
			}

			json.put("photo", photo_json);
			json.put("lastOne", "NO");
		}

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(json.toString());
		out.flush();
		out.close();
		return null;
	}
}
