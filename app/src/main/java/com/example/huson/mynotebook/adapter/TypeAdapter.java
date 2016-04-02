package com.example.huson.mynotebook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.db.DataDao;
import com.example.huson.mynotebook.db.TypeDao;
import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.domain.TypeInfo;
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
public class TypeAdapter extends CommonAdapter<TypeInfo> {
    private Context mcontext;
    private LinearLayout ll_item_data;
    private TextView tv_item_data;
    private TextView tv_poi;
    private TypeDao dao;
    private String time;

    public TypeAdapter(Context context, int layoutId, List<TypeInfo> items) {
        super(context, layoutId, items);
        mcontext = context;
    }
    @Override
    protected void convert(ViewHolder holder, int position,final TypeInfo item) {
        ll_item_data = holder.findViewById(R.id.ll_item_data);
        tv_item_data = (TextView) holder.findViewById(R.id.tv_item_data);
        tv_poi = (TextView) holder.findViewById(R.id.tv_poi);
        dao = new TypeDao(mcontext);
        try {
            ll_item_data.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View arg0) {
                    BuildDialog.myDialog().showDialog((Activity) mContext, "提示", "确定删除该类？", MyDialog.HAVEBUTTON);
                    BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = String.valueOf(item.getId());
                            String mtype = item.getType();
                            if (mtype.equals("类别")){

                            }else {
                                dao.delete(id);
                                clear();
                                addAll(dao.findAll());
                            }
                            ToastHelper.showToast("删除成功", mContext);
                            BuildDialog.myDialog().DismissDialog();
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

            tv_item_data.setText(item.getType());
            tv_poi.setText(String.valueOf(position + 1));
        }catch (Exception e){

        }


    }

    private int selectedPosition = -1;
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
}
