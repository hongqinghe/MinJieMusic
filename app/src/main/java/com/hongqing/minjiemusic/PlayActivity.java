package com.hongqing.minjiemusic;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongqing.minjiemusic.adapter.PlayViewPagerAdapter;

import com.hongqing.minjiemusic.fragment.LyricsFragment;
import com.hongqing.minjiemusic.fragment.PlayAlbumFragment;
import com.hongqing.minjiemusic.utils.Constant;
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
    private TextView songName_play;
    private TextView singer_play;
    private PlayAlbumFragment playAlbumFragment;
    private LyricsFragment lyricsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        playAlbumFragment = new PlayAlbumFragment();
        lyricsFragment = new LyricsFragment();
        initView();

    }

    @Override
    protected void change(long currentPosition) {
        List<Mp3Info> mp3InfoList = musicService.getMp3InfoList();
        if (mp3InfoList != null) {
            Mp3Info mp3Info = mp3InfoList.get((int) currentPosition);
            if (musicService.getLocal_or_net()== Constant.LOCAL_LIST){
                Uri uri = MediaUtils.getAlbumPhoto(this, mp3Info.getAlbumId(),mp3Info.getMp3InfoId());
                playAlbumFragment.setAlbumPhoto(uri);
            }else {
                //暂时网络的uri为空
                playAlbumFragment.setAlbumPhoto(null);
            }
            songName_play.setText(mp3Info.getTitle());
            singer_play.setText(mp3Info.getArtist());

            if (musicService.isPlaying()) {
                play.setImageResource(R.mipmap.play_play);
            }else {
                play.setImageResource(R.mipmap.pasue_play);
            }

        }
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
        songName_play = (TextView) findViewById(R.id.songName_play);
        singer_play = (TextView) findViewById(R.id.singer_play);
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
//        playAlbumFragment = new PlayAlbumFragment();
//        lyricsFragment = new LyricsFragment();
        fragmentList.add(playAlbumFragment);
        fragmentList.add(lyricsFragment);
        adapter = new PlayViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

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
        switch (view.getId()) {
            case R.id.back_title_icon:
                //退出Activity
                finish();
                break;
            case R.id.radio_play:

                break;
            case R.id.prev_play:
                musicService.musicPrev();
                break;
            case R.id.play:
                musicService.musicPlay();
                break;
            case R.id.next_play:
                musicService.musicNext();
                break;
            case R.id.menu_play:

                break;

        }
    }
}
