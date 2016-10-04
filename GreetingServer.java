// File Name GreetingServer.java
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class GreetingServer extends Thread {
      private ServerSocket serverSocket;
      
      public GreetingServer(int port) throws IOException {
            serverSocket = new ServerSocket(port);
            //serverSocket.setSoTimeout(10000);
      }

      public void run() {
            while(true) {
                  try {
                        System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                        Socket server = serverSocket.accept();
                        
                        System.out.println("Just connected to " + server.getRemoteSocketAddress());
                        DataInputStream in = new DataInputStream(server.getInputStream());
                        
                        System.out.println(in.readUTF());
                        DataOutputStream out = new DataOutputStream(server.getOutputStream());
                        out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
                        server.close();
                     
                  }catch(SocketTimeoutException s) {
                        System.out.println("Socket timed out!");
                        break;
                  }catch(IOException e) {
                        e.printStackTrace();
                        break;
                  }
            }
      }
      
      public static void main(String [] args) {
            String serverName = "localhost";
            int port = 58045, total=args.length , i;
            
            for (i=0;i<total ;i=i+2 ){
                  if (args[i].equals("-n")){
                        serverName = args[i+1]; 
                        //Integer.parseInt(args[1]);
                  }
                  else if (args[i].equals("-p")){
                        port = Integer.parseInt(args[i+1]);
                  }
            }
            try {
                  Thread t = new GreetingServer(port);
                  t.start();
            }
            catch(IOException e) {
                  e.printStackTrace();
            }
      }
}
