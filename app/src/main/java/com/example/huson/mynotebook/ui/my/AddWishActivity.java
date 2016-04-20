package com.example.huson.mynotebook.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.base.BaseHeadActivity;
import com.example.huson.mynotebook.db.WishDao;
import com.example.huson.mynotebook.utils.BuildDialog;
import com.example.huson.mynotebook.utils.DebugLog;
import com.example.huson.mynotebook.utils.ToastHelper;
import com.example.huson.mynotebook.view.MyDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Huson on 2016/3/31.
 * 940762301@qq.com
 */
public class AddWishActivity extends BaseHeadActivity {
    private EditText et_content;
    private EditText et_wish_ture_time;
    private EditText et_wish_need_coin;
    private LinearLayout ll_wish_ture_time;
    private Button btn_query;

    private WishDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);
    }

    @Override
    protected void initData() {
        dao = new WishDao(this);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        et_wish_ture_time.setText(str);

    }

    @Override
    protected void findView() {
        et_content = (EditText) findViewById(R.id.et_wish_context);
        et_wish_ture_time = (EditText) findViewById(R.id.et_wish_ture);
        et_wish_need_coin = (EditText) findViewById(R.id.et_wish_need_coin);
        ll_wish_ture_time =(LinearLayout) findViewById(R.id.ll_wish_ture_time);
        btn_query = (Button) findViewById(R.id.btn_query);

    }

    @Override
    protected void initView() {
        showTitle("许愿墙");
        showBack(this);

    }

    @Override
    protected void initEvent() {
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String time = formatter.format(curDate);
                dao.add(1, Integer.valueOf(et_wish_need_coin.getText().toString()), et_content.getText().toString(),
                        time, et_wish_ture_time.getText().toString(), 0);
                ToastHelper.showToast("许愿成功",AddWishActivity.this);
                finish();
                DebugLog.e(time);
                DebugLog.e(et_wish_ture_time.getText().toString());
            }
        });

        et_wish_ture_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildDialog.myDialog().showDialog(AddWishActivity.this, "请选择日期", null, MyDialog.DATEPICKER);
                BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_wish_ture_time.setText(BuildDialog.myDialog().getWholeDate());
                        BuildDialog.myDialog().DismissDialog();
                    }
                });
                BuildDialog.myDialog().ButtonCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BuildDialog.myDialog().DismissDialog();
                    }
                });
            }
        });

    }
}
