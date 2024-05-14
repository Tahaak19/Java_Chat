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
public class Group {

    static int id = 0;
    private Chat groupCreater;
    private int thisGroupId;
    private String groupName;
    private ArrayList<String> ContactsNames;
    private ArrayList<Integer> ContactsIds;
    private ArrayList<Chat> contacts;
    private ArrayList<String> chatContent;
    private DefaultListModel model;

    private ArrayList<Integer> icons_indexes;
    private ArrayList<Integer> msg_index;
    private ArrayList<Icon> icons;

    public Group(String groupName, ArrayList<Chat> chats, DefaultListModel model, Client groupCreater) {
        this.groupCreater = new Chat(groupCreater.myID, "*" + groupCreater.myName);
        this.groupName = groupName;
        this.model = model;
        thisGroupId = id++;
        this.chatContent = new ArrayList<>();
        this.icons_indexes = new ArrayList<>();
        this.msg_index = new ArrayList<>();
        this.icons = new ArrayList<>();
        ContactsNames = new ArrayList<>();
        ContactsIds = new ArrayList<>();
        contacts = chats;

    }

    public Group(String groupName, int groupId, ArrayList<Chat> chats, DefaultListModel model, Chat groupCreater) {
        this.groupCreater = groupCreater;
        this.groupName = groupName;
        this.model = model;
        thisGroupId = id++;
        this.chatContent = new ArrayList<>();
        ContactsNames = new ArrayList<>();
        ContactsIds = new ArrayList<>();
        contacts = chats;

        this.icons_indexes = new ArrayList<>();
        this.msg_index = new ArrayList<>();
        this.icons = new ArrayList<>();

    }

    void receiveMessageFromThisGroup(String message, int cuurentGroupId) {
        this.msg_index.add(chatContent.size());
        this.chatContent.add(message);
        if (thisGroupId == cuurentGroupId) {
            model.addElement(message);
        }
    }

    void sendMessageToThisGroup(String message) {
        this.chatContent.add(message);
        this.model.addElement(message);
    }

    void receiveIconFromThisGroup(String sender_name, Icon icon, int cuurentId) {
        this.msg_index.add(chatContent.size());
        this.chatContent.add(sender_name + "->");
        this.icons_indexes.add(chatContent.size()-1);
        this.icons.add(icon);
        if (this.id == cuurentId) {
            this.model.addElement(sender_name + "->");
        }
    }

    void sendIconToThisGroup(int index, Icon icon) {
        this.icons_indexes.add(index);
        this.icons.add(icon);
        this.chatContent.add("You->");
        this.model.addElement("You->");
    }

    void receiveFileIconFromThisGroup(int index, Icon icon) {
        this.icons_indexes.add(index);
        this.icons.add(icon);
    }

    void sendFileIconToThisGroup(int index, Icon icon) {
        this.icons_indexes.add(index);
        this.icons.add(icon);
    }

    void reloadGroup() {
        model.removeAllElements();
        for (String message : chatContent) {
            this.model.addElement(message);
        }
    }

    public void setModel(DefaultListModel model) {
        this.model = model;
    }

    public ArrayList<String> createMessage(String msg) {
        ArrayList<String> info = new ArrayList();
        info.add("" + this.thisGroupId);
        for (Chat contact : contacts) {
            info.add("" + contact.getId());
        }
        info.add(msg);
        return info;
    }

    public Object[] createFileMessage(byte[] file, String file_name) {
        ArrayList<String> info = new ArrayList();
        info.add("" + this.thisGroupId);
        for (Chat contact : contacts) {
            info.add("" + contact.getId());
        }
        Object[] file_msg = {info, file_name, file};
        return file_msg;
    }

    public Object[] createIconMessage(String icon_name) {
        ArrayList<Integer> info = new ArrayList();
        for (Chat contact : contacts) {
            info.add(contact.getId());
        }
        Object[] icon_msg = {info, this.thisGroupId, icon_name};
        return icon_msg;
    }

    @Override
    public String toString() {
        String s = " (";
        for (Chat contact : contacts) {
            s += contact.getName() + ", ";
        }
        s += ")";
        return this.groupName + s; //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Chat> getContacts() {
        return contacts;
    }

    public ArrayList<String> getChatContent() {
        return chatContent;
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

    public int getThisGroupId() {
        return thisGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

}
