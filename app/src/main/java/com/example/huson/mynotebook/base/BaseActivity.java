package com.example.huson.mynotebook.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;

import com.example.huson.mynotebook.utils.ClickUtil;


public abstract class BaseActivity extends FragmentActivity {
    public Context mContext;
    private int netCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    protected abstract void showLoading();
    protected abstract void hideLoading();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (ClickUtil.isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
}

}
