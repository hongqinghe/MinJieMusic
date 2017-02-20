package com.hongqing.minjiemusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.adapter.Album_listViewAdapter;
import com.hongqing.minjiemusic.adapter.Folder_listViewAdapter;
import com.hongqing.minjiemusic.utils.MediaUtils;
import com.hongqing.minjiemusic.vo.Mp3Info;

import java.util.List;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class FolderFragment extends BaseFragment {

    private View view;
    private ListView folder_listView;
    private Folder_listViewAdapter adapter;
    private List<Mp3Info> mp3InfoList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.folder_fragment_layout,null);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        folder_listView = (ListView) view.findViewById(R.id.folder_listView);
        mp3InfoList= MediaUtils.getMp3Infos(getContext());
        for (Mp3Info strings:mp3InfoList){
            System.out.println(strings.toString());
        }
        View foot = LayoutInflater.from(getContext()).inflate(R.layout.album_foot_listview, null);
        folder_listView.addFooterView(foot);
        adapter = new Folder_listViewAdapter(getContext(), mp3InfoList,foot);
        folder_listView.setAdapter(adapter);
    }
}
