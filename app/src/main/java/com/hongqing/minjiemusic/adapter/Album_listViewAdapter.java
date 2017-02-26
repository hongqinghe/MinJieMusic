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
 * 这个根据专辑Id来查找出相对应的
 * Created by 贺红清 on 2017/2/16.
 */

public class Album_listViewAdapter extends BaseAdapter {
    private Context context;
    private List<Mp3Info> mp3InfoList;
    private long[] count;
    private View foot;
    private List<String > stringList;  //用来存贮AlbumId的值
    public Album_listViewAdapter(Context context) {
        this.context = context;
    }
    public Album_listViewAdapter(Context context, List<Mp3Info> mp3InfoList,View foot) {
        this.context = context;
        this.mp3InfoList = mp3InfoList;
         this.foot=foot;
        stringList=new ArrayList<>();
        for (int i=0;i<mp3InfoList.size();i++){
            long id=mp3InfoList.get(i).getAlbumId();
            long id2=mp3InfoList.get(i+=1).getAlbumId();
            if (mp3InfoList.get(i).getAlbumId()!=mp3InfoList.get(i+=1).getAlbumId()){
                stringList.add(String.valueOf(mp3InfoList.get(i).getAlbumId()));
            }
        }
      TextView tv_album_footView= (TextView) foot.findViewById(R.id.tv_album_footView);
        tv_album_footView.setText(stringList.size()+"个专辑");
    }
    public Album_listViewAdapter(Context context, List<Mp3Info> mp3InfoList, long[] count) {
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
            contentView= LayoutInflater.from(context).inflate(R.layout.album_listeview_item_layout,null);
        }
        SimpleDraweeView sdv_singerPhoto=MyViewHolder.getView(contentView,R.id.sdv_singerPhoto,null);
        final    TextView tv_song_count_album=MyViewHolder.getView(contentView,R.id.tv_song_count_album,null);
        final TextView tv_album=MyViewHolder.getView(contentView,R.id.tv_album,null);
        final Mp3Info mp3Info=mp3InfoList.get(position);
         count=MediaUtils.getMp3CountAlbum(context, Long.parseLong(stringList.get(position)));
        //根据iD查出对应的专辑照片
//        Bitmap bitmap=MediaUtils.getArtwork(context,mp3Info.getMp3InfoId(),mp3Info.getAlbumId(),true,false);
        Uri uri=MediaUtils.getAlbumPhoto(context,mp3Info.getAlbumId(),mp3Info.getMp3InfoId());
          //拿到了bitmap对象之后  将bitmap转换为uri
//        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null));
        if (count.length>=1){
            sdv_singerPhoto.setImageURI(uri);
            tv_song_count_album.setText(count.length+"张");
            tv_album.setText(mp3Info.getAlbum());
        }
        return contentView;
    }
}
