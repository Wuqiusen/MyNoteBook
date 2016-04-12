package com.example.huson.mynotebook.db;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;


public class CoinDao {

	private CoinDBOpenHelper helper;

	public CoinDao(Context context) {
		helper = new CoinDBOpenHelper(context);
	}
	
	public long add(int coin,String year,String month,String week,String day,int type){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("coin",coin);
		values.put("year",year);
		values.put("month",month);
		values.put("week",week);
		values.put("day",day);
		values.put("type", type);
		long rowid = db.insert("coin", null, values);
		db.close();
		return rowid;
	}

	public int findAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		int coin_count = 0;
		Cursor cursor = db.query("coin", new String[]{"_id","sum(coin)","type"}, null, null, null, null, "_id desc");
		while(cursor.moveToNext()){
			int id = cursor.getInt(0);
			coin_count = cursor.getInt(1);
			int type = cursor.getInt(2);
		}
		cursor.close();
		db.close();
		return coin_count;
	}

	public List<DataInfo> checkbyWeek(String myear, String mmonth, String mweek){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<DataInfo> infos = new ArrayList<DataInfo>();
		Cursor cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
				"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
				"isread"}, "year='"+ myear +"'and month= '" + mmonth +"'and week= '" + mweek +"'", null, null, null, "_id desc");
		while(cursor.moveToNext()){
			DataInfo infrom = new DataInfo();
			int id = cursor.getInt(0);
			String msgid = cursor.getString(1);
			int type = cursor.getInt(2);
			String context = cursor.getString(3);
			String starttime = cursor.getString(4);
			String endtime = cursor.getString(5);
			String complete = cursor.getString(6);
			String year = cursor.getString(7);
			String month = cursor.getString(8);
			String week = cursor.getString(9);
			String day = cursor.getString(10);
			String finishyear = cursor.getString(11);
			String finishmonth = cursor.getString(12);
			String finishweek = cursor.getString(13);
			String finishday = cursor.getString(14);
			int isread = cursor.getInt(15);
			infrom.setId(id);
			infrom.setMsgid(msgid);
			infrom.setType(type);
			infrom.setContext(context);
			infrom.setStarttime(starttime);
			infrom.setEndtime(endtime);
			infrom.setComplete(complete);
			infrom.setYear(year);
			infrom.setMonth(month);
			infrom.setWeek(week);
			infrom.setDay(day);
			infrom.setFinishyear(finishyear);
			infrom.setFinishmonth(finishmonth);
			infrom.setFinishweek(finishweek);
			infrom.setFinishday(finishday);
			infrom.setIsread(isread);
			infos.add(infrom);
		}
		cursor.close();
		db.close();
		return infos;
	}

	public int checkbyDay(String myear, String mmonth, String mday){
		int coin = 0;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("coin", new String[]{"_id","coin","year", "month","week","day",
				"type"}, "year='"+ myear +"'and month= '" + mmonth +"'and day= '" + mday +"'", null, null, null, "_id desc");
		while(cursor.moveToNext()){
			int id = cursor.getInt(0);
			coin = cursor.getInt(1);
			String year = cursor.getString(2);
			String month = cursor.getString(3);
			String week = cursor.getString(4);
			String day = cursor.getString(5);
			int type = cursor.getInt(6);
		}
		cursor.close();
		db.close();
		return coin;
	}

}
