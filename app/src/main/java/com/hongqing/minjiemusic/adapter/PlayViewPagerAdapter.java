package com.hongqing.minjiemusic.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by 贺红清 on 2017/2/20.
 */

public class PlayViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment >  fragmentList;
    private String titles[]=new String[]{"歌词","专辑"};

    public PlayViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public PlayViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return  titles[position];
    }

}
