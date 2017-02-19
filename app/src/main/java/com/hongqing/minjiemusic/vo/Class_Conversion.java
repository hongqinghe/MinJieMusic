package com.hongqing.minjiemusic.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类用来  NetMusic和 Mp3Info 之间的转化
 * Created by 贺红清 on 2017/2/19.
 */

public class Class_Conversion {
    public static List<Mp3Info> forMatClass(ArrayList<NetMusic> data){
        List<Mp3Info> mp3InfoList=new ArrayList<>();
        for (NetMusic aaa:data) {
            //2 url, 3歌名 ,5 歌手,4：时长 6：文件大小 7 专辑名
            String url = aaa.music.get(2);
            String song_name = aaa.music.get(3);
            String singer = aaa.music.get(5);
            String s = aaa.music.get(4);
            String size = aaa.music.get(6);
            String album = aaa.music.get(7);

            Mp3Info mp3Info = new Mp3Info();
            mp3Info.setTitle(song_name);
            mp3Info.setUrl(url);
            mp3Info.setArtist(singer);
            mp3Info.setAlbum(album);
            mp3Info.setDuration(Long.parseLong(s));
            mp3Info.setSize(Long.parseLong(size));
            mp3InfoList.add(mp3Info);
        }
        return mp3InfoList;
    }
}
