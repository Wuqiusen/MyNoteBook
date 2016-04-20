package com.example.huson.mynotebook.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.base.BaseHeadActivity;
import com.example.huson.mynotebook.db.CoinDao;
import com.example.huson.mynotebook.db.DataDao;
import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.utils.BuildDialog;
import com.example.huson.mynotebook.utils.SpUtils;
import com.example.huson.mynotebook.view.MyDialog;

import java.util.List;

/**
 * Created by Huson on 2016/3/30.
 * 940762301@qq.com
 */
public class MyActivity extends BaseHeadActivity implements View.OnClickListener {
    private TextView tv_coin;
    private LinearLayout ll_my_wish;
    private LinearLayout ll_analyze;
    private LinearLayout ll_structure_analyze;
    private LinearLayout ll_setting;
    private TextView tv_user_name;


    private DataDao dao;
    private List<DataInfo> infos;

    private CoinDao coinDao = new CoinDao(this);

    public final static String WISH = "wish";
    public final static String ANALYZE = "analyze";
    public final static String ST_ANALYZE = "structure_analyze";
    public final static String SETTING = "setting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    @Override
    protected void initData() {
//        dao = new DataDao(this);
//        infos = dao.findAll();
//        int coin_count = 0;
//        for (int i = 0; i < infos.size(); i++){
//            coin_count = infos.get(i).getIsread() + coin_count;
//        }


        tv_coin.setText(String.valueOf(coinDao.findAll()));

    }

    @Override
    protected void findView() {
        tv_coin = (TextView) findViewById(R.id.tv_coin);
        ll_analyze = (LinearLayout) findViewById(R.id.ll_analyze);
        ll_my_wish = (LinearLayout) findViewById(R.id.ll_my_wish);
        ll_structure_analyze = (LinearLayout) findViewById(R.id.ll_structure_analyze);
        ll_setting = (LinearLayout) findViewById(R.id.ll_setting);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);

    }

    @Override
    protected void initView() {
        showTitle("个人中心");
        tv_user_name.setText(SpUtils.getCache(MyActivity.this, SpUtils.NAME));

    }

    @Override
    protected void initEvent() {
        ll_structure_analyze.setOnClickListener(this);
        ll_my_wish.setOnClickListener(this);
        ll_analyze.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        tv_user_name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BuildDialog.myDialog().showDialog(MyActivity.this, "请输入你的昵称", null, MyDialog.EDITTEXT);
                BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SpUtils.setCache(MyActivity.this, SpUtils.NAME, BuildDialog.myDialog().getEtText());
                        tv_user_name.setText(SpUtils.getCache(MyActivity.this, SpUtils.NAME));
                        BuildDialog.myDialog().DismissDialog();
                    }
                });
                BuildDialog.myDialog().ButtonCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BuildDialog.myDialog().DismissDialog();
                    }
                });

                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.ll_analyze:
                intent.setClass(this, MyListviewActivity.class);
                intent.putExtra("type", ANALYZE);
                break;
            case R.id.ll_my_wish:
                intent.setClass(this, MyWishActivity.class);
                break;
            case R.id.ll_structure_analyze:
                intent.setClass(this, MyListviewActivity.class);
                intent.putExtra("type", ST_ANALYZE);
                break;
            case R.id.ll_setting:
                intent.setClass(this, MyListviewActivity.class);
                intent.putExtra("type", SETTING);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_coin.setText(String.valueOf(coinDao.findAll()));
    }
}
