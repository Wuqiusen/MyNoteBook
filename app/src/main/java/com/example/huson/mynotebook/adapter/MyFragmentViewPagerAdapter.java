package com.example.huson.mynotebook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Huson on 2016/3/25.
 * 940762301@qq.com
 */
public class MyFragmentViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    public MyFragmentViewPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }


}