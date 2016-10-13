#!/usr/bin/env python
 
import socket
import sys
 


TCP_IP = '194.210.159.18'
TCP_PORT = 59000
BUFFER_SIZE = 1024
split_space = " "
message = "cucu"

print 'Number of arguments:', len(sys.argv), 'arguments.'
print 'Argument List:', str(sys.argv)
print 'try' , sys.argv[1]


input_user = raw_input()
print input_user
input_split = input_user.split()

print 'split: ', input_split , ' -- ', int(input_split[1])
while 1:
	if input_split[0]=='request':
		if input_split[2]=='t':
			palavras=input_split[3:]
			if (int(input_split[1]))==len(palavras):
				message = 'TRQ t ' + input_split[1]
				for p in palavras:
					message += ' ' + p
				message += '\n'
				break

		elif input_split[2]=='f':
			if (int(input_split[1])==1 :
				message = 'TRQ f ' + input_split[3]
				break
	
	else:
		print 'Dados errados, tente outra vez:\n'
	

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))
s.send(message)
data = s.recv(BUFFER_SIZE)
s.close()
 
print "received data:", data

sys.exit('DONE CARALHO')