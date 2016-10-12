import java.io.*;
import java.net.*;
import java.lang.*;

class trs{
	
	public static void main(String args[]) throws Exception{
		
		/* Informações de servidores por omissao*/
		int TCSPORT = 58045;
		int TRSPORT = 59000;
		InetAddress TRSIP = InetAddress.getLocalHost();
		
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		
		String TCSNAME = "localhost";
		String LANGUAGE = "";
		/* Verifiacao de input*/
		if (args.length == 1){
			LANGUAGE = args[0];
		}
		else if(args.length == 3){
			LANGUAGE = args[0];
			if(args[1].equals("-p")){
				TRSPORT = Integer.parseInt(args[2]);
			}
			else if (args[1].equals("-n")){
				TCSNAME = args[2];
			}
			else if (args[1].equals("-e")){
				TCSPORT = Integer.parseInt(args[2]);
			}
		}
		else if(args.length == 5){
			LANGUAGE = args[0];
			if(args[1].equals("-p")){
				TRSPORT = Integer.parseInt(args[2]);
				if(args[3].equals("-n")){
					TCSNAME = args[4];
				}
				else if(args[3].equals("-e")){
					TCSPORT = Integer.parseInt(args[4]);
				}
				else{
					System.out.println("Creation of server abort!\n");
					System.exit(1);
				}
			}
			else if (args[1].equals("-n")){
				TCSNAME = args[2];
				if(args[3].equals("-p")){
					TRSPORT = Integer.parseInt(args[4]);
				}
				else if(args[3].equals("-e")){
					TCSPORT = Integer.parseInt(args[4]);
				}
				else{
					System.out.println("Creation of server abort!\n");
					System.exit(1);
				}
			}
			else if (args[1].equals("-e")){
				TCSPORT = Integer.parseInt(args[2]);
				if(args[3].equals("-n")){
					TCSNAME = args[4];
				}
				else if(args[3].equals("-p")){
					TRSPORT = Integer.parseInt(args[4]);
				}
				else{
					System.out.println("Creation of server abort!\n");
					System.exit(1);
				}
			}
		}
		else if(args.length == 7){
			LANGUAGE = args[0];
			if(args[1].equals("-p")){
				TRSPORT = Integer.parseInt(args[2]);
				if(args[3].equals("-n")){
					TCSNAME = args[4];
					if(args[5].equals("-e")){
						TCSPORT = Integer.parseInt(args[6]);
					}
					else{
						System.out.println("Creation of server abort!\n");
						System.exit(1);
					}
				}
				else if(args[3].equals("-e")){
					TCSPORT = Integer.parseInt(args[4]);
					if(args[5].equals("-n")){
						TCSNAME = args[6];
					}
					else{
						System.out.println("Creation of server abort!\n");
						System.exit(1);
					}
				}
				else{
					System.out.println("Creation of server abort!\n");
					System.exit(1);
				}
			}
			else if (args[1].equals("-n")){
				TCSNAME = args[2];
				if(args[3].equals("-p")){
					TRSPORT = Integer.parseInt(args[4]);
					if(args[5].equals("-e")){
						TCSPORT = Integer.parseInt(args[6]);
					}
					else{
						System.out.println("Creation of server abort!\n");
						System.exit(1);
					}
				}
				else if(args[3].equals("-e")){
					TCSPORT = Integer.parseInt(args[4]);
					if(args[5].equals("-p")){
						TRSPORT = Integer.parseInt(args[6]);
					}
					else{
						System.out.println("Creation of server abort!\n");
						System.exit(1);
					}
				}
				else{
					System.out.println("Creation of server abort!\n");
					System.exit(1);
				}
			}
			else if (args[1].equals("-e")){
				TCSPORT = Integer.parseInt(args[2]);
				if(args[3].equals("-n")){
					TCSNAME = args[4];
					if(args[5].equals("-p")){
						TRSPORT = Integer.parseInt(args[6]);
					}
					else{
						System.out.println("Creation of server abort!\n");
					System.exit(1);
					}
				}
				else if(args[3].equals("-p")){
					TRSPORT = Integer.parseInt(args[4]);
					if(args[5].equals("-n")){
						TCSNAME = args[6];
					}
					else{
						System.out.println("Creation of server abort!\n");
						System.exit(1);
					}
				}
				else{
					System.out.println("Creation of server abort!\n");
					System.exit(1);
				}
			}
		}
		else{
			System.out.println("Creation of server abort!\n");
			System.exit(1);
		}
		
		/* Dar inicio ao registo em TCS */
		DatagramSocket socketudp = new DatagramSocket();
		
		byte[] rec = new byte[1024];
		String recaux1;
		String[] recaux2;
		byte[] env = new byte[1024];
		String envaux1;
		String[] envaux2;
		
		/* Envio de registo */
		envaux1 = "SRG "+LANGUAGE+" "+TRSIP+" "+TRSPORT;
		System.out.println(" -- rwhik -- " + envaux1);
		env = envaux1.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(env, env.length, InetAddress.getByName(TCSNAME), TCSPORT);
		socketudp.send(sendPacket);

		
		/* Registo com sucesso?*/
		/*envaux1 = "SRR status";
		env = envaux1.getBytes();
		sendPacket = new DatagramPacket(env, env.length, InetAddress.getByName(TCSNAME), TCSPORT);
		socketudp.send(sendPacket);*/

		DatagramPacket receivePacket = new DatagramPacket(rec, rec.length);
		socketudp.receive(receivePacket);
		String sentence = new String(receivePacket.getData());
		
		System.out.println("-- OLa -- " + sentence);

		if(!sentence.startsWith("SRR OK")){
			System.out.println("SRR ERR");
			System.exit(1);
		}
		else{
			
			/*Criacao de TCP*/
			while(true){
				/* Criacao do canal de comunicacao */
				ServerSocket sockettcp = new ServerSocket(TRSPORT);
				
				/* Canal de comunicacao aceite */
				Socket socketaccept = sockettcp.accept();
				
				/* Comeca a ler do cliente*/
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socketaccept.getInputStream()));
				
				recaux1 = inFromClient.readLine();
				//recaux1 = rec.toString();
				System.out.println("Received From Cliente: " + recaux1 + ".");
				recaux2=recaux1.split("\\W");
				//TRQ t N W1 W2 … WN ou TRQ f filename size data 
				//TRR t N W1 W2 … WN ou TRR f filename size data 
				if(recaux2[0].equals("TRQ")){ /*
					if(){
						
					}
					else if(){
						
					}
					else{
						
					}
				*/}
				else if(recaux2[0].equals("TRR")){ /*
					if(){
						
					}
					else if(){
						
					}
					else{
						
					}
				*/
				}
				else{
					System.out.println("Wrong Protocol!\n");
				}
				/*
				DataOutputStream outToClient = new DataOutputStream(socketaccept.getOutputStream());

				capitalizedSentence = clientSentence.toUpperCase() + '\n';
				outToClient.writeBytes(capitalizedSentence);
				*/
			}
		}
	}
}
