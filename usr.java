import java.io.*;
import java.net.*;


public class usr{

	public static void main(String args[]) throws Exception{
	   
	    int GN=45;//Group number
		int TCSport=58000+GN;//Port to TCS if not in parameters.
	   
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		  
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
			
			
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, TCSport);
			//clientSocket.send(sendPacket);

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			//clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			
			//list command
			if (sentence.equals("list")){
				
				sendData = "ULQ".getBytes();
				
				/*DatagramPacket*/ sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, TCSport);
				clientSocket.send(sendPacket);
				
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String modified_language = new String(receivePacket.getData());
				
				System.out.println("Lang_Selected: " + modified_language);
				
			}
			
			//request command
			else if (sentence.equals("request")){
				
				clientSocket.send(sendPacket);
				
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String modified_language = new String(receivePacket.getData());
				
				System.out.println("Lang_Selected: " + modified_language);
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