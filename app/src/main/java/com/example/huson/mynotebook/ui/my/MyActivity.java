package com.example.huson.mynotebook.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.base.BaseHeadActivity;
import com.example.huson.mynotebook.db.DataDao;
import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.utils.SpUtils;

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

    private DataDao dao;
    private List<DataInfo> infos;

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
        tv_coin.setText(SpUtils.getCache(this, SpUtils.COIN));

    }

    @Override
    protected void findView() {
        tv_coin = (TextView) findViewById(R.id.tv_coin);
        ll_analyze = (LinearLayout) findViewById(R.id.ll_analyze);
        ll_my_wish = (LinearLayout) findViewById(R.id.ll_my_wish);
        ll_structure_analyze = (LinearLayout) findViewById(R.id.ll_structure_analyze);
        ll_setting = (LinearLayout) findViewById(R.id.ll_setting);

    }

    @Override
    protected void initView() {
        showTitle("个人中心");

    }

    @Override
    protected void initEvent() {
        ll_structure_analyze.setOnClickListener(this);
        ll_my_wish.setOnClickListener(this);
        ll_analyze.setOnClickListener(this);
        ll_setting.setOnClickListener(this);

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
}
