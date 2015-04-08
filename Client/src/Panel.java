import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class Panel extends JPanel implements ActionListener {

	JButton send;
	JButton set;
	
	JTextField textField;
	JTextField addressField;
	JTextField port;
	JTextField name;
	
	JTextArea textArea;
	
	JScrollPane scroll;
	
	Client client;	
	InetAddress ipAddress;
	
	
	Boolean started = false; 
	
	
	public Panel(){
			
		this.setVisible(true);		
		
		send = new JButton("Send");
		send.addActionListener(this);
		
		addressField = new JTextField("IP Address");
		addressField.setPreferredSize(new Dimension(200,20));

		set = new JButton("Set");
		set.addActionListener(this);
		
		port = new JTextField("Port");		
		port.setPreferredSize(new Dimension(100,20));
		
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(300,20));
		
		name = new JTextField();
		name.setPreferredSize(new Dimension(100,20));
		
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(500, 1000));
		
		scroll = new JScrollPane(textArea);
		scroll.setPreferredSize(new Dimension(500,500));
		
		this.add(addressField);
		this.add(port);
		this.add(name);
		this.add(set);
		this.add(scroll);
		this.add(textField);
		this.add(send);	

		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == send){
			
			if(started){				
				client.sendData(textField.getText());		
			}
		}
		
		else if(e.getSource() == set){
			if(!started){				
				started = true;
				InetAddress address = null;
				try {
					address = InetAddress.getByName(addressField.getText());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				int thePort = Integer.parseInt(port.getText());
				client = new Client(this, address, thePort);
				client.start();
				client.sendBeginPackage(name.getText());								
			}
			
		}

	}
	
	public void setText(String text){
		textArea.append("\n" + text);
	}

	

}
