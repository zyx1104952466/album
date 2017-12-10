package xgu.jkx.fupeng.action;

import java.io.IOException;
import java.io.PrintWriter;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.http.ServletEncoding;
import xgu.jkx.fupeng.util.StringUtil;

public class IsUserExistAction extends MySuperAction {

	private static final long serialVersionUID = -7996931479615442125L;

	private String username;

	public void setUsername(String username) {
		this.username = username;
	}

	public String execute() {
		ServletEncoding.setEncoding(request, response);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String msg = "";
		if (StringUtil.isStringEmpty(username)) {
			 msg="<div style='padding-left:15px;'><img src='images/wait.gif'>&nbsp;&nbsp;<font size=3 color='gray'>用户名不能为空</font></div>";
			 out.print(msg);
			 out.flush();
			 out.close();
		} else {
			if (UserBean.isUsernameExist(username)) {
				// 用户已经存在
				msg = "<div style='padding-left:15px;'><img src='images/error.gif'>&nbsp;&nbsp;<font size=3 color='red'>该用户名已经存在</font></div>";
				out.print(msg);
				out.flush();
				out.close();
			} else {
				msg = "<div style='padding-left:15px;'><img src='images/ok.gif'>&nbsp;&nbsp;<font size=3 color='green'>可以使用此用户名</font></div>";
				out.print(msg);
				out.flush();
				out.close();
			}
		}
		return null;
	}
}
