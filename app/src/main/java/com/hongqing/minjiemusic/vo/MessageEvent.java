package com.hongqing.minjiemusic.vo;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class MessageEvent {
    public MessageEventType type;
    public Object data;
    public int position;
    public int local_or_net;
    public MessageEvent(MessageEventType type) {
        this.type = type;
    }

    public MessageEvent(MessageEventType type, Object data) {
        this.type = type;
        this.data = data;
    }
      //参数  Message的类型   数据  位置（从listView 中点击的位置 ）   本地音乐还是网络播放
    public MessageEvent(MessageEventType type, Object data, int position,int local_or_net) {
        this.type = type;
        this.data = data;
        this.position = position;
        this.local_or_net=local_or_net;
    }
}
