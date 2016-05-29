package com.newppt.android.db.dboption;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.newppt.android.db.dbhelper.MyDBHelper;

public class AllOption {
	
	private MyDBHelper _dbHelper;

	public AllOption(MyDBHelper _dbHelper) {
		this._dbHelper = _dbHelper;
	}
	
public boolean isExistPPTPage(String fileName, int page) {
		return selectFilePPTPage(fileName, page).moveToNext();
	}
	
	
	public Cursor selectFilePPTPage(String fileName, int page) {		
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select pptpath,notepath from pptpage where page=? and tpath ="
			         +"(select tpath from tongbuppt where tname=?)";
		Cursor cursor =  db.rawQuery(sql, new String[]{page+"",fileName});
//		db.close();
		return cursor;
	}
	
	public Cursor selectBijiPPT() {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select tpath,tname,cname from course,tongbuppt "+
				     "where course.cno = tongbuppt.cno order by cname";
		System.out.println("----mzxxxxxxx6");
		return db.rawQuery(sql, null);
	}
	
	public Cursor selectBijiPPT(String cname) {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select tpath,tname,cname from course,tongbuppt "+
				     "where cname=? and course.cno = tongbuppt.cno order by cname";
		System.out.println("----mzxxxxxxx6");
		return db.rawQuery(sql, new String[]{cname});
	}
	
	public Cursor selectBijiPPTKey(String key) {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select tpath,tname,cname from course,tongbuppt "+
				     "where tname like ? and course.cno = tongbuppt.cno order by cname";
		System.out.println("----mzxxxxxxx6");
		return db.rawQuery(sql, new String[]{"%"+key+"%"});
	}
	
	public Cursor selectYuanbanPPT(String cname) {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select ypath,yname,cname from course,yuanbanppt "+
				     "where cname=? and course.cno = yuanbanppt.cno order by cname";
		System.out.println("----mzxxxxxxx6");
		return db.rawQuery(sql, new String[]{cname});
	}
	
	public Cursor selectBiJiPPTCname() {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select distinct cname from course,tongbuppt "
				     +"where course.cno = tongbuppt.cno order by cname";
		
		return db.rawQuery(sql, null);
	}
	
	public Cursor selectYuanbanPPT() {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select ypath,yname,cname from course,yuanbanppt "
				     +"where course.cno = yuanbanppt.cno order by cname";
		return db.rawQuery(sql, null);
	}
	
	public Cursor selectYuanbanPPTKey(String key) {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select ypath,yname,cname from course,yuanbanppt "+
				     "where yname like ? and course.cno = yuanbanppt.cno order by cname";
		System.out.println("----mzxxxxxxx6");
		return db.rawQuery(sql, new String[]{"%"+key+"%"});
	}
	
	
	
	public Cursor selectYuanbanCname() {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select distinct cname from course,yuanbanppt "
				     +"where course.cno = yuanbanppt.cno order by cname";
		return db.rawQuery(sql, null);
	}
	
	public boolean insertCourse(String cname) {
		CourseOption courseOption = new CourseOption(_dbHelper);
		if(courseOption.insertNewCourse(cname)) {
			return true;
		}
		return false;
	}
			
			
}
