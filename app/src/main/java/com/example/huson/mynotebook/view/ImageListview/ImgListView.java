package com.example.huson.mynotebook.view.ImageListview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;



/**
 * Created by admin on 14-3-19.
 */
public class ImgListView extends ListView {

    public ImgListView(Context context) {
        super(context);
    }
    public ImgListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImgListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    public void addHeaderView(View v) {
        super.addHeaderView(v);
    }

    /**
     * 向下滑动让图片变大
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // 手指压下屏幕
            case MotionEvent.ACTION_DOWN:
                ImagList.mode = ImagList.MODE_DRAG;
                // 记录ImageView当前的移动位置
                ImagList.currentMatrix.set(ImagList.imageView.getImageMatrix());
                ImagList.startPoint.set(event.getX(), event.getY());
                break;
            // 手指在屏幕上移动，改事件会被不断触发
            case MotionEvent.ACTION_MOVE:
                // 拖拉图片
                if (ImagList.mode == ImagList.MODE_DRAG) {
                    float dx = event.getX() - ImagList.startPoint.x; // 得到x轴的移动距离
                    float dy = event.getY() - ImagList.startPoint.y; // 得到x轴的移动距离
                    // 在没有移动之前的位置上进行移动
                    ImagList.matrix.set(ImagList.currentMatrix);
                    float scale = (dy+ImagList.imgHeight) / ImagList.imgHeight;// 得到缩放倍数
                    if(dy>0){
                        LayoutParams relativeLayout=new LayoutParams((int)(scale*ImagList.imgWidth),(int)(scale*ImagList.imgHeight));
                        ImagList.imageView.setLayoutParams(relativeLayout);
                        ImagList.matrix.postScale(scale, scale,ImagList.imgWidth/2,0);
                    }
                }
                break;
            // 手指离开屏幕
            case MotionEvent.ACTION_UP:
                // 当触点离开屏幕，图片还原
                LayoutParams relativeLayout=new LayoutParams((int)ImagList.imgWidth,(int)ImagList.imgHeight);
                ImagList.imageView.setLayoutParams(relativeLayout);
                ImagList.matrix.set(ImagList.defaultMatrix);
            case MotionEvent.ACTION_POINTER_UP:
                ImagList.mode = 0;
                break;
        }
        ImagList.imageView.setImageMatrix(ImagList.matrix);

        return super.onTouchEvent(event);
    }
}
