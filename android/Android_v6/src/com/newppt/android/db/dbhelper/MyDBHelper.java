package com.newppt.android.db.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
	
	String table_Course = "create table course(cno varchar(10) primary key,"
						+"cname varchar(20))";
						
	
	String table_tongbuPPT = "create table tongbuppt(tpath varchar(30) primary key,"
			+"tname varchar(20),"
			+"cno varchar(10),"
			+"foreign key (cno) references course(cno))";
	
	String table_notepage = "create table notepage(notepath varchar(20) primary key,"
			+"tpath varchar(30),"
			+"foreign key (tpath) references tongbuppt(tpath))";
	
	String table_pptpage = "create table pptpage(pptpath varchar(30) primary key,"
			+"tpath varchar(30),"
			+"notepath varchar(30),"
			+"page int,"
			+"foreign key (tpath) references tongbuppt(tpath),"
			+"foreign key (notepath) references notepage(notepath))";
	
	String table_yuanbanPPT = "create table yuanbanppt(ypath varchar(30) primary key,"
			+"yname varchar(20),"
			+"cno varchar(10),"
			+"foreign key (cno) references course(cno))";
	
	String sql = "insert into course values(?,?,?)";
	
	
	public MyDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		
		
		db.execSQL(table_Course);  	
				
		db.execSQL(table_tongbuPPT);
		
		
		db.execSQL(table_notepage);
		
		
		db.execSQL(table_pptpage);
		
		
		db.execSQL(table_yuanbanPPT);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
		db.execSQL("PRAGMA foreign_keys=ON");
	}
	

}
