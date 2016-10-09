import java.io.*;
import java.net.*;
import java.lang.*;
import java.*;

class UDPServer{

      public static void main(String args[]) throws Exception{
            int tcs_port = 58045;

            if (args.length==2 && args[0].equals("-p")){
                  tcs_port = Integer.parseInt(args[1]);
            }

            File yourFile = new File("languages.txt");
            yourFile.createNewFile(); // if file already exists will do nothing 
            FileOutputStream oFile = new FileOutputStream(yourFile, false);
            
            DatagramSocket serverSocket = new DatagramSocket(tcs_port);
                  
            while (true){
            
                  byte[] receiveData = new byte[1024];
                  byte[] sendData = new byte[1024];

                  String sentence = "";

                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);

                  System.out.println("receiveData : ----------------- " + receiveData);

                  sentence = new String( receivePacket.getData());

                  System.out.println("RECEIVED: " + sentence);

                  String[] help = sentence.split("\\W");

                  InetAddress IPAddress = receivePacket.getAddress();

                  System.out.println("IPAddress: " + IPAddress);

                  //----------------------------------------------------

                  /****************************
                   *          Client          *
                   ****************************/

                  if (help[0].equals("ULQ")){
                        System.out.println("ULQ");
                  }

                  else if (help[0].equals("ULR")){
                        System.out.println("ULR");
                  }

                  else if (help[0].equals("UNQ")){
                        System.out.println("UNQ");
                  }

                  else if (help[0].equals("UNR")){
                        System.out.println("UNR");
                  }







                  //--------------------------------------------------

                  int port = receivePacket.getPort();

                  System.out.println("port: " + port);

                  String capitalizedSentence = sentence.toUpperCase();

                  System.out.println("capitalizedSentence: " + capitalizedSentence);

                  sendData = capitalizedSentence.getBytes();

                  System.out.println("sendData: " + sendData);
                  
                  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  serverSocket.send(sendPacket);

                  System.out.println("sendPacket: " + sendPacket);

                  if (help[0].equals("exit") && help.length==1) {
                        System.out.println("Saiu");
                        break;
                  }

                  else{
                        System.out.println("--------------- " + help[0].equals("exit"));
                        System.out.println("rip::::::::::: " + help.length );
                        System.out.println("nao percebo nada disto " + help[0].length());
                  }

            }

            System.out.println ("parou o while");
      }
}