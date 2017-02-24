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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.adapter.MyViewPagerAdapter;
import com.hongqing.minjiemusic.utils.PagerSlidingTabStrip;
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
    private String[] titles = {"听", "看", "唱"};
    private int[] tabBackground = {Color.WHITE};
   private PagerSlidingTabStrip strip;
    private ImageView title_search;
    private LinearLayout line_exit;

    @Override
    public void onResume() {
        super.onResume();

        }
    //单例设计模式
    public MineFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment_layout, null);
        initView();
        viewPager.setCurrentItem(0);
        return view;
    }

    private void initView() {
        title_kuGou = (ImageView) view.findViewById(R.id.title_kuGou);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        title_search = (ImageView) view.findViewById(R.id.title_search);
        strip= (PagerSlidingTabStrip) view.findViewById(R.id.strip);

        initFragment();
        adapter = new MyViewPagerAdapter(
                this.getChildFragmentManager(), fragmentList, titles);
        viewPager.setAdapter(adapter);
        setPagerStripStyle(strip);//设置样式
        setPagerStripListener(strip);//设置监听事件
        strip.setViewPager(viewPager);
        initListener();
    }

    private void setPagerStripListener(PagerSlidingTabStrip strip) {
        strip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position==1){
                    title_search.setImageResource(R.mipmap.title_person);
                }
                else {
                    title_search.setImageResource(R.mipmap.search_normal);
                }
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //设置头部tab的样式   重写的是pagerSlidingTabStrip
    private void setPagerStripStyle(PagerSlidingTabStrip strip) {
        //tab text color
        strip.setTextColor(Color.parseColor("#dfdfdf"));
        //tab chose text color
        strip.setTabChoseTextColor(Color.parseColor("#ffffff"));
        //tab text size
        strip.setTextSize(15);
        //tab chose text size
        strip.setTabChoseTextSize(18);
        //indicator color
        strip.setIndicatorColor(Color.WHITE);
        //indicator height  指示器的高度
        strip.setIndicatorHeight(4);
        //underline height
        strip.setUnderlineHeight(1);
        //expand?  是否扩大
        strip.setShouldExpand(true);
        //divider between tab   tab间分割线的颜色
        strip.setDividerColor(android.R.color.transparent);
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ListenFragment());
        fragmentList.add(new LookFragment());
        fragmentList.add(new SingFragment());
    }
    private void initListener() {
        title_kuGou.setOnClickListener(this);
    }

    //设置侧滑菜单

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_kuGou:
                menu.toggle();
                break;
        }
    }
}
