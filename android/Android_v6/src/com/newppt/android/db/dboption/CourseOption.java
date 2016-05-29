package com.newppt.android.db.dboption;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.tableentity.Course;

public class CourseOption {

	private MyDBHelper _dbHelper;
	
	
	public CourseOption(MyDBHelper dbHelper) {
		this._dbHelper = dbHelper;
	}


	public boolean isExistCouse(String cno) {
		return selectCourse(cno).moveToNext();				
	}
	
	public boolean isExistCouseCname(String cname) {
		return selectCourseCno(cname).moveToNext();				
	}
	
	public Cursor selectCourse(String cno) {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select cname from course where cno=?";
		Cursor cursor =  db.rawQuery(sql, new String[]{cno});
//		db.close();
		return cursor;
	}
	
	public Cursor selectCourseCno(String cname) {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select cno from course where cname=?";
		return  db.rawQuery(sql, new String[]{cname});
	}
	
	public Cursor selectCourse() {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select cno,cname from course";
		Cursor cursor =  db.rawQuery(sql, null);
//		db.close();
		return cursor;
	}
	
	public int countCourse (){
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select cno from course";
//		System.out.println("-------110");
		return db.rawQuery(sql, null).getCount();
	}
	
	public boolean insertNewCourse(String cname) {
		if(isExistCouseCname(cname)) {
			return false;
		}
		
		int cno  = countCourse() + 1;
//		System.out.println("-------111");
		inserCourse(new Course(cno+"", cname));
//		System.out.println("-------333");
		return true;
	}
	
	public void inserCourse(Course course) {
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		String sql = "insert into course values(?,?)";
//		System.out.println("-------334");
		db.execSQL(sql, new Object[]{course.getCno(),
				course.getCname()});

	}
	
	public boolean checkAndInserCourse(Course course) {
		if(!isExistCouse(course.getCno())) {
			inserCourse(course);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void checkAndInserCourse(Course course[]) {
		for(Course c : course) {
			checkAndInserCourse(c);
		}
	}
	
	public void deleteCourse(String cname) {
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		String sql = "delete from course where cname=?";
		db.execSQL(sql, new Object[]{cname});
//		db.close();
	}
}
