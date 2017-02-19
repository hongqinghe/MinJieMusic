package com.hongqing.minjiemusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.vo.Mp3Info;
import com.hongqing.minjiemusic.vo.NetMusic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 贺红清 on 2017/2/19.
 */

public class NDB_RecycleViewAdapter extends RecyclerView.Adapter<NDB_RecycleViewAdapter.MyViewHolder>{
  private Context context;
    private List<NetMusic> mp3InfoList;
   private  OnItemClickListener onItemClickListener;
    public NDB_RecycleViewAdapter(Context context, List<NetMusic> mp3InfoList) {
        this.context = context;
        this.mp3InfoList = mp3InfoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ndb_item_recycleview, null);
        //设置位置
        view.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NetMusic netMusic = mp3InfoList.get(position);
        ArrayList<String> music = netMusic.music;

        holder.tv_songName_ndb.setText(music.get(3));
        holder.tv_singer_ndb.setText(music.get(5));
        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positionId=holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView,positionId);
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        return mp3InfoList.size();
    }

    public static   class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_singer_ndb;
        private final TextView tv_songName_ndb;
        private final LinearLayout line_ndb;

        public MyViewHolder(View itemView) {
            super(itemView);
            line_ndb = (LinearLayout) itemView.findViewById(R.id.line_ndb);
            tv_singer_ndb = (TextView) itemView.findViewById(R.id.tv_singer_ndb);
            tv_songName_ndb = (TextView) itemView.findViewById(R.id.tv_songName_ndb);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public  interface  OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
