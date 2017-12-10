package xgu.jkx.fupeng.http;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletEncoding {
	/**
	 * 将请求与回应的文字编码都设置成为UTF-8编码
	 * @param request
	 * @param response
	 */
	public static void setEncoding(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html; charset=UTF-8");
		
//		response.setCharacterEncoding("UTF-8");
	}
}
