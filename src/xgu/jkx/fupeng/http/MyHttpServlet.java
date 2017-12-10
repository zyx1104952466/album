package xgu.jkx.fupeng.http;
/**
 * ����Ȩ����֤
 */
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyHttpServlet extends HttpServlet {


	private static final long serialVersionUID = 4056100287609909072L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//�ı����
		ServletEncoding.setEncoding(req, resp);
		
		HttpSession session=req.getSession();
//		PrintWriter out = resp.getWriter();
		if(session.getAttribute("userInfo")==null){
			//session��û�б����û���Ϣ
			RequestDispatcher requestDispatcher=req.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(req, resp);
		}else{
			doExecute(req,resp);
		}
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
	/**
	 * ʵ��ִ�з���
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void doExecute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
	}

}
