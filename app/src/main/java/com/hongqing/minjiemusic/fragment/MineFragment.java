package com.hongqing.minjiemusic.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.adapter.MyViewPagerAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private SlidingMenu menu;
    private View view;
    private ImageView title_kuGou;
    private ViewPager viewPager;
    private MyViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private String[] titles = {"看", "听", "唱"};
    private int[] tabBackground = {Color.WHITE};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment_layout, null);
        initView();
        initSlidingment();
        return view;
    }

    private void initView() {
        title_kuGou = (ImageView) view.findViewById(R.id.title_kuGou);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        initFragment();
        adapter = new MyViewPagerAdapter(
                getActivity().getSupportFragmentManager(), fragmentList, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        initListener();

    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new SingFragment());
        fragmentList.add(new LookFragment());
        fragmentList.add(new ListenFragment());
    }

    private void initListener() {
        title_kuGou.setOnClickListener(this);
    }

    //设置侧滑菜单
    private void initSlidingment() {
        menu = new SlidingMenu(getContext());
        menu.setMode(SlidingMenu.LEFT);
        //设置触摸屏幕的样式    设置边缘模式滑动打开menu(整个屏幕，边缘，不能通过手势启动三个参数)
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindWidth(500);//相对屏幕的偏移量
        menu.setBehindScrollScale(1);//设置出来的样式   1平移出现  0  代表下方出现
        menu.setFadeDegree(0.5f);//设置渐出值
        menu.attachToActivity(getActivity(), SlidingMenu.SLIDING_WINDOW);//设置出来时 的样式有基于window和content
        menu.setMenu(R.layout.slidingmenu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_kuGou:
                menu.toggle();
                break;
        }
    }
}
