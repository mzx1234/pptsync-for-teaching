package com.newppt.android.db.tableentity;

public class Course {
	
	String cno;
	String cname;
	
	public Course(String cno, String cname) {
		super();
		this.cno = cno;
		this.cname = cname;
	}
	
	public String getCno() {
		return cno;
	}
	public void setCno(String cno) {
		this.cno = cno;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}

	
}
