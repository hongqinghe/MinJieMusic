package com.hongqing.minjiemusic;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hongqing.minjiemusic.adapter.PlayViewPagerAdapter;

import com.hongqing.minjiemusic.fragment.LyricsFragment;
import com.hongqing.minjiemusic.fragment.PlayAlbumFragment;
import com.hongqing.minjiemusic.utils.AppUtils;
import com.hongqing.minjiemusic.utils.Constant;
import com.hongqing.minjiemusic.utils.DownloadUtils;
import com.hongqing.minjiemusic.utils.MediaUtils;
import com.hongqing.minjiemusic.utils.MlyException;
import com.hongqing.minjiemusic.view.view.LrcView;
import com.hongqing.minjiemusic.vo.MessageEvent;
import com.hongqing.minjiemusic.vo.MessageEventType;
import com.hongqing.minjiemusic.vo.Mp3Info;

import org.greenrobot.eventbus.EventBus;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

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
    private SeekBar seekBar;
    private TextView current_progress;
    private TextView duration;
    private static  final int UPDATA_PROGRESS=0x8;
    private ImageView mode_play;
    private ImageView iv_love;
    private Mp3Info mp3Info;
    private BaseApplication application;
    private ImageView iv_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initView();
        application = (BaseApplication) getApplication();

    }

    @Override
    protected void change(long currentPosition) {
        List<Mp3Info> mp3InfoList = musicService.getMp3InfoList();
        if (mp3InfoList != null) {
            mp3Info = mp3InfoList.get((int) currentPosition);
            if (musicService.getLocal_or_net()== Constant.LOCAL_LIST){
                Uri uri = MediaUtils.getAlbumPhoto(this, mp3Info.getAlbumId(), mp3Info.getMp3InfoId());
                playAlbumFragment.setAlbumPhoto(uri);
            }else {
                //暂时网络的uri为空
                playAlbumFragment.setAlbumPhoto(null);
            }
            duration.setText(MediaUtils.formatTime( mp3Info.getDuration())+"");
            current_progress.setText(MediaUtils.formatTime(musicService.getCurrentPosition())+"");
            seekBar.setMax((int) mp3Info.getDuration());
            setModePlay();//设置播放模式
            setLoveRes(mp3Info);//加载是否为收藏音乐
            songName_play.setText(mp3Info.getTitle());
            singer_play.setText(mp3Info.getArtist());
            if (musicService.isPlaying()) {
                play.setImageResource(R.mipmap.play_play);
            }else {
                play.setImageResource(R.mipmap.pasue_play);
            }
            //提供歌名和专辑 查找单词

       if (lyricsFragment!=null) {
           lyricsFragment.setInfo(mp3Info.getTitle(), mp3Info.getArtist());
           lyricsFragment.load();
       }
        }
    }

    private void setModePlay() {
        switch (musicService.getMODE_PLAY()) {
            case MusicService.ORDER_PLAY:
                mode_play.setImageResource(R.mipmap.order_play);
                break;
            case MusicService.RADOM_PLAY:
                mode_play.setImageResource(R.mipmap.radom_play);
                break;
            case MusicService.SINGLE_PLAY:
                mode_play.setImageResource(R.mipmap.single_cycle_play);
                break;
        }
    }

    //更新的方法
    @Override
    protected void update(long progress) {
        if (progress>0) {
          seekBar.setProgress((int) progress);
          //由于是在子线程更新播放进度所以要使用Handler来更新UI
            Message  message=Message.obtain();
            message.what=UPDATA_PROGRESS;
            message.obj=progress;
//            myHandler.obtainMessage(UPDATA_LRC_TIME,progress)
            myHandler.sendMessage(message);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
        if (fromUser) {
             musicService.seekBar(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
   public  MyHandler myHandler=new MyHandler(this);
     //使用若引用的方式更新
    private static class  MyHandler extends Handler{
       private WeakReference<PlayActivity> weakReference;
       public MyHandler(PlayActivity  playActivity){
           weakReference=new WeakReference<PlayActivity>(playActivity);
       }
       @Override
       public void handleMessage(Message msg) {
           PlayActivity playActivity=weakReference.get();
           switch (msg.what){
               case   UPDATA_PROGRESS:
//                   System.out.println(MediaUtils.formatTime((Long) msg.obj)+"打印时间为");
                   playActivity.lyricsFragment.seekLrcToTime((Long) msg.obj);
                    playActivity.current_progress.setText(MediaUtils.formatTime((Long) msg.obj)+"");
                   break;
           }
           super.handleMessage(msg);
       }
   }

    @Override
    protected void onResume() {
        super.onResume();
        bindMusicService();
    }

    @Override
    protected void onPause() {
        unBindService();
        super.onPause();
    }

    private void initView() {
        songName_play = (TextView) findViewById(R.id.songName_play);
        singer_play = (TextView) findViewById(R.id.singer_play);
        tabLayout = (TabLayout) findViewById(R.id.play_tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager_play);
        back_title_icon = (ImageView) findViewById(R.id.back_title_icon);
        prev_play = (ImageView) findViewById(R.id.prev_play);
        play = (ImageView) findViewById(R.id.play);
        next_play = (ImageView) findViewById(R.id.next_play);
        menu_play = (ImageView) findViewById(R.id.menu_play);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        mode_play = (ImageView) findViewById(R.id.mode_play);
        iv_love = (ImageView) findViewById(R.id.iv_love);
        iv_down = (ImageView) findViewById(R.id.iv_down);
        initSeekBar();
        current_progress = (TextView) findViewById(R.id.play_current_progress);
        duration = (TextView) findViewById(R.id.duration_play);
        initListener();
        List<Fragment> fragmentList = new ArrayList<>();
        playAlbumFragment = new PlayAlbumFragment();
        lyricsFragment =LyricsFragment.getIntance();
        fragmentList.add(playAlbumFragment);
        fragmentList.add(lyricsFragment);
        adapter = new PlayViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initSeekBar() {
        seekBar.setProgress(0);
        seekBar.setMax(0);
        seekBar.setOnSeekBarChangeListener(this);
    }

    private void initListener() {
        iv_love.setOnClickListener(this);
        iv_down.setOnClickListener(this);
        back_title_icon.setOnClickListener(this);
        prev_play.setOnClickListener(this);
        play.setOnClickListener(this);
        mode_play.setOnClickListener(this);
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
            case R.id.mode_play:
                //模式
                update_play_moder();
                break;
            case  R.id.iv_love:
                 setLoveMusic();
                break;
            case R.id.iv_down:
                downMusic();
                break;
        }
    }

    private void downMusic() {
        //下载歌曲
        DownloadUtils.getInstance().
                downloadMusic(this,mp3Info.getUrl(),mp3Info.getTitle())
                .setListener(new DownloadUtils.OnDownloadListener() {
                    @Override
                    public void onDownload(String result, MlyException e) {
                        if (e==null){
                            toast(getString(R.string.down_success));
                            AppUtils.scanFileAsync(PlayActivity.this,result);
                        }
                        else {
                            toast(e.toString());
                        }
                    }
                });
    }

    //点击喜欢按钮  先判断是不是喜欢 如果喜欢状态，就取消  ，否则直接保存
    private void setLoveMusic() {
        Mp3Info mp3Info = musicService.getMp3InfoList().get(musicService.getIndex());
        System.out.println(mp3Info.toString()+"this  is ");
        try {
            System.out.println("========================");
            Mp3Info likeMp3Info = application.dbManager.selector(Mp3Info.class)
                    .where("mp3InfoId","=",mp3Info.getMp3InfoId()).findFirst();
            System.out.println(likeMp3Info+"aaaaaaaaaasdfadsfsdf");
            if(likeMp3Info!=null){
                int like = likeMp3Info.getIsLike()==0?1:0;
                likeMp3Info.setIsLike(like);
                application.dbManager.update(likeMp3Info);
            }else{
                mp3Info.setIsLike(1);
                System.out.println(mp3Info.toString()+"this  is4444444 ");
                application.dbManager.save(mp3Info);

                System.out.println("===========assssss=============");
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        setLoveRes(mp3Info);
    }

    private void setLoveRes(Mp3Info mp3Info) {
        //查询并设置是否为喜欢歌曲
            try {
                System.out.println(mp3Info.getMp3InfoId()+"id");
                Mp3Info likeMp3Info = application.dbManager.selector(Mp3Info.class).
                        where("mp3InfoId", "=", mp3Info.getMp3InfoId()).findFirst();
                if (likeMp3Info != null && likeMp3Info.getIsLike()==1) {
                    System.out.println(likeMp3Info.toString()+"sssssssssssssssss");
                    iv_love.setImageResource(R.mipmap.love_play);
                }else{
                    iv_love.setImageResource(R.mipmap.love_play_normal);
                    System.out.println("sssssssyyyyyyssssssssss");
                }

            } catch (DbException e) {
                e.printStackTrace();
            }


    }

    private void update_play_moder() {
        int mode = musicService.getMODE_PLAY();
        if (mode==MusicService.SINGLE_PLAY){
            //顺序播放
            toast("顺序播放");
            musicService.setMODE_PLAY(MusicService.ORDER_PLAY);
            mode_play.setImageResource(R.mipmap.order_play);
        }else  if (mode==MusicService.ORDER_PLAY){
            //随机播放
            toast("随机播放");
            musicService.setMODE_PLAY(MusicService.RADOM_PLAY);
            mode_play.setImageResource(R.mipmap.radom_play);
        }else  if (mode==MusicService.RADOM_PLAY){
            //单曲循环
            toast("单曲循环");
            musicService.setMODE_PLAY(MusicService.SINGLE_PLAY);
            mode_play.setImageResource(R.mipmap.single_cycle_play);
        }
    }
}
