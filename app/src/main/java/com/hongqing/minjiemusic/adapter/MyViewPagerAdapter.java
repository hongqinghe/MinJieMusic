package com.hongqing.minjiemusic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;

import java.util.List;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragmentList;
    private String[] titles = {"看", "听", "唱"};
    private SpannableString spannableString;
    private int[] tabBackground = {Color.WHITE};

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);

        this.fragmentList = fragmentList;
    }

    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);

        this.fragmentList = fragmentList;
        this.titles = titles;
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


        return titles[position];
    }

}
