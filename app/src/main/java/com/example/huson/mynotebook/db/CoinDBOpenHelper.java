package com.example.huson.mynotebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoinDBOpenHelper extends SQLiteOpenHelper {

	public CoinDBOpenHelper(Context context) {
		super(context, "coin.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table coin (_id integer primary key autoincrement,coin integer," +
				"year varchar(10),month varchar(10),week varchar(10),day varchar(10),type integer)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
