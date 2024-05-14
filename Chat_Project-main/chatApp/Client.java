/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat_Project.chatApp;

import Chat_Project.messagePackage.Message;
import Chat_Project.serverPackage.Server;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

public class Client {

    //her clientın bir soketi olmalı
    public int myID;
    public Socket socket;
    public String myName;
    public ArrayList<Chat> myChats;
    public ArrayList<Group> myGroups;
    //verileri almak için gerekli nesne
    public ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public ObjectOutputStream sOutput;
//    //serverı dinleme thredi 

    public void Start(String ip, int port) {
        try {
            myGroups = new ArrayList<>();
            myChats = new ArrayList<>();
            // Client Soket nesnesi
            this.socket = new Socket(ip, port);
            System.out.println("Servera bağlandı");
            // input stream
            this.sInput = new ObjectInputStream(this.socket.getInputStream());
            // output stream
            this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //client durdurma fonksiyonu
    public void Stop() {
        try {
            if (this.socket != null) {
                this.sOutput.flush();
                this.sOutput.close();
                this.sInput.close();
                this.socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //mesaj gönderme fonksiyonu
    public void Send(Object message, Message.Message_Type mt) {
        try {
            Message msg = new Message(mt, message);
            this.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    byte[] prepareFileToSend(String FILE_TO_SEND) {
//        //String FILE_TO_SEND = "src/Chat_Project/messagePackage/woman.png";
//        byte[] mybytearray = null;
//        FileInputStream fis = null;
//        BufferedInputStream bis = null;
//        try {
//            // send file
//            File myFile = new File(FILE_TO_SEND);
//            mybytearray = new byte[(int) myFile.length()];
//            fis = new FileInputStream(myFile);
//            bis = new BufferedInputStream(fis);
//            bis.read(mybytearray, 0, mybytearray.length);
//            System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
//            //this.sOutput.write(mybytearray, 0, mybytearray.length);
//            
//            this.sOutput.flush();
//            System.out.println("Done.");
//        } catch (IOException ex) {
//            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return mybytearray;
//    }

    void reloadMyContacts(DefaultListModel model) {
        for (Chat myChat : myChats) {
            model.addElement(myChat);
        }

    }

}
