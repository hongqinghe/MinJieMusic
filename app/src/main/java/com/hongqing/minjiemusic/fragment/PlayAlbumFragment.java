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

public class PlayAlbumFragment extends BaseFragment {

    private View view;
    private SimpleDraweeView simpleDraweeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lyrics_fragment_layout, container, false);
        simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_singerPhoto_lry);
//        simpleDraweeView.setImageResource(R.mipmap.background_play);

        return view;

    }
    public void setAlbumPhoto(Uri uri){
        simpleDraweeView.setImageURI(uri);
    }
}
