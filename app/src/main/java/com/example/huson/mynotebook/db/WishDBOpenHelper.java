package com.example.huson.mynotebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WishDBOpenHelper extends SQLiteOpenHelper {

	public WishDBOpenHelper(Context context) {
		super(context, "wish.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table wish (_id integer primary key autoincrement,type integer,needcoin integer," +
				"context varchar(300),starttime varchar(20),endtime varchar(20),isfinish integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
