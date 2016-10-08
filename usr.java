import java.io.*;
import java.net.*;


public class usr{
	
	private void error(){
		System.out.println("Creation of server abort!\n");
		System.exit(1)
	}

	public static void main(String args[]) throws Exception{
	   
	    int GN = 45;//Group number
		int TCSport = 58000+GN;//Port to TCS if not in parameters.
		String TCSname = "localhost";
		
		/**********************Checking if args are inserted on program start;****************************/
		//Only one of TCSport or TCSname is inserted
		if(args.length == 3){
			if(args[1] == "-p" ){
				TCSPORT = args[2];
			}
			else if (args[1] == "-n"){
				TCSname = args[2];
			}
			else{
				error();
			}
		}
		//TCSport and TCSname inserted
		else if(args.length == 5){
			
			if(args[1] == "-p" ){
				TRSPORT = args[2];
				
				if(args[3] == "-n"){
					TCSNAME = args[4];
				}
				
				else{
					error();
				}
			}
			else if (args[1] == "-n"){
				TCSNAME = args[2];
				
				if(args[3] == "-p"){
					TRSPORT = args[4];
				}
				
				else{
					error();
				}
			}
			else{
				error();
			}
			
		
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(TCSName);
		
		//Declaration of Arrays of bytes for the connection;
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		/*String sentence1 = inFromUser.readLine();
		sendData = sentence1.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, TCSport);
		clientSocket.send(sendPacket);		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedSentence);*/
		
		
		//while message not "exit";
		/*while (!(sentence1.equals("exit"))){*/
		while (true){
		
			//read input from comand line;
			String sentence = inFromUser.readLine();
			
			
			String[] help = sentence.split("\\W");
			
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, TCSport);
			//clientSocket.send(sendPacket);

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			//clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			
			//list command
			if (help[0].equals("list")){
				
				sendData = "ULQ".getBytes();
				
				/*DatagramPacket*/ sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, TCSport);
				clientSocket.send(sendPacket);
				
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String modified_language = new String(receivePacket.getData());
				
				System.out.println("Lang_Selected: " + modified_language);
				
			}
			
			//request command
			else if (help[0].equals("request")){
			
				//request with file
				if (help[2].equals("f")){
					//envio de um ficheiro
					
					
				}
				
				//request with text
				else if (help[2].equals("t")){
					//envio de palavras
					
					
				}
				
				else{
					
					System.out.println("That's wrong buddy");
					
				}
			}

			//exit command
			else if (sentence.equals("exit")){
				//sentence1 = sentence;
				System.out.println("Leaving");
				clientSocket.send(sendPacket);

				break;

			}
			
			//everything else
			else{

				clientSocket.send(sendPacket);

				clientSocket.receive(receivePacket);
				modifiedSentence = new String(receivePacket.getData());

				System.out.println("FROM SERVER:" + modifiedSentence);
			}
		}
		clientSocket.close();
   }
}