package com.example.huson.mynotebook.db;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.domain.WishInfo;

import java.util.ArrayList;
import java.util.List;


public class WishDao {
	
	private WishDBOpenHelper helper;
	
	public WishDao(Context context) {
		helper = new WishDBOpenHelper(context);
	}
	
	public long add(int type,int needcoin, String context,String starttime, String endtime,int isfinish){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("type",type);
		values.put("needcoin",needcoin);
		values.put("context",context);
		values.put("starttime",starttime);
		values.put("endtime",endtime);
		values.put("isfinish",isfinish);
		long rowid = db.insert("wish", null, values);
		db.close();
		return rowid;
	}
	
	public int delete(String i){
		SQLiteDatabase db = helper.getWritableDatabase();
		int result = db.delete("wish", "_id=?", new String[]{i});
		db.close();
		return result;
	}

	public int updatefinish(String str,int finish, String endtime){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("isfinish", finish);
		values.put("endtime", endtime);
		int result = db.update("wish", values, "_id =?",new String[]{str});
		return result;
	}
	public int updatefinishtime(String str, String time){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("endtime", time);
		int result = db.update("wish", values, "_id =?",new String[]{str});
		return result;
	}
	
	
	public List<WishInfo> findAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<WishInfo> infos = new ArrayList<WishInfo>();
		Cursor cursor = db.query("wish", new String[]{"_id","type","needcoin","context","starttime",
				"endtime","isfinish"}, null, null, null, null, "_id desc");
		while(cursor.moveToNext()){
			WishInfo infrom = new WishInfo();
			int id = cursor.getInt(0);
			int type = cursor.getInt(1);
			int needcoin = cursor.getInt(2);
			String context = cursor.getString(3);
			String starttime = cursor.getString(4);
			String endtime = cursor.getString(5);
			int isfinish = cursor.getInt(6);
			infrom.setId(id);
			infrom.setType(type);
			infrom.setNeedcoin(needcoin);
			infrom.setContext(context);
			infrom.setStarttime(starttime);
			infrom.setEndtime(endtime);
			infrom.setIsfinish(isfinish);
			infos.add(infrom);
		}
		cursor.close();
		db.close();
		return infos;
	}


	public List<WishInfo> checkbyType(String mtype){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<WishInfo> infos = new ArrayList<WishInfo>();
		Cursor cursor = db.query("data", new String[]{"_id","type","needcoin","context","starttime",
				"endtime","isfinish"},"type =?", new String[]{mtype}, null, null, "_id desc");
		while(cursor.moveToNext()){
			WishInfo infrom = new WishInfo();
			int id = cursor.getInt(0);
			int type = cursor.getInt(1);
			int needcoin = cursor.getInt(2);
			String context = cursor.getString(3);
			String starttime = cursor.getString(4);
			String endtime = cursor.getString(5);
			int isfinish = cursor.getInt(6);
			infrom.setId(id);
			infrom.setNeedcoin(needcoin);
			infrom.setType(type);
			infrom.setContext(context);
			infrom.setStarttime(starttime);
			infrom.setEndtime(endtime);
			infrom.setIsfinish(isfinish);
			infos.add(infrom);
		}
		cursor.close();
		db.close();
		return infos;
	}
	public WishInfo checkData(String mid){
		SQLiteDatabase db = helper.getReadableDatabase();
		WishInfo infrom = new WishInfo();
		Cursor cursor = db.query("wish", new String[]{"_id","type","needcoin","context","starttime",
				"endtime","isfinish"},"_id =?", new String[]{mid}, null, null, "_id desc");
		while(cursor.moveToNext()){
			int id = cursor.getInt(0);
			int type = cursor.getInt(1);
			int needcoin = cursor.getInt(2);
			String context = cursor.getString(3);
			String starttime = cursor.getString(4);
			String endtime = cursor.getString(5);
			int isfinish = cursor.getInt(6);
			infrom.setId(id);
			infrom.setNeedcoin(needcoin);
			infrom.setType(type);
			infrom.setContext(context);
			infrom.setStarttime(starttime);
			infrom.setEndtime(endtime);
			infrom.setIsfinish(isfinish);
		}
		cursor.close();
		db.close();
		return infrom;
	}

	
}
