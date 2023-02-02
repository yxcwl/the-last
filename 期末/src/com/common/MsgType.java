package com.common;

/**
 * 接收信息的几种情况
 */
public enum MsgType {
    LOGIN_SUCCEED,//成功
    LOGIN_FAILED,//失败
    ALREADY_LOGIN,//已登录
    UNLOAD_LOGIN,//退出
    GET_ONLINE_FRIENDS,//获得好友在线列表
    RET_ONLINE_FRIENDS,//返回好友列表
    NOT_ONLINE,//离线中
    SERVER_CLOSE,//服务器重连
    COMMON_MESSAGE//信息
}