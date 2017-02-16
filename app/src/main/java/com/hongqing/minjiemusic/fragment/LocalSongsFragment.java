package com.hongqing.minjiemusic.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hongqing.minjiemusic.R;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class LocalSongsFragment extends BaseFragment {

    private View view;
    private Toolbar toolbar;
    private AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.localsongs_fragment_layout, null);
        toolbar = (Toolbar) view.findViewById(R.id.toolBar);
        activity = ((AppCompatActivity) getActivity());
       toolbar.setTitleTextColor(Color.WHITE);
        activity.setSupportActionBar(toolbar);  //注意下面的设置一定要在setSupportActionBar之后
        ActionBar actionBar=activity.getSupportActionBar();
        setHasOptionsMenu(true);//设置toolbar能使用菜单
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.localSongs);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.local_songs_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }
}
