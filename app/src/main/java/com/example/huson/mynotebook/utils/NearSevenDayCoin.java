package com.example.huson.mynotebook.utils;

import android.content.Context;

import com.example.huson.mynotebook.db.CoinDao;
import com.example.huson.mynotebook.domain.NearSevenDayCoinInfo;
import com.example.huson.mynotebook.view.datepicker.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Huson on 2016/4/12 0012.
 * 940762301@qq.com
 */
public class NearSevenDayCoin {

    public static List<NearSevenDayCoinInfo> getNearSevenDay(Context context){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        int year = Integer.valueOf(time.substring(0, 4));
        int month = Integer.valueOf(time.substring(5, 7));
        int day = Integer.valueOf(time.substring(8, 10));
        CoinDao dao = new CoinDao(context);

        List<NearSevenDayCoinInfo> nearSevenDayCoinInfos;
        nearSevenDayCoinInfos = new ArrayList<>();
        NearSevenDayCoinInfo sevenDayCoinInfo = new NearSevenDayCoinInfo();
        if (day - 7 < 0){
            for (int j = 0; j < day; j++){
                sevenDayCoinInfo.setDay(day - j);
                sevenDayCoinInfo.setCoin(dao.checkbyDay(String.valueOf(year), String.valueOf(month), String.valueOf(day - j)));

                for (int i = 0; i < 7 - day; i++){
                    sevenDayCoinInfo.setDay(DateUtils.getMonthDays(year, month - 1) - i);
                    sevenDayCoinInfo.setCoin(dao.checkbyDay(String.valueOf(year), String.valueOf(month - 1), String.valueOf(DateUtils.getMonthDays(year, month - 1) - i)));
                }
            }
        }else {
            for (int i = 0; i < 7 ; i++){
                sevenDayCoinInfo.setDay(day - i);
                sevenDayCoinInfo.setCoin(dao.checkbyDay(String.valueOf(year), String.valueOf(month), String.valueOf(day - i)));
            }
        }
        nearSevenDayCoinInfos.add(sevenDayCoinInfo);
        return nearSevenDayCoinInfos;
    }

}
