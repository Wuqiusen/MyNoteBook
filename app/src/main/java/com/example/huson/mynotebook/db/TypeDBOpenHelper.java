package com.example.huson.mynotebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TypeDBOpenHelper extends SQLiteOpenHelper {

	public TypeDBOpenHelper(Context context) {
		super(context, "type.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table type (_id integer primary key autoincrement,type varchar(20)," +
				"name varchar(300),other varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
