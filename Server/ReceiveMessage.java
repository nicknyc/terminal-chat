package Server;

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
    Vector<String> q = null;
    
    public ReceiveMessage(DataInputStream d,Vector<String> q){
        this.in = d;
        this.q = q;
    }
    
    public void run(){
        while(true){
            try {
                msg = in.readUTF();
                q.add(this.getName() + ": " + msg);
                System.out.println(this.getName()+" " + msg);
                System.out.println(q.size());
            } catch (IOException ex) {
                System.out.println("Error reading message: "+ex);
            }
        }
    }
}
