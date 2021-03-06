package com.example.huson.mynotebook.ui.my;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.adapter.MySpinnerAdapter;
import com.example.huson.mynotebook.adapter.MyTypeSpinnerAdapter;
import com.example.huson.mynotebook.adapter.MyWishAdapter;
import com.example.huson.mynotebook.adapter.STSpinnerAdapter;
import com.example.huson.mynotebook.adapter.TodayEventAdapter;
import com.example.huson.mynotebook.adapter.TypeAdapter;
import com.example.huson.mynotebook.base.BaseHeadActivity;
import com.example.huson.mynotebook.db.DataDao;
import com.example.huson.mynotebook.db.TypeDao;
import com.example.huson.mynotebook.db.WishDao;
import com.example.huson.mynotebook.domain.ChartData;
import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.domain.TypeInfo;
import com.example.huson.mynotebook.domain.WishInfo;
import com.example.huson.mynotebook.utils.BuildDialog;
import com.example.huson.mynotebook.utils.ColorUtil;
import com.example.huson.mynotebook.utils.DebugLog;
import com.example.huson.mynotebook.utils.ToastHelper;
import com.example.huson.mynotebook.view.MyDialog;
import com.example.huson.mynotebook.view.PieChart.PieChart;
import com.example.huson.mynotebook.view.PieChart.TitleValueColorEntity;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Huson on 2016/3/30.
 * 940762301@qq.com
 */
public class MyListviewActivity extends BaseHeadActivity {
    private ListView lv;
    private Spinner sp_isfinish;
    private Spinner sp_type;
    private LinearLayout ll_sp;
    private LinearLayout ll_add_view;
    private View view;

    private List<TypeInfo> typeinfo;
    private List<Integer> typecount = new ArrayList<Integer>();
    private List<DataInfo> datainfo;
    private List<WishInfo> wishinfo;
    private List<String> isfinish = new ArrayList<String>();
    private List<String> st_type = new ArrayList<String>();
    private List<String> stype;
    private MySpinnerAdapter mAdapter;
    private MySpinnerAdapter SptypeAdapter;
    private MyTypeSpinnerAdapter myTypeSpinnerAdapter;
    private TypeAdapter typeAdapter;
    private TodayEventAdapter dataAdapter;
    private STSpinnerAdapter STtypeAdapter;
    private int typeNo = 0;
    private String type;
    private int isfinishNo = 0;

    private TypeDao typeDao;
    private DataDao dataDao;
    private WishDao wishDao;

    private String type_name;
    private String title_name;

    private PieChart piechart;

    private int[] colorsArr;
    private ArrayList<ChartData> chartDatas;

    private int[]  color = {R.color.orange,R.color.yellow,R.color.lightgreen,R.color.green,R.color.red};

    private final int NO_CONTEXT = 111;

