package com.hongqing.minjiemusic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;

import java.util.List;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class LocalSongsViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragmentList;
    private List<String >  stringList;
    public LocalSongsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public LocalSongsViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> stringList) {
        super(fm);

        this.fragmentList = fragmentList;
        this.stringList = stringList;
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
        return stringList.get(position);
    }

}
