package xgu.jkx.fupeng.action;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.http.ServletEncoding;
import xgu.jkx.fupeng.model.User;
import xgu.jkx.fupeng.util.StringUtil;

public class RegisterAction extends MySuperAction {

	private static final long serialVersionUID = -4512894791086068073L;

	private String VerifyCode;
	private String username;
	private String nickName;
	private String password;
	private String rePassword;
	private String email;

	public String getVerifyCode() {
		return VerifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		VerifyCode = verifyCode;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		ServletEncoding.setEncoding(request, response);
		
		if (!request.getSession().getAttribute("rand").toString().equals(this.VerifyCode)) {
			// 验证码错误
			request.setAttribute("errorMessage", "验证码错误，注册失败！");
			request.setAttribute("returnUrl", "register.jsp");
			return ERROR;
		}
		if (StringUtil.isStringEmpty(username)
				|| StringUtil.isStringEmpty(password)
				|| StringUtil.isStringEmpty(nickName)
				|| StringUtil.isStringEmpty(email)) {
			request.setAttribute("errorMessage", "不允许添加空数据！");
			request.setAttribute("returnUrl", "register.jsp");
			return ERROR;
		} else {
			User user = new User();
			user.setNickName(nickName);
			user.setPassword(password);
			user.setUsername(username);
			user.setEmail(email);
			user = UserBean.addUser(user);
			if (user == null) {
				request.setAttribute("errorMessage", "注册失败！");
				request.setAttribute("returnUrl", "register.jsp");
				return ERROR;
			} else {
				request.setAttribute("errorMessage", "注册成功，现在可以去登陆了！");
				request.setAttribute("returnUrl", "login.jsp");
				return ERROR;
			}
		}
	}
}
