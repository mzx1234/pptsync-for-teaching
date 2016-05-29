package com.newppt.android.db.dboption;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.tableentity.PPTPage;

public class PPTPageOption {

	private MyDBHelper _dbHelper;
	
	public PPTPageOption(MyDBHelper _dbHelper) {
		this._dbHelper = _dbHelper;
	}
	
	public boolean isExistPPTPage(String pptPath) {
		return selectFilePPTPage(pptPath).moveToNext();
	}
	
	
    //查询某PPT页面的笔记页面
	public Cursor selectFilePPTPage(String pptPath) {		
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select notepath from pptpage where pptpath=?";
		Cursor cursor =  db.rawQuery(sql, new String[]{pptPath});
//		db.close();
		return cursor;
	}
	
	//查询某文件的PPT页面和笔记页面
	public Cursor selectBiJiPPTPage(String tpath) {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select pptpath,notepath from pptpage where tpath=? order by page";
		return  db.rawQuery(sql, new String[]{tpath});
	}
	
	public Cursor selectFilePPTPage() {		
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select * from pptpage ";
		Cursor cursor = db.rawQuery(sql, null);
//		db.close();
		return cursor;
	}
	
	public void inserBijiPPTPage(PPTPage pptPage) {
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		String sql = "insert into pptpage values(?,?,?,?)";
		db.execSQL(sql, new Object[]{pptPage.getPptPath(),pptPage.gettPath(),
				                     pptPage.getNotePath(),pptPage.getPage()});
//		db.close();
	}
	
	public boolean checkAndInserBijiPPTPage(PPTPage pptPage) {
		if(!isExistPPTPage(pptPage.getPptPath())) {
			inserBijiPPTPage(pptPage);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void updateBijiPPTPage(String pptpath, String notepath) {
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		String sql = "update pptpage set notepath = ? where pptpath = ?";
		db.execSQL(sql, new Object[]{notepath,pptpath});
//		db.close();
	}
	
	

}
