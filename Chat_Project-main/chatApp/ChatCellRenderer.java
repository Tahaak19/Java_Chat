/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat_Project.chatApp;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 *
 * @author Dell
 */
public class ChatCellRenderer implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    ArrayList<Integer> indexes;
    ArrayList<Icon> icones;
    ArrayList<Integer> msgIndex;

    public ChatCellRenderer() {
        this.indexes = new ArrayList<>();
        this.icones = new ArrayList<>();
        this.msgIndex = new ArrayList<>();
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
        renderer.setSize(30, 200);
        renderer.setHorizontalTextPosition(SwingConstants.LEADING);

        for (int j = 0; j < indexes.size(); j++) {
            if (indexes.get(j) == index) {
                renderer.setIcon(icones.get(j));
            }
        }
        for (Integer integer : msgIndex) {
            if (integer == index) {
                renderer.setBackground(Color.lightGray);
            }
        }
        return renderer;
    }

    void refresh(Chat currentChat) {
        this.indexes = currentChat.getIcons_indexes();
        this.icones = currentChat.getIcons();
        this.msgIndex = currentChat.getMsg_index();
    }
    void refresh(Group currentGroup) {
        this.indexes = currentGroup.getIcons_indexes();
        this.icones = currentGroup.getIcons();
        this.msgIndex = currentGroup.getMsg_index();
    }
}
