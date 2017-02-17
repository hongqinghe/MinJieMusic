package com.hongqing.minjiemusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hongqing.minjiemusic.R;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class SingerFragment extends BaseFragment {

    private View view;
    private ListView singer_listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.singles_fragment_layout, null);
        initView();
        return view;
    }

    private void initView() {
        singer_listView = (ListView) view.findViewById(R.id.singer_listView);

    }

    //单例设计模式
    private SingerFragment() {
    }
    private static SingerFragment singerFragment = new SingerFragment();
    public static SingerFragment getInstance() {
        return singerFragment;
    }
}
