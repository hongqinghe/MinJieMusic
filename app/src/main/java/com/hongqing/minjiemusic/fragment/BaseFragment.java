package com.hongqing.minjiemusic.fragment;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.vo.MessageEvent;
import com.hongqing.minjiemusic.vo.MessageEventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class BaseFragment extends Fragment{
    private FragmentManager manager;
    private FragmentTransaction ft;

    @Override
    public void onResume() {
        super.onResume();
        manager=getActivity().getSupportFragmentManager();
        ft=manager.beginTransaction();
    }
    public void toast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
