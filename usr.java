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
				int count=0;
				String words = "";
				for (int l=3;l<help.length;l++){
					count++;
					words = words.concat(help[l]);
					
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
				
				System.out.println(response);
				
				//verification print
				//System.out.println(response);
				
				
				String[] TCS_to_TRS = response.split(" ");
				
				String[] port = TCS_to_TRS[2].split("\\n");
				
				if (TCS_to_TRS[0].equals("UNR")){
					
					/*
					for (String l : TCS_to_TRS){
						System.out.println(l + "----hello" + l.length());
					}*/
					
					String nome = TCS_to_TRS[2];
					
					
					int TRSport = Integer.parseInt(port[0]);
					String TRSname = TCS_to_TRS[1];
					InetAddress IPAddressTRS = InetAddress.getByName(TRSname);
					
					//request with file
					if (help[2].equals("f")){
						//lang select
						
						Socket socket = null;

						socket = new Socket(TRSname, TRSport);

						File file = new File("M:\\test.xml");
						// Get the size of the file
						long length = file.length();
						byte[] bytes = new byte[16 * 1024];
						InputStream in = new FileInputStream(file);
						OutputStream out = socket.getOutputStream();

						int count1;
						while ((count1 = in.read(bytes)) > 0) {
							out.write(bytes, 0, count1);
						}

						out.close();
						in.close();
						socket.close();
						
					}
					
					//request with text
					else if (help[2].equals("t")){
						//lang select
					
						/*Socket socketTRS = new Socket(TRSname, TRSport);
						//System.out.println(languages[numb_lang_selected+1]);
						sendData = ("TRQ t " + count + " " + words + "\n").getBytes();
						/*DatagramPacket*/ /*sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressTRS, TRSport);
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
							
						}*//*
						for (int j = 2; j < languages.length; j++){
							System.out.println(j-1 + " " + languages[j]);
						}*/
				/*
						Socket socket = new Socket(TRSname, TRSport); // Create and connect the socket
						DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

						// Send first message
						dOut.writeBytes(("TRQ t " + count + " " + words + "\n"));
						//dOut.writeUTF("This is the first type of message.");
						dOut.flush(); // Send off the data
						*/
						
						
						/*byte[] message = words.getBytes();
;
						Socket socket=new Socket(TRSname, TRSport);
						OutputStream socketOutputStream = socket.getOutputStream();
						socketOutputStream.write(message);
						
						InputStream socketInputStream = socket.getInputStream();
						
						message = socketInputStream.read();
						String printstuff = message.getData();
						System.out.println(printstuff);*/
						
						

						/* // Send the second message
						dOut.writeByte(2);
						dOut.writeUTF("This is the second type of message.");
						dOut.flush(); // Send off the data

						// Send the third message
						dOut.writeByte(3);
						dOut.writeUTF("This is the third type of message (Part 1).");
						dOut.writeUTF("This is the third type of message (Part 2).");
						dOut.flush(); // Send off the data

						// Send the exit message
						dOut.writeByte(-1);
						dOut.flush(); */

						//dOut.close();
				
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