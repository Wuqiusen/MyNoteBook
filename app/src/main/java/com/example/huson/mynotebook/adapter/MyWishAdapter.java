package com.example.huson.mynotebook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.db.DataDao;
import com.example.huson.mynotebook.db.WishDao;
import com.example.huson.mynotebook.domain.WishInfo;
import com.example.huson.mynotebook.ui.LookEventActivity;
import com.example.huson.mynotebook.utils.BuildDialog;
import com.example.huson.mynotebook.utils.SpUtils;
import com.example.huson.mynotebook.utils.ToastHelper;
import com.example.huson.mynotebook.view.MyDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Huson on 2016/3/29.
 * 940762301@qq.com
 */
public class MyWishAdapter extends CommonAdapter<WishInfo> {
    private Context mcontext;
    private LinearLayout ll_item_wish;
    private TextView tv_wish_content;
    private TextView tv_isfinish;
    private TextView tv_coin;
    private TextView tv_wish_start;
    private TextView tv_wish_true_day;
    private TextView tv_wish_finish;
    private WishDao dao;
    private String time;

    private int isfinish;

    public MyWishAdapter(Context context, int layoutId, List<WishInfo> items) {
        super(context, layoutId, items);
        mcontext = context;
    }
    @Override
    protected void convert(ViewHolder holder, int position,final WishInfo item) {
        ll_item_wish = (LinearLayout) holder.findViewById(R.id.ll_item_data);
        tv_wish_content = (TextView) holder.findViewById(R.id.tv_wish_content);
        tv_isfinish = (TextView) holder.findViewById(R.id.tv_isfinish);
        tv_coin = (TextView) holder.findViewById(R.id.tv_coin);
        tv_wish_start = (TextView) holder.findViewById(R.id.tv_wish_start);
        tv_wish_finish = (TextView) holder.findViewById(R.id.tv_wish_finish);
        tv_wish_true_day = (TextView) holder.findViewById(R.id.tv_wish_true_day);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        time = formatter.format(curDate);
        dao = new WishDao(mcontext);
        try {
            isfinish = item.getIsfinish();
            ll_item_wish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isfinish == 1) {
                        ToastHelper.showToast("该愿望已实现", mContext);
                    } else {
                        BuildDialog.myDialog().showDialog((Activity) mContext, "提示", "确定该愿望已经实现？", MyDialog.HAVEBUTTON);
                        BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int coin = item.getNeedcoin();
                                int coinSum = Integer.valueOf(SpUtils.getCache(mContext, SpUtils.COIN));
                                if (coinSum < coin){
                                    ToastHelper.showToast("对不起，你的金币不足", mContext);
                                }else {
                                    coinSum = coinSum - coin;
                                    SpUtils.setCache(mContext, SpUtils.COIN, String.valueOf(coinSum));
                                    String id = String.valueOf(item.getId());
                                    dao.updatefinish(id, 1, time);
                                    clear();
                                    addAll(dao.findAll());
                                    BuildDialog.myDialog().DismissDialog();
                                    ToastHelper.showToast("恭喜，恭喜，愿望实现，离白富美更近一步了！！！", mContext);
                                }
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
            });

            ll_item_wish.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View arg0) {
                    BuildDialog.myDialog().showDialog((Activity) mContext, "提示", "确定删除该愿望？", MyDialog.HAVEBUTTON);
                    BuildDialog.myDialog().ButtonQuery(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = String.valueOf(item.getId());
                            dao.delete(id);
                            clear();
                            addAll(dao.findAll());
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
            tv_wish_content.setText(item.getContext());

            if (isfinish == 1){
                tv_isfinish.setText("已实现");
                tv_wish_true_day.setText("实现日期：");
            }else {
                tv_isfinish.setText("未实现");
                tv_wish_true_day.setText("期待日期：");
            }
            tv_coin.setText(String.valueOf(item.getNeedcoin()));
            tv_wish_start.setText(item.getStarttime());
            tv_wish_finish.setText(item.getEndtime());
        }catch (Exception e){

        }


    }

    private int selectedPosition = -1;
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
}
