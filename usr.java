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
			
			String[] languages;
			
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
				String lang_list = new String(receivePacket.getData());
				
				languages = lang_list.split("\n");
				
				for (String s : languages){
					System.out.println(s);
					
				}
				
				System.out.println("Lang_Selected: " + lang_list);
				
			}
			
			//request command
			else if (help[0].equals("request")){
				
				if (help.length == 1){
					System.out.println("That's wrong dude.\nTry again.");
					
				}
				
/* 				sendData = languages.getBytes();
				
				DatagramPacket sendPacket1 = new DatagramPacket(sendData, sendData.length, IPAddress, TCSport); */
				
			
				//request with file
				else if (help[2].equals("f")){
					//lang select
					
					// clientSocket.send(sendPacket1);
					
					
				}
				
				//request with text
				else if (help[2].equals("t")){
					//lang select
					// clientSocket.send(sendPacket1);
					
				}
				
				else{
					
					System.out.println("That's wrong buddy");
					
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