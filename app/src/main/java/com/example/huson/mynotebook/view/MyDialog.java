package com.example.huson.mynotebook.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.huson.mynotebook.R;
import com.example.huson.mynotebook.utils.ToastHelper;


/**
 * Created by huson on 2015/10/14.
 */
public class MyDialog extends AlertDialog {
    private String imgurl;
    private Context backContext;
    private Activity backactivity;
    private String mtitle;
    private String mtype;
    private long mtotal;
    private long mcurrent;
    private String mmesg;
    private TextView title;
    private TextView line;
    private TextView mesg;
    private TextView tv_percent;
    private LinearLayout ll_tn;
    private ProgressBar dialog_progressbar;
    private ImageView imageView;
    private Button cancel;
    private Button query;
    private EditText editText;

    public static final String HAVEBUTTON = "havebutton";
    public static final String NOBUTTON = "nobutton";
    public static final String PROGRESS = "progress";
    private static final String IMAGEVIEW = "imageview";
    public static final String EDITTEXT = "edittext";

    /**
     * mtype=0版本更新 ，mtype=1进度条，mtype=2图片， mtype=3注销
     * @param context
     * @param url
     */

    public MyDialog(Activity context, String url) {
        super(context);
        backactivity = context;
        imgurl = url;
        mtype = IMAGEVIEW;
    }

    public MyDialog(Context context, int theme, Activity activity) {
        super(context, theme);
        backContext = context;
        backactivity = activity;

    }

    public MyDialog(Activity activity, String title, String mesg, String type) {
        super(activity);
//        backContext = activity;
        backactivity = activity;
        mtitle = title;
        mmesg = mesg;
        mtype = type;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mydialog);
        imageView =(ImageView) findViewById(R.id.img_dialog_station);
        dialog_progressbar =(ProgressBar) findViewById(R.id.dialog_progressbar);
            title = (TextView) findViewById(R.id.title_img);
            line = (TextView) findViewById(R.id.line_img);
            mesg = (TextView) findViewById(R.id.mesage_img);
            tv_percent = (TextView) findViewById(R.id.tv_percent);
            ll_tn= (LinearLayout) findViewById(R.id.ll_dialog);
            cancel = (Button) findViewById(R.id.cancel_dialog);
            query = (Button) findViewById(R.id.query_dialog);
            editText = (EditText) findViewById(R.id.et_mydialog);
        title.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        mesg.setVisibility(View.GONE);
        ll_tn.setVisibility(View.GONE);
        tv_percent.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);

        initView();

    }

    private void initView(){

        switch (mtype){
            case HAVEBUTTON:
                title.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                mesg.setVisibility(View.VISIBLE);
                ll_tn.setVisibility(View.VISIBLE);
                title.setText(mtitle);
                mesg.setText(mmesg);
                break;
            case PROGRESS:
                tv_percent.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                title.setText(mtitle);
                dialog_progressbar.setVisibility(View.VISIBLE);
                setCanceledOnTouchOutside(false);

                break;
            case IMAGEVIEW://未可用
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                break;
            case NOBUTTON:
                title.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                mesg.setVisibility(View.VISIBLE);
                ll_tn.setVisibility(View.INVISIBLE);
                title.setText(mtitle);
                mesg.setText(mmesg);
                break;
            case EDITTEXT:
                title.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
                ll_tn.setVisibility(View.VISIBLE);
                title.setText(mtitle);
                mesg.setText(mmesg);
                break;

        }
    }

    public void ProgressPlan(long total, long current){

        dialog_progressbar.setMax((int) total);
        dialog_progressbar.setProgress((int) current);
        if (dialog_progressbar.getMax() != 0) {
            int i = dialog_progressbar.getProgress() * 100 / dialog_progressbar.getMax();
            tv_percent.setText(String.valueOf(i) + "%");
        } else {
            ToastHelper.showToast("当前网络状态不良,请稍后重试", backactivity);
        }
    }

    public void ButtonCancel(View.OnClickListener listener){
        cancel.setOnClickListener(listener);
    }

    public void ButtonQuery(View.OnClickListener listener){
        query.setOnClickListener(listener);
    }

    public void SetButtonText(String calaeltext, String querytext){
        cancel.setText(calaeltext);
        query.setText(querytext);
    }

}
