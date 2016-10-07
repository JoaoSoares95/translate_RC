import java.io.*;
import java.net.*;
import java.*;
import java.lang.*;

class server_usr{
	
	public static void main(String args[]) throws Exception{	
	
		int GN=45;//Group number
		int TCSport=58000+GN;//Server port if not in parameters.
		
		DatagramSocket serverSocket = new DatagramSocket(TCSport);
	
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		
		while(true){
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String( receivePacket.getData());
			System.out.println("RECEIVED: " + sentence);
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String capitalizedSentence = sentence;
			sendData = capitalizedSentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
			sentence.delete(0,sentence.length());
			
	   }
	}
}