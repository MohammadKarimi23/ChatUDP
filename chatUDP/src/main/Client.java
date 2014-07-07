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

public class Client extends JFrame implements ActionListener, Runnable{
	JTextField jtf;
	JTextArea  jta;
	DatagramSocket serverSocket;

	public Client(){
		
		setSize(400,600);
		setLocation(400,0);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("client");
		getContentPane().setBackground(Color.GRAY);
		
		jtf = new JTextField();
		jtf.setSize(300,40);
		jtf.setLocation(20,20);
		jtf.addActionListener(this);
		getContentPane().add(jtf);
		
		jta = new JTextArea();
		JScrollPane jp = new JScrollPane(jta);
		jp.setSize(300,300);
		jp.setLocation(20,100);

		getContentPane().add(jp);
		
		setVisible(true);
		try {
			serverSocket = new DatagramSocket(4446);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		Client client = new Client();
		(new Thread(client)).start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
//		byte [] buffer = new byte[255];
//		buffer = jtf.getText().getBytes();
		try {
			serverSocket.send(new DatagramPacket(jtf.getText().getBytes(),jtf.getText().getBytes().length,InetAddress.getByName("localhost"),4445));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 jtf.setText("");
	}
	@Override
	public void run() {
		byte [] buffer = new byte[255];
		while(true){
			DatagramPacket packet = new DatagramPacket( buffer, 255 );
			try {
				serverSocket.receive(packet);
				
				jta.append("client : "+(new String(packet.getData())).trim()+"\n");	
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}

}
