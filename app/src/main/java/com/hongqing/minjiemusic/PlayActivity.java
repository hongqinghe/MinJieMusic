package com.hongqing.minjiemusic;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.hongqing.minjiemusic.adapter.PlayViewPagerAdapter;

import com.hongqing.minjiemusic.fragment.PlayAlbumFragment;
import com.hongqing.minjiemusic.utils.MediaUtils;
import com.hongqing.minjiemusic.vo.Mp3Info;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PlayViewPagerAdapter adapter;
    private PlayAlbumFragment albumFragment;
    private ImageView back_title_icon;
    private ImageView radio_play;
    private ImageView prev_play;
    private ImageView play;
    private ImageView next_play;
    private ImageView menu_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
        System.out.println("创建playActivity");
    }

    @Override
    protected void change(long currentPosition) {
        System.out.println(musicService.getClass().toString() + "在playActivity中拿到service =========");
//        Mp3Info mp3Info=musicService.getMp3InfoList().get((int) currentPosition);
//        System.out.println(musicService.getMp3InfoList().toString());
//
//        albumFragment.setSimpleDraweeView(
//        MediaUtils.getAlbumPhoto(this,mp3Info.getAlbumId(),mp3Info.getMp3InfoId()));

    }

    @Override
    protected void update(long progress) {

    }


    @Override
    protected void onResume() {
        System.out.println("playActivity==============onResume");
        super.onResume();
        bindMusicService();
    }


    @Override
    protected void onPause() {
        unBindService();
        System.out.println("playActivity==============onPause");
        super.onPause();
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.play_tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager_play);
        back_title_icon = (ImageView) findViewById(R.id.back_title_icon);
        radio_play = (ImageView) findViewById(R.id.radio_play);
        prev_play = (ImageView) findViewById(R.id.prev_play);
        play = (ImageView) findViewById(R.id.play);
        next_play = (ImageView) findViewById(R.id.next_play);
        menu_play = (ImageView) findViewById(R.id.menu_play);
        initListener();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PlayAlbumFragment());
        fragmentList.add(new PlayAlbumFragment());
        adapter = new PlayViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        albumFragment = new PlayAlbumFragment();
    }

    private void initListener() {

        back_title_icon.setOnClickListener(this);
        radio_play.setOnClickListener(this);
        prev_play.setOnClickListener(this);
        play.setOnClickListener(this);
        next_play.setOnClickListener(this);
        menu_play.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_title_icon:
                //退出Activity
                finish();
                break;
                case R.id.radio_play:

                break;
                case R.id.prev_play:

                break;
                case R.id.play:

                break;
                case R.id.next_play:

                break;
                case R.id.menu_play:

                break;

        }
    }
}
