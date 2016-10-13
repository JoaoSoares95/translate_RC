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

            int n_languagues = 0;
            String m_ok = "OK", m_nok = "NOK", m_err = "ERR";
            String m_sur = "SUR ", m_srr = "SRR ", m_ulr = "ULR ", m_unr = "UNR ";

            ArrayList <ArrayList <String>> languages = new ArrayList <ArrayList <String>>();

            System.out.println(InetAddress.getLocalHost());

            //File yourFile = new File("languages.txt");
            //yourFile.createNewFile(); // if file already exists will do nothing 
            //FileOutputStream oFile = new FileOutputStream(yourFile, false);
            
            DatagramSocket serverSocket = new DatagramSocket(tcs_port);
                  
            while (true){
            
                  byte[] receiveData = new byte[1024];
                  byte[] sendData = new byte[1024];

                  String sentence = "", messege = "";

                  

                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);

                  //System.out.println("receiveData : ----------------- " + receiveData);

                  sentence = new String( receivePacket.getData());

                  System.out.println("RECEIVED: " + sentence);

                  String[] help = sentence.split(" ");

                  //System.out.println("HELP                           - "+help[0]+" n:" + help.length);

                  InetAddress IPAddress = receivePacket.getAddress();

                  //System.out.println("IPAddress: " + IPAddress);

                  /****************************
                   *          Client          *
                   ****************************/


                  if (help[0].startsWith("ULQ")){               //enviar a lista de linguagens
                        System.out.println("ULQ");          //


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
                          
                          System.out.println(help[1] + " " + help[1].length() + " -- " + l.get(0));
                          
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
                  System.out.println("PASSOU DOS IFS");

                  

                  //--------------------------------------------------

                  //if(!(messege.isEmpty())){

                        int port = receivePacket.getPort();

                        //System.out.println("port: " + port);

                        String capitalizedSentence = messege;//.toUpperCase();

                        //System.out.println("capitalizedSentence: " + capitalizedSentence);

                        sendData = capitalizedSentence.getBytes();

                        //System.out.println("sendData: " + sendData);
                        
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        serverSocket.send(sendPacket);

                        //System.out.println("sendPacket: " + sendPacket);

                        /*if (help[0].equals("exit") && help.length==1) {
                              System.out.println("Saiu");
                              break;
                        }*/

                        /*else{
                              System.out.println("--------------- " + help[0].equals("exit"));
                              System.out.println("rip::::::::::: " + help.length );
                              System.out.println("nao percebo nada disto " + help[0].length());
                        }*/
                  //}

            }
            //System.out.println ("parou o while");
      }
}