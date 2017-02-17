package com.hongqing.minjiemusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongqing.minjiemusic.R;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class FolderFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.singles_fragment_layout,null);
        return view;
    }
}
