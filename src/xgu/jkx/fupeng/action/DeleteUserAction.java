package xgu.jkx.fupeng.action;

import java.util.List;


import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.CommentBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.Album;
import xgu.jkx.fupeng.model.User;

public class DeleteUserAction extends MySuperAction {

	private static final long serialVersionUID = 7745544846601637074L;
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String execute() throws Exception {
		User user = (User) session.get("admin");
		if (null == user) {
			request.setAttribute("returnUrl", "gethotusers.action");
			request.setAttribute("errorMessage", "ʧ�ܣ�����Ȩ���쳣��");
			return ERROR;
		} else {
			// ɾ���û����������� 
			if (!CommentBean.deleteCommentByUsername(UserBean.getUserInfoById(userId).getUsername())) {
				request.setAttribute("returnUrl", "gethotusers.action");
				request.setAttribute("errorMessage", "ʧ�ܣ�ɾ���û����۳���");
				return ERROR;
			}
			
			//��ȡ�û����������
			List<Album> albums = AlbumBean.getAlbumByUserId(userId);
			for (int i = 0; i < albums.size(); i++) {
				System.out.println("��"+i+1+"������ id= "+albums.get(i).getId());
			}
			
						
			// ɾ���û�������ͼƬ 
			if (!AlbumBean.clearAlbums(albums)) {
				request.setAttribute("returnUrl", "gethotusers.action");
				request.setAttribute("errorMessage", "ʧ�ܣ�ɾ���û�ͼƬ����");
				return ERROR;
			}
			

			// ɾ���û����������
			for (int i = 0; i < albums.size(); i++) {
				if (!AlbumBean.deleteAlbumById(albums.get(i).getId())) {
					request.setAttribute("returnUrl", "gethotusers.action");
					request.setAttribute("errorMessage", "ʧ�ܣ�ɾ���û�������");
					return ERROR;
				}
				
			}
			
			//ɾ����������ͼƬ�����Ŀ¼
			
			//ɾ���û�
			if (!UserBean.deleteUserById(this.userId)) {
				request.setAttribute("returnUrl", "gethotusers.action");
				request.setAttribute("errorMessage", "ʧ�ܣ�ɾ���û�����");
				return ERROR;
			}
			
			return SUCCESS;
		}

	}
}