    public android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{switch(msg.what) {
                case NO_CONTEXT:
                    view.invalidate();
                    ll_add_view.addView(view);
                    ll_add_view.invalidate();
                    break;
            }

            }catch (Exception e){
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String mtype = intent.getStringExtra("type");
        typeDao = new TypeDao(this);
        typeinfo = typeDao.findAll();
        type = typeinfo.get(0).getType();
        DebugLog.e("++++++++++++++++++" + typeinfo.size());
        DebugLog.e("++++++++++++++++++" + typeinfo.get(0).getType());
        if (mtype.equals(MyActivity.SETTING)){
            ll_add_view.setVisibility(View.GONE);
            ll_sp.setVisibility(View.GONE);
            title_name = "事件分类";
            showRightButton(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BuildDialog.myDialog().showDialog(MyListviewActivity.this, "添加类别",null, MyDialog.EDITTEXT);
                    BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (BuildDialog.myDialog().getEtText().isEmpty()) {
                                ToastHelper.showToast("你的输入有误", MyListviewActivity.this);
                            } else {
                                type_name = BuildDialog.myDialog().getEtText();
                                typeDao.add(type_name, "事件", null);
                                if (typeDao != null){
                                    typeinfo = typeDao.findAll();
                                    if (typeAdapter != null){
                                        typeAdapter.clear();
                                        typeAdapter.addAll(typeinfo);

                                    }
                                }
                            }
                            BuildDialog.myDialog().DismissDialog();
                        }
                    });
                    BuildDialog.myDialog().ButtonCancel(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BuildDialog.myDialog().DismissDialog();
                        }
                    });
                }
            }, "添加");
            typeAdapter = new TypeAdapter(this, R.layout.item_datainfo, typeinfo);
            lv.setAdapter(typeAdapter);
            typeAdapter.notifyDataSetChanged();//-->从新调用getcount 调用getview
        }else if (mtype.equals(MyActivity.ANALYZE)){
            ll_add_view.setVisibility(View.GONE);
            isfinish.add("全部");
            isfinish.add("已完成");
            isfinish.add("未完成");
            title_name = "分类查看";
            mAdapter = new MySpinnerAdapter(this, R.layout.item_spinner, isfinish);
            myTypeSpinnerAdapter = new MyTypeSpinnerAdapter(this, R.layout.item_spinner, typeinfo);
            dataDao = new DataDao(this);
            SpinnerisFinish(sp_isfinish);
            SpinnerType(sp_type);
            datainfo = dataDao.findAll();
            dataAdapter = new TodayEventAdapter(this, R.layout.item_datainfo, datainfo);
            lv.setAdapter(dataAdapter);
            dataAdapter.notifyDataSetChanged();//-->从新调用getcount 调用getview
        }else if (mtype.equals(MyActivity.ST_ANALYZE)){
            lv.setVisibility(View.GONE);
            ll_add_view.setVisibility(View.VISIBLE);
            isfinish.add("全部");
            isfinish.add("月");
            isfinish.add("周");
            isfinish.add("日");
            title_name = "结构分析";
            chartDatas = new ArrayList<ChartData>();
            colorsArr = new int[typeinfo.size() - 1];
//            initPieChart();
            mAdapter = new MySpinnerAdapter(this, R.layout.item_spinner, isfinish);
            dataDao =new DataDao(this);
            STisfinish(sp_isfinish);
            stype = dataDao.checkType(isfinishNo);
            SptypeAdapter = new MySpinnerAdapter(this, R.layout.item_spinner, stype);
            STtype(sp_type);

        }

    }

    private void STtype(Spinner spinner) {
        sp_type.setAdapter(SptypeAdapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               typeNo = position;
               if (isfinishNo == 0)
                   return;
               type = typeinfo.get(position).getType();
               List<TitleValueColorEntity> data3 = new ArrayList<TitleValueColorEntity>();
               if (!chartDatas.isEmpty())
                   chartDatas.clear();
               for (int i = 0; i < typeinfo.size(); i++) {
                   int count = dataDao.checkTypeCount(isfinishNo, stype.get(position), typeinfo.get(i).getType());
                   DebugLog.e("count++++++++" + count);
                   typecount.add(count);
                   DebugLog.e("typecount++++++++" + typecount.getClass().getName());
                   DebugLog.e("Type++++++++" + typeinfo.get(i).getType());
                   if (i != typeinfo.size() - 1)
                       colorsArr[i] = ColorUtil.colorLib[i];

                   data3.add(new TitleValueColorEntity(typeinfo.get(i).getType(), count, color[i]));

                   if (i != 0) {
                       chartDatas.add(new ChartData(typeinfo.get(i).getType(), count, ""));
                       DebugLog.e(count + "============================");
                   }

               }

               DefaultRenderer renderer = buildCategoryRenderer(colorsArr); //把分布的颜色传给渲染器
               renderer.setZoomButtonsVisible(true);
               renderer.setZoomEnabled(true);
               renderer.setChartTitleTextSize(20);
               renderer.setInScroll(true);

               if (!chartDatas.isEmpty()) {
                   //        View view = ChartFactory.getPieChartView(this, buildCategoryDataset("Project budget", values), renderer);
                   //饼状图文字信息和对应的百分比
                   view = ChartFactory.getPieChartView(MyListviewActivity.this, buildCategoryDataset("Project budget", chartDatas), renderer);
                   view.setBackgroundColor(Color.WHITE);
                   view.postInvalidate();


               }

               ll_add_view.removeAllViews();
               ll_add_view.addView(view);

//               if (!data3.isEmpty())
//                   piechart.setData(data3);
//               piechart.invalidate();

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }

    @Override
    protected void findView() {
        lv = (ListView) findViewById(R.id.lv_layout);
        sp_isfinish = (Spinner) findViewById(R.id.sp_isfinish);
        sp_type = (Spinner) findViewById(R.id.sp_type);
        ll_sp = (LinearLayout) findViewById(R.id.ll_sp);
        ll_add_view = (LinearLayout) findViewById(R.id.ll_add_view);

    }

    @Override
    protected void initView() {
        showTitle(title_name);

    }

    @Override
    protected void initEvent() {

    }

    private void STisfinish(Spinner spinner){
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DebugLog.e(String.valueOf(position));
                isfinishNo = position;
                typeNo = 0;
                if (dataDao != null) {
                    stype = dataDao.checkType(isfinishNo);//获取相对应月、周、日的数据
                    if (SptypeAdapter != null) {
                        SptypeAdapter.clear();
                        SptypeAdapter.addAll(stype);

                    }
                }
                List<TitleValueColorEntity> data3 = new ArrayList<TitleValueColorEntity>();
                if (!chartDatas.isEmpty())
                    chartDatas.clear();
                for (int i = 0; i < typeinfo.size(); i++) {
                    int count = dataDao.checkTypeCount(isfinishNo, stype.get(typeNo), typeinfo.get(i).getType());
                    DebugLog.e("count++++++++" + count);
                    typecount.add(count);
                    DebugLog.e("typecount++++++++" + typecount.getClass().getName());
                    DebugLog.e("Type++++++++" + typeinfo.get(i).getType());
                    if (i != typeinfo.size() - 1) {
                        colorsArr[i] = ColorUtil.colorLib[i];
                    }

                    data3.add(new TitleValueColorEntity(typeinfo.get(i).getType(), count, color[i]));

                    if (i != 0) {
                        chartDatas.add(new ChartData(typeinfo.get(i).getType(), count, ""));
                    }

                }

                DefaultRenderer renderer = buildCategoryRenderer(colorsArr); //把分布的颜色传给渲染器
                renderer.setZoomButtonsVisible(true);
                renderer.setZoomEnabled(true);
                renderer.setChartTitleTextSize(20);
                renderer.setInScroll(true);

                if (!chartDatas.isEmpty()) {
                    //        View view = ChartFactory.getPieChartView(this, buildCategoryDataset("Project budget", values), renderer);
                    //饼状图文字信息和对应的百分比
                    view = ChartFactory.getPieChartView(MyListviewActivity.this, buildCategoryDataset("Project budget", chartDatas), renderer);
                    view.setBackgroundColor(Color.WHITE);
                    view.postInvalidate();
                }
                ll_add_view.removeAllViews();
                ll_add_view.addView(view);

//               if (!data3.isEmpty())
//                   piechart.setData(data3);
//               piechart.invalidate();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SpinnerisFinish(Spinner spinner){
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DebugLog.e(String.valueOf(position));
                isfinishNo = position;
                if (position == 0){
                    datainfo = dataDao.checkbyType(type);
                }else {
                    datainfo = dataDao.checkbyIsfinishAndType(String.valueOf(isfinishNo), type);
                }
                if (dataAdapter != null){
                    dataAdapter.clear();
                    dataAdapter.addAll(datainfo);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void SpinnerType(Spinner spinner){
        spinner.setAdapter(myTypeSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DebugLog.e(String.valueOf(position));
                typeNo = position;
                type = typeinfo.get(position).getType();
                if (position == 0){
                    datainfo = dataDao.checkbyIsfinish(String.valueOf(isfinishNo));

                }else {
                    datainfo = dataDao.checkbyIsfinishAndType(String.valueOf(isfinishNo), type);
                }
                if (dataAdapter != null){
                    dataAdapter.clear();
                    dataAdapter.addAll(datainfo);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 随机生成颜色
     * @return 随机生成的十六进制颜色
     */

    private String randomColor(){
        int colorStr=(int)Math.floor(Math.random() * 0xFFFFFF);

        return "#"+Integer.toHexString(colorStr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DebugLog.e("onResume");
    }

    /**
     * 把分布的颜色传给渲染器
     * @param colors
     * @return
     */
    protected DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(20);//设置字体大小
        renderer.setLegendTextSize(20);
        renderer.setMargins(new int[]{20, 30, 15, 0});// 设置边距,上左下右
//        renderer.setChartTitle(title);
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }


    /**
     * 饼状图文字信息
     * @return
     */
    protected CategorySeries buildCategoryDataset(String title, ArrayList<ChartData> list) {
        CategorySeries series = null;
        if (series != null){
            series.clear();
        }else {
            series =  new CategorySeries(title);
        }

        DecimalFormat df = new DecimalFormat("###.00");
        float All = 0.0f;

        //计算百分比
        for (ChartData bfenbi : list){
            All = All + bfenbi.count;

        }

        //根据list值分配视图 颜色
        for (ChartData chartData : list)
        {
            String bfb = "";
            double num =  Double.parseDouble(String.valueOf(chartData.count));
            float count = chartData.count;
            if (All == 0){
                bfb = "0";
            }else {
                bfb =String.valueOf(count/All*100);
            }
            DebugLog.e("count:" + chartData.count);
            DebugLog.e("bfb:" + bfb);

            series.add(chartData.type + " (" + chartData.count + chartData.dw + ")" + bfb + "%", num);
        }
        return series;
    }



}
