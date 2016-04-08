package com.example.huson.mynotebook.ui.my;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.domain.TypeInfo;
import com.example.huson.mynotebook.domain.WishInfo;
import com.example.huson.mynotebook.utils.BuildDialog;
import com.example.huson.mynotebook.utils.DebugLog;
import com.example.huson.mynotebook.utils.ToastHelper;
import com.example.huson.mynotebook.view.MyDialog;
import com.example.huson.mynotebook.view.PieChart.PieChart;
import com.example.huson.mynotebook.view.PieChart.TitleValueColorEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Huson on 2016/3/30.
 * 940762301@qq.com
 */
public class MyListviewActivity extends BaseHeadActivity {
    private ListView lv;
    private Spinner sp_isfinish;
    private Spinner sp_type;
    private LinearLayout ll_sp;

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
    private int isfinishNo = 0;

    private TypeDao typeDao;
    private DataDao dataDao;
    private WishDao wishDao;

    private String type_name;
    private String title_name;

    private PieChart piechart;

    private int[]  color = {R.color.orange,R.color.yellow,R.color.lightgreen,R.color.green,R.color.red};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        typeDao = new TypeDao(this);
        typeinfo = typeDao.findAll();
        DebugLog.e("++++++++++++++++++" + typeinfo.size());
        DebugLog.e("++++++++++++++++++" + typeinfo.get(0).getType());
        if (type.equals(MyActivity.SETTING)){
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
        }else if (type.equals(MyActivity.ANALYZE)){
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
        }else if (type.equals(MyActivity.ST_ANALYZE)){
            isfinish.add("全部");
            isfinish.add("月");
            isfinish.add("周");
            isfinish.add("日");
            title_name = "结构分析";
            initPieChart();
            mAdapter = new MySpinnerAdapter(this, R.layout.item_spinner, isfinish);
            dataDao =new DataDao(this);
            STisfinish(sp_isfinish);
            stype = dataDao.checkType(isfinishNo);
            SptypeAdapter = new MySpinnerAdapter(this, R.layout.item_spinner, stype);
            STtype(sp_type);
            lv.setVisibility(View.GONE);
        }

    }

    private void STtype(Spinner spinner) {
        sp_type.setAdapter(SptypeAdapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               typeNo = position;
               List<TitleValueColorEntity> data3 = new ArrayList<TitleValueColorEntity>();
               for (int i = 0; i < typeinfo.size(); i++){
                   int count = dataDao.checkTypeCount(isfinishNo, stype.get(typeNo), i);
                   DebugLog.e("count++++++++" + count);
                   typecount.add(count);
                   DebugLog.e("typecount++++++++" + typecount.getClass().getName());
                   DebugLog.e("Type++++++++" + typeinfo.get(i).getType());

                   data3.add(new TitleValueColorEntity(typeinfo.get(i).getType(),count, color[i]));

//        data3.add(new TitleValueColorEntity("2",3,getResources().getColor(R.color.orange)));
//        data3.add(new TitleValueColorEntity("3",6,getResources().getColor(R.color.yellow)));
//        data3.add(new TitleValueColorEntity("4",2,getResources().getColor(R.color.lightgreen)));
//        data3.add(new TitleValueColorEntity("5", 2, getResources().getColor(R.color.green)));
               }
               if (!data3.isEmpty())
                   piechart.setData(data3);
               piechart.invalidate();

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
                if (dataDao != null){
                    stype = dataDao.checkType(isfinishNo);
                    if (SptypeAdapter != null){
                        SptypeAdapter.clear();
                        SptypeAdapter.addAll(stype);

                    }
                }
                List<TitleValueColorEntity> data3 = new ArrayList<TitleValueColorEntity>();
                for (int i = 0; i < typeinfo.size(); i++){
                    int count = dataDao.checkTypeCount(isfinishNo, stype.get(typeNo), i);
                    DebugLog.e("count++++++++" + count);
                    typecount.add(count);
                    DebugLog.e("typecount++++++++" + typecount.getClass().getName());
                    DebugLog.e("Type++++++++" + typeinfo.get(i).getType());

                    data3.add(new TitleValueColorEntity(typeinfo.get(i).getType(),count, color[i]));

//        data3.add(new TitleValueColorEntity("2",3,getResources().getColor(R.color.orange)));
//        data3.add(new TitleValueColorEntity("3",6,getResources().getColor(R.color.yellow)));
//        data3.add(new TitleValueColorEntity("4",2,getResources().getColor(R.color.lightgreen)));
//        data3.add(new TitleValueColorEntity("5", 2, getResources().getColor(R.color.green)));
                }
                if (!data3.isEmpty())
                piechart.setData(data3);
                piechart.invalidate();

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
                    datainfo = dataDao.checkbyType(typeNo);
                }else {
                    datainfo = dataDao.checkbyIsfinishAndType(String.valueOf(isfinishNo), typeNo);
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
                if (position == 0){
                    datainfo = dataDao.checkbyIsfinish(String.valueOf(isfinishNo));

                }else {
                    datainfo = dataDao.checkbyIsfinishAndType(String.valueOf(isfinishNo), typeNo);
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

    private void initPieChart()
    {
        this.piechart = (PieChart)findViewById(R.id.piechart);
        piechart.setVisibility(View.VISIBLE);
//        if (!typeinfo.isEmpty()){
//            if (!typecount.isEmpty()){
//                List<TitleValueColorEntity> data3 = new ArrayList<TitleValueColorEntity>();
//                for (int i = 0; i < typeinfo.size(); i++){
//                    data3.add(new TitleValueColorEntity(typeinfo.get(i).getType(),typecount.get(i), Color.parseColor(randomColor())));
//                }
//
////        data3.add(new TitleValueColorEntity("2",3,getResources().getColor(R.color.orange)));
////        data3.add(new TitleValueColorEntity("3",6,getResources().getColor(R.color.yellow)));
////        data3.add(new TitleValueColorEntity("4",2,getResources().getColor(R.color.lightgreen)));
////        data3.add(new TitleValueColorEntity("5", 2, getResources().getColor(R.color.green)));
//                piechart.setData(data3);
//            }
//        }
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
//        initPieChart();
    }


}
