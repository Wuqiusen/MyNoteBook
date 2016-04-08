package com.example.huson.mynotebook.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.adapter.MyTypeSpinnerAdapter;
import com.example.huson.mynotebook.base.BaseHeadActivity;
import com.example.huson.mynotebook.db.DataDao;
import com.example.huson.mynotebook.db.TypeDao;
import com.example.huson.mynotebook.domain.TypeInfo;
import com.example.huson.mynotebook.utils.AlarmReceiver;
import com.example.huson.mynotebook.utils.BuildDialog;
import com.example.huson.mynotebook.utils.DebugLog;
import com.example.huson.mynotebook.utils.ToastHelper;
import com.example.huson.mynotebook.view.MyDialog;
import com.example.huson.mynotebook.view.datepicker.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Huson on 2016/3/28.
 * 940762301@qq.com
 */
public class AddEventActivity extends BaseHeadActivity{
    private TextView tv_add_time;
    private EditText et_start_year;
    private EditText et_start_month;
    private EditText et_start_day;
    private EditText et_start_hour;
    private EditText et_end_year;
    private EditText et_end_month;
    private EditText et_end_day;
    private EditText et_end_hour;
    private Spinner sp_type;
    private EditText et_context;
    private Button btn_query;

    private List<TypeInfo> list_type;
    private MyTypeSpinnerAdapter mAdapter;

    private DataDao dao;
    private TypeDao typeDao;
    private String msgid;
    private String starttime;
    private String endtime;
    private String context;
    private String complete;
    private int type;
    private String year;
    private String month;
    private String week;
    private String day;

    private String now;

    private AlarmManager alarmManager=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

    }

    @Override
    protected void initData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        tv_add_time.setText(str);
        Intent intent = getIntent();
        year = intent.getStringExtra("year");
        month = intent.getStringExtra("month");
        day = intent.getStringExtra("day");
        week = intent.getStringExtra("week");
        et_start_year.setText(year);
        et_start_month.setText(month);
        et_start_day.setText(day);
        et_end_year.setText(year);
        et_end_month.setText(month);
        et_end_day.setText(day);

        SimpleDateFormat formatter3 = new SimpleDateFormat("HH:mm");
        et_start_hour.setText(formatter3.format(curDate));
        now = formatter3.format(curDate);
        et_end_hour.setText(now);

        typeDao = new TypeDao(this);
        list_type = typeDao.findAll();
        mAdapter = new MyTypeSpinnerAdapter(this, R.layout.item_spinner, list_type);
        sp_type.setAdapter(mAdapter);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DebugLog.e(String.valueOf(position));
                type = position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void findView() {
        tv_add_time = (TextView) findViewById(R.id.tv_add_time);
        et_start_year = (EditText) findViewById(R.id.et_start_year);
        et_start_month = (EditText) findViewById(R.id.et_start_month);
        et_start_day = (EditText) findViewById(R.id.et_start_day);
        et_start_hour = (EditText) findViewById(R.id.et_start_hour);
        et_end_year = (EditText) findViewById(R.id.et_end_year);
        et_end_month = (EditText) findViewById(R.id.et_end_month);
        et_end_day = (EditText) findViewById(R.id.et_end_day);
        et_end_hour = (EditText) findViewById(R.id.et_end_hour);
        et_context = (EditText) findViewById(R.id.et_context);
        sp_type = (Spinner) findViewById(R.id.sp_type);
        btn_query = (Button) findViewById(R.id.btn_query);

    }

    @Override
    protected void initView() {
        showTitle("添加事件");

    }

    @Override
    protected void initEvent() {
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0){
                    ToastHelper.showToast("请选择事件类型", AddEventActivity.this);
                    return;
                }
                Calendar c=Calendar.getInstance();//获取日期对象
                starttime = getTextforET(et_start_year)+setETText(et_start_month)+setETText(et_start_day)+getTextforET(et_start_hour);
                endtime = getTextforET(et_end_year)+setETText(et_end_month)+setETText(et_end_day)+getTextforET(et_end_hour);
                context = getTextforET(et_context);
                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                msgid = formatter2.format(curDate);
                week = String.valueOf(DateUtils.getWeek(Integer.parseInt(getTextforET(et_start_year)),
                        Integer.parseInt(getTextforET(et_start_month)) - 1, Integer.parseInt(getTextforET(et_start_day))));
                DebugLog.e("==========================="+week);
                dao = new DataDao(AddEventActivity.this);
                dao.add(msgid, type, context, starttime, endtime, "2", getTextforET(et_start_year),
                        setETText(et_start_month), week, setETText(et_start_day), null, null, null, null, 1);
                c.setTimeInMillis(System.currentTimeMillis());
                c.set(Calendar.YEAR, Integer.parseInt(getTextforET(et_start_year)));
                c.set(Calendar.MONTH, Integer.parseInt(getTextforET(et_start_month)) - 1);
                c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(getTextforET(et_start_day)));
                c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(getTextforET(et_start_hour).substring(0, 2)));
                c.set(Calendar.MINUTE, Integer.parseInt(getTextforET(et_start_hour).substring(3, 5)));
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
                Intent intent = new Intent(AddEventActivity.this, AlarmReceiver.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", Integer.valueOf(msgid.substring(4, 14)));
                bundle.putString("context", getTextforET(et_context));
                intent.putExtras(bundle);

                PendingIntent pendingIntent= PendingIntent.getBroadcast(
                        AddEventActivity.this, Integer.valueOf(msgid.substring(4, 14)), intent, 0);// 第二个参数为区别不同闹铃的唯一标识
                /* 设置闹钟 */
                alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis()+0, pendingIntent);

                ToastHelper.showToast("添加成功", AddEventActivity.this);
                AddEventActivity.this.finish();

            }
        });

        et_start_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHour(et_start_hour);

            }
        });

        et_end_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHour(et_end_hour);
            }
        });

    }

    private void getHour(final EditText et){
        BuildDialog.myDialog().showDialog(AddEventActivity.this, "请选择时间", null, MyDialog.TIMEPICKER);
        BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(BuildDialog.myDialog().getWholeTime());
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
}
