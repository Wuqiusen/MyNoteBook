package com.example.huson.mynotebook.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.domain.DataInfo;
import com.example.huson.mynotebook.domain.TypeInfo;

import java.util.List;


/**
 * Created by Huson on 2016/3/1.
 * 940762301@qq.com
 */
public class STSpinnerAdapter extends CommonAdapter<DataInfo> {
    private List<String> mlist;
    private Context mcontext;

    public STSpinnerAdapter(Context context, int layoutId, List<DataInfo> items) {
        super(context, layoutId, items);
        mcontext = context;
    }

    @Override
    protected void convert(ViewHolder holder, int position, DataInfo item) {
        //将文本内容填充到 item.xml中的文本显示框中
        TextView one=(TextView)holder.findViewById(R.id.tv_item_spinner);
        try{
            one.setText(item.getMonth());
        }catch (Exception e){

        }
    }
    private int selectedPosition = -1;
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

}
