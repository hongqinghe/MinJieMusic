package com.hongqing.minjiemusic;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hongqing.minjiemusic.utils.Constant;

import org.xutils.DbManager;
import org.xutils.x;

import static android.content.ContentValues.TAG;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class BaseApplication extends Application {
    public DbManager dbManager;
    public static Context context;
    public SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);//frasco的初始化

        x.Ext.init(this);//xutils的初始化



        DbManager.DaoConfig config = new DbManager.DaoConfig()
                .setDbName(Constant.DB_NAME)
                .setDbVersion(Constant.VERSION)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        //开启WAL,对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        Log.i(TAG, "onUpgrade: "+oldVersion);
                    }
                });
      dbManager= x.getDb(config);
        context = getApplicationContext();

    }
}
