/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat_Project.chatApp;

import Chat_Project.serverPackage.Server;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author Dell
 */
public class Utils {

    static void receiveFile(byte[] file, String file_name) throws IOException {
        String FILE_TO_RECEIVED = getPath() + "/src/Chat_Project/chatApp/myFiles/" + file_name;

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            // receive file
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);

            bos.write(file, 0, file.length);
            bos.flush();
            System.out.println("File " + FILE_TO_RECEIVED
                    + " downloaded (" + file.length + " bytes read)");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }

    }

    static ImageIcon getIcon(String icon_name) {
        BufferedImage img = null;
        Image dimg = null;
        ImageIcon icon = null;
        try {
            img = ImageIO.read(new File(getPath() + "/src/Chat_Project/images/" + icon_name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        icon = new ImageIcon(dimg);
        return icon;
    }

    static byte[] prepareFileToSend(String FILE_TO_SEND) {
        //String FILE_TO_SEND = "src/Chat_Project/messagePackage/woman.png";
        byte[] mybytearray = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            // send file
            File myFile = new File(FILE_TO_SEND);
            mybytearray = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
            //this.sOutput.write(mybytearray, 0, mybytearray.length);

            System.out.println("Done.");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mybytearray;
    }

    static File chooseFile(ChatMain page) {
        File f = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose File");
        int secim = fileChooser.showOpenDialog(page);
        if (secim == JFileChooser.APPROVE_OPTION) {
            f = fileChooser.getSelectedFile();
        }
        return f;
    }

    static void refreshContactsList(Client client, ArrayList<String[]> contacts, DefaultListModel currentChatModel) {
        for (String[] contact : contacts) {
            int id = Integer.parseInt(contact[0]);
            String name = contact[1];
            boolean exists = false;
            if (id == client.myID) {
                continue;
            }
            for (Chat myChat : client.myChats) {
                if (myChat.getId() == id) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                client.myChats.add(new Chat(id, name, currentChatModel));
            }
        }
    }

    static void refreshContactsListDisconnect(Client client, ArrayList<String[]> contacts) {
        for (int i = 0; i < client.myChats.size(); i++) {
            boolean exists = false;
            for (String[] contact : contacts) {
                int id = Integer.parseInt(contact[0]);
                String name = contact[1];
                if (i == 0) {
                    System.out.println(name);
                }
                if (client.myChats.get(i).getId() == id) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                client.myChats.remove(i);
                break;
            }
        }
    }

    //m4a, mp4
    static ImageIcon decideFileIcon(String file_name) {
        String type = file_name.split("\\.")[1];
        BufferedImage img = null;
        Image dimg = null;
        ImageIcon icon = null;
        try {//getPath() + "/src/Chat_Project/images/
            img = ImageIO.read(new File(getPath() + "/src/Chat_Project/images/" + type + ".png"));
//            System.out.println("bulundu");
        } catch (IOException e) {
            try {
                img = ImageIO.read(new File(getPath() + "/src/Chat_Project/images/folder.png"));
//                System.out.println("folder");
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        dimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        icon = new ImageIcon(dimg);
        return icon;
    }
    
    public static String getPath() {
        String currentPath = null;
        try {
            currentPath = new java.io.File(".").getCanonicalPath();
            currentPath = currentPath.substring(0, currentPath.indexOf("ChatApp") + 7);//  C:\Users\Dell\Documents\NetBeansProjects\Yehtzee
        } catch (IOException ex) { 
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentPath;
    }
}
