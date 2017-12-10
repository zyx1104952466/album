package xgu.jkx.fupeng.bean;

public class PageInfo {
	private int allNum; // 总记录数
	private int num; // 每页个数
	private int pageNum; // 总页数
	private int currentPage; // 当前页码
	private int itemNum; // 当前页个数

	public PageInfo(int allNum, int num, int allPage, int currentPage,
			int itemNum) {
		super();
		this.allNum = allNum;
		this.num = num;
		this.pageNum = allPage;
		this.currentPage = currentPage;
		this.itemNum = itemNum;
	}

	public int getAllNum() {
		return allNum;
	}

	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

}
