package com.example.huson.mynotebook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.base.BaseHeadFragment;
import com.example.huson.mynotebook.ui.AddEventActivity;
import com.example.huson.mynotebook.ui.my.MyActivity;
import com.example.huson.mynotebook.utils.ToastHelper;
import com.example.huson.mynotebook.view.datepicker.MonthDateView;

import java.util.List;

/**
 * Created by Huson on 2016/3/25.
 * 940762301@qq.com
 */
public class TimeTubeFragment  extends BaseHeadFragment{
    private View rootView;
    private ImageView iv_left;
    private ImageView iv_right;
    private TextView tv_date;
    private TextView tv_week;
    private TextView tv_today;
    private MonthDateView monthDateView;
    private List<Integer> list ;

    private Button btn_my;//测试用

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
//        list = new ArrayList<Integer>();
//        list.add(10);
//        list.add(12);
//        list.add(15);
//        list.add(16);
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_timetube, container, false);
        rootView = setContentView(rootView);
        findView();
        initView();
        initEvent();
        return rootView;
    }

    private void initEvent() {
        monthDateView.setTextView(tv_date,tv_week);
        monthDateView.setDaysHasThingList(list);
        monthDateView.setDateClick(new MonthDateView.DateClick() {

            @Override
            public void onClickOnDate() {
                if (monthDateView.getmSelDay() == 0){
                    ToastHelper.showToast("请选择你要添加的日期", getActivity());
                    return;
                }
                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                intent.putExtra("year", String.valueOf(monthDateView.getmSelYear()));
                intent.putExtra("month", String.valueOf(monthDateView.getmSelMonth()));
                intent.putExtra("day", String.valueOf(monthDateView.getmSelDay()));
                intent.putExtra("week", String.valueOf(monthDateView.getWeekRow()));
                startActivity(intent);
            }
        });
        setOnlistener();

    }

    private void initView() {
        showTitle("时光之旅");
//        showHeadRightButton("个人中心", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MyActivity.class);
//                getActivity().startActivity(intent);
//            }
//        });
        showRightImg(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyActivity.class);
                getActivity().startActivity(intent);
            }
        });


    }

    private void findView() {
        iv_left = (ImageView) rootView.findViewById(R.id.iv_left);
        iv_right = (ImageView) rootView.findViewById(R.id.iv_right);
        monthDateView = (MonthDateView) rootView.findViewById(R.id.monthDateView);
        tv_date = (TextView) rootView.findViewById(R.id.date_text);
        tv_week  =(TextView) rootView.findViewById(R.id.week_text);
        tv_today = (TextView) rootView.findViewById(R.id.tv_today);

        btn_my = (Button) rootView.findViewById(R.id.btn_my);
//        btn_my.setVisibility(View.GONE);//测试使用

    }
    private void setOnlistener(){
        iv_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.onLeftClick();
            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.onRightClick();
            }
        });

        tv_today.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.setTodayToView();
            }
        });

        btn_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }
}
