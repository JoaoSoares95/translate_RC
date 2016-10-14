#!/usr/bin/env python
 
import os
import socket
import sys
import fcntl
import struct
 
#function to get local address to inform tcs
def get_ip_address():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.connect(("8.8.8.8", 80))
    return s.getsockname()[0]

#variable defining
TCP_IP = get_ip_address()
TCS_PORT = 59000
UDP_IP = TCP_IP
TRS_PORT = 58045
BUFFER_SIZE = 1024
message = ""
languagues = []
n_lan = 0

size = len(sys.argv)

if size <= 1:
	sys.exit("Not enough args")
else:
	lang = sys.argv[1]


		
#Verificacao de input
if size == 2 or size == 4 or size == 6 or 8:
	print "entroiu"
	for i in range( 1 , size , 2 ):
		if sys.argv[i] == '-n':
			UDP_IP = sys.argv[i+1]
			print UDP_IP
		elif sys.argv[i] == '-p':
			UDP_PORT = int( sys.argv[i+1] )
			print UDP_PORT
		elif sys.argv[i] == "-e":
			TCS_PORT = int( sys.argv[i+1] )	
else:
	print "Creation of Server Abort"

#output to socket defining
message = "SRG " + lang + " " + TCP_IP + " " + str(TCS_PORT) + "\n"
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.sendto(message,(UDP_IP,TRS_PORT))
data = s.recv(BUFFER_SIZE)
s.close()
print "received data:", data


#Verifications
print 'Number of arguments:', len(sys.argv), 'arguments.'
print 'Argument List:', str(sys.argv)
print 'try' , get_ip_address()
input_user=''

#cycle to communicate
while 1:
	#input
	input_user = raw_input()
	print input_user, ' -- ', len(input_user)
	input_split = input_user.split()

	if input_split[0]=='list':
		message = "ULQ\n"
		s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
		s.sendto(message,(UDP_IP,TRS_PORT))
		data = s.recv(BUFFER_SIZE)
		s.close()
		print "received data:", data

		input_split = data.split()

		print "lista: ", input_split
		if (len(input_split)-2) == int(input_split[1]) and input_split[0]=='ULR' :
			n_lan = int(input_split[1])
			languagues = input_split[2:]
			i=1
			for l in languagues:
				print i, ' ', l
				i+=1
		else:
			print 'Mensagem errada do TCS'


	elif input_split[0]=='request':
		n = int(input_split[1])
		if n > 0 and n <= n_lan :
			print "fcfgvbhnj", n_lan-1
			message = "UNQ " + languagues[ int(input_split[1]) - 1 ] + "\n"
			s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
			s.sendto(message,(UDP_IP,UDP_PORT))
			data = s.recv(BUFFER_SIZE)
			s.close()
			print "received data:", data

			data_split = data.split() 
		
		if (len(input_split)) > 2 and input_split[2]=='t' and data_split[0] == "UNR" :
			TCP_IP=data_split[1]
			print TCP_IP
			TCs_PORT=int(data_split[2])
			print TCS_PORT
			palavras=input_split[3:]
			message = 'TRQ t ' + str(len(palavras))
			for p in palavras:
				if p!='':
					message += ' ' + p
			message += '\n'
			s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			s.connect((TCP_IP, TCS_PORT))
			s.send(message)
			data = s.recv(BUFFER_SIZE)
			s.close()
			print "received data:", data


		elif len(input_split) > 2 and input_split[2]=='f':
			TCP_IP=data_split[1]
			print TCP_IP
			TCS_PORT=int(data_split[2])
			ola=input_split[3]
			f = open(ola,'rb')
			l = f.read(BUFFER_SIZE)
			
			
			#if (int(input_split[1])==1) :
			message = 'TRQ f ' + input_split[3]+ " " + str(os.stat(ola).st_size ) + " " + l +"\n"

			s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			s.connect((TCP_IP, TCS_PORT))
			s.send(message)
			s.send(l)

			data = s.recv(BUFFER_SIZE)
			s.close()
			print "received data:", data

		else:
			print 'Dados errados, tente outra vez:'

	else:
		print 'Dados errados, tente outra vez:'
		
	
 

sys.exit('DONE CARALHO')

	
