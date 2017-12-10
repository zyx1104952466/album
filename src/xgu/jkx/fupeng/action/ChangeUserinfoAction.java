package xgu.jkx.fupeng.action;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.User;

public class ChangeUserinfoAction extends MySuperAction {

	private static final long serialVersionUID = -7943972722138001251L;

	private int id;
	private String username;
	private String nickName;
	private String email;
	private String oldPassword;
	private String newPassword;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		User user = (User) session.get("user");
		if (!(user.getPassword()).equals(oldPassword)) {
			request.setAttribute("errorMessage", "信息修改失败，提供的原始密码不正确！");
			request.setAttribute("returnUrl", "changeuserinfo.jsp");
			return ERROR;
		}
		if (!UserBean.updateUserinfo(id, newPassword, email, nickName))
			return ERROR;
		user = UserBean.getUserInfoById(id);
		session.put("user", user);
		return SUCCESS;
	}
}
