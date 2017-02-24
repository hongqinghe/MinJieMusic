package com.hongqing.minjiemusic;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hongqing.minjiemusic.fragment.GTBFragment;
import com.hongqing.minjiemusic.fragment.LXBFragment;
import com.hongqing.minjiemusic.fragment.LocalSongsFragment;
import com.hongqing.minjiemusic.fragment.LookFragment;
import com.hongqing.minjiemusic.fragment.MineFragment;
import com.hongqing.minjiemusic.fragment.NDBFragment;
import com.hongqing.minjiemusic.utils.Constant;
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

public class MainActivity extends BaseActivity  {

    private FragmentTransaction ft;
    private SongsBottom_View songBottom_view;
    public static MediaPlayer mediaPlayer;
    private boolean isNext = true;//用来判断是不是暂停状态下单价下一首
    private ArrayList<Mp3Info> mp3InfoList;
    private Mp3Info mp3Info;
    private MineFragment mineFragment;
    private int local_or_net;

    /**
     * 在onCreate中创建对象 ，要不在activity不可见的时候他会重新创建对象，
     * 会在成一个fragment already add... 这个异常
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        //添加6.0权限
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
        ,Manifest.permission.READ_EXTERNAL_STORAGE},1);
        mineFragment =new MineFragment();
        FragmentManager  fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_layout_main,mineFragment);
        transaction.commit();
        songBottom_view = (SongsBottom_View) findViewById(R.id.songBottom_view);

        initListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        bindMusicService();
    }

    @Override
    protected void change(long currentPosition) {

        //通过接口更新状态的方法  先进行非空判断，避免初始化第一次启动异常
        if (musicService.getMp3InfoList() != null) {
        songBottom_view.setSongInfo(musicService.getMp3InfoList().get((int) currentPosition).getTitle(),
                musicService.getMp3InfoList().get((int) currentPosition).getArtist());
        if (musicService.isPlaying()) {
            songBottom_view.setPlay_bottom(true);
        }else {
            songBottom_view.setPlay_bottom(false);
        }

        }
    }

    @Override
    protected void update(long progress) {
   //更新进度条的方法
    }

    @Override
    protected void onPause() {
        unBindService();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    private void initListener() {
        songBottom_view.setBottomListener(new SongsBottom_View.BottomListener() {
            @Override
            public void setSimpleDrawerViewListener() {
                //图片的点击事件
                Intent intent=new Intent(MainActivity.this,PlayActivity.class);
                startActivity(intent);
            }
            @Override
            public void setPlayListener() {
                //播放
          if (musicService.getMp3InfoList()!=null) {
              System.out.println("点击了播放按钮");
              musicService.musicPlay();
          }
            }
            @Override
            public void setNextListener() {
                //下一首
                musicService.musicNext();
            }
            @Override
            public void setMenuListListener() {
                //菜单
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void MessageSunsriberBack(MessageEvent event){
        if (event.type==MessageEventType.BACK_MINE){
          FragmentManager  fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragmentManager.popBackStack();
            transaction.commit();
        }
    }
    //显示本地音乐
    @Subscribe
    public void MessageSubscriberLocal(MessageEvent event){
        Log.i("MessageSubscriber",event.type+"");
        if (event.type== MessageEventType.SHOW_LOCAL_SONGS) {
            FragmentManager  fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_layout_main,new LocalSongsFragment());
            transaction.hide(mineFragment);  //隐藏当前的Fragment
            transaction.addToBackStack(null);//添加回退栈
            transaction.commit();

        }
    }
    @Subscribe
    public void MessageSubscriberNdb(MessageEvent event){
        Log.i("MessageSubscriber",event.type+"");
        if (event.type== MessageEventType.SHOW_NDB_FRAGMENT) {
            FragmentManager  fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_layout_main,new NDBFragment());
            transaction.hide(mineFragment);  //隐藏当前的Fragment
            transaction.addToBackStack(null);//添加回退栈
            transaction.commit();
        } else    if (event.type== MessageEventType.SHOW_GTB_FRAGMENT) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_layout_main, new GTBFragment());
            transaction.hide(mineFragment);  //隐藏当前的Fragment
            transaction.addToBackStack(null);//添加回退栈
            transaction.commit();
        }else    if (event.type== MessageEventType.SHOW_LXB_FRAGMENT) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_layout_main, new LXBFragment());
            transaction.hide(mineFragment);  //隐藏当前的Fragment
            transaction.addToBackStack(null);//添加回退栈
            transaction.commit();
        }
    }

}
