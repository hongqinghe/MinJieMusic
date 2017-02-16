package com.hongqing.minjiemusic.vo;

/**
 * Created by 贺红清 on 2017/2/16.
 */

public class MessageEvent {
    public MessageEventType type;
    public Object data;
    public int position;
    public MessageEvent(MessageEventType type) {
        this.type = type;
    }

    public MessageEvent(MessageEventType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public MessageEvent(MessageEventType type, Object data, int position) {
        this.type = type;
        this.data = data;
        this.position = position;
    }
}
