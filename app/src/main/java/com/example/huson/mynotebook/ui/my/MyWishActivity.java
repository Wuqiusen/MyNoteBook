package com.example.huson.mynotebook.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.adapter.MyWishAdapter;
import com.example.huson.mynotebook.adapter.TodayEventAdapter;
import com.example.huson.mynotebook.base.BaseHeadActivity;
import com.example.huson.mynotebook.db.DataDao;
import com.example.huson.mynotebook.db.WishDao;
import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.domain.WishInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Huson on 2016/3/31.
 * 940762301@qq.com
 */
public class MyWishActivity extends BaseHeadActivity {
    private Button btn_add_wish;
    private ListView lv_wish;

    private WishDao dao;
    private List<WishInfo> infos;
    private MyWishAdapter madapter;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywish);
    }

    @Override
    protected void initData() {
        dao = new WishDao(this);
        infos = dao.findAll();
        madapter = new MyWishAdapter(MyWishActivity.this, R.layout.item_wishinfo, infos);
        lv_wish.setAdapter(madapter);
        madapter.notifyDataSetChanged();//-->从新调用getcount 调用getview

    }

    @Override
    protected void findView() {
        btn_add_wish = (Button) findViewById(R.id.btn_add);
        lv_wish = (ListView) findViewById(R.id.lv_layout);
        lv_wish.setDivider(null);
        lv_wish.setDividerHeight(12);

    }

    @Override
    protected void initView() {
        showTitle("愿望墙");

    }

    @Override
    protected void initEvent() {
        btn_add_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWishActivity.this, AddWishActivity.class));

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dao != null){
            infos = dao.findAll();
            if (madapter != null){
                madapter.clear();
                madapter.addAll(infos);
            }
        }
    }
}
