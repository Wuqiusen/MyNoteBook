package com.example.huson.mynotebook.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.huson.mynotebook.ui.my.MyActivity;
import com.example.huson.mynotebook.ui.my.MyWishActivity;
import com.example.huson.mynotebook.utils.DebugLog;
import com.example.huson.mynotebook.utils.SpUtils;
import com.example.huson.mynotebook.view.CustomViewPager;
import com.example.huson.mynotebook.view.datepicker.DateUtils;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Huson on 2016/4/21.
 * 940762301@qq.com
 */
public class HaveTopImgActivity extends BaseHeadActivity implements View.OnClickListener {
    private TextView tv_coin;
    private TextView tv_near_coin;
    private Button btn_add;
    private LinearLayout ll_stacked;
    private RadioButton rb_timetube;
    private RadioButton rb_todayevent;
    private ImageView img_my;
    private TextView tv_wish;
//    private ArrayList<Fragment> tabFragments;
//    private CustomViewPager mViewPager;

    private CoinDao coinDao = new CoinDao(this);
    private List<Day> days;
    private ArrayList<ChartData> chartDatas = new ArrayList<ChartData>();
    private double maxValue = 0; //数据的最大值
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_img_two);
        Window window = getWindow();
         //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
         //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         //设置状态栏颜色
//        window.setStatusBarColor();
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
    }

    @Override
    protected void initData() {
        TypeDao typeDao = new TypeDao(this);
        if (typeDao.findAll().isEmpty()) {
            typeDao.add("类别", "事件", null);
            typeDao.add("学习", "事件", null);
            typeDao.add("工作", "事件", null);
            typeDao.add("生活", "事件", null);
        }

        tv_coin.setText(String.valueOf(coinDao.findAll()));
        //获取今天日期
        Calendar calendar = Calendar.getInstance();
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH) + 1;
        int Day = calendar.get(Calendar.DATE);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);

        String y = time.substring(0, 4);
        String m = time.substring(4, 6);
        String d = time.substring(6, 8);



//        String str[] = {"01","02","03","04","05","06", "07", "08", "09"};
//        if (Month <10){
//            String month = str[Month - 1];
//        }

        if (Day - 7 < 0){
            for (int j = 0; j < Day; j++){
                int coinCount = coinDao.checkbyDay(y, m, String.valueOf((Integer.valueOf(d) - j)));
                chartDatas.add(new ChartData(String.valueOf((Integer.valueOf(d) - j)), coinCount, ""));
                //算出所有数据的最大值
                double value = Double.parseDouble(String.valueOf(coinCount));
                DebugLog.e("================" + String.valueOf((Integer.valueOf(d) - j)));
                if (value > maxValue) {
                    maxValue = value;
                }

            }
            for (int i = 0; i < 7 - Day; i++){
//                int coinCount = coinDao.checkbyDay(String.valueOf(Year), String.valueOf(Month),
//                        String.valueOf((DateUtils.getMonthDays(Year, Month) - i)));
                int coinCount = coinDao.checkbyDay(y, String.valueOf((Integer.valueOf(m) - 1)),
                        String.valueOf((DateUtils.getMonthDays(Year, (Integer.valueOf(m) - 1)) - i)));
                chartDatas.add(new ChartData(String.valueOf((DateUtils.getMonthDays(Year, (Integer.valueOf(m) - 1)) - i)), coinCount, ""));
                //算出所有数据的最大值
                double value = Double.parseDouble(String.valueOf(coinCount));
                DebugLog.e("================" + (DateUtils.getMonthDays(Year, Month) - i));
                if (value > maxValue) {
                    maxValue = value;
                }

            }
        }else {
            for (int i = 0; i < 7 ; i++){
//                int coinCount = coinDao.checkbyDay(String.valueOf(Year), String.valueOf("0" + (Month + 1)), String.valueOf((Day - i)));
                int coinCount = coinDao.checkbyDay(y, m, String.valueOf((Integer.valueOf(d) - i)));
                chartDatas.add(new ChartData(String.valueOf((Integer.valueOf(d) - i)), coinCount, ""));
                //算出所有数据的最大值
                double value = Double.parseDouble(String.valueOf(coinCount));
                DebugLog.e("================" + String.valueOf(Year));
                DebugLog.e("================" + String.valueOf("0" + (Month + 1)));
                DebugLog.e("================" + String.valueOf((Day - i)));
                DebugLog.e("================" + coinCount);
                if (value > maxValue) {
                    maxValue = value;
                }
            }
        }
