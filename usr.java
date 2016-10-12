import java.io.*;
import java.net.*;
import java.lang.*;

public class usr{

	//Cant use on static main
	private void error(){
		System.out.println("Creation of server abort!\n");
		System.exit(1);
	}
	
	public static void main(String[] args) throws Exception{
	   
	    int GN = 45;//Group number
		int TCSport = 58000+GN;//Port to TCS if not in parameters.
		String TCSname = "localhost";
		
		for (String s: args) {
            System.out.println(s);
        }
		
		/**********************Checking if args are inserted on program start;****************************/
		//Only one of TCSport or TCSname is inserted
		if(args.length == 2){
			if(args[0].equals("-p") ){
				Integer.parseInt(args[1]);
				TCSport = Integer.parseInt(args[1]);
			}
			else if (args[0].equals("-n")){
				TCSname = args[1];
			}
			else{
				System.out.println("Wrong Args!\n");
				System.exit(1);
			}
		}
		
		//TCSport and TCSname inserted
		else if(args.length == 4){
			
			if(args[0].equals("-p")){
				TCSport = Integer.parseInt(args[1]);
				
				if(args[2].equals("-n")){
					TCSname = args[3];
				}
				
				else{
					System.out.println("Wrong Args!\n");
					System.exit(1);
				}
			}
			else if (args[0].equals("-n")){
				TCSname = args[1];
				
				if(args[2].equals("-p")){
					TCSport = Integer.parseInt(args[3]);
				}
				
				else{
					System.out.println("Wrong Args!\n");
					System.exit(1);
				}
			}
			else{
				System.out.println("Wrong Args!\n");
				System.exit(1);	
			}
		}
			
		
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(TCSname);
		
		//Declaration of Arrays of bytes for the connection;
		
		
		/*String sentence1 = inFromUser.readLine();
		sendData = sentence1.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, TCSport);
		clientSocket.send(sendPacket);		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedSentence);*/
		
		String[] languages = new String[99];
		int lang_number = 0;
		int numb_lang_selected = 0;
		/*while (!(sentence1.equals("exit"))){*/
		
		//while message not "exit";
		
		while (true){
			byte[] receiveData = new byte[10240];
			byte[] sendData = new byte[10240];
			
			//read input from comand line;
			String sentence = inFromUser.readLine();
			
			
			String[] help = sentence.split(" ");
			
			
			
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, TCSport);
			//clientSocket.send(sendPacket);

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			//clientSocket.receive(receivePacket);
			//String modifiedSentence = new String(receivePacket.getData());
			
			
			//list command
			if (help[0].equals("list")){
				
				
				sendData = "ULQ\n".getBytes();
				/*DatagramPacket*/ sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, TCSport);
				clientSocket.send(sendPacket);
				
				
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String lang_list = new String(receivePacket.getData());
				
				//split languages received
				languages = lang_list.split(" ");
				//language number
				lang_number = Integer.parseInt(languages[1]);
								
				/*for (String s : languages){
					System.out.println(s);
					
				}*/
				for (int j = 2; j < languages.length; j++){
					System.out.println(j-1 + " " + languages[j]);
				}
			}
			//request command
			else if (help[0].equals("request")){
				
				
				//check if request has a proper number of args
				if (help.length <= 3){
					System.out.println("That's wrong dude.\nTry again.");
					
				}
				
				int size = help.length;
				String words="";
				
				for (int j=3;j<size;j++){
					
					words.concat(help[j]);
					
				}
				
				numb_lang_selected = Integer.parseInt(help[1]);
				
				//System.out.println(languages[numb_lang_selected+1]);
				sendData = ("UNQ " + languages[numb_lang_selected+1]).getBytes();
				
				/*DatagramPacket*/ sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, TCSport);
				clientSocket.send(sendPacket);

				//erro provavelmente aqui;
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String response = new String(receivePacket.getData());
				
				//verification print
				//System.out.println(response);
				
				
				String[] TCS_to_TRS = response.split(" ");
				
				//get the port right because of \n
				String[] port = TCS_to_TRS[2].split("\\n");
				
				if (TCS_to_TRS[0].equals("UNR")){
					
					/*
					for (String l : TCS_to_TRS){
						System.out.println(l + "----hello" + l.length());
					}*/
					
					String nome = TCS_to_TRS[2];
					
					
					int TRSport = Integer.parseInt(port[0]);
					String TRSname = TCS_to_TRS[1];
					
					//request with file
					if (help[2].equals("f")){
						//lang select
						// clientSocket.send(sendPacket1);
						
					}
					
					//request with text
					else if (help[2].equals("t")){
						//lang select
						/* Socket clientSocket_TRS = new Socket(TRSname, TRSport);
						DataOutputStream outToServer = new DataOutputStream(clientSocket_TRS.getOutputStream());
						
						
						BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						
						outToServer.writeBytes(help[3] + "\n");
						
						String modifiedSentence = inFromServer.readLine();
						System.out.println("FROM SERVER:" + modifiedSentence);
						 */
						
						//String sentence;
						String modifiedSentence;
						
						//wrong type of variable
						//InputStream stream = new ByteArrayInputStream(words.getBytes());
						//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(stream));
						Socket clientSocket1 = new Socket(TRSname, TRSport);
						DataOutputStream outToServer = new DataOutputStream(clientSocket1.getOutputStream());
						BufferedReader inFromServer1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
						sentence = inFromUser.readLine();
						outToServer.writeBytes("TRQ t " + words);
						modifiedSentence = inFromServer1.readLine();
						System.out.println("FROM SERVER:" + modifiedSentence);
						clientSocket1.close();
						
						
					}
					
					else{
						
						System.out.println("That's wrong buddy");
						
					}
				}
				else{
					//send error for request
				}
				
			}

			//exit command
			else if (sentence.equals("exit")){
				//sentence1 = sentence;
				System.out.println("Leaving\n");
				clientSocket.send(sendPacket);
				break;

			}
			
			//everything else
			else{
				
				System.out.println("That's wrong dude.\nTry again.");
				
				/*clientSocket.send(sendPacket);

				clientSocket.receive(receivePacket);
				modifiedSentence = new String(receivePacket.getData());

				System.out.println("FROM SERVER:" + modifiedSentence);*/
			}
		}
		clientSocket.close();
   }
}