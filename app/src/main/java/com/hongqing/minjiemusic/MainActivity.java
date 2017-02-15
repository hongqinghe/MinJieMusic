package com.hongqing.minjiemusic;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.hongqing.minjiemusic.fragment.MineFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends BaseActivity {
    @ViewInject(R.id.fragment_layout_main)
    FrameLayout frameLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        fragmentManager=getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_layout_main,new MineFragment());
        ft.commit();
    }
}
