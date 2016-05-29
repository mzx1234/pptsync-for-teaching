package com.newppt.android.db.dboption;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.tableentity.BiJiPPT;


public class BiJiPPTOption {

	private MyDBHelper _dbHelper;
	
	public BiJiPPTOption(MyDBHelper _dbHelper) {
		this._dbHelper = _dbHelper;
	}
	
	public boolean isExistPPT(String fileName) {
		return selectFilePPT(fileName).moveToNext();
	}
	
	public Cursor selectFilePPT(String fileName) {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select tpath,cno from tongbuppt where tname=?";
		Cursor cursor =  db.rawQuery(sql, new String[]{fileName});
//		db.close();
		return cursor;
	}
	
	
	public Cursor selectFilePPT() {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select * from tongbuppt";
		Cursor cursor =  db.rawQuery(sql, null);
//		db.close();
		return cursor;
	}
	
	
	public void inserBijiPPT(BiJiPPT biJiPPT) {
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		String sql = "insert into tongbuppt values(?,?,?)";
		db.execSQL(sql, new Object[]{biJiPPT.gettPath(),
				biJiPPT.gettName(),biJiPPT.getCno()});
//		db.close();
	}
	
	public boolean checkAndInserBijiPPT(BiJiPPT biJiPPT) {
		if(!isExistPPT(biJiPPT.gettName())) {
			inserBijiPPT(biJiPPT);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void checkAndInserBijiPPT(BiJiPPT biJiPPT[]) {
		for(BiJiPPT biji : biJiPPT) {
			checkAndInserBijiPPT(biji);
		}
	}
	
	public void deleteBijiPPT(String tpath) {
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		String sql = "delete from tongbuppt where tpath=?";
		String sql_1 = "delete from pptpage where tpath=?";
		String sql_2 = "delete from notepage where tpath=?";
		db.execSQL(sql_2, new Object[]{tpath});
		db.execSQL(sql_1, new Object[]{tpath});
		db.execSQL(sql, new Object[]{tpath});
//		db.close();
	}
}
