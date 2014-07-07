package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Vector;

public class MainProgram extends Vector<User>{
	
	private static final long serialVersionUID = 6847529177852569499L;

	public MainProgram() {
		add(new User("Bahman", 4445));
		add(new User("Ramin", 4446));
		add(new User("Ali", 4447));
	}

	public static void main(String[] args) {
		MainProgram mp = new MainProgram();
		(new MasterServer()).start();
		for(int i=0;i<mp.size();i++)
		{

			(new Thread(new Server(mp.elementAt(i).getName(),mp.elementAt(i).getPort(),i,mp))).start();

		}
//		(new Test(" SALAM ")).start();
//		(new Test(" BYE ")).start();
	}

}
class User{
	   String name;
	   int port;
	   public User(String s, int p){
		   name=s;
		   port=p;
	   }
	   int getPort(){return port;}
	   String getName(){return name;}
	}

class Test extends Thread{
	DatagramSocket socket;
	String s;
	public Test(String s) {
	this.s = s;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
	byte[] b = new byte[255];
		for(int i=0 ; i< 100 ; i++){
				try {
				b = (i+ s).getBytes();
			socket.send(new DatagramPacket(b,(i+s).length(),InetAddress.getByName("localhost"),4445));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	}
}