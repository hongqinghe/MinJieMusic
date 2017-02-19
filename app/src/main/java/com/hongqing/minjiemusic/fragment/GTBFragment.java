package com.hongqing.minjiemusic.fragment;

import android.accounts.NetworkErrorException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.adapter.NDB_RecycleViewAdapter;
import com.hongqing.minjiemusic.utils.Constant;
import com.hongqing.minjiemusic.utils.JsoupUtils;
import com.hongqing.minjiemusic.vo.Class_Conversion;
import com.hongqing.minjiemusic.vo.MessageEvent;
import com.hongqing.minjiemusic.vo.MessageEventType;
import com.hongqing.minjiemusic.vo.Mp3Info;
import com.hongqing.minjiemusic.vo.NetMusic;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * Created by 贺红清 on 2017/2/19.
 */

public class GTBFragment extends BaseFragment {
    private View view;
    private RecyclerView recyclerView_ndb;
    private NDB_RecycleViewAdapter adapter;
    private ArrayList<Mp3Info> mp3InfoList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout loading_layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gtb_fragment_layout, null);
        initView();
        back();
        return view;
    }

    private void back() {
        ImageView iv_back= (ImageView) view.findViewById(R.id.iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent(MessageEventType.BACK_MINE));
            }
        });
    }

    private void initView() {
        recyclerView_ndb = (RecyclerView) view.findViewById(R.id.recyclerView_ndb);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        loading_layout = (LinearLayout) view.findViewById(R.id.loading_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        // 设定下拉圆圈的背景
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        new JsoupUtils().getNetMusic(getContext(), JsoupUtils.TYPE_GTB, new JsoupUtils.GetNetMusicListener() {
            @Override
            public void onSuccess(final ArrayList<NetMusic> data) {
                setSwipeRefreshLayoutStyle(data);
                adapter = new NDB_RecycleViewAdapter(getContext(), data);
                //线性布局管理器  参数  水平或垂直（默认为垂直）是否进行反转
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);
                recyclerView_ndb.setLayoutManager(linearLayoutManager);
            //设置点击事件
                adapter.setOnItemClickListener(new NDB_RecycleViewAdapter.OnItemClickListener() {

                    private List<Mp3Info> mp3InfoList;
                    @Override
                    public void onItemClick(View view, int position) {
                        //将NetMusic和 Mp3Info转化 方便设置播放
                        mp3InfoList = Class_Conversion.forMatClass(data);
                        EventBus.getDefault().post(new MessageEvent(MessageEventType.PLAY_MUSIC, mp3InfoList, position, Constant.NET_LIST));
                    }
                });

                recyclerView_ndb.setAdapter(adapter);
            }
            @Override
            public void onError(NetworkErrorException e) {
                toast("请检查网络状态");
            }
        });
    }
    private void setSwipeRefreshLayoutStyle(final ArrayList<NetMusic> data) {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (data.size()<0){
                    swipeRefreshLayout.setRefreshing(true);
                    loading_layout.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        loading_layout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }
}
