package com.server.model;

import com.common.Message;
import com.common.MsgType;
import com.common.User;
import com.server.tools.JDBC_Util;
import com.server.tools.ManageClientThread;
import com.server.tools.ServerConClientThread;
import com.server.view.ServerFrame;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    private ServerSocket server;
    private Socket client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean isRunning;

    public Server(){
        System.out.println("---------------Server(9999)---------------");
        isRunning = true;
        new Thread(this).start();
    }

    /**
     * 线程结束
     */
    public void myStop() {
        isRunning = false;
        close(server);
    }

    @Override
    public void run() {
        try {

            server = new ServerSocket(9999);
            while(isRunning) {
                //通过阻塞来等待用户输入
                client = server.accept();
                System.out.println("一个客户端已连接....");
                input = new ObjectInputStream(client.getInputStream());
                output = new ObjectOutputStream(client.getOutputStream());
                User u = (User)input.readObject();
                System.out.println(u.toString());
                doUserLogin(u);
            }
        } catch (IOException e) {
            close(output,input,client,server);//释放空间
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 处理逻辑
     * @param u
     */
    private void doUserLogin(User u){
        Message msg = new Message();
        JDBC_Util jc = new JDBC_Util();

        if(null == ManageClientThread.getClientThread(u.getUid())){
            try{
                String qname = jc.checkUserInfo(u);
                if(null != qname){//登录成功
                    msg.setType(MsgType.LOGIN_SUCCEED);
                    msg.setContent(qname + "-" + jc.getFriendsList(u.getUid()));
                    output.writeObject(msg);
                    //客户端连接成功就为其创建线程保持与服务器端通讯
                    ServerConClientThread th = new ServerConClientThread(client);
                    th.start();
                   //添加线程
                    ManageClientThread.addClientThread(u.getUid(),th);
                    //通知用户
                    th.notifyOthers(u.getUid());
                    ServerFrame.showMsg("用户"+u.getUid()+"已成功登录");
                }else{
                    msg.setType(MsgType.LOGIN_FAILED);
                    output.writeObject(msg);
                    close(output,input,client);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{//已登录
            try {
                msg.setType(MsgType.ALREADY_LOGIN);
                output.writeObject(msg);
                close(output,input,client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭多个线程
     */
    private void close(Closeable... ios) {//不固定
        for(Closeable io: ios) {
            try {
                if(null != io)
                    io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}