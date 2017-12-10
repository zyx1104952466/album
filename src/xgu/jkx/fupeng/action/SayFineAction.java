package xgu.jkx.fupeng.action;

import java.io.IOException;
import java.io.PrintWriter;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.http.ServletEncoding;

public class SayFineAction extends MySuperAction {

	private static final long serialVersionUID = -9188963776475652186L;
	private int photoId;

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		ServletEncoding.setEncoding(request, response);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (session.get("sayFine") == null) {
			session.put("sayFine", photoId);
			PhotoBean.updateFine(photoId);
			out.print("sayFineOk");
		} else if (Integer.parseInt(session.get("sayFine").toString()) != photoId) {
			session.put("sayFine", photoId);
			PhotoBean.updateFine(photoId);
			out.print("sayFineOk");
		} else {
			out.print("noRepeat");
		}
		out.flush();
		out.close();
		return null;
	}
}
