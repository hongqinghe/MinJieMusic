package com.hongqing.minjiemusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.adapter.Album_listViewAdapter;
import com.hongqing.minjiemusic.adapter.Singer_listViewAdapter;
import com.hongqing.minjiemusic.utils.MediaUtils;
import com.hongqing.minjiemusic.vo.Mp3Info;

import java.util.List;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class AlbumFragment extends BaseFragment {
    private View view;
    private ListView singer_listView;
    private List<Mp3Info> mp3InfoList;
    private Album_listViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view= inflater.inflate(R.layout.singer_fragment_layout,null);
          initView();
        return view;
    }

    private void initView() {
        singer_listView = (ListView) view.findViewById(R.id.singer_listView);
        mp3InfoList= MediaUtils.getMp3Infos(getContext());
        adapter=new Album_listViewAdapter(getContext(),mp3InfoList);
        singer_listView.setAdapter(adapter);
    }


}
