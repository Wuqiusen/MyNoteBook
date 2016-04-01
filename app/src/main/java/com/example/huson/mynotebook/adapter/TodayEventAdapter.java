package com.example.huson.mynotebook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.db.DataDao;
import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.ui.LookEventActivity;
import com.example.huson.mynotebook.utils.BuildDialog;
import com.example.huson.mynotebook.utils.ToastHelper;
import com.example.huson.mynotebook.view.MyDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Huson on 2016/3/29.
 * 940762301@qq.com
 */
public class TodayEventAdapter extends CommonAdapter<DataInfo> {
    private Context mcontext;
    private LinearLayout ll_item_data;
    private TextView tv_item_data;
    private TextView tv_poi;
    private DataDao dao;
    private String time;

    public TodayEventAdapter(Context context, int layoutId, List<DataInfo> items) {
        super(context, layoutId, items);
        mcontext = context;
    }
    @Override
    protected void convert(ViewHolder holder, int position,final DataInfo item) {
        ll_item_data = holder.findViewById(R.id.ll_item_data);
        tv_item_data = (TextView) holder.findViewById(R.id.tv_item_data);
        tv_poi = (TextView) holder.findViewById(R.id.tv_poi);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        time = formatter.format(curDate);
        dao = new DataDao(mcontext);
        try {
            ll_item_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, LookEventActivity.class);
                    intent.putExtra("msgid", item.getMsgid());
                    mcontext.startActivity(intent);
                }
            });

            ll_item_data.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View arg0) {
                    BuildDialog.myDialog().showDialog((Activity) mContext, "提示", "确定删除该通知？", MyDialog.HAVEBUTTON);
                    BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = String.valueOf(item.getId());
                            dao.delete(id);
                            clear();
                            addAll(dao.checkbyDay(time.substring(0, 4), time.substring(4, 6), time.substring(6, 8)));
                            BuildDialog.myDialog().DismissDialog();
                            ToastHelper.showToast("删除成功", mContext);
                        }
                    });
                    BuildDialog.myDialog().ButtonCancel(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BuildDialog.myDialog().DismissDialog();
                        }
                    });
                    return false;
                }
            });

            tv_item_data.setText(item.getContext());
            tv_poi.setText(String.valueOf(position + 1));
        }catch (Exception e){

        }


    }

    private int selectedPosition = -1;
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
}
