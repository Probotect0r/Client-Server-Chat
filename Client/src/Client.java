import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Client extends Thread{
	
	DatagramSocket socket;
	Panel panel;
	InetAddress ipAddress;
	int port;
	
	public Client(Panel panel, InetAddress ipAddress, int port) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.panel = panel;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			panel.textArea.setText("Socket could not be created.");		
		}		
		
	}	
		
	public void sendData(String message){
		String sendMsg = "notbegin" + message;
		byte[] data = new byte[1024];
		data = sendMsg.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
			
		} catch (IOException e) {
			e.printStackTrace();
			panel.textArea.setText("Package could not be sent");
		}			
	}
	
	public void sendBeginPackage(String clientName){
		String begin = "begin" + clientName;
		byte[] data = new byte[1024];
		data = begin.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
			
		} catch (IOException e) {
			e.printStackTrace();
			panel.setText("Package could not be sent");
		}
		
		
		
	}
	
	
	public void run(){		
		while (true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
				panel.setText(new String(packet.getData()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	

}
