package com.hongqing.minjiemusic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.utils.MediaUtils;
import com.hongqing.minjiemusic.vo.Mp3Info;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个根据歌名来查找出相对应的文件路径
 * Created by 贺红清 on 2017/2/16.
 */

public class Folder_listViewAdapter extends BaseAdapter {
    private Context context;
    private List<Mp3Info> mp3InfoList;
    private String[] count;
    private View foot;

    private List<String > stringList;  //用来存贮file的值
    private List<ArrayList<CharSequence> > stringList2;  //用来存贮file的值
    public Folder_listViewAdapter(Context context) {
        this.context = context;
    }
    public Folder_listViewAdapter(Context context, List<Mp3Info> mp3InfoList, View foot) {
        this.context = context;
        this.mp3InfoList = mp3InfoList;
         this.foot=foot;
        stringList=new ArrayList<>();
        stringList2=new ArrayList<>();

         for (int i=0;i<mp3InfoList.size();i++){
             count = MediaUtils.getMp3File(context,mp3InfoList.get(i).getTitle());
             stringList.add(count[0]);
         }
        System.out.println(count.length+"------------------------");
      TextView tv_album_footView= (TextView) foot.findViewById(R.id.tv_album_footView);
        tv_album_footView.setText(mp3InfoList.size()+"首歌");
    }
    @Override
    public int getCount() {
        return stringList.size();
//        return count.length;
    }

    @Override
    public Object getItem(int position) {
//        return count[position];
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        if (contentView==null){
            contentView= LayoutInflater.from(context).inflate(R.layout.folder_listeview_item_layout,null);
        }
        ImageView iv_folder=MyViewHolder.getView(contentView,R.id.iv_folder,null);
        final TextView tv_folder_name=MyViewHolder.getView(contentView,R.id.tv_folder_name,null);
        final    TextView tv_folder=MyViewHolder.getView(contentView,R.id.tv_folder,null);
        final Mp3Info mp3Info=mp3InfoList.get(position);
//        System.out.println(stringList2.get(position));
        tv_folder_name.setText(stringList.get(position));
        tv_folder.setText(mp3Info.getUrl());
        return contentView;
    }
}
