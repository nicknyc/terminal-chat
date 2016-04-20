/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author PK
 */
class SendMessage extends Thread {

    private Vector<String> q;
    private DataOutputStream out;
    private JTextArea a = null;
    private boolean running = false;

    public SendMessage(DataOutputStream out, Vector<String> q, JTextArea a) {
        super();
        this.out = out;
        this.q = q;
        this.a = a;
    }
    
    public void run(){
        running = true;
        while(running){
            System.out.println("Queue has " + q.size() +" messages waiting");
            while(q.size() > 0){
                try {
                    String msg = q.remove(0);
                    out.writeUTF(msg);
                    a.append("\n" + msg);
                } catch (IOException ex) {
                    Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
