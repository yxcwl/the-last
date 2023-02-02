package com.server.tools;

import com.common.Message;
import com.common.MsgType;
import com.server.view.ServerFrame;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ��������ͻ���ͨ���߳�
 */
public class ServerConClientThread extends Thread {

    private Socket client;
    private volatile boolean isRunning;

    public ServerConClientThread(Socket client) {
        this.client = client;
        this.isRunning = true;
    }

    public Socket getClient() {
        return client;
    }

    public void myStop(){
        isRunning = false;
    }


    public void notifyOthers(String uid){
        ObjectOutputStream out = null;
        Message msg = new Message();
        msg.setType(MsgType.RET_ONLINE_FRIENDS);
        msg.setContent(ManageClientThread.getOnLineList());
        for (Object o : ManageClientThread.getClientThreads().keySet()) {
            try {
                String id = o.toString();
                if(!id.equals(uid)){//����֪ͨ�Լ�
                    msg.setGetterId(id);
                    out = new ObjectOutputStream(ManageClientThread.getClientThread(id).client.getOutputStream());
                    out.writeObject(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        try {
            while(isRunning){
                ObjectInputStream input = new ObjectInputStream(this.client.getInputStream());
                Message msg = (Message) input.readObject();
                //�ж���Ϣ����
                if(msg.getType() == MsgType.GET_ONLINE_FRIENDS) {
                    msg.setType(MsgType.RET_ONLINE_FRIENDS);
                    msg.setGetterId(msg.getSenderId());
                    msg.setContent(ManageClientThread.getOnLineList());
                    ObjectOutputStream output = new ObjectOutputStream(ManageClientThread.getClientThread(msg.getGetterId()).client.getOutputStream());
                    output.writeObject(msg);
                    System.out.println("返回列表成功");
                }else if(msg.getType() == MsgType.COMMON_MESSAGE) {
                    System.out.println(msg.toString());
                    ServerConClientThread thread = ManageClientThread.getClientThread(msg.getGetterId());//除了自己不通知
                    if(null == thread){//不在线的化，进行通知

                        ObjectOutputStream output = new ObjectOutputStream(ManageClientThread.getClientThread(msg.getSenderId()).client.getOutputStream());
                        msg.setType(MsgType.NOT_ONLINE);
                        output.writeObject(msg);
                        System.out.println("通知成功");
                    }else{
                        ObjectOutputStream output = new ObjectOutputStream(thread.client.getOutputStream());
                        output.writeObject(msg);
                        System.out.println("转发成功");
                    }
                }else if(msg.getType() == MsgType.UNLOAD_LOGIN) {
                    String fromId = msg.getSenderId();
                    //�������߳�
                    myStop();
                    ManageClientThread.removeClientThread(fromId);
                    notifyOthers(fromId);
                    System.out.println(fromId+" 已退出");
                    ServerFrame.showMsg("用户"+fromId+"已退出");
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}