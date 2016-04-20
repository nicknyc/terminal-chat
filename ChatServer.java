import java.net.*;
import java.util.Date;
import java.util.Vector;
import java.io.*;

public class ChatServer implements Runnable {

	private Vector<ChatServerThread> clients = new Vector<ChatServerThread>();
	private MessageQueueThread messageQueue = null;
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount = 0;
	private Vector<String> group = null;
        private ServerForm window = null;
	
        /*public ChatServer(int port) {
		try {
			System.out.println("Binding to port " + port + ", please wait  ...");
			server = new ServerSocket(port);
			System.out.println("Server started: " + server);
			group = new ArrayList<String>();
			start();
		}
		catch(IOException ioe) {
			System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
	}*/
        
	public ChatServer(ServerForm window,int port) {
            this.window = window;
		try {
			window.println("Binding to port " + port + ", please wait  ...");
			server = new ServerSocket(port);
			window.println("Server started: " + server);
                        window.setPort1(server.toString().substring(server.toString().lastIndexOf("=")+1,server.toString().length() - 1));
			group = new Vector<String>();
			start();
		}
		catch(IOException ioe) {
			System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
	}
	
	public void run() {
		while (thread != null) {
			try {
				window.println("Waiting for a client ...");
				addThread(server.accept());
			}
			catch(IOException ioe) {
				window.println("Server accept error: " + ioe);
				stop();
			}
		}
	}
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		if (messageQueue == null) {
			messageQueue = new MessageQueueThread();
			messageQueue.start();
		}
	}
	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}
	
	private int findClient(int ID) {
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getID() == ID)
				return i;
		return -1;
	}
	
	public void createGroup(String groupName){
		if(!group.contains(groupName)){
			group.add(groupName);
		}
	}
	public boolean checkGroup(String groupName){
		if(group.contains(groupName)){
			return true;
		}
		return false;
	}
        public void removeGroup(String groupName){
            if(!group.contains(groupName)){
                group.remove(group.indexOf(groupName));
            }
        }
	public String getAllGroup(){
		return group.toString();
	}
	
	public synchronized void broadcast(String groupName, String username, String messageString) {
		Date inputTime = new Date();
		
		for (int i = 0; i < clientCount; i++){
			if(clients[i].getGroupName().equals(groupName)){
				messageQueue.enqueue(new ChatMessage(clients[i],messageString,groupName,inputTime,username));
			}
		}
	}
	
	public synchronized void removeClient(int ID) {
		int pos = findClient(ID);
		if (pos >= 0) {
			ChatServerThread toTerminate = clients[pos];
			window.println("Removing client thread " + ID + " at " + pos);
			if (pos < clientCount-1){
				for (int i = pos+1; i < clientCount; i++){
					clients[i-1] = clients[i];
				}
			}
			clientCount--;
			try {
				toTerminate.close();
			}
			catch(IOException ioe) {
				window.println("Error closing thread: " + ioe);
			}
			toTerminate.stopExecution();
		}
	}
	
	private void addThread(Socket socket) {
		if (clientCount < clients.length) {
			window.println("Client accepted: " + socket);
			clients[clientCount] = new ChatServerThread(this, socket,window);
			try {
				clients[clientCount].openSocketConnection();
				clients[clientCount].start();
				clientCount++;
			}
			catch(IOException ioe) {
				window.println("Error opening thread: " + ioe);
			}
		} else
			window.println("Client refused: maximum " + clients.length + " reached.");
	}
	
	/*@SuppressWarnings("unused")
	public static void main(String args[]) {
		ChatServer server = null;
		server = new ChatServer(Integer.parseInt(args[0]));	
		MessageQueueThread messageQueue = null;

	}*/
}