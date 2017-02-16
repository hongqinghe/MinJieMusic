package com.hongqing.minjiemusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongqing.minjiemusic.R;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class LookFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.slidingmenu,null);
        return view;

    }
    //单例设计模式
    private LookFragment(){}
    private static LookFragment lookFragment  =new LookFragment();
    public  static  LookFragment  getInstance(){
        return lookFragment;
    }
}
