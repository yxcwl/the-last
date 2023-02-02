package com.client.tools;

import com.common.Message;
import com.common.MsgType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;


public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = 1L;
    private Message msg;

    public MyTreeCellRenderer(Message msg){
        this.msg = msg;
    }

    /**
     * 对getTreeCellRendererComponent()重写
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {

        //执行
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);

        //得到TreeNode
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        //初始化
        String str = node.toString();
        //��¼�ɹ�ʱ��ʼ���б�
        if(msg.getType() == MsgType.LOGIN_SUCCEED) {
            if (node.isLeaf()) {
                this.setIcon(new ImageIcon("image/friendlist/qq.png"));
            }else
                this.setIcon(new ImageIcon("image/friendlist/lie.png"));
            //�ѻ�ȡ�����ߺ����б�
        }else if(msg.getType() == MsgType.RET_ONLINE_FRIENDS) {
            String [] onlineFriends = msg.getContent().split(" ");
            if (node.isLeaf()) {
                //�õ����е�id����
                str = str.split("\\(")[1];
                str = str.substring(0,str.length()-1);
                this.setIcon(new ImageIcon("image/friendlist/qq.png"));
                for (String onlineFriend : onlineFriends) {
                    if (str.equals(onlineFriend))
                        this.setIcon(new ImageIcon("image/friendlist/qq.gif"));
                }

            }else
                this.setIcon(new ImageIcon("image/friendlist/lie.png"));
        }
        return this;
    }

}