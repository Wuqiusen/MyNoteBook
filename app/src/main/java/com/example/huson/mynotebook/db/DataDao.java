package com.example.huson.mynotebook.db;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.huson.mynotebook.domain.DataInfo;

import java.util.ArrayList;
import java.util.List;


public class DataDao {
	
	private DataDBOpenHelper helper;
	
	public DataDao(Context context) {
		helper = new DataDBOpenHelper(context);
	}
	
	public long add(String msgid,int type,String context,String starttime, String endtime, String complete,
					String year, String month, String week,String day,String finishyear,
					String finishmonth, String finishweek, String finishday, int isread){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("msgid",msgid);
		values.put("type",type);
		values.put("context",context);
		values.put("starttime",starttime);
		values.put("endtime",endtime);
		values.put("complete",complete);
		values.put("year",year);
		values.put("month",month);
		values.put("week",week);
		values.put("day",day);
		values.put("finishyear",finishyear);
		values.put("finishmonth",finishmonth);
		values.put("finishweek",finishweek);
		values.put("finishday",finishday);
		values.put("isread",isread);
		long rowid = db.insert("data", null, values);
		db.close();
		return rowid;
	}
	
	public int delete(String i){
		SQLiteDatabase db = helper.getWritableDatabase();
		int result = db.delete("data", "_id=?", new String[]{i});
		db.close();
		return result;
	}

	public int updateComplete(String str, String complete){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("complete", complete);
		int result = db.update("data", values, "msgid =?",new String[]{str});
		return result;
	}
	public int update2(String str){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("isread", 0);
		int result = db.update("data", values, "msgid =?",new String[]{str});
		return result;
	}
	
	
	public List<DataInfo> findAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<DataInfo> infos = new ArrayList<DataInfo>();
		Cursor cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
				"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
				"isread"}, null, null, null, null, "_id desc");
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

	public DataInfo checkData(String mid){
		SQLiteDatabase db = helper.getReadableDatabase();
		DataInfo infrom = new DataInfo();
		Cursor cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
				"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
				"isread"},"msgid =?", new String[]{mid}, null, null, "_id desc");
		while(cursor.moveToNext()){
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
		}
		cursor.close();
		db.close();
		return infrom;
	}

	public List<DataInfo> checkbyIsfinish(String str){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<DataInfo> infos = new ArrayList<DataInfo>();
		Cursor cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
				"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
				"isread"}, "complete=?", new String[]{str}, null, null, "_id desc");
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

	public List<DataInfo> checkbyIsfinishAndType(String finish, int mtype){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<DataInfo> infos = new ArrayList<DataInfo>();
		Cursor cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
				"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
				"isread"}, "complete='"+ finish +"'and type = '" + mtype +"'", null, null, null, "_id desc");
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

	public List<DataInfo> checkbyType(int stype){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<DataInfo> infos = new ArrayList<DataInfo>();
		Cursor cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
				"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
				"isread"}, "type='"+ stype +"'", null, null, null, "_id desc");
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

	public List<DataInfo> checkbyYear(String str){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<DataInfo> infos = new ArrayList<DataInfo>();
		Cursor cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
				"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
				"isread"}, "year=?", new String[]{str}, null, null, "_id desc");
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

	public List<DataInfo> checkbyMonth(String myear, String mmonth){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<DataInfo> infos = new ArrayList<DataInfo>();
		Cursor cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
				"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
				"isread"}, "year='"+ myear +"'and month = '" + mmonth +"'", null, null, null, "_id desc");
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

	public List<DataInfo> checkbyDay(String myear, String mmonth, String mday){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<DataInfo> infos = new ArrayList<DataInfo>();
		Cursor cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
				"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
				"isread"}, "year='"+ myear +"'and month= '" + mmonth +"'and day= '" + mday +"'", null, null, null, "_id desc");
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

	public List<String> checkbyDay(int itype){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<String> infos = new ArrayList<String>();
		Cursor cursor = null;
		if (itype == 1){
			cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
					"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
					"isread"}, "distinct month", null, null, null, "_id desc");
		}else if (itype == 2){
			cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
					"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
					"isread"}, "distinct week", null, null, null, "_id desc");
		}else if (itype == 3){
			cursor = db.query("data", new String[]{"_id","msgid","type","context","starttime",
					"endtime","complete","year", "month","week","day","finishyear", "finishmonth","finishweek","finishday",
					"isread"}, "distinct day", null, null, null, "_id desc");
		}
		infos.add("È«²¿");
		while(cursor.moveToNext()){
			String string = cursor.getString(0);
			infos.add(string);
		}
		cursor.close();
		db.close();
		return infos;
	}

	
}