//
//        days = DateUtils.getNearSevenDay(Year, Month, Day);
//
//
//        for (Day day : days) {
//            int coinCount = coinDao.checkbyDay(String.valueOf(day.getYear()), String.valueOf(day.getMonth()), String.valueOf(day.getDay()));
//            chartDatas.add(new ChartData(String.valueOf(day.getDay()), coinCount, ""));
//            //算出所有数据的最大值
//            double value = Double.parseDouble(String.valueOf(coinCount));
//            DebugLog.e("================" +day.getDay());
//            if (value > maxValue) {
//                maxValue = value;
//            }
//
//        }
        maxValue = maxValue + (maxValue / 8);   //让左边刻度线高出数字的最大值

    }

    @Override
    protected void findView() {
        tv_coin = (TextView) findViewById(R.id.tv_coin);
        tv_near_coin = (TextView) findViewById(R.id.tv_near_coin);
        btn_add = (Button) findViewById(R.id.btn_add);
        ll_stacked = (LinearLayout) findViewById(R.id.ll_stacked);
        rb_timetube = (RadioButton) findViewById(R.id.rb_timetube);
        rb_todayevent = (RadioButton) findViewById(R.id.rb_todayevent);
        img_my = (ImageView) findViewById(R.id.img_my);
        tv_wish = (TextView) findViewById(R.id.tv_wish);

        rb_timetube.setChecked(false);
        rb_todayevent.setChecked(false);

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

        ContentResolver cr = this.getContentResolver();
        if (SpUtils.isHaveImg(this)){
            Uri ImgUri = Uri.parse(SpUtils.getCache(this, SpUtils.IMGURI));
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(ImgUri));
                /* 将Bitmap设定到ImageView */
                img_my.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                DebugLog.e(e.getMessage());
            }
        }

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
        img_my.setOnClickListener(this);
        tv_wish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rb_timetube:
                intent.setClass(this, MainActivity.class);
                intent.putExtra("page", "0");
//                mViewPager.setCurrentItem(0);
                break;
            case R.id.rb_todayevent:
                intent.setClass(this, MainActivity.class);
                intent.putExtra("page", "1");
//                mViewPager.setCurrentItem(1);
                break;
            case R.id.img_my:
                intent.setClass(this, MyActivity.class);
                break;
            case R.id.tv_wish:
                intent.setClass(this, MyWishActivity.class);
                break;
        }
        startActivity(intent);
    }

    // 数据设置

    private XYMultipleSeriesDataset getBarDataset(ArrayList<ChartData> chartDataArrayList) {

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        CategorySeries series = new CategorySeries("最近七天金币数");
        // 声明一个柱形图
        // 为柱形图添加值
        for (ChartData chartData : chartDataArrayList) {
            series.add(Double.parseDouble(String.valueOf(chartData.count)));
        }

        dataset.addSeries(series.toXYSeries());// 添加该柱形图到数据设置列表


        return dataset;
    }

    // 描绘器设置

    public XYMultipleSeriesRenderer getBarRenderer(ArrayList<ChartData> chartDataArrayList) {

        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        // 通过SimpleSeriesDenderer设置描绘器的颜色

        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.rgb(135, 206, 235)); //定义柱状图的颜色
        r.setChartValuesTextSize(26);
        renderer.addSeriesRenderer(r);
        setChartSettings(renderer, chartDataArrayList);// 设置描绘器的其他属性

        return renderer;
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer, ArrayList<ChartData> chartDataArrayList) {

		renderer.setChartTitle("最近七天金币数");// 设置柱图名称
        renderer.setChartTitleTextSize(30);
		renderer.setXTitle("日期");// 设置X轴名称
		renderer.setYTitle("数量");// 设置Y轴名称

        renderer.setXAxisMin(0.5);// 设置X轴的最小值为0.5
        renderer.setXAxisMax(7.5);// 设置X轴的最大值为5
        renderer.setYAxisMin(0);// 设置Y轴的最小值为0
        renderer.setYAxisMax(maxValue);// 设置Y轴最大值为500
        renderer.setDisplayChartValues(true); // 设置是否在柱体上方显示值
        renderer.setShowGrid(true);// 设置是否在图表中显示网格
        renderer.setXLabels(0);// 设置X轴显示的刻度标签的个数
        renderer.setBarSpacing(0.2);  //柱状间的间隔
        renderer.setZoomButtonsVisible(true);
        renderer.setMarginsColor(getResources().getColor(R.color.white));
        renderer.setGridColor(getResources().getColor(R.color.white));
        renderer.setPanEnabled(false, false);//允许左右拖动,但不允许上下拖动.
        renderer.setZoomButtonsVisible(false);//设置显示放大缩小按钮
        renderer.setZoomEnabled(true);//设置允许放大缩小.
        renderer.setZoomRate(1.0f);//放大的倍率
        renderer.setBarSpacing(3.1f);//柱子间宽度
        renderer.setLabelsTextSize(22);//设置字体大小
        renderer.setLabelsColor(Color.BLACK);
        renderer.setAxisTitleTextSize(22);
        renderer.setPointSize(10);
        renderer.setAxisTitleTextSize(25);
        //为X轴的每个柱状图设置底下的标题 比如  福建 ，广东.....
        int count = 1;
        for (ChartData chartData : chartDataArrayList) {
            renderer.addXTextLabel(count, chartData.type);
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
        rb_todayevent.setChecked(false);
        view.invalidate();
        ll_stacked.removeAllViews();
        ll_stacked.addView(view);
    }
}
