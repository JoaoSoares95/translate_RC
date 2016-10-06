import java.io.*;
import java.net.*;

class UDPServer{

      public static void main(String args[]) throws Exception{

                  DatagramSocket serverSocket = new DatagramSocket(9876);
                  
            

            while (true){
            
                  byte[] receiveData = new byte[1024];
                  byte[] sendData = new byte[1024];
                  String sentence = "";

                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);

                  sentence = new String( receivePacket.getData());
                  System.out.println("RECEIVED: " + sentence);

                  String help=sentence;
                  System.out.println("-----------ddddddddddd---- " + help.equals("exit"));


                  if (help.equalsIgnoreCase("exit")) {
                        System.out.println("Saiu");
                        break;
                  }

                  else{
                        System.out.println("--------------- " + sentence.equals("exit"));
                        System.out.println("rip::::::::::: " + sentence);
                        System.out.println("nao percebo nada disto");
                  }
                  
                  InetAddress IPAddress = receivePacket.getAddress();

                  System.out.println("IPAddress: " + IPAddress);

                  int port = receivePacket.getPort();

                  System.out.println("port: " + port);

                  String capitalizedSentence = sentence.toUpperCase();

                  System.out.println("capitalizedSentence: " + capitalizedSentence);

                  sendData = capitalizedSentence.getBytes();

                  System.out.println("sendData: " + sendData);
                  
                  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  serverSocket.send(sendPacket);

                  System.out.println("sendPacket: " + sendPacket);

            }

            System.out.println ("parou o while");
      }
}