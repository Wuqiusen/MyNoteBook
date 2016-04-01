package com.example.huson.mynotebook.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huson.mynotebook.R;

public abstract class BaseHeadActivity extends FragmentActivity {

    private RelativeLayout rel_contentArea;
    private Button btn_headRightButton;
    private Button btn_backButton;
    private TextView btn_headTitle;
	private RelativeLayout rel_base_headArea;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_head);
        assignViews();
    }

    @Override
    public void setContentView(int layoutResID) {
        View v = getLayoutInflater().inflate(layoutResID, rel_contentArea, false);
        setContentView(v);
        findView();
        initData();
        initView();
        initEvent();
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, view.getLayoutParams());
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        rel_contentArea.addView(view, params);

    }

	protected void hideHeadArea(){
		rel_base_headArea.setVisibility(View.GONE);
	}


    public void showBackButton(OnClickListener listener) {
        btn_backButton.setOnClickListener(listener);
        btn_backButton.setVisibility(View.VISIBLE);
    }
    public void showRightButton(OnClickListener listener,String string) {
        btn_headRightButton.setOnClickListener(listener);
        btn_headRightButton.setText(string);
        btn_headRightButton.setVisibility(View.VISIBLE);
    }
    public void showBack(final Activity activity) {
        btn_backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        btn_backButton.setVisibility(View.VISIBLE);
    }

    public void showTitle(String title) {
        btn_headTitle.setText(title);
        btn_headTitle.setVisibility(View.VISIBLE);
    }
	public void hideTitle(){
		btn_headTitle.setVisibility(View.GONE);
	}

    private void assignViews() {
		rel_contentArea = (RelativeLayout) findViewById(R.id.rel_base_contentArea);
		btn_backButton = (Button) findViewById(R.id.btn_base_head_back);
		btn_headTitle = (TextView) findViewById(R.id.tv_base_head_title);
		btn_headRightButton = (Button) findViewById(R.id.btn_base_head_right_button);
		rel_base_headArea = (RelativeLayout) findViewById(R.id.rel_base_headArea);
		btn_headTitle.setVisibility(View.GONE);
		btn_headRightButton.setVisibility(View.GONE);


	}

    public String getTextforET(EditText et){
       return et.getText().toString().trim();
    }

    public String setETText(EditText editText){
            String s = editText.getText().toString().trim();
            if (s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") ||
                    s.equals("5") || s.equals("6") || s.equals("7") || s.equals("8")
                    || s.equals("9")) {
                s = "0" + s;
            }
        return s;

    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 获取布局控件
     */
    protected abstract void findView();

    /**
     * 初始化View的一些数据
     */
    protected abstract void initView();

    /**
     * 设置点击监听
     */
    protected abstract void initEvent();
}
