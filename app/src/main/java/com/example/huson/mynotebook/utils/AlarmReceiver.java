package com.example.huson.mynotebook.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.huson.mynotebook.ui.AlarmActivity;

/**
 * Created by Huson on 2016/3/29.
 * 940762301@qq.com
 */
public class AlarmReceiver extends BroadcastReceiver {

    private Intent mIntent = null;
    private PendingIntent mPendingIntent = null;
    private Notification mNotification = null;
    private NotificationManager mNotificationManager = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        mIntent = intent;
        Bundle bundle = mIntent.getExtras();
        mNotificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent i = new Intent(context, AlarmActivity.class);
        i.putExtras(bundle);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);


//        mIntent = new Intent(context,AddBwlActivity.class);
//        mIntent.setClass(context,AlarmActivity.class);
//        mPendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
//        mNotification = new Notification();
//        mNotification.tickerText="备忘录";
//        //设置默认声音、默认振动、和默认闪光灯
//        mNotification.defaults = Notification.DEFAULT_ALL;
//        //点击通知后自动取消
//        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
//        mNotification.setLatestEventInfo(context, bundle.getString("title"), bundle.getString("context"), mPendingIntent);
//        mNotificationManager.notify(1,mNotification);
    }
}
