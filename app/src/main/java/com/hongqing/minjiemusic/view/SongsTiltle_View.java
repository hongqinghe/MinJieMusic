package com.hongqing.minjiemusic.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongqing.minjiemusic.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 自定义View  主要是实现头部标题栏
 * Created by 贺红清 on 2017/1/29.
 */

public class SongsTiltle_View extends LinearLayout implements View.OnClickListener {
    @ViewInject(R.id.title_back)
    ImageView tiltle_back;
    @ViewInject(R.id.title_text)
    TextView title_text;
    @ViewInject(R.id.title_search)
    ImageView title_search;
    @ViewInject(R.id.title_menu)
    ImageView title_menu;
    private Context mcontext;

    //维护接口
    private ITitleImageListener listener;
    public SongsTiltle_View(Context context) {
        super(context);
        this.mcontext = context;
        initView();
    }

    public SongsTiltle_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
        initView();
    }

    private void initView() {
        View.inflate(mcontext, R.layout.songs_title_view, this);
        x.view().inject(this);
        tiltle_back.setOnClickListener(this);
        title_search.setOnClickListener(this);
        title_menu.setOnClickListener(this);
    }

    //提供改变文本框的方法
    public void setTitle_text(String text) {
        title_text.setText(text);
    }
//     给图片设置点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
               listener.setBackListener();
                break;
            case R.id.title_search:
              listener.setSearchListener();
                break;
            case R.id.title_menu:
            listener.setMenuListener();
                break;
        }
    }

    //提供一个设置点击事件的方法
    public void setTitleListener(ITitleImageListener listener){
        this.listener=listener;
    }
    //由于三种事件都是一样的结果所以直接写成一样的点击事件
    public interface ITitleImageListener {
        void setBackListener();

        void setMenuListener();

        void setSearchListener();
    }
}