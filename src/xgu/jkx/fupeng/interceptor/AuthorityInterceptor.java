package xgu.jkx.fupeng.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import xgu.jkx.fupeng.model.User;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 2301478600235967985L;

	// 拦截Action处理的拦截方法
	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		// 取出名为user的session属性
		HttpServletRequest request = ServletActionContext.getRequest();
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();
		User user = (User) session.get("user");
		if (null == user) user = (User)session.get("admin");  
		// 如果没有登陆，返回去登陆
		if (user != null) {
			System.out.println("拦截器开始验证用户是否有操作权限...通过");
			return invocation.invoke();
		} else {
			// 没有登陆
			System.out.println("拦截器开始验证用户是否有操作权限...未通过");
			request.setAttribute("returnUrl", "login.jsp");
			request.setAttribute("errorMessage", "您无权此次操作，请重新登陆！");
			return Action.LOGIN;
		}
	}
}
