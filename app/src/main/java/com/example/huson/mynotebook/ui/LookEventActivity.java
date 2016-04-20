package com.example.huson.mynotebook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.base.BaseHeadActivity;
import com.example.huson.mynotebook.db.CoinDao;
import com.example.huson.mynotebook.db.DataDao;
import com.example.huson.mynotebook.db.TypeDao;
import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.domain.TypeInfo;
import com.example.huson.mynotebook.utils.BuildDialog;
import com.example.huson.mynotebook.utils.DebugLog;
import com.example.huson.mynotebook.utils.SpUtils;
import com.example.huson.mynotebook.utils.ToastHelper;
import com.example.huson.mynotebook.view.MyDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Huson on 2016/3/29.
 * 940762301@qq.com
 */
public class LookEventActivity extends BaseHeadActivity implements View.OnClickListener{
    private TextView tv_event_context;
    private TextView tv_start_time;
    private TextView tv_end_time;
    private TextView tv_type;
    private RadioButton cb_nofinish;
    private RadioButton cb_halffinish;
    private RadioButton cb_finish;
    private Button btn_query;

    private DataDao dao;
    private DataInfo infos;

    private CoinDao coinDao = new CoinDao(this);

    private TypeDao typeDao;
    private List<TypeInfo> typeInfo;

    private String msgid;
    private String msg;
    private String complete;
    private int coincount = Integer.valueOf(SpUtils.getCache(this, SpUtils.COIN));
    private int needcoin = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_event);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        msgid = intent.getStringExtra("msgid");
        dao = new DataDao(this);
        infos = dao.checkData(msgid);
        tv_event_context.setText(infos.getContext());
        tv_start_time.setText(infos.getStarttime());
        tv_end_time.setText(infos.getEndtime());
        String type = infos.getType();
        String complete = infos.getComplete();
        typeDao = new TypeDao(this);
        typeInfo = typeDao.findAll();
        if (complete.equals("2")){
            cb_nofinish.setChecked(true);
        }else if (complete.equals("3")){
            cb_nofinish.setClickable(false);
            cb_halffinish.setChecked(true);
        }else if (complete.equals("1")){
            cb_nofinish.setClickable(false);
            cb_halffinish.setClickable(false);
            cb_finish.setChecked(true);
        }
            tv_type.setText(type);

    }

    @Override
    protected void findView() {
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        tv_event_context = (TextView) findViewById(R.id.tv_event_context);
        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
        tv_type = (TextView) findViewById(R.id.tv_type);
        cb_finish = (RadioButton) findViewById(R.id.cb_finish);
        cb_halffinish = (RadioButton) findViewById(R.id.cb_halffinish);
        cb_nofinish = (RadioButton) findViewById(R.id.cb_nofinsih);
        btn_query = (Button) findViewById(R.id.btn_query);

    }

    @Override
    protected void initView() {
        showTitle("事件详情");

    }

    @Override
    protected void initEvent() {
        btn_query.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_query:
                if (cb_finish.isChecked()){
                    msg = "你确定该事件已完成？";
                    complete = "1";
                    needcoin = 4;
                }else if (cb_nofinish.isChecked()){
                    msg = "你确定该事件未完成？";
                    complete = "2";
                    needcoin = 0;
                }else if (cb_halffinish.isChecked()){
                    msg = "你确定该事件已完成50%？";
                    complete = "3";
                    needcoin = 2;
                }else {
                    ToastHelper.showToast("请先确定完成度，谢谢！", this);
                }
                BuildDialog.myDialog().showDialog(this, "开工时间到", msg, MyDialog.HAVEBUTTON);
                BuildDialog.myDialog().ButtonCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BuildDialog.myDialog().DismissDialog();

                    }
                });
                BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BuildDialog.myDialog().DismissDialog();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String time = formatter.format(curDate);

                        dao.updateComplete(msgid, complete);
                        SpUtils.setCache(LookEventActivity.this, SpUtils.COIN, String.valueOf(coincount + needcoin));
                        coinDao.add(needcoin, time.substring(0, 4), time.substring(4, 6), null, time.substring(6, 8), 0);
                        ToastHelper.showToast("恭喜，你获得"+ needcoin +"个金币！", LookEventActivity.this);
                        finish();
                    }
                });
                break;
        }

    }


}
