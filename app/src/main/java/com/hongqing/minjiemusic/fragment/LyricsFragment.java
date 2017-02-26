package com.hongqing.minjiemusic.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hongqing.minjiemusic.R;
import com.hongqing.minjiemusic.utils.Constant;
import com.hongqing.minjiemusic.utils.DownloadUtils;
import com.hongqing.minjiemusic.utils.MlyException;
import com.hongqing.minjiemusic.view.view.DefaultLrcBuilder;
import com.hongqing.minjiemusic.view.view.ILrcBuilder;
import com.hongqing.minjiemusic.view.view.ILrcView;
import com.hongqing.minjiemusic.view.view.LrcRow;
import com.hongqing.minjiemusic.view.view.LrcView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by 贺红清 on 2017/2/20.
 */

public class LyricsFragment extends BaseFragment {

    private View view;
    private String songName;
    private String artist;
    public static   LyricsFragment getIntance(){
        //在这种只用一个对象的情况下 建议使用单例设计模式进行创建 这样 确定只有一个对象正在操作
        LyricsFragment lyricsFragment=new LyricsFragment();
        return  lyricsFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lyrics_fragment_layout, container, false);
        initView();
        return view;
    }
    private LrcView lrcView;

    private void initView() {
        lrcView= (LrcView) view.findViewById(R.id.lrcView);
        lrcView.setListener(new ILrcView.LrcViewListener() {
            @Override
            public void onLrcSeeked(int newPosition, LrcRow row) {
            }
        });
        //提示内容  正在加载歌词中
        lrcView.setLoadingTipText(getString(R.string.load_lrc));
        load();
    }
    public void setInfo(String songName,String artist){
        this.songName =songName;
        this.artist =artist;
    }
    public void load() {
        File lrcDirFile = new File(Environment.getExternalStorageDirectory() + Constant.DIR_MUSIC + songName + "-" + artist + ".lrc");
        if(lrcDirFile.exists()){
            loadLrc(lrcDirFile.getPath());
        }else {
            //下载歌词
            DownloadUtils.getInstance().downloadLRC(getContext(),songName, artist)
                    .setListener(new DownloadUtils.OnDownloadListener() {
                        @Override
                        public void onDownload(String result, MlyException e) {
                            if(e==null){
                                loadLrc(result);
                            }else{
                                tipText(e.toString());
                                lrcView.setLrc(null);
                            }

                        }
                    });
        }
    }

    private void loadLrc(String path) {
        //加载歌词
        if(path!=null && lrcView!=null) {
            File lrcFile = new File(path);
            StringBuffer buf = new StringBuffer(1024 * 10);
            char[] chars = new char[1024];
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(lrcFile)));
                int len = -1;
                while ((len = in.read(chars)) != -1) {
                    buf.append(chars, 0, len);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ILrcBuilder builder = new DefaultLrcBuilder();
            List<LrcRow> rows = builder.getLrcRows(buf.toString());
            lrcView.setLrc(rows);
        }
    }


    //设置同步播放的时间进度
    public void seekLrcToTime(long time){
        lrcView.seekLrcToTime(time);
    }
    //设置提示内容
    public void tipText(String text){
        lrcView.setLoadingTipText(text);
    }
}
