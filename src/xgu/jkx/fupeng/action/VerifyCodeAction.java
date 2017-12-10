package xgu.jkx.fupeng.action;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.util.VerifyCode;

public class VerifyCodeAction extends MySuperAction {

	private static final long serialVersionUID = 1314802399218331099L;

	public String execute() throws Exception {
		new VerifyCode(request, response, new Integer("6"));       //生成长度为6的验证码 
		return null;
	}
	
	
}
