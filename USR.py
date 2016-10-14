#!/usr/bin/env python
 
import os
import socket
import sys
import fcntl
import struct
 
def get_ip_address():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.connect(("8.8.8.8", 80))
    return s.getsockname()[0]

TCP_IP = get_ip_address()
TCP_PORT = 59000
UDP_IP = TCP_IP
UDP_PORT = 58045
BUFFER_SIZE = 1024
message = ""
languagues = []
n_lan = 0

size = len(sys.argv)

if size == 3 or size == 5:
	print "entroiu"
	for i in range(1,size,2):
		if sys.argv[i]=='-n':
			UDP_IP=sys.argv[i+1]
			print UDP_IP
		elif sys.argv[i]=='-p':
			UDP_PORT=int(sys.argv[i+1])
			print UDP_PORT
else:
	print "Creation of Server abort"


print 'Number of arguments:', len(sys.argv), 'arguments.'
print 'Argument List:', str(sys.argv)
print 'try' , get_ip_address()
input_user=''


while 1:
	input_user = raw_input()
	print input_user, ' -- ', len(input_user)
	input_split = input_user.split()

	if input_split[0]=='list':
		message = "ULQ\n"
		s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
		s.sendto(message,(UDP_IP,UDP_PORT))
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
			print data_split

			TCP_IP=data_split[1]
			print TCP_IP
			print "jfehbguycsvdahfkjlqoirljvmk : ",data_split[2], " - ", len(data_split[2]), " - "
			TCP_PORT=int(data_split[2])
			print TCP_PORT
			palavras=input_split[3:]
			message = 'TRQ t ' + str(len(palavras))
			for p in palavras:
				if p!='':
					message += ' ' + p
			message += '\n'
			s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			s.connect((TCP_IP, TCP_PORT))
			s.send(message)
			data = s.recv(BUFFER_SIZE)
			s.close()
			print "received data:", data


		elif len(input_split) > 3 and input_split[2]=='f':
			TCP_IP=data_split[1]
			print TCP_IP
			TCP_PORT=int(data_split[2])
			ola="Images/"+input_split[3]
			f = open(ola,'rb')
			l = f.read(BUFFER_SIZE)
			
			
			#if (int(input_split[1])==1) :
			message = 'TRQ f ' + input_split[3]+ " " + str(os.stat(ola).st_size ) + " " + l +"\n"

			s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			s.connect((TCP_IP, TCP_PORT))
			s.send(message)
			s.send(l)
			s.settimeout(None)

			data = s.recv(BUFFER_SIZE)
			s.close()
			print "received data:", data

			data_split=data.split()


			f1 = open(data_split[2], 'wb')
			print type(f1)

			length = 3 + 1 + len(data_split[2]) + len(data_split[3])+ 4
			lixo=data[length:]
			lixo=lixo[:int(data_split[3])]
			print '---------------------------------------------------------------',lixo, "ola", len(lixo)
			f1.write(lixo)
			#size_file=f1.st_size 
			f1.close()
			#if size_file==int(data_split[3]):
			print 'O ficheiro ficou guardado'

		else:
			print 'Dados errados, tente outra vez:'

	else:
		print 'Dados errados, tente outra vez:'
		
	
 

sys.exit('DONE CARALHO')

	


