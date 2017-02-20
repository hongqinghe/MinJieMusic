package com.hongqing.minjiemusic.adapter;

import android.content.ContentResolver;
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
import com.hongqing.minjiemusic.utils.MediaUtilsDemo;
import com.hongqing.minjiemusic.vo.Mp3Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class Singer_listViewAdapter extends BaseAdapter {
    private Context context;
    private List<Mp3Info> mp3InfoList;
    private long[] count;
    private List<Long> art_idList;
    private List<String > stringList;
    private View foot;
    public Singer_listViewAdapter(Context context) {
        this.context = context;
    }

    public Singer_listViewAdapter(Context context, List<Mp3Info> mp3InfoList,View foot) {
        this.context = context;
        this.mp3InfoList = mp3InfoList;
        stringList=new ArrayList<>();
        art_idList=new ArrayList<>();
        this.foot=foot;
        for (int i=0;i<mp3InfoList.size();i++){
            stringList.add(mp3InfoList.get(i).getArtist());
            art_idList.add(mp3InfoList.get(i).getMp3InfoId());
        }
        TextView tv_album_footView= (TextView) foot.findViewById(R.id.tv_album_footView);
        tv_album_footView.setText(stringList.size()+"个歌手");
    }
    public Singer_listViewAdapter(Context context, List<Mp3Info> mp3InfoList, long[] count ,View foot) {
        this.context = context;
        this.mp3InfoList = mp3InfoList;
        this.count = count;

    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
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
        TextView tv_singer_singer=MyViewHolder.getView(contentView,R.id.tv_singer_singer,null);
        Mp3Info mp3Info=mp3InfoList.get(position);
         count=MediaUtils.getMp3CountSinger(context,stringList.get(position));
         //这里使用getAlbumPhoto 方法加载数据  这样速度比较快
        Uri uri=MediaUtils.getAlbumPhoto(context,mp3Info.getAlbumId(),art_idList.get(position));
        sdv_singerPhoto.setImageURI(uri);
        tv_song_Count_singer.setText(count.length+"首");
        tv_singer_singer.setText(mp3Info.getArtist());
        return contentView;
    }
}
