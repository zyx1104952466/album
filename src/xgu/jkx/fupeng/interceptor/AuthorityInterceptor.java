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

	// ����Action��������ط���
	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		// ȡ����Ϊuser��session����
		HttpServletRequest request = ServletActionContext.getRequest();
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();
		User user = (User) session.get("user");
		if (null == user) user = (User)session.get("admin");  
		// ���û�е�½������ȥ��½
		if (user != null) {
			System.out.println("��������ʼ��֤�û��Ƿ��в���Ȩ��...ͨ��");
			return invocation.invoke();
		} else {
			// û�е�½
			System.out.println("��������ʼ��֤�û��Ƿ��в���Ȩ��...δͨ��");
			request.setAttribute("returnUrl", "login.jsp");
			request.setAttribute("errorMessage", "����Ȩ�˴β����������µ�½��");
			return Action.LOGIN;
		}
	}
}
