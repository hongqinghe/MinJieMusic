package com.hongqing.minjiemusic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hongqing.minjiemusic.vo.MessageEvent;
import com.hongqing.minjiemusic.vo.MessageEventType;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.x;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public MusicService musicService;
    private boolean serviceBound = false;//定义一个标记 判断服务是否已绑定
    public BaseApplication application;
    private SlidingMenu menu;
    private LinearLayout line_exit;
    private static ArrayList<Activity> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, MusicService.class));
        //记录没打开的Activity
        list.add(this);
        initSlidingmenu();
        //初始化加载数据库
//        application = (BaseApplication) getApplication();
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
        EventBus.getDefault().unregister(this);
        super.onStop();

    }
    private void initSlidingmenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        //设置触摸屏幕的样式    设置边缘模式滑动打开menu(整个屏幕，边缘，不能通过手势启动三个参数)
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindWidth(500);//相对屏幕的偏移量
        menu.setBehindScrollScale(1);//设置出来的样式   1平移出现  0  代表下方出现
        menu.setFadeDegree(0.5f);//设置渐出值
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);//设置出来时 的样式有基于window和content
        menu.setMenu(R.layout.slidingmenu);
        line_exit = (LinearLayout) findViewById(R.id.line_exit);
        line_exit.setOnClickListener(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exit(MessageEvent event){
       if (event.type==MessageEventType.EXIT_APP){

           stopService(new Intent(this,MusicService.class));//退出service
           //将每个Activity关闭
           for (int i=0;i<list.size();i++){
               list.get(i).finish();
           }
           list = null;
       }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
 //重写menu按钮
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_MENU){
                 menu.toggle();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onClick(View view) {
   switch (view.getId()){
       case R.id.line_exit:
           //退出整个Activity
           toast("退出");
           EventBus.getDefault().post(new MessageEvent(MessageEventType.EXIT_APP));

           break;
   }
    }
}

