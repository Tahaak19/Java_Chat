/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat_Project.serverPackage;

import Chat_Project.messagePackage.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author INSECT
 */
public class SClient extends Thread {

    int id;
    public String name = "NoName";
    Socket soket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
//    FileLicenerThread flThread;
//    //cilent eşleştirme thredi
//    
    //rakip client
    SClient rival;
    //eşleşme durumu
    public boolean paired = false;

    public SClient(Socket gelenSoket, int id) {
        this.soket = gelenSoket;
        this.id = id;
        try {
            this.sOutput = new ObjectOutputStream(this.soket.getOutputStream());
            this.sInput = new ObjectInputStream(this.soket.getInputStream());
//            this.flThread = new FileLicenerThread(this);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //thread nesneleri
//        this.pairThread = new PairingThread(this);
        //thread nesneleri
//        this.pairThread = new PairingThread(this);

    }

    //client mesaj gönderme
    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //client dinleme threadi
    //her clientin ayrı bir dinleme thredi var
    public void run() {
        //client bağlı olduğu sürece dönsün
        while (this.soket.isConnected()) {
            try {
                //mesajı bekleyen kod satırı
                Message received = (Message) (this.sInput.readObject());
                //mesaj gelirse bu satıra geçer
                //mesaj tipine göre işlemlere ayır
                switch (received.type) {
                    case Connect:
                        Server.Send(this, new Message(Message.Message_Type.Connect, this.id));
                        this.name = received.content.toString();
                        Server.broadcast(Message.Message_Type.Refresh);
                        break;
                    case Disconnect:
                        System.out.println(this.name + " Disconnect");
                        Server.clients.remove(this);
                        Server.broadcast(Message.Message_Type.Disconnect);
                        this.stop();
                        this.sOutput.flush();
                        this.sOutput.close();
                        this.sInput.close();
                        this.soket.close();

                        break;
                    case Text:
                        String[] message = (String[]) received.content;
                        int id = Integer.parseInt(message[0]);
                        String content = message[1];
                        Message m = new Message(Message.Message_Type.Text, new String[]{this.id + "", content});
                        Server.Send(id, m);
                        break;
                    case File:
                        BufferedReader reader = (BufferedReader) received.content;
                        System.out.println(reader.readLine());
                        break;

                    case newGroup:
                        ArrayList<String[]> clients = (ArrayList) received.content;
                        for (int i = 1; i < clients.size(); i++) {
                            int idx = Integer.parseInt(clients.get(i)[0]);
                            String name = clients.get(i)[1];
                            if (idx != this.id) {
                                Server.Send(idx, received);
                            }
                        }
                        break;
                    case TextGroup:
                        ArrayList<String> messageGroup = (ArrayList<String>) received.content;
                        int group_id = Integer.parseInt(messageGroup.get(0));
                        String messageGroup_content = messageGroup.get(messageGroup.size() - 1);
                        for (int i = 1; i < messageGroup.size() - 1; i++) {
                            Message mg = new Message(Message.Message_Type.TextGroup, new String[]{this.name, group_id + "", messageGroup_content});
                            Server.Send(Integer.parseInt(messageGroup.get(i)), mg);
                        }
//                        Message m = new Message(Message.Message_Type.Text, new String[]{this.id + "", content});
//                        Server.Send(id, m);
                        break;
                    case file_msg:
                        Object[] file = (Object[]) received.content;
                        int iddd = (int) file[2];
                        file[2] = this.id;
                        Server.Send(iddd, received);
                        break;
                    case icon_ms:
                        Object[] icon_contex = (Object[]) received.content;
                        int idd = (int) icon_contex[0];
                        icon_contex[0] = this.id;
                        Server.Send(idd, received);
                        break;
                    case file_msg_group:
                        Object[] fileMsg = (Object[]) received.content;
                        ArrayList<String> file_info = (ArrayList<String>) fileMsg[0];
                        String file_name = (String) fileMsg[1];
                        byte[] file_content = (byte[]) fileMsg[2];
                        for (int i = 1; i < file_info.size(); i++) {
                            Message mg = new Message(Message.Message_Type.file_msg_group, new Object[]{this.name, file_info.get(0), file_name, file_content, this.id});
                            Server.Send(Integer.parseInt(file_info.get(i)), mg);
                        }
                        break;
                    case icon_ms_group:
                        //{contacts, GroupId, icon_name};
                        System.out.println("icon_ms_group scliente geldi");
                        Object[] iconMsg = (Object[]) received.content;
                        Message msg_icon = new Message(Message.Message_Type.icon_ms_group, new Object[]{this.name, iconMsg[1], iconMsg[2]});
                        ArrayList<Integer> contacts_id =(ArrayList<Integer>)iconMsg[0];  
                        for (Integer contact_id : contacts_id) {
                             Server.Send(contact_id, msg_icon);
                        }
                        break;
                }

            } catch (IOException ex) {
                Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                //client bağlantısı koparsa listeden sil
                Server.clients.remove(this);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                //client bağlantısı koparsa listeden sil
                Server.clients.remove(this);
            }
        }

    }

}
