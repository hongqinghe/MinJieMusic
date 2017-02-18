package com.hongqing.minjiemusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 贺红清 on 2017/2/19.
 */

public class RecycleView_lookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static  class MyContentViewHolder extends RecyclerView.ViewHolder {
        public MyContentViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static   class MyHeadViewHolder extends RecyclerView.ViewHolder{
        public MyHeadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
