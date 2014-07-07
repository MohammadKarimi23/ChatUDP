package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Vector;

public class MasterServer extends Thread {

	DatagramSocket serverSocket;
	public MasterServer() {
		try {
			serverSocket = new DatagramSocket(4450);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		
		Vector<User> mp = new Vector<User>();
		while(true){
			byte [] buffer = new byte[255];
			DatagramPacket packet = new DatagramPacket( buffer, 255   );
			try {
				serverSocket.receive(packet);
				System.out.println(new String(packet.getData()));
				String s = new String(packet.getData());
				if (s.startsWith("&&")){
					int i ;
					for(i=0 ; i<s.length()&&s.charAt(i)!=' '  ; i++){
						
					}
					
					User u =new User(s.substring(2 , i ) , Integer.parseInt(s.substring(i+1 , i+5)));
					mp.add(u);
					System.out.println(u.getName() + "_" +u.getPort());
				}else
				for(int i=0;i<mp.size();i++){
					String s1 = new String(packet.getData());
					s1 = mp.elementAt(i).getName() + ":"+ s1;
					serverSocket.send(new DatagramPacket(s1.getBytes(), s1.length() , InetAddress.getByName("localhost") , mp.elementAt(i).getPort()));
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
//
//class User{
//	   String name;
//	   int port;
//	   public User(String s, int p){
//		   name=s;
//		   port=p;
//	   }
//	   int getPort(){return port;}
//	   String getName(){return name;}
//	}

