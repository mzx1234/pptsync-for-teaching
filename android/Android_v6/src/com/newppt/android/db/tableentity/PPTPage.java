package com.newppt.android.db.tableentity;



public class PPTPage {

	
	String pptPath;
	String tPath;
	String notePath;
	Integer page;
	
	public PPTPage(String pptPath, String tPath, String notePath, Integer page) {
		super();
		this.pptPath = pptPath;
		this.tPath = tPath;
		this.notePath = notePath;
		this.page = page;
	}

	public String getPptPath() {
		return pptPath;
	}

	public void setPptPath(String pptPath) {
		this.pptPath = pptPath;
	}

	public String gettPath() {
		return tPath;
	}

	public void settPath(String tPath) {
		this.tPath = tPath;
	}

	public String getNotePath() {
		return notePath;
	}

	public void setNotePath(String notePath) {
		this.notePath = notePath;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	
	
	
}
