package com.newppt.android.db.tableentity;

public class BiJiPPT {

	String tPath;
	String tName;
	String cno;
	public BiJiPPT(String tPath, String tName, String cno) {
		super();
		this.tPath = tPath;
		this.tName = tName;
		this.cno = cno;
	}
	public String gettPath() {
		return tPath;
	}
	public void settPath(String tPath) {
		this.tPath = tPath;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public String getCno() {
		return cno;
	}
	public void setCno(String cno) {
		this.cno = cno;
	}
	
	
}
