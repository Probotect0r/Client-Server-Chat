import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JPanel;


public class ServerThread extends Thread{
	
	private DatagramSocket socket;
	private Panel panel;
	private int port;
	private boolean fullClientPorts = false; 
	private boolean fullClientAddress = false; 
	private boolean fullClientNames = false; 
	
	
	InetAddress[] address = new InetAddress[5];
	int[] ports = new int[5];
	String[] names = new String[5];
	
	

	public ServerThread(Panel panel, int thePort) {
		this.panel = panel;
		this.port = thePort;
		try {
			socket = new DatagramSocket(port);
			panel.setTextArea("The server has begun listening on port " + port);
		} catch (SocketException e) {
			e.printStackTrace();
			panel.setTextArea("Server cant open socket and listen on port " + port);
		}
	}
	
	public void run(){
		while(true){			
			byte[] data = new byte[1024];			
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			try {
				socket.receive(packet);			// waits for packet to arrive
			} catch (IOException e) {
				e.printStackTrace();
				panel.setTextArea("Socket cant recieve packets");
			}
			
			//get the message from the packet and trim it
			String message = new String(packet.getData());
			message = message.trim();
			
			int clientPort;
			InetAddress clientAddress;
			clientPort = packet.getPort();
			clientAddress = packet.getAddress();
			
			//if its a begin package then register the ip and port of the new client
			if (message.substring(0, 5).equalsIgnoreCase("begin")){
				
				String clientName = null;

				// Inserts the newly connected client's ip to the ip array if not full
				for (int i=0; i<address.length; i++){
					if (!fullClientAddress){
						if (address[i] == null){
							address[i] = clientAddress;
							ports[i] = clientPort;
							clientName = message.substring(5);
							System.out.println(clientName);
							names[i] = clientName;
							System.out.println(names[i]);
							break;
						}
						else if (i == 4){
							fullClientAddress = true;
							break;
						}
					}					
				}
				
				this.sendMessageToAll(clientName + " has joined the chat");				
			}
			// if the packet is not a begin packet then send the message from that client to all clients
			else if (message.substring(0, 8).equalsIgnoreCase("notbegin")){
				String tempName = null;
				for (int i=0; i<address.length;i++){
					if (clientAddress == address[i]){
						tempName = names[i];
						System.out.println(tempName);
						System.out.println(i);
						break;
					}
				}
				String updatedMessage = tempName + ": " + message.substring(8);
				this.sendMessageToAll(updatedMessage);

			}
			
			
			
			
		}
		
	}
	
	public void sendMessageToAll(String message){
		byte[] data = new byte[1024];
		data = message.getBytes();
		for (int i=0; i<4; i++){			
			if (ports[i] == 0 || address[i] == null){
				break;
			}
			else {
				DatagramPacket messageToAll = new DatagramPacket(data, data.length, address[i], ports[i]);
				try {
					socket.send(messageToAll);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		}
		
		panel.setTextArea(message);
		
		
	}

}
