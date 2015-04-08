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



public class Panel extends JPanel implements ActionListener{
	
	
	Boolean started = false; 
	
	ServerThread server;
	
	JButton start;
	JButton send;
	
	JTextField port; 
	JTextField reply;
	
	JTextArea textArea;
	JScrollPane scroll;
	
	
	
	public Panel(){		
		super();		
		this.setVisible(true);	
		
		start = new JButton("Start");
		start.addActionListener(this);
		
		
		
		port = new JTextField();
		port.setPreferredSize(new Dimension(100,20));
		
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(700,1000));
		
		scroll = new JScrollPane(textArea);
		scroll.setPreferredSize(new Dimension(700,600));
		
		
		
		
		this.add(port);
		this.add(start);
		this.add(scroll);
		
		
	}
	
	public void setTextArea(String message){
		textArea.append("\n" + message);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == start){
			if (!started){
				started = true;
				String ThePort = port.getText();
				int listenOn = Integer.parseInt(ThePort);
				
				server = new ServerThread(this, listenOn);
				server.start();
			}
		}
		
	}
	

}
