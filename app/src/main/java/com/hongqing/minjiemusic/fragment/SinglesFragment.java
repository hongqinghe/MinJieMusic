package com.hongqing.minjiemusic.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.adapter.Singles_listViewAdapter;
import com.hongqing.minjiemusic.utils.Constant;
import com.hongqing.minjiemusic.utils.MediaUtils;
import com.hongqing.minjiemusic.vo.MessageEvent;
import com.hongqing.minjiemusic.vo.MessageEventType;
import com.hongqing.minjiemusic.vo.Mp3Info;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class SinglesFragment extends BaseFragment implements AdapterView.OnItemClickListener, Serializable {
    private ListView listView_singles;
    private View view;
    private List<Mp3Info> mp3InfoList;
    private Singles_listViewAdapter listViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.singles_fragment_layout, null);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        listView_singles = (ListView) view.findViewById(R.id.listView_localSongs_singles);
        mp3InfoList = new ArrayList<>();
        mp3InfoList = MediaUtils.getMp3Infos(getContext());
        //添加尾部
        View foot = LayoutInflater.from(getContext()).inflate(R.layout.album_foot_listview, null);
        listView_singles.addFooterView(foot);
        listViewAdapter = new Singles_listViewAdapter(getContext(), mp3InfoList, foot);
        listView_singles.setAdapter(listViewAdapter);
        listView_singles.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        EventBus.getDefault().post(new MessageEvent(MessageEventType.PLAY_MUSIC, mp3InfoList, position, Constant.LOCAL_LIST));
    }

}
