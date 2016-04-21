package com.example.huson.mynotebook.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.adapter.MyFragmentViewPagerAdapter;
import com.example.huson.mynotebook.base.BaseActivity;
import com.example.huson.mynotebook.base.BaseHeadActivity;
import com.example.huson.mynotebook.db.CoinDao;
import com.example.huson.mynotebook.db.TypeDao;
import com.example.huson.mynotebook.domain.ChartData;
import com.example.huson.mynotebook.domain.Day;
import com.example.huson.mynotebook.ui.fragment.TimeTubeFragment;
import com.example.huson.mynotebook.ui.fragment.TodayEventFragment;
import com.example.huson.mynotebook.view.CustomViewPager;
import com.example.huson.mynotebook.view.datepicker.DateUtils;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Huson on 2016/4/21.
 * 940762301@qq.com
 */
public class HaveTopImgActivity extends BaseHeadActivity implements View.OnClickListener{
    private TextView tv_coin;
    private TextView tv_near_coin;
    private Button btn_add;
    private LinearLayout ll_stacked;
    private RadioButton rb_timetube;
    private RadioButton rb_todayevent;
//    private ArrayList<Fragment> tabFragments;
//    private CustomViewPager mViewPager;

    private CoinDao coinDao = new CoinDao(this);
    private List<Day> days;
    private ArrayList<ChartData> chartDatas = new ArrayList<ChartData>();
    private double  maxValue = 0; //数据的最大值
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_img_two);
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

        tv_coin.setText(String.valueOf(coinDao.findAll()));
        //获取今天日期
        Calendar calendar = Calendar.getInstance();
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DATE);
        days = DateUtils.getNearSevenDay(Year, Month, Day);

        for (Day day: days){
            int coinCount = coinDao.checkbyDay(String.valueOf(day.getYear()), String.valueOf(day.getMonth()),String.valueOf(day.getDay()));
            chartDatas.add(new ChartData(String.valueOf(day.getDay()), coinCount, ""));
            //算出所有数据的最大值
                double value = Double.parseDouble(String.valueOf(coinCount));
                if (value > maxValue)
                {
                    maxValue = value;
                }

            }
            maxValue = maxValue + (maxValue/8);   //让左边刻度线高出数字的最大值

    }

    @Override
    protected void findView() {
        tv_coin = (TextView) findViewById(R.id.tv_coin);
        tv_near_coin = (TextView) findViewById(R.id.tv_near_coin);
        btn_add = (Button) findViewById(R.id.btn_add);
        ll_stacked = (LinearLayout) findViewById(R.id.ll_stacked);
        rb_timetube = (RadioButton) findViewById(R.id.rb_timetube);
        rb_todayevent = (RadioButton) findViewById(R.id.rb_todayevent);

        rb_timetube.setChecked(false);
        rb_timetube.setChecked(false);

    }

    @Override
    protected void initView() {
        view = ChartFactory.getBarChartView(this, getBarDataset(chartDatas), getBarRenderer(chartDatas), BarChart.Type.STACKED); //Type.STACKED
        view.setBackgroundColor(Color.WHITE);
        ll_stacked.addView(view);
//        TimeTubeFragment fragment1 = new TimeTubeFragment();
//        TodayEventFragment fragment2 = new TodayEventFragment();
//        tabFragments = new ArrayList<>();
//        tabFragments.add(fragment1);
//        tabFragments.add(fragment2);
//        MyFragmentViewPagerAdapter mAdapter = new MyFragmentViewPagerAdapter(getSupportFragmentManager(),tabFragments);
//        mViewPager.setAdapter(mAdapter);
        hideHeadArea();

    }

    @Override
    protected void initEvent() {
        rb_timetube.setOnClickListener(this);
        rb_todayevent.setOnClickListener(this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HaveTopImgActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (v.getId()){
            case R.id.rb_timetube:
                intent.putExtra("page", "0");
//                mViewPager.setCurrentItem(0);
                break;
            case R.id.rb_todayevent:
                intent.putExtra("page", "1");
//                mViewPager.setCurrentItem(1);
                break;
        }
        startActivity(intent);
    }

    // 数据设置

    private XYMultipleSeriesDataset getBarDataset(ArrayList<ChartData> chartDataArrayList)
    {

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        CategorySeries series = new CategorySeries("最近七天金币数");
        // 声明一个柱形图
        // 为柱形图添加值
        for (ChartData chartData : chartDataArrayList)
        {
            series.add(Double.parseDouble(String.valueOf(chartData.count)));
        }

        dataset.addSeries(series.toXYSeries());// 添加该柱形图到数据设置列表




        return dataset;
    }

    // 描绘器设置

    public XYMultipleSeriesRenderer getBarRenderer(ArrayList<ChartData> chartDataArrayList)
    {

        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        // 通过SimpleSeriesDenderer设置描绘器的颜色

        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.rgb(1, 128, 205)); //定义柱状图的颜色
        renderer.addSeriesRenderer(r);



        setChartSettings(renderer, chartDataArrayList);// 设置描绘器的其他属性

        return renderer;
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer, ArrayList<ChartData> chartDataArrayList)
    {

//		renderer.setChartTitle("个人收支表");// 设置柱图名称
//		renderer.setXTitle("名单");// 设置X轴名称
//		renderer.setYTitle("数量");// 设置Y轴名称

        renderer.setXAxisMin(0.5);// 设置X轴的最小值为0.5
        renderer.setXAxisMax(5.5);// 设置X轴的最大值为5
        renderer.setYAxisMin(0);// 设置Y轴的最小值为0
        renderer.setYAxisMax(maxValue);// 设置Y轴最大值为500
        renderer.setDisplayChartValues(true); // 设置是否在柱体上方显示值
        renderer.setShowGrid(true);// 设置是否在图表中显示网格
        renderer.setXLabels(0);// 设置X轴显示的刻度标签的个数
        renderer.setBarSpacing(0.2);  //柱状间的间隔
        renderer.setZoomButtonsVisible(true);
        renderer.setMarginsColor(getResources().getColor(R.color.white));
        renderer.setGridColor(getResources().getColor(R.color.white));
        //为X轴的每个柱状图设置底下的标题 比如  福建 ，广东.....
        int count=1;
        for (ChartData chartData : chartDataArrayList)
        {
            renderer.addXTextLabel(count,chartData.type);
            count++;
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

    @Override
    protected void onResume() {
        super.onResume();
        tv_coin.setText(String.valueOf(coinDao.findAll()));
        rb_timetube.setChecked(false);
        rb_timetube.setChecked(false);
        view.invalidate();
        ll_stacked.removeAllViews();
        ll_stacked.addView(view);
    }
}
