import java.io.*;
import java.net.*;
import java.lang.*;
import java.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class TRS{
	
	public static void main(String args[]) throws Exception{
		
		/* Informações de servidores por omissao*/
		int TCSPORT = 58045;
		int TRSPORT = 59000;
		String TCSNAME = "localhost";
		InetAddress TRSIP;
		
		System.out.println(InetAddress.getLocalHost().getHostAddress());

		

		String LANGUAGE = "";

		int size=args.length;
		/* Verifiacao de input*/
		if (size == 1 || size ==3 || size==5 || size==7){
			LANGUAGE = args[0];
			for (int i=1; i < size; i+=2) {
				//System.out.println(" -- " +i + " -- " + args[i] + " -- ");
				if(args[i].equals("-p")){
					TRSPORT = Integer.parseInt(args[i+1]);
					
				}
				else if (args[i].equals("-n")){
					TCSNAME = args[i+1];
				}
				else if (args[i].equals("-e")){
					TCSPORT = Integer.parseInt(args[i+1]);
				}
				
			}
		}
		else{
			System.out.println("Creation of server abort!\n");
			System.exit(1);
		}
		
		TRSIP = InetAddress.getLocalHost();
		
		/* Dar inicio ao registo em TCS */
		DatagramSocket socketudp = new DatagramSocket();
		
		byte[] rec = new byte[10240];
		String recaux1;
		String[] recaux2;
		byte[] env = new byte[10240];
		String envaux1;
		String[] envaux2;
		
		/* Envio de registo */
		envaux1 = "SRG " + LANGUAGE + " " + TRSIP.getHostAddress() + " " + TRSPORT + "\n";

		System.out.println(LANGUAGE);

		env = envaux1.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(env, env.length, InetAddress.getByName(TCSNAME), TCSPORT);
		socketudp.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(rec, rec.length);
		socketudp.receive(receivePacket);
		String sentence = new String(receivePacket.getData());
		
		System.out.println(sentence);

		if(!sentence.startsWith("SRR OK")){
			System.out.println("SRR ERR");
			System.exit(1);
		}
		else{

			/* Criacao do canal de comunicacao */
			ServerSocket sockettcp = new ServerSocket(TRSPORT);
			
			/*Criacao de TCP*/
			while(true){
				
				/* Canal de comunicacao aceite */
				Socket socketaccept = sockettcp.accept();
				
				/* Comeca a ler do cliente*/
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socketaccept.getInputStream()));
				
				recaux1 = inFromClient.readLine();
				//recaux1 = rec.toString();
				System.out.println("Received From Cliente: " + recaux1);
				recaux2=recaux1.split(" ");
				
				int times;
				int timesaux;
				String line = "";
				String[] lineSplit;
				String traduzido = "TRR " + recaux2[1];
				String file = "";
				int k = 0;
				
				if(recaux2[0].equals("TRQ")){
					if(recaux2[1].equals("t")){
						if (LANGUAGE.equals("Ingles") || LANGUAGE.equals("ingles")){
							file = "Files/text_translation.txt";
						}
						else if (LANGUAGE.equals("Frances") || LANGUAGE.equals("frances")){
							file = "Files/text_translation1.txt";
						}
						else if (LANGUAGE.equals("Holandes") || LANGUAGE.equals("holandes")){
							file = "Files/text_translation_hol.txt";
						}
						else{
							file = "Files/text_translate2.txt";
						}
						System.out.println("file escolhido:"+file);
						times = Integer.parseInt(recaux2[2]);
						timesaux = 0;
						try{
							while (timesaux != times){
								BufferedReader ficheiro = new BufferedReader(new FileReader(file));
								int i = 3 + timesaux;
								while ((line = ficheiro.readLine()) != null){
									System.out.println("linha de ficheiro: " + line);
									lineSplit = line.split(" ");
									if(recaux2[i].equals(lineSplit[0])){
										traduzido += " "+lineSplit[1];
										break;
									}
								}
								timesaux += 1;
							}
						}
						catch(Exception e){
							System.out.println("error reading the file");
						}
					}
					else if(recaux2[1].equals("f")){
						try{
							BufferedReader ficheiro = new BufferedReader(new FileReader("Files/file_translation.txt"));
							String nomeTraduzido ="";
							while ((line = ficheiro.readLine()) != null){
								System.out.println("linha de ficheiro: " + line);
								lineSplit = line.split(" ");
								if(lineSplit[0].equals(recaux2[2])){
									nomeTraduzido = lineSplit[1];
									break;
								}
							}
							try{
								
								FileInputStream fileInputStream = null;
								File HeyFile= new File("Images/"+nomeTraduzido);
								byte[] bytes = new byte[(int) HeyFile.length()];
								//convert file into array of bytes
								fileInputStream = new FileInputStream(HeyFile);
								fileInputStream.read(bytes);
								fileInputStream.close();
								String data = new String(bytes , "UTF-8");
								traduzido += " " + nomeTraduzido + " " + HeyFile.length() + " " + data ;
							}
							catch (IOException e) {
								System.out.println("error reading the file and transforming to data");
							}
						}
						catch(Exception e){
							System.out.println("error reading the file");
						}
					}
					else{
						System.out.println("Wrong Protocol!\n");
					}
				}
				else{
					System.out.println("Wrong Protocol!\n");
				}

				traduzido += "\n";
				DataOutputStream outToClient = null;
				outToClient = new DataOutputStream(socketaccept.getOutputStream());
				byte[] enviar = traduzido.getBytes();
				outToClient.write(enviar, 0 , enviar.length);
				outToClient.close();

				System.out.println("Keep server alive? [y=yes or n=no]");
				BufferedReader alive = null;
				alive = new BufferedReader(new InputStreamReader(System.in));
				String input = alive.readLine();

				if (!(input.equals("y"))) {
					while(true){
						envaux1 = "SUN "+LANGUAGE+" "+TRSIP.getHostAddress()+" "+TRSPORT+"\n";

						env = envaux1.getBytes();
						sendPacket = new DatagramPacket(env, env.length, InetAddress.getByName(TCSNAME), TCSPORT);
						socketudp.send(sendPacket);

						receivePacket = new DatagramPacket(rec, rec.length);
						socketudp.receive(receivePacket);
						sentence = new String(receivePacket.getData());


						if(!sentence.startsWith("SUR OK")){
							System.out.println("SUR ERR");
							System.out.println("Try kill the server again\n");
						}
						else{
							sockettcp.close();
							System.exit(0);
						}
					}
				}
			}
		}
	}
}
