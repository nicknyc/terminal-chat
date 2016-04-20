package Client;

import Server.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PK
 */
public class ReceiveMessage extends Thread{
    String msg = "";
    DataInputStream in = null;
    JTextArea a = null;
    
    public ReceiveMessage(DataInputStream d,JTextArea a){
        this.in = d;
        this.a = a;
    }
    
    public void run(){
        while(true){
            try {
                msg = in.readUTF();
                if(msg.startsWith("server: ")){
                    //server reply command
                }
                a.append("\n" + msg);
                System.out.println(msg);
            } catch (IOException ex) {
                System.out.println("Error reading message: "+ex);
            }
        }
    }
}
