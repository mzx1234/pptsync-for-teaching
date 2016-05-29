package com.newppt.android.db.dboption;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.tableentity.NotePage;

public class NotePageOption {

	private MyDBHelper _dbHelper;

	public NotePageOption(MyDBHelper _dbHelper) {
		this._dbHelper = _dbHelper;
	}
	
	public boolean isExistNotePage(String notePath) {
		return selectFileNotePage(notePath).moveToNext();
	}

	public Cursor selectFileNotePage(String notePath) {		
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select * from notepage where notepath=?";
		Cursor cursor =  db.rawQuery(sql, new String[]{notePath});
//		db.close();
		return cursor;
	}
	
	public void inserBijiNotePage(NotePage notePage) {
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		String sql = "insert into notepage values(?,?)";
		db.execSQL(sql, new Object[]{notePage.getNotePath(),
				                     notePage.gettPath()});
//		db.close();
	}
	
	public boolean checkAndInserBijiNotePage(NotePage notePage) {
		if(isExistNotePage(notePage.getNotePath())) {
			System.out.println("----------15");
			deleteBijiNotePage(notePage.getNotePath());
		}
		inserBijiNotePage(notePage);
		return true;
		
	}
	
	
	
	public void deleteBijiNotePage(String notePath) {
		System.out.println("----------11");
		System.out.println("----------"+notePath);
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		System.out.println("----------12");
		String sql = "delete from notepage where notepath=?";
		String sql1 = "delete from pptpage where notepath=?";
		db.execSQL(sql1, new Object[]{notePath});
		db.execSQL(sql, new Object[]{notePath});
		System.out.println("----------13");
//		db.close();
	}
}
