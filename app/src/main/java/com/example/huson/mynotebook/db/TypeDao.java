package com.example.huson.mynotebook.db;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.huson.mynotebook.domain.TypeInfo;
import com.example.huson.mynotebook.domain.TypeInfo;

import java.util.ArrayList;
import java.util.List;


public class TypeDao {

	private TypeDBOpenHelper helper;

	public TypeDao(Context context) {
		helper = new TypeDBOpenHelper(context);
	}
	
	public long add(String type, String name,String other){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("type",type);
		values.put("name",name);
		values.put("other",other);
		long rowid = db.insert("type", null, values);
		db.close();
		return rowid;
	}
	
	public int delete(String i){
		SQLiteDatabase db = helper.getWritableDatabase();
		int result = db.delete("type", "_id=?", new String[]{i});
		db.close();
		return result;
	}

	public List<TypeInfo> findAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<TypeInfo> infos = new ArrayList<TypeInfo>();
		Cursor cursor = db.query("type", new String[]{"_id","type","name","other"}, null, null, null, null, "_id");
		while(cursor.moveToNext()){
			TypeInfo infrom = new TypeInfo();
			int id = cursor.getInt(0);
			String type = cursor.getString(1);
			String name = cursor.getString(2);
			String other = cursor.getString(3);
			infrom.setId(id);
			infrom.setType(type);
			infrom.setName(name);
			infrom.setOther(other);
			infos.add(infrom);
		}
		cursor.close();
		db.close();
		return infos;
	}


	public List<TypeInfo> checkbyName(String mname){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<TypeInfo> infos = new ArrayList<TypeInfo>();
		Cursor cursor = db.query("type", new String[]{"_id","type","name","other"},"name =?", new String[]{mname}, null, null, "_id desc");
		while(cursor.moveToNext()){
			TypeInfo infrom = new TypeInfo();
			int id = cursor.getInt(0);
			String type = cursor.getString(1);
			String name = cursor.getString(2);
			String other = cursor.getString(3);
			infrom.setId(id);
			infrom.setType(type);
			infrom.setName(name);
			infrom.setOther(other);
			infos.add(infrom);
		}
		cursor.close();
		db.close();
		return infos;
	}

	
}
