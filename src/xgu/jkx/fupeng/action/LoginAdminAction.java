package xgu.jkx.fupeng.action;


import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.model.User;

public class LoginAdminAction extends MySuperAction {

	private static final long serialVersionUID = 1465711369464823614L;
	
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user = UserBean.LoginAdmin(user);
		if (user != null) {
			session.put("admin",user);
System.out.println("管理员："+user.getUsername()+"登陆了！");
			return SUCCESS;
		} else {
			request.setAttribute("returnUrl", "login_admin.jsp");
			request.setAttribute("errorMessage", "帐号或者密码不正确！");
			return ERROR;
		}
	}

}
