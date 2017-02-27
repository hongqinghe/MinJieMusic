package com.hongqing.minjiemusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.vo.Mp3Info;

import java.io.File;
import java.util.List;

import static com.hongqing.minjiemusic.view.view.LrcRow.TAG;

/**
 * Created by 贺红清 on 2017/2/27.
 */

public class Down_recycleViewAdapter extends RecyclerView.Adapter<Down_recycleViewAdapter.MyViewHolder> {
  private Context  context;
    private List<Mp3Info> mp3InfoList;
  private File[] files;
    public Down_recycleViewAdapter(Context context, List<Mp3Info> mp3InfoList) {
        this.context = context;
        this.mp3InfoList = mp3InfoList;
    }

    public Down_recycleViewAdapter(Context context, File[] files) {
        this.context = context;
        this.files = files;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.down_item_recycleview,null);
        System.out.println("=============");
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Mp3Info mp3Info = mp3InfoList.get(position);
//        holder.imageView_music.setImageResource();
//        holder.tv_songName.setText(mp3Info.getTitle());
//        holder.tv_singer.setText(mp3Info.getArtist());
        System.out.println(files.length+"=============");
        holder.tv_songName.setText(files[position].getName());
    }

    @Override
    public int getItemCount() {
//        return mp3InfoList.size();
        return files.length;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_songName;
        private  final ImageView imageView_music;
        private final TextView tv_singer;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView_music = (ImageView) itemView.findViewById(R.id.imageView_music);
            tv_songName = (TextView) itemView.findViewById(R.id.tv_songName);
            tv_singer = (TextView) itemView.findViewById(R.id.tv_singer);
        }
    }
}
