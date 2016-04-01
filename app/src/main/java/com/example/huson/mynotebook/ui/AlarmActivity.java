package com.example.huson.mynotebook.ui;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.huson.mynotebook.utils.AlarmReceiver;
import com.example.huson.mynotebook.utils.BuildDialog;
import com.example.huson.mynotebook.utils.DebugLog;
import com.example.huson.mynotebook.view.MyDialog;

import java.io.IOException;

/**
 * Created by Huson on 2016/3/29.
 * 940762301@qq.com
 */
public class AlarmActivity extends Activity {
    private Context mcontext;
    private String context;
    private String id;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mp = new MediaPlayer();
        if (alert!=null) {
            DebugLog.e(alert.toString());
        }
        try {
            mp.setDataSource(AlarmActivity.this, alert);
            mp.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getExtras();
        BuildDialog.myDialog().showDialog(this, "开工时间到", context, MyDialog.HAVEBUTTON);
        BuildDialog.myDialog().ButtonCancel(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildDialog.myDialog().DismissDialog();
                mp.stop();
                finish();
            }
        });
        BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildDialog.myDialog().DismissDialog();
                mp.stop();
                finish();
            }
        });
        mp.setLooping(true);
        mp.start();

    }

    private void getExtras() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        context = bundle.getString("context");


    }


}
