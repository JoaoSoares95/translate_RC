// File Name GreetingClient.java
import java.net.*;
import java.io.*;
import java.lang.*;
import java.*;
import java.util.*;

public class GreetingClient {

      public static void main(String [] args) {
            String serverName = "localhost", mensagem="random";
            int port = 58045, total=args.length , i;
            Scanner user_input = new Scanner( System.in );
            System.out.println("penis1");

            for (i=0;i<total ;i=i+2 ){
                  if (args[i].equals("-n")){
                        serverName = args[i+1]; 
                        //Integer.parseInt(args[1]);
                  }
                  else if (args[i].equals("-p")){
                        port = Integer.parseInt(args[i+1]);
                  }
            }

            System.out.println("penis2");

            while(!(mensagem.equals("exit"))){
                  System.out.println("penis3");
                  System.out.println("Mensagem a enviar: \n");
                  mensagem = user_input.next();
                  try {
                        System.out.println("Connecting to " + serverName + " on port " + port);
                        Socket client = new Socket(serverName, port);
                        
                        System.out.println("Just connected to " + client.getRemoteSocketAddress());
                        OutputStream outToServer = client.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        
                        out.writeUTF("Hello from " + client.getLocalSocketAddress());
                        InputStream inFromServer = client.getInputStream();
                        DataInputStream in = new DataInputStream(inFromServer);
                        
                        System.out.println("Server says " + in.readUTF());
                        client.close();
                  }catch(IOException e) {
                        e.printStackTrace();
                  }
            }
            System.out.println("penis4");
      }
}
