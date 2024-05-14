/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat_Project.messagePackage;

/**
 *
 * @author INSECT
 */
public class Message implements java.io.Serializable {

    //mesaj tipleri enum 
    public static enum Message_Type {
        Connect, Disconnect, Text, Start, start, end,
        File, Refresh, newGroup, TextGroup, file_msg, icon_ms, icon_ms_group, file_msg_group
    }
    //mesajın tipi
    public Message_Type type;
    //mesajın içeriği obje tipinde ki istenilen tip içerik yüklenebilsin
    public Object content;

    public Message(Message_Type t, Object content) {
        this.type = t;
        this.content = content;
    }

}
