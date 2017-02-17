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

import java.util.List;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class Singer_listViewAdapter extends BaseAdapter {
    private Context context;
    private List<Mp3Info> mp3InfoList;
    private long[] count;
    public Singer_listViewAdapter(Context context) {
        this.context = context;
    }

    public Singer_listViewAdapter(Context context, List<Mp3Info> mp3InfoList) {
        this.context = context;
        this.mp3InfoList = mp3InfoList;
    }

    public Singer_listViewAdapter(Context context, List<Mp3Info> mp3InfoList, long[] count) {
        this.context = context;
        this.mp3InfoList = mp3InfoList;
        this.count = count;
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
        TextView tv_singer_singer=MyViewHolder.getView(contentView,R.id.tv_singer_singer,null);
        Mp3Info mp3Info=mp3InfoList.get(position);
         count=MediaUtils.getMp3CountSinger(context,mp3Info.getArtist());
        Bitmap bitmap=MediaUtils.getArtwork(context,mp3Info.getMp3InfoId(),mp3Info.getAlbumId(),true,false);
          //拿到了bitmap对象之后  将bitmap转换为uri
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null));

        sdv_singerPhoto.setImageURI(uri);
        tv_song_Count_singer.setText(count.length+"首");
        tv_singer_singer.setText(mp3Info.getArtist());
        return contentView;
    }
}
