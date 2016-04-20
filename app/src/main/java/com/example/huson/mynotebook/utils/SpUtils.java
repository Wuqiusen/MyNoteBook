package com.example.huson.mynotebook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by Huson on 2016/3/28.
 * 940762301@qq.com
 */
public class SpUtils {
    private static SharedPreferences sp;
    private final static String CACHE_FILE_NAME = "com.example.huson.mynotebook";
    public final static String CODE = "code";
    public final static String NAME = "name";
    private static void initSp(Context mContext,String fileName) {
        sp = mContext.getSharedPreferences(fileName,Context.MODE_PRIVATE);
    }
    public static void setCache(Context mContext,String key,String value){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }
    public static String getCache(Context mContext, String key){
        if(sp == null){
            initSp(mContext, CACHE_FILE_NAME);
        }
        return sp.getString(key,null);
    }
}
