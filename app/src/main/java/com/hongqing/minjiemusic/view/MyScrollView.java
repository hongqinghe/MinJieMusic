package com.hongqing.minjiemusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by 贺红清 on 2017/1/16.
 */

/**
 * 重写了MyScrollView 避免在ScrollView中嵌套listView冲突
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        requestDisallowInterceptTouchEvent(true);
//        return super.dispatchTouchEvent(ev);
//    }
}
