package com.hongqing.minjiemusic;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.hongqing.minjiemusic.fragment.LocalSongsFragment;
import com.hongqing.minjiemusic.fragment.LookFragment;
import com.hongqing.minjiemusic.fragment.MineFragment;
import com.hongqing.minjiemusic.view.SongsBottom_View;
import com.hongqing.minjiemusic.vo.MessageEvent;
import com.hongqing.minjiemusic.vo.MessageEventType;
import com.hongqing.minjiemusic.vo.Mp3Info;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private SongsBottom_View songBottom_view;
    private boolean ispasue = false;//是否是暂停播放
    private boolean isplaying = false;//是否正在播放
    public static MediaPlayer mediaPlayer;
    private int index = 0;//定义下标来控制播放哪一首歌
    private boolean isNext = true;//用来判断是不是暂停状态下单价下一首
    private ArrayList<Mp3Info> arrayList;
    private Mp3Info mp3Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentManager = getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_layout_main, MineFragment.getInstance());
        ft.commit();
        mediaPlayer = new MediaPlayer();
        songBottom_view = (SongsBottom_View) findViewById(R.id.songBottom_view);
        initListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void MessageSubscriber(MessageEvent event) {
        Log.i("MessageSubscriber", event.type + "");
        if (event.type == MessageEventType.PLAY_MUSIC) {
//            System.out.println((event.data).toString() + "输出得到的数据");
            int id = event.position;
            arrayList = (ArrayList<Mp3Info>) event.data;
            System.out.println(arrayList.get(id).toString() + "this   is info");
            mp3Info = arrayList.get(id);
            songBottom_view.setSongInfo(mp3Info.getTitle(), mp3Info.getArtist());
            musicStart(mp3Info);
        }
    }

    //播放音乐
    private void musicStart(Mp3Info mp3Info) {
        if (ispasue) {
            mediaPlayer.start();
            isplaying = false;
        } else {
            if (isplaying) {  //判断是不是正在播放  ，如果正在播放则就释放 然后在进行播放
                mediaPlayer.reset();
                mediaPlayer.stop();
            }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(this, Uri.parse(mp3Info.getUrl()));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            isplaying=true;
            ispasue=false;
            songBottom_view.setPlay_bottom(true);

        }
    }


    private void initListener() {
        songBottom_view.setBottomListener(new SongsBottom_View.BottomListener() {
            @Override
            public void setSimpleDrawerViewListener() {
                //图片的点击事件

            }

            @Override
            public void setPlayListener() {
                //播放
                if (mp3Info!=null){
                     musicPlay();
                }
            }
            @Override
            public void setNextListener() {
                //下一首

            }

            @Override
            public void setMenuListListener() {
                //菜单
            }
        });
    }
 //点击播放按钮
    private void musicPlay() {
        if (isplaying){
            mediaPlayer.pause();
            ispasue=true;
            isplaying=false;
            songBottom_view.setPlay_bottom(false);
        }else {
            System.out.println(mp3Info.getTitle().toString()+"zheshi   snaonoafna");
            musicStart(mp3Info);
            songBottom_view.setPlay_bottom(true);
            isplaying=true;
            ispasue=false;
        }
    }

}
