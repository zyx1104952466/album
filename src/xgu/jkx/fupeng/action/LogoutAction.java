package xgu.jkx.fupeng.action;


import xgu.jkx.fupeng.actionSuper.MySuperAction;

public class LogoutAction extends MySuperAction{

	private static final long serialVersionUID = 6483406643817812162L;
	
	public String execute() {
		session.remove("user");
		session.remove("admin");
		return SUCCESS;
	}
}
