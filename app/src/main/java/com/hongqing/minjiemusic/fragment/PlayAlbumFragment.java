package com.hongqing.minjiemusic.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongqing.minjiemusic.R;

/**
 * Created by 贺红清 on 2017/2/20.
 */

public class PlayAlbumFragment extends  BaseFragment {

    private View view;
    private SimpleDraweeView simpleDraweeView;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.playalbum_fragment_layout, null);
        simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_singerPhoto);
        return view;
    }
    public  void setSimpleDraweeView(Uri uri){
        simpleDraweeView.setImageURI(uri);
    }

}
