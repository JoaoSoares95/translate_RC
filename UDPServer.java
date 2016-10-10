import java.io.*;
import java.net.*;
import java.lang.*;
import java.*;
import java.util.*;

class UDPServer{

      public static void main(String args[]) throws Exception{
            int tcs_port = 58045;

            if (args.length==2 && args[0].equals("-p")){
                  tcs_port = Integer.parseInt(args[1]);
            }

            int n_languagues=0;
            String m_ok="OK\n", m_nok="NOK\n", m_err = "ERR\n";
            String m_sur="SUR ", m_srr="SRR ";

            ArrayList <ArrayList <String>> languages = new ArrayList <ArrayList <String>>();

            //File yourFile = new File("languages.txt");
            //yourFile.createNewFile(); // if file already exists will do nothing 
            //FileOutputStream oFile = new FileOutputStream(yourFile, false);
            
            DatagramSocket serverSocket = new DatagramSocket(tcs_port);
                  
            while (true){
            
                  byte[] receiveData = new byte[1024];
                  byte[] sendData = new byte[1024];

                  String sentence = "";

                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);

                  //System.out.println("receiveData : ----------------- " + receiveData);

                  sentence = new String( receivePacket.getData());

                  System.out.println("RECEIVED: " + sentence);

                  String[] help = sentence.split("\\W");

                  InetAddress IPAddress = receivePacket.getAddress();

                  //System.out.println("IPAddress: " + IPAddress);

                  //----------------------------------------------------

                  /****************************
                   *          Client          *
                   ****************************/

                  if (help[0].equals("ULQ")){               //enviar a lista de linguagens
                        System.out.println("ULQ");          //
                  }                                         //
                                                            //
                  else if (help[0].equals("ULR")){          // ULR nL L1 L2 … LnL
                        System.out.println("ULR");          //
                  }

                  else if (help[0].equals("UNQ")){
                        System.out.println("UNQ");
                  }

                  else if (help[0].equals("UNR")){
                        System.out.println("UNR");
                  }

                  /****************************
                   *         Tradutor         *
                   ****************************/   

                  else if (help[0].equals("SRG")){
                        System.out.println("SRG");
                        
                        if (help.length==4){   
                              ArrayList <String> language = new ArrayList<String>();
                              
                              for (int i=0; i<4; i++) {
                                    for (int j=0; i<n_languagues; j++) {
                                          languages
                                    }
                                    language.add(help[i]);

                              }
                              languages.add(language);
                              n_languagues++;
                        }
                  }

                  else if (help[0].equals("SRR")){
                        System.out.println("SRR");
                  }

                  else if (help[0].equals("SUN")){
                        System.out.println("SUN");

                  }

                  else if (help[0].equals("SUR")){
                        System.out.println("SUR");
                  }


                  //--------------------------------------------------

                  int port = receivePacket.getPort();

                  //System.out.println("port: " + port);

                  String capitalizedSentence = sentence.toUpperCase();

                  System.out.println("capitalizedSentence: " + capitalizedSentence);

                  sendData = capitalizedSentence.getBytes();

                  System.out.println("sendData: " + sendData);
                  
                  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  serverSocket.send(sendPacket);

                  //System.out.println("sendPacket: " + sendPacket);

                  if (help[0].equals("exit") && help.length==1) {
                        System.out.println("Saiu");
                        break;
                  }

                  /*else{
                        System.out.println("--------------- " + help[0].equals("exit"));
                        System.out.println("rip::::::::::: " + help.length );
                        System.out.println("nao percebo nada disto " + help[0].length());
                  }*/

            }

            System.out.println ("parou o while");
      }
}