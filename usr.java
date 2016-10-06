import java.io.*;
import java.net.*;



public class usr{
/*
	//variable definition;
	private int GN;
	private int TRSport;
	private int TCSport;
	
	
	//methods for usr;
	public void usr(int GN_c, int TRSport_c, int TCSport_c){
		GN=GN_c;
		TRSport=TRSport_c;
		TCSport=TCSport_c;
	}
		
}

import java.io.*;
import java.net.*;

class UDPClient{*/
   public static void main(String args[]) throws Exception
   {
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      String sentence = inFromUser.readLine();
      sendData = sentence.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
      clientSocket.send(sendPacket);
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String modifiedSentence = new String(receivePacket.getData());
      System.out.println("FROM SERVER:" + modifiedSentence);
      clientSocket.close();
   }
}