package Server;

import java.io.BufferedReader;
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
    private Vector<String> groups = null;
    private String           username	 = null;
    private BufferedReader   socketIn  	 = null;
    private PrintWriter      socketOut   = null;
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

    public int getID() {
        return ID;
    }

    public Vector<String> getGroups() {
        return groups;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void run(){
        running = true;
        while(running){
            try {
                String msg = socketIn.readLine();
                if(msg.startsWith("#")){
                    //system command reply with server msg imm
                }
                else{
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void openConnection() {
        try {
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
