package com.hongqing.minjiemusic.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongqing.minjiemusic.R;

import static android.content.ContentValues.TAG;

/**
 * Created by 贺红清 on 2017/2/20.
 */

public class PlayAlbumFragment extends BaseFragment {

    private View view;
    private SimpleDraweeView simpleDraweeView;
    private Uri local_uri=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.playalbum_fragment_layout, container, false);
        simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_singerPhoto_play);
//        Log.i(TAG, "onCreateView: 1 ");
        setAlbumPhoto(local_uri);
        return view;

    }
    //这里遇到的问题是 在playActivity发送的消息  先执行了，而fragment还没有被创建
    // 在这里的解决办法是将传进来的参数先保存下来，在onCreate的时候再次调用这个方法就行了
    public void setAlbumPhoto(Uri uri){
        local_uri=uri;
//        System.out.println(uri+"这是uri");
//        Log.i(TAG, "onCreateView: 2 ");
        if (simpleDraweeView!=null) {
            simpleDraweeView.setImageURI(uri);
        }
    }
}
