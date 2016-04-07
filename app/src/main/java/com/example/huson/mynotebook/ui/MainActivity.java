package com.example.huson.mynotebook.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.db.TypeDao;
import com.example.huson.mynotebook.ui.fragment.TimeTubeFragment;
import com.example.huson.mynotebook.ui.fragment.TodayEventFragment;
import com.example.huson.mynotebook.adapter.MyFragmentViewPagerAdapter;
import com.example.huson.mynotebook.base.BaseHeadActivity;
import com.example.huson.mynotebook.utils.SpUtils;
import com.example.huson.mynotebook.view.CustomViewPager;

import java.util.ArrayList;


public class MainActivity extends BaseHeadActivity implements View.OnClickListener {

    private CustomViewPager mViewPager;
    private RadioButton rb_timetube;
    private RadioButton rb_todayevent;
    private ArrayList<Fragment> tabFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpUtils.setCache(this, SpUtils.COIN, "0");//设置金币

    }

    @Override
    protected void initData() {
        TypeDao typeDao = new TypeDao(this);
        if (typeDao.findAll().isEmpty()){
            typeDao.add("类别", "事件", null);
            typeDao.add("学习", "事件", null);
            typeDao.add("工作", "事件", null);
            typeDao.add("生活", "事件", null);
        }
    }

    protected void findView() {
        mViewPager = (CustomViewPager) findViewById(R.id.vp_main);
        rb_timetube = (RadioButton) findViewById(R.id.rb_timetube);
        rb_todayevent = (RadioButton) findViewById(R.id.rb_todayevent);
    }

    protected void initView() {
        TimeTubeFragment fragment1 = new TimeTubeFragment();
        TodayEventFragment fragment2 = new TodayEventFragment();
        tabFragments = new ArrayList<>();
        tabFragments.add(fragment1);
        tabFragments.add(fragment2);
        MyFragmentViewPagerAdapter mAdapter = new MyFragmentViewPagerAdapter(getSupportFragmentManager(),tabFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setPagingEnabled(false);
        rb_timetube.setChecked(true);
        hideHeadArea();
    }

    protected void initEvent() {
        rb_timetube.setOnClickListener(this);
        rb_todayevent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_timetube:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rb_todayevent:
                mViewPager.setCurrentItem(1);
                break;
        }
    }


    // 双击back键，退出应用
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}