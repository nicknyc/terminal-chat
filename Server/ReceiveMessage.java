
import java.io.*;
import java.net.*;
import java.awt.*;
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
    JTextArea txt_area = null;
    
    public ReceiveMessage(DataInputStream d,JTextArea a){
        this.in = d;
        this.txt_area = a;
    }
    
    public void run(){
        while(true){
            try {
                msg = in.readUTF();
                txt_area.append("\n" + this.getName() + ": " + msg);
            } catch (IOException ex) {
                System.out.println("Error reading message: "+ex);
            }
        }
    }
}
