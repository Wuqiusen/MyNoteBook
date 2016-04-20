package com.example.huson.mynotebook.domain;

import java.io.Serializable;

/**
 * Created by Huson on 2016/4/20.
 * 940762301@qq.com
 */
public class ChartData implements Serializable {

    public String type; //类别

    public int count; //数据

    public String bfb;  //百分比

    public String dw;  //单位

    public ChartData(String mType, int mCount, String dw)
    {
        super();
        this.type = mType;
        this.count = mCount;
        this.dw = dw;
    }

    public ChartData ()
    {

    }

}
