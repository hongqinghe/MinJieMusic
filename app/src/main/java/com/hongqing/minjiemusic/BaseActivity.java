package com.hongqing.minjiemusic;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    public MusicService musicService;
    private boolean serviceBound = false;//定义一个标记 判断服务是否已绑定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, MusicService.class));
    }

    public void bindMusicService() {
        if (!serviceBound) {
            System.out.println("在 activity中绑定服务  -----============");
            bindService(new Intent(this, MusicService.class), connection, BIND_AUTO_CREATE);
            serviceBound=true;
        }

    }

    public void unBindService() {
        if (serviceBound) {
            unbindService(connection);
            serviceBound = false;
        }
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            System.out.println("ServiceConnection");
            System.out.println("ServiceConnection===="+componentName+"======连接的name============");

            musicService = ((MusicService.MyBind) iBinder).getService();
            //这是设置点击时的监听事件
            musicService.setMusicUpdataListener(new MusicService.MusicUpdateListener() {
                @Override
                public void updateProgess(long progress) {
                    update(progress);
                }

                @Override
                public void changeData(long currentPosition) {
                    System.out.println("监听事键执行了");
                 change(currentPosition);
                }
            });
             //初始化时候的点击事件
            change(musicService.getIndex());
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("onServiceDisconnected--------------");
            musicService=null;
            serviceBound = false;
        }
    };

    protected abstract void change(long currentPosition);

    protected abstract void update(long progress);

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        musicService.stopSelf();
        super.onDestroy();
    }
}

