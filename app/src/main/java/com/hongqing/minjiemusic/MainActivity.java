package com.hongqing.minjiemusic;

import android.Manifest;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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
    private boolean ispasue = false;//是否是暂停播放
    private boolean isplaying = false;//是否正在播放
    public static MediaPlayer mediaPlayer;
    private int index = 0;//定义下标来控制播放哪一首歌
    private boolean isNext = true;//用来判断是不是暂停状态下单价下一首
    private ArrayList<Mp3Info> mp3InfoList;
    private Mp3Info mp3Info;
    private MineFragment mineFragment;
    private LocalSongsFragment localSongsFragment;
    private ViewPager viewpager;
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

    //播放音乐
    private void musicStart(Mp3Info mp3Info) {
        if (ispasue) {
            if (isNext){
                ispasue = false;
                isNext = false;
                mediaPlayer.reset();
                musicStart(mp3Info);
            }
            mediaPlayer.start();
            isplaying = false;
        } else {
            if (mediaPlayer.isPlaying()) {  //判断是不是正在播放  ，如果正在播放则就释放 然后在进行播放

                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(this, Uri.parse(mp3Info.getUrl()));
              //判断是网络播放的话要异步缓冲进行播放 ，且要设置监听事件
                if (local_or_net== Constant.LOCAL_LIST){
                    mediaPlayer.prepare();

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {

                                    musicNext();
                                }
                            });
                            mp.start();//开始播放
                        }
                    });
//                    mediaPlayer.start();
                }else  if (local_or_net== Constant.NET_LIST){
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    musicNext();
                                }
                            });
                            mp.start();//开始播放
                        }
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            songBottom_view.setSongInfo(mp3Info.getTitle(), mp3Info.getArtist());
            isplaying=true;
            ispasue=false;
            isNext=false;
            songBottom_view.setPlay_bottom(true);

        }
    }


    private void initListener() {
        songBottom_view.setBottomListener(new SongsBottom_View.BottomListener() {
            @Override
            public void setSimpleDrawerViewListener() {
                //图片的点击事件
//                Intent intent=new Intent(MainActivity.this,PlayActivity.class);
//                startActivity(intent);
                //参数 类型   播放集合    网络or本地  当前进度
                EventBus.getDefault().post(new MessageEvent(MessageEventType.SHOW_PLAY_ACTIVITY
                        ,mp3InfoList,index,local_or_net,mediaPlayer.getCurrentPosition()));
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
               musicNext();
            }
            @Override
            public void setMenuListListener() {
                //菜单
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                //发生错误的时候调用
                mediaPlayer.reset();
                return false;
            }
        });
    }
 //点击播放按钮
    private void musicPlay() {
        if (isplaying){
            mediaPlayer.pause();
            ispasue=true;
            isNext=true;
            isplaying=false;
            songBottom_view.setPlay_bottom(false);
        }else {
            musicStart(mp3Info);
            songBottom_view.setPlay_bottom(true);
            isplaying=true;
            isNext=false;
            ispasue=false;
        }
    }
    private void musicNext() {
        //判断不为空的时候进行传递
        if (mp3Info != null) {
            if (index + 1 < mp3InfoList.size()) {
                index += 1;
            } else {
                index = 0;//置为0循环播放
            }
            isNext = true;//每次点击的时候都置为TRUE
            System.out.println(index+"================index-------");
            mp3Info = mp3InfoList.get(index);
            musicStart(mp3Info);
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageSubscriber(MessageEvent event) {
        Log.i("MessageSubscriber", event.type + "");
        if (event.type == MessageEventType.PLAY_MUSIC) {
            index = event.position;
            local_or_net = event.local_or_net;
            mp3InfoList = (ArrayList<Mp3Info>) event.data;
            mp3Info = mp3InfoList.get(index);
            songBottom_view.setSongInfo(mp3Info.getTitle(), mp3Info.getArtist());
            musicStart(mp3Info);
        }
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
