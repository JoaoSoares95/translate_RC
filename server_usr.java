import java.io.*;
import java.net.*;
import java.*;
import java.lang.String;

class server_usr{
	
	public static void main(String args[]) throws Exception{	
	
		int GN=45;//Group number
		int TCSport=58000+GN;//Server port if not in parameters.
		
		DatagramSocket serverSocket = new DatagramSocket(TCSport);
	
		while(true){
			
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String( receivePacket.getData());
			System.out.println("RECEIVED: " + sentence);
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			sendData = null;
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = null;
			sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
	   }
	}
}