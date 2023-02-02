
package com.client.view;

import com.client.tools.ManageChatFrame;
import com.client.tools.ManageThread;
import com.common.Message;
import com.common.MsgType;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户交流，查看记录
 */
public class Chat extends JFrame implements ActionListener,MouseListener {

    private JPanel panel_north;//面板
    private JLabel jbl_touxiang;//头像
    private JLabel jbl_friendname;//好友名称
    private JButton btn_exit, btn_min;//功能按钮

    private JButton btn_func1_north, btn_func2_north, btn_func3_north, btn_func4_north, btn_func5_north, btn_func6_north, btn_func7_north;
    //聊天面板
    private JTextPane panel_Msg;

    private JPanel panel_south;//
    private JTextPane jtp_input;//消息输入
    //��Ϣ�������Ϸ�9�����ܰ�ť(δʵ��)
    private JButton btn_func1_south, btn_func2_south, btn_func3_south,btn_func4_south, btn_func5_south, btn_func6_south, btn_func7_south, btn_func8_south, btn_func9_south;
    private JButton recorde_search;//查看记录
    private JButton btn_send, btn_close;//发送

    private JPanel panel_east;
    private CardLayout cardLayout;//
    //
    private final JLabel label1 = new JLabel(new ImageIcon("image/dialogimage/hh.gif"));
    private JTextPane panel_Record;

    private boolean isDragged = false;
    private Point frameLocation;
    private String myId;
    private String myName;
    private String friendId;
    private DateFormat df;

