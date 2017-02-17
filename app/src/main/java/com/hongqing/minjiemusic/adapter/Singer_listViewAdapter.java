package com.hongqing.minjiemusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.vo.Mp3Info;

import java.util.List;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class Singer_listViewAdapter extends BaseAdapter {
    private Context context;
    private List<Mp3Info> mp3InfoList;

    public Singer_listViewAdapter(Context context) {
        this.context = context;
    }

    public Singer_listViewAdapter(Context context, List<Mp3Info> mp3InfoList) {
        this.context = context;
        this.mp3InfoList = mp3InfoList;
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
            contentView= LayoutInflater.from(context).inflate(R.layout.singer_listview_item_layout,null);
        }
        SimpleDraweeView sdv_singerPhoto=MyViewHolder.getView(contentView,R.id.sdv_singerPhoto,null);
        TextView tv_song_Count_singer=MyViewHolder.getView(contentView,R.id.tv_song_Count_singer,null);
        TextView tv_singer_singles=MyViewHolder.getView(contentView,R.id.tv_singer_singles,null);
        Mp3Info mp3Info=mp3InfoList.get(position);

        tv_song_Count_singer.setText(mp3Info.getTitle());
        tv_singer_singles.setText(mp3Info.getAlbum());
        return contentView;
    }
}
