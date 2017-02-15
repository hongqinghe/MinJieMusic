package com.hongqing.minjiemusic.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by 贺红清 on 2017/2/15.
 */

public class BaseFragment extends Fragment{

    public void toast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
