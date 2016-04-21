package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PK
 */
public class ChatServerThread extends Thread{
    
    private ServerForm sf = null;
    private Socket socket = null;
    private int ID;
    private String           username	 = null;
    private DataInputStream   in  	 = null;
    protected DataOutputStream      out   = null;
    private Vector<String> unread = null;
    protected boolean running = false;
    
    ChatServerThread(ServerForm sf, Socket socket) {
        super();
        this.sf = sf;
        this.socket = socket;
        ID = socket.getPort();
        username = ID+"";
        unread = new Vector<String>();
    }   
    public void run(){
        running = true;
        System.out.println("Thread "+username+" is running.");
        while(running){
            try {
                String msg = in.readUTF();
                if(msg.equalsIgnoreCase("-list")){
                    //system command reply with server msg imm
                    String grouplist = "server: " + sf.groups.toString();
                    out.writeUTF(grouplist);
                }
                else{
                    String group = msg.substring(0,msg.indexOf(' '));
                    System.out.println("'" + group + "'");
                    if(!sf.groups.contains(group)) sf.groups.add(group);
                    sf.q.add(msg);
                    System.out.println(msg);
                    sf.broadcast(msg);
                }
            } catch (IOException ex) {
                Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void openConnection() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}