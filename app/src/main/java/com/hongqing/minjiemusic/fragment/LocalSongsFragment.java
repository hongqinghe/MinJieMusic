package com.hongqing.minjiemusic.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.adapter.LocalSongsViewPagerAdapter;
import com.hongqing.minjiemusic.utils.PagerSlidingTabStrip;
import com.hongqing.minjiemusic.vo.MessageEvent;
import com.hongqing.minjiemusic.vo.MessageEventType;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class LocalSongsFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "TAG";
    private View view;
    private Toolbar toolbar;
    private AppCompatActivity activity;
    private PagerSlidingTabStrip psts_pagerStrip;
    private ViewPager viewPager;
    private LocalSongsViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> stringList;
    private ImageView iv_more_localSongs;
    private ImageView localSongs_back;
    private SinglesFragment singlesFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.localsongs_fragment_layout, null);
        Log.i(TAG, "onCreateView: ");

        return view;
    }


    private void initView() {
        psts_pagerStrip = (PagerSlidingTabStrip) view.findViewById(R.id.psts_pagerStrop);
        setPagerStripStyle(psts_pagerStrip);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_localSongs);
        iv_more_localSongs = (ImageView) view.findViewById(R.id.iv_more_localSongs);
        localSongs_back = (ImageView) view.findViewById(R.id.localSongs_back);
        initListener();
        stringList = new ArrayList<>();
        stringList.add("单曲");
        stringList.add("歌手");
        stringList.add("专辑");
        stringList.add("文件夹");
        initFragment();
        //在Fragment中嵌套fragment的时候，
        // 使用子管理者   这样据可以使用子类的manager对象
        // this.getChildFragmentManager() 不使用getActivity().getSupportFragmentManager()得到manager
        adapter = new LocalSongsViewPagerAdapter(
                this.getChildFragmentManager(), fragmentList, stringList);
        viewPager.setAdapter(adapter);
        psts_pagerStrip.setViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        viewPager.setCurrentItem(0);
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new SinglesFragment());
        fragmentList.add(new SingerFragment());
        fragmentList.add(new AlbumFragment());
        fragmentList.add(new FolderFragment());
    }

    private void initListener() {
        iv_more_localSongs.setOnClickListener(this);
        localSongs_back.setOnClickListener(this);
    }

    private void setPagerStripStyle(PagerSlidingTabStrip strip) {
        //tab text color
        strip.setTextColor(Color.parseColor("#2b2b2b"));
        //tab text size
        strip.setTextSize(15);
        //indicator color
        strip.setIndicatorColor(Color.parseColor("#09aaef"));
        //indicator height  指示器的高度
        strip.setIndicatorHeight(4);
        //underline height
        strip.setUnderlineHeight(1);
        //expand?  是否扩大
        strip.setShouldExpand(true);
        //divider between tab   tab间分割线的颜色
        strip.setDividerColor(android.R.color.transparent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more_localSongs:
                showPopupWindow(view);//展开弹出式菜单
                break;
            case R.id.localSongs_back:
                //点击返回主页面
                EventBus.getDefault().post(new MessageEvent(MessageEventType.BACK_MINE));
                break;
        }
    }

    private void showPopupWindow(View view) {
        View more_view = LayoutInflater.from(getContext()).inflate(R.layout.more_localsongs_layout, null);
        PopupWindow popupWindow = new PopupWindow(more_view,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        //这里必须设置添加背景  ，不然不能点击取消
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        popupWindow.showAsDropDown(view);
    }
}
