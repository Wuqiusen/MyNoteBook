package com.example.huson.mynotebook.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.adapter.MyTypeSpinnerAdapter;
import com.example.huson.mynotebook.adapter.TypeAdapter;
import com.example.huson.mynotebook.base.BaseHeadActivity;
import com.example.huson.mynotebook.db.TypeDao;
import com.example.huson.mynotebook.domain.TypeInfo;
import com.example.huson.mynotebook.utils.BuildDialog;
import com.example.huson.mynotebook.utils.ToastHelper;
import com.example.huson.mynotebook.view.MyDialog;

import java.util.List;

/**
 * Created by Huson on 2016/4/1.
 * 940762301@qq.com
 */
public class SettingActivity extends BaseHeadActivity {
    private ListView lv;
    private Spinner sp_isfinish;
    private Spinner sp_type;
    private LinearLayout ll_sp;
    private TypeDao typeDao;
    private List<TypeInfo> typeinfo;
    private MyTypeSpinnerAdapter myTypeSpinnerAdapter;
    private TypeAdapter typeAdapter;

    private String type_name;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
    }

    @Override
    protected void initData() {
        typeDao = new TypeDao(this);
        typeinfo = typeDao.findAll();
            ll_sp.setVisibility(View.GONE);
            showRightButton(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BuildDialog.myDialog().showDialog(SettingActivity.this, "添加类别",null, MyDialog.EDITTEXT);
                    BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (BuildDialog.myDialog().getEtText().isEmpty()) {
                                ToastHelper.showToast("你的输入有误", SettingActivity.this);
                            } else {
                                type_name = BuildDialog.myDialog().getEtText();
                                typeDao.add(type_name, "事件", null);

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

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (typeDao != null){
            typeinfo = typeDao.findAll();
            if (typeAdapter != null){
                typeAdapter.clear();
                typeAdapter.addAll(typeinfo);

            }
        }
    }
}
