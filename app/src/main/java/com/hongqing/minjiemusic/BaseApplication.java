package com.hongqing.minjiemusic;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.x;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);//frasco的初始化
        x.Ext.init(this);//xutils的初始化

    }
}
