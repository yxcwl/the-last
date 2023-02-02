package com.client.view;

import com.client.tools.ClientToServerThread;
import com.client.tools.ManageChatFrame;
import com.client.tools.ManageThread;
import com.common.Message;
import com.common.MsgType;
import com.client.tools.MyTreeCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class FriendList extends JFrame implements ActionListener,MouseListener,TreeModelListener{

    private static final long serialVersionUID = 1L;
    private Container c;
    private Point tmp,loc;
    private boolean isDragged = false;
    private String ownerId;
    private String myName;
    private JTree jtree;

    public FriendList(String name, String ownerId, Message msg) {
        this.ownerId = ownerId;
        this.myName = name;
        //��ȡ����������
        c = this.getContentPane();
        //���ô����С
        this.setSize(274,600);
        //���ò���
        c.setLayout(null);
        //���Ͻ���С����ť
        JButton btn_min = new JButton(new ImageIcon("image/friendlist/fmin.png"));
        btn_min.setBounds(217, 0, 28, 28);
        btn_min.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //������С��
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        c.add(btn_min);
        //���Ͻ��˳���ť
        JButton btn_exit = new JButton(new ImageIcon("image/friendlist/fexit.png"));
        btn_exit.addActionListener(this);
        btn_exit.setBounds(245, 0, 28, 28);
        btn_exit.addActionListener(e->{
            sendUnloadMsgToServer();
        });
        c.add(btn_exit);
        //���Ͻ�qq��ǩ
        JLabel jbl_leftTop = new JLabel(new ImageIcon("image/friendlist/biaoti.png"));
        jbl_leftTop.setBounds(0, 0, 44, 21);
        c.add(jbl_leftTop);
        //qqͷ��
        JLabel jbl_photo = new JLabel(new ImageIcon("image/friendlist/Qtouxiang.jpg"));
        jbl_photo.setBounds(10, 23, 78, 78);
        c.add(jbl_photo);
        //qq�ǳ�
        JLabel jbl_qqName = new JLabel(name+"("+ownerId+")");
        jbl_qqName.setBounds(109, 32, 68, 21);
        c.add(jbl_qqName);
        //����ǩ��
        JTextField jtf_personalSign = new JTextField();
        jtf_personalSign.setBounds(110, 63, 167, 21);
        c.add(jtf_personalSign);
        //����״̬ѡ���б�
        String[] status = {"����","����","����"};
        JComboBox<String> online_status = new JComboBox<>(status);
        online_status.setSelectedIndex(0);
        online_status.setBounds(195, 32, 63, 21);
        c.add(online_status);
        //ͷ���·��߸����ܰ�ť
        //��ť1
        JButton btn_h1 = new JButton(new ImageIcon("image/friendlist/tubiao1.png"));
        btn_h1.setBounds(0, 111, 20, 23);
        c.add(btn_h1);
        //��ť2
        JButton btn_h2 = new JButton(new ImageIcon("image/friendlist/tubiao2.png"));
        btn_h2.setBounds(28, 111, 20, 23);
        c.add(btn_h2);
        //��ť3
        JButton btn_h3 = new JButton(new ImageIcon("image/friendlist/tubiao3.png"));
        btn_h3.setBounds(58, 111, 20, 23);
        c.add(btn_h3);
        //��ť4
        JButton btn_h4 = new JButton(new ImageIcon("image/friendlist/tubiao4.png"));
        btn_h4.setBounds(88, 111, 20, 23);
        c.add(btn_h4);
        //��ť5
        JButton btn_h5 = new JButton(new ImageIcon("image/friendlist/tubiao5.png"));
        btn_h5.setBounds(118, 111, 20, 23);
        c.add(btn_h5);
        //��ť6
        JButton btn_h6 = new JButton(new ImageIcon("image/friendlist/tubiao6.png"));
        btn_h6.setBounds(148, 111, 20, 23);
        c.add(btn_h6);
        //��ť7
        JButton btn_h7 = new JButton(new ImageIcon("image/friendlist/tubiao7.png"));
        btn_h7.setBounds(178, 111, 20, 23);
        c.add(btn_h7);
        //������
        JTextField jtf_search = new JTextField();
        jtf_search.setBounds(0, 134, 247, 23);
        c.add(jtf_search);
        //������ť
        JButton btn_search = new JButton(new ImageIcon("image/friendlist/search.png"));
        btn_search.setBounds(247, 134, 30, 23);
        c.add(btn_search);

        //�ϰ벿�ֱ���ͼ
        JLabel jbl_background = new JLabel(new ImageIcon("image/friendlist/beijing.png"));
        jbl_background.setBounds(0, 0, 277, 156);
        c.add(jbl_background);
        //�ײ�8�����ܰ�ť
        //��ť1
        JButton btn_l1 = new JButton(new ImageIcon("image/friendlist/mainmenue.png"));
        btn_l1.setBounds(0, 577, 30, 23);
        c.add(btn_l1);

        //��ť2
        JButton btn_l2 = new JButton(new ImageIcon("image/friendlist/shezhi.png"));
        btn_l2.setBounds(30, 577, 30, 23);
        c.add(btn_l2);
        //��ť3
        JButton btn_l3 = new JButton(new ImageIcon("image/friendlist/messagemanage.png"));
        btn_l3.setBounds(60, 577, 30, 23);
        c.add(btn_l3);
        //��ť4
        JButton btn_l4 = new JButton(new ImageIcon("image/friendlist/filehleper.png"));
        btn_l4.setBounds(90, 577, 30, 23);
        c.add(btn_l4);
        //��ť5
        JButton btn_l5 = new JButton(new ImageIcon("image/friendlist/shoucang.png"));
        btn_l5.setBounds(120, 577, 30, 23);
        c.add(btn_l5);
        //��ť6
        JButton btn_l6 = new JButton(new ImageIcon("image/friendlist/tubiao8.png"));
        btn_l6.setBounds(150, 577, 30, 23);
        c.add(btn_l6);
        //��ť7
        JButton btn_l7 = new JButton(new ImageIcon("image/friendlist/tubiao9.png"));
        btn_l7.addActionListener(this);
        btn_l7.setBounds(180, 577, 30, 23);
        c.add(btn_l7);
        //��ť8
        JButton btn_l8 = new JButton(new ImageIcon("image/friendlist/apl.png"));
        btn_l8.addActionListener(this);
        btn_l8.setBounds(210, 577, 64, 23);
        c.add(btn_l8);

        //��ʾ�����б�
        initList(this, msg);

        //ȥ���䶨��װ�ο�
        this.setUndecorated(true);
        //���ô���ɼ�
        this.setVisible(true);
        //�����������¼�
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
                //��ק����ͼ��ָ�
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                //�޶���Χ�ڿ���ק
                if(e.getY()<30)
                {
                    //��ȡ��갴��λ��
                    tmp = new Point(e.getX(), e.getY());
                    isDragged = true;
                    //��קʱ�������ͼ��
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragged) {
                    //��������봰�����λ�ò���
                    loc = new Point(getLocation().x + e.getX() - tmp.x,
                            getLocation().y + e.getY() - tmp.y);
                    setLocation(loc);
                }
            }

        });
    }

    /**
     * ��������Ϣ���͵�������
     */
    public void sendUnloadMsgToServer() {
        Message msg = new Message();
        msg.setSenderId(ownerId);
        msg.setType(MsgType.UNLOAD_LOGIN);
        try {
            ClientToServerThread th = ManageThread.getThread(ownerId);
            ObjectOutputStream out = new ObjectOutputStream(th.getClient().getOutputStream());
            out.writeObject(msg);
            //�����߳�
            th.myStop();
            ManageThread.removeThread(ownerId);
            this.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    /**
     * �����νṹ��ʾȫ�������б�
     * @param msg
     */
    public void initList(JFrame f, Message msg){
        //��Hashtable����jtree��ʾ�����б�
        Hashtable<String,Object> ht = new Hashtable<>();
        String[] friends = msg.getContent().split(" ");
        ht.put("�ҵĺ���",friends);
        jtree = new JTree(ht);
        jtree.setCellRenderer(new MyTreeCellRenderer(msg));
        jtree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    JTree tree = (JTree) e.getSource();
                    TreePath path = tree.getSelectionPath();
                    if(null != path){
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        if(node.isLeaf()){
                            String[] info = node.toString().split("\\(");
                            String friendId = info[1].substring(0,info[1].length()-1);//ȡ��id��
                            String frameName = ownerId+friendId;
                            if(ManageChatFrame.getChatFrame(frameName) == null){
                                System.out.println("����frameName="+frameName);
                                ManageChatFrame.addChatFrame(frameName, new Chat(ownerId, myName, friendId, info[0]));
                            }else{
                                JOptionPane.showMessageDialog(f,"�ô����Ѵ���!");
                            }
                        }
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(jtree);
        scrollPane.setBounds(0, 157, 274, 422);
        c.add(scrollPane);
    }

    public void updateOnlineFriends(Message msg) {
        this.jtree.setCellRenderer(new MyTreeCellRenderer(msg));
    }

    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void treeNodesInserted(TreeModelEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void treeNodesRemoved(TreeModelEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void treeStructureChanged(TreeModelEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}
