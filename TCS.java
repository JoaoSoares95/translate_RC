import java.io.*;
import java.net.*;
import java.lang.*;
import java.*;
import java.util.*;

class TCS{

      public static void main(String args[]) throws Exception{
            int tcs_port = 58045;

            if (args.length==2 && args[0].equals("-p")){
                  tcs_port = Integer.parseInt(args[1]);
            }

            int n_languagues = 0;
            String m_ok = "OK", m_nok = "NOK", m_err = "ERR";
            String m_sur = "SUR ", m_srr = "SRR ", m_ulr = "ULR ", m_unr = "UNR ";

            ArrayList <ArrayList <String>> languages = new ArrayList <ArrayList <String>>();

            System.out.println(InetAddress.getLocalHost().getHostName());

            DatagramSocket serverSocket = new DatagramSocket(tcs_port);
                  
            while (true){
            
                  byte[] receiveData = new byte[1024];
                  byte[] sendData = new byte[1024];

                  String sentence = "", messege = "";

                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);

                  sentence = new String( receivePacket.getData());

                  System.out.println("RECEIVED: " + sentence);

                  String[] help = sentence.split(" ");

                  InetAddress IPAddress = receivePacket.getAddress();


                  /****************************
                   *          Client          *
                   ****************************/


                  if (help[0].startsWith("ULQ")){
                        System.out.println("ULQ");


                        String numero=Integer.toString(n_languagues);

                        messege = m_ulr + numero;

                        System.out.println(messege);

                        for (int i = 0; i < n_languagues ; i++ ) {
                              ArrayList <String> l = languages.get(i);
                              messege = messege + " " + l.get(0);
                        }
                        
                        messege = messege + "\n";
                  }   

                  else if (help[0].equals("UNQ")){
                      System.out.println("UNQ");
                      
                      
                      for (int i = 0; i < n_languagues ; i++ ) {
                          
                          ArrayList <String> l = languages.get(i);
                          
                          if (help[1].startsWith(l.get(0))){
                              messege = m_unr + l.get(1) + " " + l.get(2) + "\n";
                              System.out.println(messege);
                              break;
                          }
                      }
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
                              
                              for (int j=0; j<n_languagues; j++) {
                                    ArrayList <String> l = languages.get(j); 
                                    if(l.get(0).equals(help[1]) || l.get(1).equals(help[2]) || l.get(2).equals(help[3])){
                                          messege = m_srr + m_nok;
                                    }   
                              }

                              for (int i=1; i<4; i++) {
                                    if (!(language.add(help[i]))){
                                          messege = m_srr + m_err;
                                    }
                              }

                              languages.add(language);
                              n_languagues++;                        }

                        else{ //Senao tiver numero de argumentos certos
                              messege=m_srr+m_err;
                        }

                        if (messege.isEmpty()){ //Se estiver vazio significa que está tudo OK
                              messege=m_srr+m_ok;
                        }
                  }

                  else if (help[0].equals("SUN")){
                        System.out.println("SUN");

                        if (help.length==4){   
                              ArrayList <String> n_to_remove = new ArrayList<String>();
                              int a=0;

                              for (int j=0; j<n_languagues; j++) {
                                    ArrayList <String> l = languages.get(j); 
                                    if(l.get(0).equals(help[1]) && l.get(1).equals(help[2]) && l.get(2).equals(help[3])){
                                          if (n_to_remove.isEmpty()){
                                                n_to_remove = l;
                                          }
                                          else {
                                                a=1;
                                          }
                                    }   
                              }

                              if(!(n_to_remove.isEmpty() && a==0)){
                                    if (languages.remove(n_to_remove)){
                                          messege = m_sur + m_ok;
                                          n_languagues--;
                                    }
                              }
                              else{
                                    messege = m_sur + m_nok;
                              }
                              
                              
                        }

                        else{ //Senao tiver numero de argumentos certos
                              messege=m_sur+m_err;
                        }
                  }

                        int port = receivePacket.getPort();


                        String capitalizedSentence = messege;


                        sendData = capitalizedSentence.getBytes();

                        
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        serverSocket.send(sendPacket);


            }
    }
}