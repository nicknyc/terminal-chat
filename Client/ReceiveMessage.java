package Client;

import Server.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author PK
 */
public class ReceiveMessage extends Thread{
    String msg = "";
    DataInputStream in = null;
    JTextArea a = null;
    ClientForm cf = null;
    
    public ReceiveMessage(DataInputStream d,JTextArea a,ClientForm cf){
        this.in = d;
        this.a = a;
        this.cf = cf;
    }
    
    public void run(){
        while(true){
            try {
                msg = in.readUTF();
                String groupname = msg.substring(0,msg.indexOf(" "));
                String tmp = msg.substring(msg.indexOf(" ") + 1);
                String username = tmp.substring(0,tmp.indexOf(" "));
                String txt = tmp.substring(tmp.indexOf(" ") + 1);
                if(msg.startsWith("server: ")){
                    //server reply command
                    a.append("\n" + msg);
                    System.out.println(msg);
                }
                if(cf.online){
                    if(cf.groupname.equalsIgnoreCase(groupname)){
                        msg = username + ": " + txt;
                        a.append("\n" + msg);
                        System.out.println(msg);
                    }
                    else if(cf.groups.contains(groupname)){
                        cf.q.add(msg);
                    }
                } 
                else if(cf.groups.contains(groupname)){
                    cf.q.add(msg);
                }
            } catch (Exception ex) {
                System.out.println("Error reading message: "+ex);
            }
        }
    }
}
