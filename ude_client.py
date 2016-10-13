#!/usr/bin/env python
 
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
BUFFER_SIZE = 1024
message = ""

size=len(sys.argv)

if size == 3 or size == 5:
	for i in range(1,size,2):
		if sys.argv[i]=='-n':
			TCP_IP=sys.argv[i+1]
		elif sys.argv[i]=='-p':
			TCP_PORT=sys.argv[i+1]


print 'Number of arguments:', len(sys.argv), 'arguments.'
print 'Argument List:', str(sys.argv)
print 'try' , get_ip_address()
input_user=''


while 1:
	while 1:
		input_user = raw_input()
		print input_user, ' -- ', len(input_user)
		input_split = input_user.split()

		if input_split[0]=='list':
			message = "ULQ"
			break
		
		
		if input_split[0]=='request':
			if (len(input_split)) > 2 and input_split[2]=='t':
				palavras=input_split[3:]
				message = 'TRQ t ' + str(len(palavras))
				for p in palavras:
					if p!='':
						message += ' ' + p
				message += '\n'
				break

			elif len(input_split) > 2 and input_split[2]=='f':
				if (int(input_split[1])==1) :
					message = 'TRQ f ' + input_split[3]
					break
			else:
				print 'Dados errados, tente outra vez:'

		
		
		else:
			print 'Dados errados, tente outra vez:'
			

	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	s.connect((TCP_IP, TCP_PORT))
	s.send(message)
	data = s.recv(BUFFER_SIZE)
	s.close()
 
print "received data:", data

sys.exit('DONE CARALHO')