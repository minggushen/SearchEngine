package xyz.stackoverflow.searchengine.util;

public class PageInfo {

	private static final int showCount = 10;
	private int totalPage;
	private int totalResult;
	private int currentPage;
	private int currentResult;
	private int startPage;
	
	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getTotalResult() {
		return totalResult;
	}
	
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
		this.totalPage = totalResult / showCount == 0 ? totalResult / showCount : totalResult / showCount + 1;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		this.startPage = currentPage - (currentPage-1) %  showCount;
	}
	
	public int getCurrentResult() {
		return currentResult;
	}
	
	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
