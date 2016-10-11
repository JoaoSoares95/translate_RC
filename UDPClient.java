import java.io.*;
import java.net.*;

class UDPClient {
      public static void main(String args[]) throws Exception {
            while(true){
                  
                  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                  
                  DatagramSocket clientSocket = new DatagramSocket();
                  
                  InetAddress IPAddress = InetAddress.getByName("localhost");
                  
                  byte[] receiveData = new byte[1024];
            
                  String sentence = inFromUser.readLine();

                  System.out.println("sentence: " + sentence);

                  byte[] sendData = sentence.getBytes();

                  //System.out.println("sendData: " + sendData);
                  
                  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 58045);
                  clientSocket.send(sendPacket);
                  
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  clientSocket.receive(receivePacket);

                  String modifiedSentence = new String(receivePacket.getData());
                  System.out.println("FROM SERVER:" + modifiedSentence);
                  
                  if (sentence.equals("exit")) {
                        System.out.println("sentence:::::::::: " + sentence);
                        break;
                        
                  }
                  
                  sentence="";
                  clientSocket.close();
            }
            
            
      }
}