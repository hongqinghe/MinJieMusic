package com.hongqing.minjiemusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.vo.Mp3Info;

import java.util.List;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class Singles_listViewAdapter extends BaseAdapter {
    private Context context;
    private List<Mp3Info> mp3InfoList;
     private View foot;
    public Singles_listViewAdapter(Context context) {
        this.context = context;
    }

    public Singles_listViewAdapter(Context context, List<Mp3Info> mp3InfoList,View foot) {
        this.context = context;
        this.mp3InfoList = mp3InfoList;
        this.foot=foot;
        TextView tv_album_footView= (TextView) foot.findViewById(R.id.tv_album_footView);
        tv_album_footView.setText(mp3InfoList.size()+"首歌");
    }
    @Override
    public int getCount() {
        return mp3InfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mp3InfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        if (contentView==null){
            contentView= LayoutInflater.from(context).inflate(R.layout.listview_singles_layout,null);
        }
        TextView tv_songName__singles=MyViewHolder.getView(contentView,R.id.tv_songName__singles,null);
        TextView tv_singer_singles=MyViewHolder.getView(contentView,R.id.tv_singer_singles,null);
        Mp3Info mp3Info=mp3InfoList.get(position);
        tv_songName__singles.setText(mp3Info.getTitle());
        tv_singer_singles.setText(mp3Info.getArtist());
        return contentView;
    }
}
