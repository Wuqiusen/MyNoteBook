package com.example.huson.mynotebook.ui.my;

import android.os.Bundle;
import android.widget.ListView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.base.BaseHeadActivity;

/**
 * Created by Huson on 2016/3/30.
 * 940762301@qq.com
 */
public class MyListviewActivity extends BaseHeadActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void findView() {
        lv = (ListView) findViewById(R.id.lv_layout);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }
}
