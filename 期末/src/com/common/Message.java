package com.common;

import java.io.Serializable;

/**
 * 服务器和客户端的交互
 */
public class Message implements Serializable {

    private MsgType type;//类型
    private String content;//信息
    private String senderId;//发送者id标识
    private String senderName;//发送者
    private String getterId;//接收者id标识
    private String sendTime;//接收者

    public Message() {
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getGetterId() {
        return getterId;
    }

    public void setGetterId(String getterId) {
        this.getterId = getterId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return type + "--"+sendTime + ":" + senderId + "��" + getterId + "˵��" + content;
    }
}