package com.newppt.android.db.dboption;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.tableentity.YuanBanPPT;

public class YuanbanPPTOption {

	private MyDBHelper _dbHelper;

	public YuanbanPPTOption(MyDBHelper _dbHelper) {
		this._dbHelper = _dbHelper;
	}
	
	public boolean isExistYuanbanPPT(String fileName) {
		return selectYuanbanPPT(fileName).moveToNext();
	}
	
	public Cursor selectYuanbanPPT(String fileName) {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select * from yuanbanppt where ypath=?";
		Cursor cursor =  db.rawQuery(sql, new String[]{fileName});
//		db.close();
		return cursor;
	}
	
	public Cursor selectYuanbanPPT() {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		String sql = "select * from yuanbanppt";
		Cursor cursor =  db.rawQuery(sql, null);
//		db.close();
		return cursor;
	}
	
	public void inserYuanbanPPT(YuanBanPPT yuanbanPPT) {
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		String sql = "insert into yuanbanppt values(?,?,?)";
		db.execSQL(sql, new Object[]{yuanbanPPT.getyPath(),
				yuanbanPPT.getYname(),yuanbanPPT.getCno()});
//		db.close();
	}
	
	public boolean checkAndInserYuanbanPPT(YuanBanPPT yuanbanPPT) {
		if(!isExistYuanbanPPT(yuanbanPPT.getyPath())) {
			inserYuanbanPPT(yuanbanPPT);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void deleteYuanbanPPT(String ypath) {
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		String sql = "delete from yuanbanppt where ypath=?";
		db.execSQL(sql, new Object[]{ypath});
//		db.close();
	}
}
