package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.*;

public class Server extends JFrame implements ActionListener, Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3449497572924733622L;
	JTextField jtf;
	JTextArea  jta;
	
	JComboBox jcb;
	DatagramSocket serverSocket;
	MainProgram mp;
	public Server(String title, int port, int loc, MainProgram mp){
		this.mp = mp;
		setSize(400,600);
		setLocation(loc*420,0);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(title);
		getContentPane().setBackground(Color.GRAY);
		
		jtf = new JTextField();
		jtf.setSize(300,40);
		jtf.setLocation(20,20);
		
		jtf.addActionListener(this);
		getContentPane().add(jtf);
		
		jcb = new JComboBox();
		for(int i=0;i<mp.size();i++)
			jcb.addItem(mp.elementAt(i).getName());
		jcb.setSelectedItem(title);
		jcb.setSize(300, 40);
		jcb.setLocation(20,70);
		jcb.addActionListener(this);
		getContentPane().add(jcb);
		jta = new JTextArea();
		jta.setEditable(false);
		jta.setFocusable(false);
		JScrollPane jp = new JScrollPane(jta);
		jp.setSize(300,300);
		jp.setLocation(20,120);

		getContentPane().add(jp);
		
		setVisible(true);
		try {
			serverSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		try {
			serverSocket.send(new DatagramPacket(("&&"+title+" " + port).getBytes(),("&&"+title+" " + port).length(),InetAddress.getByName("localhost"),4450));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	
	public static void main(String[] args) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		byte [] buffer = new byte[255];
		
//		buffer = jtf.getText().getBytes();
		if((e.getSource().getClass().getName()).equals("javax.swing.JComboBox")) {jtf.requestFocus();}
		else{
		try {
			serverSocket.send(new DatagramPacket(jtf.getText().getBytes(),jtf.getText().getBytes().length,InetAddress.getByName("localhost"),4450));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}}
	 jtf.setText("");
		
	}

	@Override
	public void run() {
		byte [] buffer = new byte[255];
		boolean b=false;
		while(true){
			DatagramPacket packet = new DatagramPacket( buffer, 255 );
			try {
				serverSocket.receive(packet);
				int port = packet.getPort();
				String s= "noname";
				for(int i=0;i<mp.size();i++){
					if(mp.elementAt(i).getPort()==port) s= mp.elementAt(i).getName();
				}
				jta.append(s+" : "+(new String(packet.getData())).trim()+"\n");	
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}

}
