/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat_Project.chatApp;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.Icon;

/**
 *
 * @author Dell
 */
public class Chat {

    private int id;
    private String name;
    private ArrayList<String> chatContent;
    private ArrayList<Integer> icons_indexes;
    private ArrayList<Integer> msg_index;
    private ArrayList<Icon> icons;
    private DefaultListModel model;

    public Chat(int id, String name, DefaultListModel model) {
        this.id = id;
        this.name = name;
        this.chatContent = new ArrayList<>();
        this.msg_index = new ArrayList<>();
        this.icons_indexes = new ArrayList<>();
        this.icons = new ArrayList<>();
        this.model = model;
    }

    public Chat(int id, String name) {
        this.id = id;
        this.name = name;
        this.chatContent = new ArrayList<>();
    }

    public Chat() {
        this.chatContent = new ArrayList<>();
    }

    void receiveMessageFromThisChat(String message, int cuurentId) {
        this.msg_index.add(chatContent.size());
        this.chatContent.add(message);
        if (this.id == cuurentId) {
            model.addElement(message);
        }
    }

    void sendMessageToThisChat(String message) {
        this.chatContent.add(message);
        this.model.addElement(message);
    }

    void receiveIconFromThisChat(int index, Icon icon, int cuurentId) {
        this.msg_index.add(chatContent.size());
        this.icons_indexes.add(index);
        this.icons.add(icon);
        this.chatContent.add(this.name + "->");
        if (this.id == cuurentId) {
            this.model.addElement(this.name + "->");
        }
    }

    void sendIconToThisChat(int index, Icon icon) {
        this.icons_indexes.add(index);
        this.icons.add(icon);
        this.chatContent.add("You->");
        this.model.addElement("You->");
    }

    void receiveFileIconFromThisChat(int index, Icon icon) {
        this.icons_indexes.add(index);
        this.icons.add(icon);
    }

    void sendFileIconToThisChat(int index, Icon icon) {
        this.icons_indexes.add(index);
        this.icons.add(icon);
    }

    void reloadChat() {
        model.removeAllElements();
        for (String message : chatContent) {
            this.model.addElement(message);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getIcons_indexes() {
        return icons_indexes;
    }

    public ArrayList<Integer> getMsg_index() {
        return msg_index;
    }

    public ArrayList<Icon> getIcons() {
        return icons;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getChatContent() {
        return chatContent;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
