import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChatClient implements Runnable {
	
	private Socket socket              	= null;
	private Thread thread              	= null;
	protected PrintWriter socketOut 		= null;
	private ChatClientThread client    	= null;
	private boolean	running 			= false;
        private ClientForm window               = null;
	
	public ChatClient(String serverName, int serverPort, ClientForm window) {
		this.window = window;
		window.println("Establishing connection. Please wait ...");
		try {
			socket = new Socket(serverName, serverPort);
			window.println("Connected: " + socket);
			start();
		}
		catch(UnknownHostException uhe){
			window.println("Host unknown: " + uhe.getMessage());
		}
		catch(IOException ioe) {
			window.println("Unexpected exception: " + ioe.getMessage());
		}
	}
	
	public void run() {
		running=true;
		while (running) {
		}
	}
	
	public void handle(String msg) {
		//sendTerminateStatus
		if (msg.equals(".BYE")) {
			window.println("Good bye. Press RETURN to exit ...");
			stop();
		} 
		//sendSystemStatus 
		if (msg.substring(0,1).equals("*")) {
			window.println(">>>"+msg.substring(1));
		} 
		//Normal Chat Message
		else{
			window.println(msg);
		}
	}
	
	public void start() throws IOException {
		socketOut = new PrintWriter(socket.getOutputStream(), true);
		if (thread == null) {
			client = new ChatClientThread(this, socket,window);
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
		try {
			if (socketOut != null)  socketOut.close();
			if (socket    != null)  socket.close();
		}
		catch(IOException ioe) {
			window.println("Error closing ...");
		}
		client.closeSocketConnection();
		client.stopExecution();
	}
	
//	public static void main(String args[]) {
//		@SuppressWarnings("unused")
//		ChatClient client = null;
//		/*System.out.println("Usage: java ChatClient host port");*/
//		client = new ChatClient(args[0], Integer.parseInt(args[1]));
//	}
}