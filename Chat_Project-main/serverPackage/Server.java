/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat_Project.serverPackage;

import Chat_Project.messagePackage.Message;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServerThread extends Thread {

    public void run() {
        //server kapanana kadar dinle
//        ClientListControlThread clct = new ClientListControlThread();
//        clct.start();
        while (!Server.serverSocket.isClosed()) {
            try {
                System.out.println("Wait for a client...");
                // clienti bekleyen satır
                //bir client gelene kadar bekler
                Socket clientSocket = Server.serverSocket.accept();
                //client gelirse bu satıra geçer
                System.out.println("A client have come...");
                //gelen client soketinden bir sclient nesnesi oluştur
                //bir adet id de kendimiz verdik
                SClient nclient = new SClient(clientSocket, Server.client_id);

                Server.client_id++;
                //clienti listeye ekle.
                Server.clients.add(nclient);
                //client mesaj dinlemesini başlat
                nclient.start();

            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

public class Server {

    // Socket of the server
    public static ServerSocket serverSocket;
    // client id 
    public static int client_id = 0;
    // The port that the server will be liston to
    public static int port;
    // Serverı sürekli dinlemede tutacak thread nesnesi
    public static ServerThread serverMainThread;
    // list of all cleints that are connected
    public static ArrayList<SClient> clients = new ArrayList<>();

    // only one client can enter pairing thread to search for a rival at the same time
    public static Semaphore pairingLock = new Semaphore(1, true);

    public static void Start(int port_number) {
        try {
            Server.port = port_number;
            Server.serverSocket = new ServerSocket(Server.port);

            Server.serverMainThread = new ServerThread();
            Server.serverMainThread.start();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // serverdan clietlara mesaj gönderme
    //clieti alıyor ve mesaj olluyor
    public static void Send(SClient client, Message messge) {

        try {
            client.sOutput.writeObject(messge);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Send(int clientID, Message messge) {

        try {
            for (SClient client : clients) {
                if (clientID == client.id) {
                    client.sOutput.writeObject(messge);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

 

    public static void broadcast(Message.Message_Type mt) {
        for (SClient client1 : clients) {
            ArrayList<String[]> temp = new ArrayList<>();
            for (SClient client2 : clients) {
                if (!client2.equals(client1)) {
                    String[] c = {"" + client2.id, client2.name};
                    temp.add(c);
                }
            }
            Message msg = new Message(mt, temp.clone());
            Send(client1, msg);
        }
    }

    //client gelişini dinleme threadi
    public static void main(String[] args) {
        // TODO code application logic here

        Server.Start(2000);
    }

}
