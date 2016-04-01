package com.example.huson.mynotebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataDBOpenHelper extends SQLiteOpenHelper {

	public DataDBOpenHelper(Context context) {
		super(context, "infrom.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table data (_id integer primary key autoincrement,msgid varchar(20),type varchar(20)," +
				"context varchar(300),starttime varchar(20),endtime varchar(20),complete varchar(10)," +
				"year varchar(10),month varchar(10),week varchar(10),day varchar(10),finishyear varchar(10),"+
				"finishmonth varchar(10),finishweek varchar(10),finishday varchar(10),isread integer)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
