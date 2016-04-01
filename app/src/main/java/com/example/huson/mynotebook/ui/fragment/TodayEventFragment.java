package com.example.huson.mynotebook.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.adapter.TodayEventAdapter;
import com.example.huson.mynotebook.base.BaseHeadFragment;
import com.example.huson.mynotebook.db.DataDao;
import com.example.huson.mynotebook.domain.DataInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Huson on 2016/3/25.
 * 940762301@qq.com
 */
public class TodayEventFragment extends BaseHeadFragment {
    private View rootView;
    private ListView lv_data;

    private DataDao dao;
    private List<DataInfo> infos;
    private TodayEventAdapter madapter;
    private String time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            //返回原有的布局对象  不往下走了
            return rootView;
        }
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_todayevent, container, false);
        rootView = setContentView(rootView);
        findView();
        initData();
        initView();
        initEvent();
        return rootView;
    }

    private void initData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        time = formatter.format(curDate);
        dao = new DataDao(mContext);
        infos = dao.checkbyDay(time.substring(0, 4), time.substring(4, 6), time.substring(6, 8));
        madapter = new TodayEventAdapter(mContext, R.layout.item_datainfo, infos);
        lv_data.setAdapter(madapter);
        madapter.notifyDataSetChanged();//-->从新调用getcount 调用getview
    }

    private void initEvent() {

    }

    private void initView() {
        showTitle("今天事件");

    }

    private void findView() {
        lv_data = (ListView) rootView.findViewById(R.id.lv_today_event);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (dao != null){
            infos = dao.checkbyDay(time.substring(0, 4), time.substring(4, 6), time.substring(6, 8));
            if (madapter != null){
                madapter.clear();
                madapter.addAll(infos);
            }
        }
    }
}
