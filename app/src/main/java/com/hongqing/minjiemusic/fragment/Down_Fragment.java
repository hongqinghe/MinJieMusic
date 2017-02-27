package com.hongqing.minjiemusic.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.adapter.Down_recycleViewAdapter;
import com.hongqing.minjiemusic.utils.Constant;
import com.hongqing.minjiemusic.vo.Mp3Info;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import static com.hongqing.minjiemusic.view.view.LrcRow.TAG;

/**
 * Created by 贺红清 on 2017/2/27.
 */

public class Down_Fragment extends BaseFragment {

    private View view;
    private RecyclerView recyclerView;
 private Down_recycleViewAdapter adapter;
    private List<Mp3Info> mp3InfoList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.down_fragment,null);
        initView();
        return view;
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        adapter=new Down_recycleViewAdapter(getContext(),scanDownloadMusicCount());
        recyclerView.setAdapter(adapter);
    }
    private File[] scanDownloadMusicCount(){
        File file = new File(Environment.getExternalStorageDirectory()+ Constant.DIR_MLY_MUSIC);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                if(Constant.DIR_LRC.endsWith(s)){
                    return false;
                }
                return true;
            }
        });
        Log.i(TAG, "scanDownloadMusicCount: "+files.length);
        return files;
    }
}