    public Chat(String myId, String myName, String friendId, String friendName) {

        this.myId = myId;
        this.friendId = friendId;
        this.myName = myName;
        df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
        //��ȡ��������
        Container c = this.getContentPane();
        //���ò���
        c.setLayout(null);

        //�������
        panel_north = new JPanel();
        panel_north.setBounds(0, 0, 729, 92);
        panel_north.setLayout(null);
        //���ӱ������
        c.add(panel_north);
        //���Ͻǻ�ɫͷ��
        jbl_touxiang = new JLabel(new ImageIcon("image/dialogimage/huisetouxiang.png"));
        jbl_touxiang.setBounds(10, 10, 42, 42);
        panel_north.add(jbl_touxiang);
        //ͷ���ҷ���������ĶԷ�����
        jbl_friendname = new JLabel(friendName+"("+friendId+")");
        jbl_friendname.setBounds(62, 21, 105, 20);
        panel_north.add(jbl_friendname);
        //���Ͻ���С����ť
        btn_min = new JButton(new ImageIcon ("image/dialogimage/min.png"));
        btn_min.addActionListener(e -> setExtendedState(JFrame.ICONIFIED));
        btn_min.setBounds(668, 0, 30, 30);
        panel_north.add(btn_min);
        //���Ͻǹرհ�ť
        btn_exit = new JButton(new ImageIcon ("image/dialogimage/exit.jpg"));
        btn_exit.addActionListener(this);
        btn_exit.setBounds(698, 0, 30, 30);
        panel_north.add(btn_exit);
        //ͷ���·����ܰ�ť
        //���ܰ�ť1
        btn_func1_north = new JButton(new ImageIcon("image/dialogimage/yuyin.png"));
        btn_func1_north.setBounds(10, 62, 36, 30);
        panel_north.add(btn_func1_north);
        //���ܰ�ť2
        btn_func2_north = new JButton(new ImageIcon("image/dialogimage/shipin.png"));
        btn_func2_north.setBounds(61, 62, 36, 30);
        panel_north.add(btn_func2_north);
        //���ܰ�ť3
        btn_func3_north = new JButton(new ImageIcon("image/dialogimage/tranfile.jpg"));
        btn_func3_north.setBounds(112, 62, 36, 30);
        panel_north.add(btn_func3_north);
        //���ܰ�ť4
        btn_func4_north = new JButton(new ImageIcon("image/dialogimage/createteam.jpg"));
        btn_func4_north.setBounds(163, 62, 36, 30);
        panel_north.add(btn_func4_north);
        //���ܰ�ť5
        btn_func5_north = new JButton(new ImageIcon("image/dialogimage/yuancheng.png"));
        btn_func5_north.setBounds(214, 62, 36, 30);
        panel_north.add(btn_func5_north);
        //���ܰ�ť6
        btn_func6_north = new JButton(new ImageIcon("image/dialogimage/sharedisplay.png"));
        btn_func6_north.setBounds(265, 62, 36, 30);
        panel_north.add(btn_func6_north);
        //���ܰ�ť7
        btn_func7_north = new JButton(new ImageIcon("image/dialogimage/yingyong.jpg"));
        btn_func7_north.setBounds(318, 62, 36, 30);
        panel_north.add(btn_func7_north);
        //���ñ�����屳��ɫ
        //panel_north.setBackground(new Color(105, 197, 239));
        panel_north.setBackground(new Color(22, 154, 228));

        //�в�����������ʾ����
        panel_Msg = new JTextPane();
        JScrollPane scrollPane_Msg = new JScrollPane(panel_Msg);
        scrollPane_Msg.setBounds(0, 92, 446, 270);
        c.add(scrollPane_Msg);

        //�ϲ����
        panel_south = new JPanel();
        panel_south.setBounds(0, 370, 446, 170);
        panel_south.setLayout(null);
        //�����ϲ����
        c.add(panel_south);
        //����������
        jtp_input = new JTextPane();
        jtp_input.setBounds(0, 34, 446, 105);
        //���ӵ��ϲ����
        panel_south.add(jtp_input);
        //�ı��������Ϸ����ܰ�ť
        //���ܰ�ť1
        btn_func1_south = new JButton(new ImageIcon("image/dialogimage/word.png"));
        btn_func1_south.setBounds(10, 0, 30, 30);
        panel_south.add(btn_func1_south);
        //���ܰ�ť2
        btn_func2_south = new JButton(new ImageIcon("image/dialogimage/biaoqing.png"));
        btn_func2_south.setBounds(47, 0, 30, 30);
        panel_south.add(btn_func2_south);
        //���ܰ�ť3
        btn_func3_south = new JButton(new ImageIcon("image/dialogimage/magic.jpg"));
        btn_func3_south.setBounds(84, 0, 30, 30);
        panel_south.add(btn_func3_south);
        //���ܰ�ť4
        btn_func4_south = new JButton(new ImageIcon("image/dialogimage/zhendong.jpg"));
        btn_func4_south.setBounds(121, 0, 30, 30);
        panel_south.add(btn_func4_south);
        //���ܰ�ť5
        btn_func5_south = new JButton(new ImageIcon("image/dialogimage/yymessage.jpg"));
        btn_func5_south.setBounds(158, 0, 30, 30);
        panel_south.add(btn_func5_south);
        //���ܰ�ť6
        btn_func6_south = new JButton(new ImageIcon("image/dialogimage/dgninput.jpg"));
        btn_func6_south.setBounds(195, 0,30, 30);
        panel_south.add(btn_func6_south);
        //���ܰ�ť7
        btn_func7_south = new JButton(new ImageIcon("image/dialogimage/sendimage.jpg"));
        btn_func7_south.setBounds(232, 0, 30, 30);
        panel_south.add(btn_func7_south);
        //���ܰ�ť8
        btn_func8_south = new JButton(new ImageIcon("image/dialogimage/diange.jpg"));
        btn_func8_south.setBounds(269, 0, 30, 30);
        panel_south.add(btn_func8_south);
        //���ܰ�ť9
        btn_func9_south = new JButton(new ImageIcon("image/dialogimage/jietu.jpg"));
        btn_func9_south.setBounds(306, 0, 30, 30);
        panel_south.add(btn_func9_south);
        //��ѯ�����¼
        recorde_search = new JButton(new ImageIcon("image/dialogimage/recorde.png"));
        recorde_search.addActionListener(e-> {
            System.out.println("������������¼");
            cardLayout.next(panel_east);
        });
        recorde_search.setBounds(350, 0, 96, 30);
        panel_south.add(recorde_search);
        //��Ϣ�رհ�ť
        btn_close = new JButton(new ImageIcon("image/dialogimage/close.jpg"));
        btn_close.setBounds(290, 145, 64, 24);
        btn_close.addActionListener(this);
        panel_south.add(btn_close);
        //��Ϣ���Ͱ�ť
        btn_send = new JButton(new ImageIcon("image/dialogimage/send.jpg"));
        btn_send.addActionListener(this);
        btn_send.setBounds(381, 145, 64, 24);
        panel_south.add(btn_send);

        //�������(ͼƬ�������¼)
        panel_east = new JPanel();
        //��Ƭ����
        cardLayout = new CardLayout(2,2);
        panel_east.setLayout(cardLayout);
        panel_east.setBounds(444, 91, 285, 418);
        //���Ӷ������
        c.add(panel_east);
        //��ʾ�����¼���
        panel_Record = new JTextPane();
        panel_Record.setText("-----------------------------聊天记录--------------------------\n\n");
        JScrollPane scrollPane_Record = new JScrollPane(panel_Record);
        scrollPane_Record.setBounds(2, 2, 411, 410);
        //���ӵ��������
        panel_east.add(label1);
        panel_east.add(scrollPane_Record);

        //ע������¼�������
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //����ͷ�
                isDragged = false;
                //���ָ�
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                //��갴��
                //��ȡ�����Դ���λ��
                frameLocation = new Point(e.getX(),e.getY());
                isDragged = true;
                //����Ϊ�ƶ���ʽ
                if(e.getY() < 92)
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        //ע������¼�������
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //ָ����Χ�ڵ��������ק
                if(e.getY() < 92){
                    //����������ק�ƶ�
                    if(isDragged) {
                        Point loc = new Point(getLocation().x+e.getX()-frameLocation.x,
                                getLocation().y+e.getY()-frameLocation.y);
                        //��֤�����Դ���λ�ò���,ʵ���϶�
                        setLocation(loc);
                    }
                }
            }
        });

        this.setIconImage(new ImageIcon("image/login/Q.png").getImage());//�޸Ĵ���Ĭ��ͼ��
        this.setSize(728, 553);//���ô����С
        this.setUndecorated(true);//ȥ���Դ�װ�ο�
        this.setVisible(true);//���ô���ɼ�

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_send){
            System.out.println("发送");
            sendMsg(this, this.myName);

        }else if(e.getSource() == btn_close | e.getSource()  == btn_exit) {
            ManageChatFrame.removeChatFrame(myId + friendId);
            System.out.println("remove chatFrame="+myId + friendId);
            this.dispose();
        }
    }

    /**
     * 信息发送
     * @param f
     */
    public void sendMsg(JFrame f, String senderName){
        String str = jtp_input.getText();
        if(!str.equals("")){
            Message msg = new Message();
            msg.setType(MsgType.COMMON_MESSAGE);
            msg.setSenderId(this.myId);
            msg.setSenderName(senderName);
            msg.setGetterId(this.friendId);
            msg.setContent(str);
            msg.setSendTime(df.format(new Date()));
            try {
                ObjectOutput out = new ObjectOutputStream(ManageThread.getThread(this.myId).getClient().getOutputStream());
                out.writeObject(msg);
                System.out.println("发送成功");
                showMessage(msg,true);
                jtp_input.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(f,"不能发送空内容");
        }
    }


    public void showMessage(Message msg, boolean fromSelf) {
        showMessage(panel_Msg, msg, fromSelf);//����ʾ�������������
        showMessage(panel_Record, msg, fromSelf);//����ʾ�������¼���
    }

    public void showMessage(JTextPane jtp, Message msg, boolean fromSelf) {
        //������ʾ��ʽ
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrset, "仿宋");
        StyleConstants.setFontSize(attrset,14);
        Document docs = jtp.getDocument();
        String info = null;
        /**
         * 发出信息内容，颜色，格式
         */
        try {
            if(fromSelf){
                info = "我  ";
                StyleConstants.setForeground(attrset, Color.MAGENTA);
                docs.insertString(docs.getLength(), info, attrset); StyleConstants.setForeground(attrset, Color.red);
                info = msg.getSendTime()+":\n";
                StyleConstants.setForeground(attrset, Color.black);
                docs.insertString(docs.getLength(), info, attrset);
                info = " "+msg.getContent()+"\n";
                StyleConstants.setFontSize(attrset,16);
                StyleConstants.setForeground(attrset, Color.green);
                docs.insertString(docs.getLength(), info, attrset);
            }else{
                info = msg.getSenderName()+"("+msg.getSenderId()+")  ";
                StyleConstants.setForeground(attrset, Color.red);
                docs.insertString(docs.getLength(), info, attrset); StyleConstants.setForeground(attrset, Color.red);
                info = msg.getSendTime()+":\n";
                StyleConstants.setForeground(attrset, Color.black);
                docs.insertString(docs.getLength(), info, attrset);
                info = " "+msg.getContent()+"\n";
                StyleConstants.setFontSize(attrset,16);
                StyleConstants.setForeground(attrset, Color.blue);
                docs.insertString(docs.getLength(), info, attrset);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

}
