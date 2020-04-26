package utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.*;

public class SocketClient {

	private static InetAddress host;
	private static Socket socket = null;
	private static ObjectOutputStream oos = null;
	private static ObjectInputStream ois = null;
	private static int port;
	
	private Util util;
	
	//----------------------------------------------------------------------
	public SocketClient(Util util){
		this.util = util;
		port = 7722;
		//localhost
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException e) { e.printStackTrace(); }
	}

	//----------------------------------------------------------------------
	public static String send(String messageSend) {
		String messageGet="";

		//establish socket connection to server
	    try {
				socket = new Socket(host.getHostName(), port);
				
	            //write to socket using ObjectOutputStream
	            oos = new ObjectOutputStream(socket.getOutputStream());
	            //System.out.println(" client Sending request to Socket Server");
	            
	            oos.writeObject(messageSend);
	                 
	            //oos.flush();
	            
	            //read the server response message
	            ois = new ObjectInputStream(socket.getInputStream());
	            
	            
	            //read message
				try {
					messageGet = ois.readObject().toString();
					//message = (String)ois.readObject();
					//if (ois.read() == -1) { message = ""; }
					//else { message = ois.readObject().toString(); }
				}
				catch (ClassNotFoundException e) {
				//catch (IOException e) {
					System.out.println(" client error: " + e.toString());
				}
				
	            //System.out.println(" [" + i +"] client: " + message);

				//close resources
	            ois.close();
	            oos.close();
	            //try {
				//	Thread.sleep(200);
				//} catch (InterruptedException e) { e.printStackTrace(); }
	            
	    }
	    catch (UnknownHostException e) { e.printStackTrace(); }
	    catch (IOException e)          { e.printStackTrace(); }
	    
	    return messageGet;
	} //send
	//----------------------------------------------------------------------
	
}
