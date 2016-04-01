package com.example.huson.mynotebook.utils;

import android.util.Log;

/**
 * Created by Huson on 2016/3/28.
 * 940762301@qq.com
 */
public class ClickUtil {

    private static long lastClickTime = 0;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        Log.i("log",""+timeD);
        return timeD <= 100;
    }
}
