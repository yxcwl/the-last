
package com.server.view;

import com.common.Message;
import com.common.MsgType;
import com.server.model.Server;
import com.server.tools.ManageClientThread;
import com.server.tools.ServerConClientThread;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *服务器关闭
 */
public class ServerFrame extends JFrame implements ActionListener ,MouseListener{

    private static final long serialVersionUID = 1L;

    JButton btn_start, btn_close;//功能按钮
    public static JTextArea textArea_log;//日志
    private JLabel jlb_border1, jlb_border2, jlb_border3, jlb_border4, jlb_border5;//用户页面
    private JLabel label_log;//日志标签
    private static DateFormat df;//日志的解析
    private Server s;

    public static void main(String[] args) {
        new ServerFrame();
    }

    public ServerFrame()
    {
        df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
        //��ȡ��������
        Container c = this.getContentPane();
        //���ò���
        c.setLayout(null);
        //���ϲ���
        label_log = new JLabel();
        label_log.setFont(new Font("某某字体",Font.BOLD,20));
        label_log.setText("系统日志记录");
        label_log.setBounds(155, 10, 100, 15);
        c.add(label_log);
        //��־��¼���
        textArea_log = new JTextArea();
        JScrollPane scrollPane_log = new JScrollPane(textArea_log);
        scrollPane_log.setBounds(10, 33, 377, 376);
        c.add(scrollPane_log);

        //������������ť
        btn_start = new JButton("启动服务器");
        btn_start.setBounds(45, 450, 120, 24);
        btn_start.addActionListener(this);
        c.add(btn_start);
        //�������رհ�ť
        btn_close = new JButton("关闭服务器");
        btn_close.setBounds(230, 450, 120, 24);
        btn_close.addActionListener(this);
        c.add(btn_close);

        //分割 1
        jlb_border1 = new JLabel();
        jlb_border1.setBounds(392, 0, 2, 500);
        jlb_border1.setOpaque(true);//��͸��
        jlb_border1.setBackground(Color.GREEN);
        c.add(jlb_border1);
        //分割 2
        jlb_border2 = new JLabel();
        jlb_border2.setBounds(5, 425, 404, 2);
        jlb_border2.setOpaque(true);
        jlb_border2.setBackground(Color.GREEN);
        c.add(jlb_border2);
        //分割 3
        jlb_border3 = new JLabel();
        jlb_border3.setBounds(3, 0, 2, 502);
        jlb_border3.setOpaque(true);
        jlb_border3.setBackground(Color.GREEN);
        c.add(jlb_border3);
        //分割 4
        jlb_border4 = new JLabel();
        jlb_border4.setOpaque(true);
        jlb_border4.setBackground(Color.GREEN);
        jlb_border4.setBounds(5, 500, 404, 2);
        c.add(jlb_border4);
       //分割 5
        jlb_border5 = new JLabel();
        jlb_border5.setBounds(5, 0, 404, 2);
        jlb_border5.setOpaque(true);
        jlb_border5.setBackground(Color.GREEN);
        c.add(jlb_border5);

        this.setResizable(false);//页面设置
        this.setSize(411, 560);//大小
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭方式
        this.setVisible(true);
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    /**
     * 点击后
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btn_start){//启动
            s = new Server();
            showMsg("服务器启动...");
        }
        if(e.getSource() == btn_close){//关闭
            beforeServerClose();
            showMsg("服务器关闭...");
        }
    }


    public static void showMsg(String s) {
        textArea_log.append(df.format(new Date())+": "+s+"\n"+"-".repeat(92)+"\n\n");
    }

    /**
     * 通知用户并结束线程
     */
    private void beforeServerClose(){
        Message msg = new Message();
        msg.setType(MsgType.SERVER_CLOSE);
        for(Object o: ManageClientThread.getClientThreads().keySet()){
            String toId = o.toString();
            msg.setGetterId(toId);
            ServerConClientThread th = ManageClientThread.getClientThread(toId);
            try {
                ObjectOutputStream out = new ObjectOutputStream(th.getClient().getOutputStream());
                out.writeObject(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);//�ȴ����пͻ�������
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s.myStop();//服务器停止
    }

}