package com.newppt.android.db.tableentity;

public class YuanBanPPT {
	
	String yPath;
	String yname;
	String cno;
	public YuanBanPPT(String yPath, String yname, String cno) {
		super();
		this.yPath = yPath;
		this.yname = yname;
		this.cno = cno;
	}
	public String getyPath() {
		return yPath;
	}
	public void setyPath(String yPath) {
		this.yPath = yPath;
	}
	public String getYname() {
		return yname;
	}
	public void setYname(String yname) {
		this.yname = yname;
	}
	public String getCno() {
		return cno;
	}
	public void setCno(String cno) {
		this.cno = cno;
	}
	
	

}
