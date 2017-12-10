package xgu.jkx.fupeng.action;


import java.io.PrintWriter;
import java.util.List;

import xgu.jkx.fupeng.actionSuper.MySuperAction;
import xgu.jkx.fupeng.bean.AlbumBean;
import xgu.jkx.fupeng.bean.PhotoBean;
import xgu.jkx.fupeng.bean.UserBean;
import xgu.jkx.fupeng.http.ServletEncoding;

public class SearchSuggestAction extends MySuperAction {

	private static final long serialVersionUID = 7257734921366893228L;
	private int searchType;
	private String keyword;
	
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Override
	public String execute() throws Exception {
System.out.println("type="+searchType);
System.out.println("keyword="+keyword);
		ServletEncoding.setEncoding(request, response);
	//	if(keyword.equals("")) return null;
		List<String> strList = null;
		if(keyword == null || "".equals(keyword.trim())) return null;
		if (searchType == 1) {
			strList = AlbumBean.searchSuggest(keyword);
		} else if(searchType == 2) {
			strList = PhotoBean.searchSuggest(keyword);
		} else if(searchType == 3) {
			strList = UserBean.searchSuggest(keyword);
		} else return null;
		String str = "";
		for (int i = 0; i < strList.size(); i++) {
			str += "<div id='item' style='width:162px;color:#0b77be;'>"+strList.get(i)+"</div>$$";
		}
		PrintWriter out = response.getWriter();
		out.print(str);
		out.flush();
		out.close();
		return null;
	}
	
}
