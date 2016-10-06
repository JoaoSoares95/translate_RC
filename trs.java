import java.io;
import java.net;

class TRSSERVER{

	private void error(){
		System.out.println("Creation of server abort!\n");
		System.exit(1)
	}
	
	public static void main(String args[]) throws Exception{
		
		/* Informações de servidores por omissao*/
		int TCSPORT = 59045;
		int TRSPORT = 59000;
		int TRSIP = InetAddress.getLocalHost(); 
		String TCSNAME = "localhost";
		String LANGUAGE;
		/* Verifiacao de input*/
		LANGUAGE = args[0];
		if(args.length == 3){
			if(args[1] == "-p" ){
				TRSPORT = args[2];
			}
			else if (args[1] == "-n"){
				TCSNAME = args[2];
			}
			else if (args[1] == "-e"){
				TCSPORT = args[2];
			}
		}
		else if(args.length == 5){
			if(args[1] == "-p" ){
				TRSPORT = args[2];
				if(args[3] == "-n"){
					TCSNAME = args[4];
				}
				else if(args[3]== "-e"){
					TCSPORT = args[4];
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
				else if(args[3]== "-e"){
					TCSPORT = args[4];
				}
				else{
					error();
				}
			}
			else if (args[1] == "-e"){
				TCSPORT = args[2];
				if(args[3] == "-n"){
					TCSNAME = args[4];
				}
				else if(args[3]== "-p"){
					TRSPORT = args[4];
				}
				else{
					error();
				}
			}
		}
		else if(args.length == 7){
			if(args[1] == "-p" ){
				TRSPORT = args[2];
				if(args[3] == "-n"){
					TCSNAME = args[4];
					if(args[5] == "-e"){
						TCSPORT = args[6];
					}
					else{
						error();
					}
				}
				else if(args[3]== "-e"){
					TCSPORT = args[4];
					if(args[5] == "-n"){
						TCSNAME = args[6];
					}
					else{
						error();
					}
				}
				else{
					error();
				}
			}
			else if (args[1] == "-n"){
				TCSNAME = args[2];
				if(args[3] == "-p"){
					TRSPORT = args[4];
					if(args[5] = "-e"){
						TCSPORT = args[6];
					}
					else{
						error();
					}
				}
				else if(args[3]== "-e"){
					TCSPORT = args[4];
					if(args[5] = "-p"){
						TRSPORT = args[6];
					}
					else{
						error();
					}
				}
				else{
					error();
				}
			}
			else if (args[1] == "-e"){
				TCSPORT = args[2];
				if(args[3] == "-n"){
					TCSNAME = args[4];
					if(args[5] == "-p"){
						TRSPORT = args[6];
					}
					else{
						error();
					}
				}
				else if(args[3]== "-p"){
					TRSPORT = args[4];
					if(args[5] == "-n"){
						TCSNAME = args[6];
					}
					else{
						error();
					}
				}
				else{
					error();
				}
			}
		}
		else{
			error();
		}
		
		/* Dar inicio ao registo em TCS */
		DatagramSocket socketudp = new DatagramSocket(128);
		byte[] rec = new byte[1024];
		byte[] env = new byte[1024];
		
		/* Envio de registo */
		env = "SRG "+LANGUAGE+" "+TRSIP+" "+TRSPORT ;
		DatagramPacket sendPacket = new DatagramPacket(env, sendData.length, TCSNAME, TCSPORT);
        socket.send(sendPacket);

		/* Registo com sucesso?*/
		env = "SRR status";
		DatagramPacket sendPacket = new DatagramPacket(env, sendData.length, TCSNAME, TCSPORT);
        socketudp.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(rec, rec.length);
        socketudp.receive(receivePacket);
        String sentence = new String( receivePacket.getData());
		if(!sentence.equals("status = OK")){
			System.out.println("SRR ERR");
			System.exit(1);
		}
		else{
			
			/*Criacao de TCP*/
			while(true){
				ServerSocket sockettcp = new ServerSocket(6789);
				
				Socket connectionSocket = sockettcp.accept();
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				
				
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				clientSentence = inFromClient.readLine();
				System.out.println("Received: " + clientSentence);
				capitalizedSentence = clientSentence.toUpperCase() + '\n';
				outToClient.writeBytes(capitalizedSentence);
			}
		}
	}
}
