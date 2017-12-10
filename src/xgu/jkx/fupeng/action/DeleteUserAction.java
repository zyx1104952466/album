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
			request.setAttribute("errorMessage", "失败：操作权限异常！");
			return ERROR;
		} else {
			// 删除用户的所有评论 
			if (!CommentBean.deleteCommentByUsername(UserBean.getUserInfoById(userId).getUsername())) {
				request.setAttribute("returnUrl", "gethotusers.action");
				request.setAttribute("errorMessage", "失败：删除用户评论出错！");
				return ERROR;
			}
			
			//获取用户的所有相册
			List<Album> albums = AlbumBean.getAlbumByUserId(userId);
			for (int i = 0; i < albums.size(); i++) {
				System.out.println("第"+i+1+"个相册的 id= "+albums.get(i).getId());
			}
			
						
			// 删除用户的所有图片 
			if (!AlbumBean.clearAlbums(albums)) {
				request.setAttribute("returnUrl", "gethotusers.action");
				request.setAttribute("errorMessage", "失败：删除用户图片出错！");
				return ERROR;
			}
			

			// 删除用户的所有相册
			for (int i = 0; i < albums.size(); i++) {
				if (!AlbumBean.deleteAlbumById(albums.get(i).getId())) {
					request.setAttribute("returnUrl", "gethotusers.action");
					request.setAttribute("errorMessage", "失败：删除用户相册出错！");
					return ERROR;
				}
				
			}
			
			//删除所有物理图片和相册目录
			
			//删除用户
			if (!UserBean.deleteUserById(this.userId)) {
				request.setAttribute("returnUrl", "gethotusers.action");
				request.setAttribute("errorMessage", "失败：删除用户出错！");
				return ERROR;
			}
			
			return SUCCESS;
		}

	}
}
