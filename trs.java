import java.io.*;
import java.net.*;
import java.lang.*;
import java.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class trs{
	
	public static void main(String args[]) throws Exception{
		
		/* Informações de servidores por omissao*/
		int TCSPORT = 58045;
		int TRSPORT = 59000;
		String TCSNAME = "localhost";
		InetAddress TRSIP = InetAddress.getByName(TCSNAME);
		
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		

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
		
		TRSIP = InetAddress.getByName(TCSNAME);
		
		/* Dar inicio ao registo em TCS */
		DatagramSocket socketudp = new DatagramSocket();
		
		byte[] rec = new byte[1024];
		String recaux1;
		String[] recaux2;
		byte[] env = new byte[1024];
		String envaux1;
		String[] envaux2;
		
		/* Envio de registo */
		envaux1 = "SRG "+LANGUAGE+" "+TRSIP.getHostAddress()+" "+TRSPORT + "\n";

		env = envaux1.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(env, env.length, TRSIP, TCSPORT);
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
				System.out.println("Received From Cliente: " + recaux1);
				recaux2=recaux1.split("\\W");
				
				int times;
				int timesaux;
				String line = "";
				String[] lineSplit;
				String traduzido = "TRR " + recaux2[1];
				String file = "";
				int k = 0;
				
				while(k!=recaux2.length){
					System.out.println("recaux2["+k+"] :" + recaux2[k]);
				}
				
				
				if(recaux2[0].equals("TRQ")){
					if(recaux2[1].equals("t")){
						if (LANGUAGE.equals("Ingles") || LANGUAGE.equals("ingles")){
							file = "text_translation.txt";
						}
						else if (LANGUAGE.equals("Frances") || LANGUAGE.equals("frances")){
							file = "text_translation1.txt";
						}
						else if (LANGUAGE.equals("Holandes") || LANGUAGE.equals("holandes")){
							file = "text_translation_hol.txt";
						}
						else{
							file = "text_translate2.txt";
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
									System.out.println(lineSplit[0]);
									System.out.println(lineSplit[1]);
									if(recaux2[i] == lineSplit[0]){
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
							BufferedReader ficheiro = new BufferedReader(new FileReader(file));
							String nomeTraduzido ="" ;
							while ((line = ficheiro.readLine()) != null){
								System.out.println("linha de ficheiro: " + line);
								lineSplit = line.split(" ");
								if(recaux2[3] == lineSplit[0]){
									traduzido += " " + lineSplit[1];
									nomeTraduzido = lineSplit[1];
									break;
								}
							}
							try{
								File HeyFile= new File(nomeTraduzido);
								BufferedImage image = ImageIO.read(HeyFile);
								String data = "";
								data = image.toString();
								traduzido += " "+file.length() + " " + data;
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
				System.out.println( "A Enviar: "+traduzido);
				DataOutputStream outToClient = new DataOutputStream(socketaccept.getOutputStream());
				
				outToClient.writeBytes(traduzido);
				
				BufferedReader alive = null;
		
				System.out.println("Keep server alive?");
				alive = new BufferedReader(new InputStreamReader(System.in));
				String input = alive.readLine();

                if (!("y".equals(input)) || !("yes".equals(input))) {
					while(true){
						System.out.println("Server is dead");
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
							System.exit(0);
						}
					}
				}
			}
		}
	}
}
