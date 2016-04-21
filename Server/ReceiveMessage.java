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
    Vector<DataInputStream> in = null;
    Vector<String> q = null;
    
    public ReceiveMessage(Vector<String> q){
        this.in = new Vector<DataInputStream>();
        this.q = q;
    }
    
    public void run(){
        while(true){
            try {
                for(DataInputStream is: in){
                    msg = is.readUTF();
                    q.add(msg);
                    System.out.println(this.getName()+" " + msg);
                    System.out.println(q.size());
                }
            } catch (IOException ex) {
                System.out.println("Error reading message: "+ex);
            }
        }
    }

    void addInput(DataInputStream is) {
        in.add(is);
    }
}
