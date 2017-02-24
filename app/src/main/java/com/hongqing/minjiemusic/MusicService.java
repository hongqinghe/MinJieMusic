package com.hongqing.minjiemusic;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hongqing.minjiemusic.fragment.MineFragment;
import com.hongqing.minjiemusic.utils.Constant;
import com.hongqing.minjiemusic.vo.MessageEvent;
import com.hongqing.minjiemusic.vo.MessageEventType;
import com.hongqing.minjiemusic.vo.Mp3Info;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 贺红清 on 2017/2/20.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mediaPlayer;
    private boolean ispasue = false;//是否是暂停播放
    private boolean isplaying = false;//是否正在播放
    private int index = 0;//定义下标来控制播放哪一首歌
    private boolean isNext = false;//用来判断是不是暂停状态下单价下一首
    private ArrayList<Mp3Info> mp3InfoList;
    private Mp3Info mp3Info;
    private int local_or_net;
    private int currentPosition;//当前播放的进度
    public static   final int ORDER_PLAY=0x5;
    public static   final int RADOM_PLAY=0x6;
    public static   final int SINGLE_PLAY=0x7;
    private int MODE_PLAY;
   private ExecutorService  executorService= Executors.newSingleThreadExecutor();//单线程池用于更新播放进度
    //判断音乐是否正在播放 （也可以通过service直接调用Mediaplay对象来判断）
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    public int getLocal_or_net() {
        return local_or_net;
    }

    //提供播放的下标
    public int getIndex() {
        return index;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
//这里注意要在服务创建的时候进行注册EventBus
// 因为他只会执行一次服务不会多次创建，出现的问题就是没打开一个Activity的时候要进行绑定服务，
// 如果每次在绑定的时候都进行注册EventBus 造成消息无法接受的现象
        EventBus.getDefault().register(this);
        //设置默认播放模式
        setMODE_PLAY(ORDER_PLAY);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("unbind服务");
        return super.onUnbind(intent);
    }

    @Override
    public boolean stopService(Intent name) {
        System.out.println("stop服务");
        EventBus.getDefault().unregister(this);
        return super.stopService(name);
    }
    //对外提供数据，就要提供方法，外面调用方法拿到数据（这里将mp3List集合提供）
    //思路二  通过接口设置数据   在listView中设置监听接口，Activity实现接口，通过接口就传入数据让服务进行播放  这样
    //他就不用注册为监听者了
    public List<Mp3Info> getMp3InfoList(){
        return  mp3InfoList;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }
     //注意这里的播放完成事件要写在prepared方法中
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        if (musicUpdateListener!=null)
        musicUpdateListener.changeData(index); //当前播放的是哪个位置 通过位置查找集合中的数据
    }
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        mediaPlayer.reset();
        return false;
    }
    //这里设置完成事件的时候会出现一个异常  就是在播放网络音乐的时候，由于网络状态的状态是异步缓冲的所以就会造成一个
    //播放异常，点击播放的时候，他会跳到下一首，不会从当前歌曲进行播放所以需要在暂停的逻辑里面将
    //  mediaPlayer.setOnCompletionListener(null);置为空，这样就不会出现播放异常

    public int getMODE_PLAY() {
        return MODE_PLAY;
    }

    public void setMODE_PLAY(int MODE_PLAY) {
        this.MODE_PLAY = MODE_PLAY;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        switch (MODE_PLAY){
            case ORDER_PLAY:  //顺序播放
                musicNext();
                break;
            case SINGLE_PLAY:  //单曲循环
                musicStart(mp3InfoList.get(index));
                break;
            case  RADOM_PLAY:   //随机播放
                Random random=new Random();
                musicStart(mp3InfoList.get(random.nextInt(mp3InfoList.size())));
                break;
        }
    }
    public int  getCurrentPosition() {
        if (mediaPlayer != null)
            return mediaPlayer.getCurrentPosition();
        else return 0;
    }
  //设置seekBar的进度
    public void seekBar(int  currentPosition){
        mediaPlayer.seekTo(currentPosition);
    }
    private class Update implements Runnable {
        public Update (){
          isplaying=true;
        }
        @Override
        public void run() {
            while (isplaying){
                    if (musicUpdateListener!=null){
                        musicUpdateListener.updateProgess(getCurrentPosition());
                    }
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //绑定服务
    class MyBind extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageSubscriber(MessageEvent event) {
        if (event.type == MessageEventType.PLAY_MUSIC) {
            index = event.position;
            local_or_net = event.local_or_net;
            mp3InfoList = (ArrayList<Mp3Info>) event.data;
            mp3Info = mp3InfoList.get(index);
            musicUpdateListener.changeData(index);
            musicStart(mp3Info);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //点击播放按钮
    public void musicPlay() {
        if (isplaying) {
            mediaPlayer.pause();
            isplaying = false;
            musicUpdateListener.changeData(index);//暂停播放的时候也要改变状态
            mediaPlayer.setOnCompletionListener(null);
            ispasue = true;
            isNext = true;
        } else {
            isNext = false;
            musicStart(mp3Info);

            isplaying = true;
            ispasue = false;
        }
    }
   public void musicPrev(){
       if (mp3Info!=null){
           if (index!=0&&index-1>=0){
               index--;
           }else {
               int size = mp3InfoList.size()-1;
               index= size;
           }
           isNext=true;
           musicStart(mp3InfoList.get(index));
       }

   }
    public void musicNext() {
        //判断不为空的时候进行传递
        if (mp3Info != null) {

            //设置播放模式
            if (index + 1 < mp3InfoList.size()) {
                index += 1;
            } else {
                index = 0;//置为0循环播放
            }
            isNext = true;//每次点击的时候都置为TRUE
            mp3Info = mp3InfoList.get(index);
            musicStart(mp3Info);
        }
    }

    //播放音乐
    public void musicStart(Mp3Info mp3Info) {
        if (ispasue) {
            if (isNext) {
                ispasue = false;
                isNext = false;
                mediaPlayer.reset();
                musicStart(mp3Info);
            }

            mediaPlayer.start();
            isplaying = true;
            musicUpdateListener.changeData(index);
        } else {
            if (mediaPlayer.isPlaying()) {  //判断是不是正在播放  ，如果正在播放则就释放 然后在进行播放
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            System.out.println("正真播放");

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(this, Uri.parse(mp3Info.getUrl()));
                //判断是网络播放的话要异步缓冲进行播放 ，且要设置监听事件
                if (local_or_net == Constant.LOCAL_LIST) {
                    mediaPlayer.prepare();
                } else if (local_or_net == Constant.NET_LIST) {
                    mediaPlayer.prepare();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            isplaying = true;
            ispasue = false;
        }
        executorService.execute(new Update());
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
    private MusicUpdateListener musicUpdateListener;

    public void setMusicUpdataListener(MusicUpdateListener musicUpdataListener) {
        this.musicUpdateListener = musicUpdataListener;
    }

    interface MusicUpdateListener{
        void updateProgess(long progress);
        void changeData(long   currentPosition);
    }



}
