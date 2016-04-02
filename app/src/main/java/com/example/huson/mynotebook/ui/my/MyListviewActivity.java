package com.example.huson.mynotebook.ui.my;

import android.content.Intent;
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
    private List<DataInfo> datainfo;
    private List<WishInfo> wishinfo;
    private List<String> isfinish = new ArrayList<String>();
    private List<String> st_type = new ArrayList<String>();
    private MySpinnerAdapter mAdapter;
    private MyTypeSpinnerAdapter myTypeSpinnerAdapter;
    private TypeAdapter typeAdapter;
    private TodayEventAdapter dataAdapter;
    private int typeNo = 0;
    private int isfinishNo = 0;

    private TypeDao typeDao;
    private DataDao dataDao;
    private WishDao wishDao;

    private String type_name;
    private String title_name;

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
            mAdapter = new MySpinnerAdapter(this, R.layout.item_spinner, isfinish);
            STisfinish(sp_isfinish);
        }

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
}
