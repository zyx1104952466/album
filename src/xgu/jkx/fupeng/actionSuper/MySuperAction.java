package xgu.jkx.fupeng.actionSuper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class MySuperAction extends ActionSupport implements SessionAware,ServletRequestAware,ServletResponseAware {

	private static final long serialVersionUID = -6821303263294899895L;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	@SuppressWarnings("unchecked")
	protected Map session;

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session=session;	
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;		
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}	
}