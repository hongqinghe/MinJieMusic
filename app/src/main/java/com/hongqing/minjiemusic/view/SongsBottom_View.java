package com.hongqing.minjiemusic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hongqing.minjiemusic.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 贺红清 on 2017/1/29.
 */

public class SongsBottom_View extends LinearLayout implements View.OnClickListener {
    @ViewInject(R.id.sdv_kugou_songsBottom)
    SimpleDraweeView simpleDraweeView;
    @ViewInject(R.id.tv_SongName_bottom)
    TextView songName_bottom;
    @ViewInject(R.id.iv_internet_bottom)
    ImageView internet_bottom;
    @ViewInject(R.id.tv_Singer_bottom)
    TextView singer_bottom;
    @ViewInject(R.id.ib_play_bottom)
    ImageButton play_bottom;
    @ViewInject(R.id.ib_next_bottom)
    ImageButton next_bottom;
    @ViewInject(R.id.ib_list_bottom)
    ImageButton list_bottom;

    private Context mcontext;
    private BottomListener bottomListener;

    public SongsBottom_View(Context context) {
        super(context);
        initView();
        initListener();
    }


    public SongsBottom_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
        initView();
        initListener();
    }

    private void initView() {
        View.inflate(mcontext, R.layout.songs_bottom_view, this);
        x.view().inject(this);

    }
    private void initListener() {
        simpleDraweeView.setOnClickListener(this);
        play_bottom.setOnClickListener(this);
        next_bottom.setOnClickListener(this);
        list_bottom.setOnClickListener(this);
    }
//提供设置图片资源
    public void setSimpleDraweeView(Uri uri) {
        simpleDraweeView.setImageURI(uri);
    }
    //设置歌名歌手的方法
    public void setSongInfo(String songName,String singer){
        songName_bottom.setText(songName);
        singer_bottom.setText(singer);
    }
    public void setInternet_bottom(Bitmap bitmap){
        internet_bottom.setImageBitmap(bitmap);
    }

    public void setPlay_bottom(boolean b){
        play_bottom.setSelected(b);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sdv_kugou_songsBottom:
                bottomListener.setSimpleDraweeViewListener();
                break;
            case R.id.ib_play_bottom:
                bottomListener.setPlayListener();
                break;
            case R.id.ib_next_bottom:
                bottomListener.setNextListener();
                break;
            case R.id.ib_list_bottom:
                bottomListener.setListListener();
                break;
        }
    }

    public void setBottomListener(BottomListener bottomListener) {
        this.bottomListener = bottomListener;
    }

    public interface BottomListener {
        void setSimpleDraweeViewListener();

        void setPlayListener();

        void setNextListener();

        void setListListener();
    }
}
