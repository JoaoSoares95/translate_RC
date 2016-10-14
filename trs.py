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
TRS_PORT = 59000
UDP_IP = TCP_IP
TCS_PORT = 58045
BUFFER_SIZE = 10240
message = ""
languagues = []
n_lan = 0

size = len(sys.argv)

if size <= 1:
	sys.exit("Not enough args")
else:
	lang = sys.argv[1]

print lang

		
#Verificacao de input
if size == 2 or size == 4 or size == 6 or size == 8:
	print "entroiu"
	for i in range( 2 , size , 2 ):
		if sys.argv[i] == '-n':
			UDP_IP = sys.argv[i+1]
			print UDP_IP
		elif sys.argv[i] == '-p':
			TRS_PORT = int( sys.argv[i+1] )
			print UDP_PORT
		elif sys.argv[i] == "-e":
			TCS_PORT = int( sys.argv[i+1] )	
			print TCS_PORT
else:
	print "Creation of Server Abort"

###############################Avisar o TCS que iniciamos#####################################

message = "SRG " + lang + " " + TCP_IP + " " + str(TCS_PORT)
print message

#Socket UDP para TCS
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.sendto(message,(UDP_IP,TCS_PORT))
data = s.recv(BUFFER_SIZE)

print "received data:", data
s.close()

###############################################################################################

#######################################Verificacoes---------- apagar##################################

print 'Number of arguments:', len(sys.argv), 'arguments.'
print 'Argument List:', str(sys.argv)
print 'try' , get_ip_address()
input_user=''

######################################################################################################

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

############################################Ciclo infinito para comunicacao###################################
while 1:

	s.bind((TCP_IP, TCS_PORT))
	s.listen(1)
	
	addr = s.accept()
	print addr
	
	data = s.recv(BUFFER_SIZE)
	print data
	
	input_split = data.split()
	print "Splitted: ", input_split

	#Verifica se o cliente manda comando certo
	if input_split[0] == "TRQ":
		if len(input_split) >= 4:
		
			#Vai receber palavras
			if input_split[1] == "t":
				words = input_split[3:]
				print words
				
				returned_words = ""
				f = open('text_translation.txt', 'r')
				file_line = ""
				while file_line != null:
					file_line = f.readline()
					string_file = file_line.split()
					for j in words:
						if j == string_file[0]:
							returned_words += string_file[1]
							
			#Vai receber um ficheiro
			elif input_split[1] == "f":
				file = input_split[3]
				returned_file = ""
				f = open('file_translation.txt', 'r')
				file_line = ""
				while file_line != null:
					file_line = f.readline()
					string_file = file_line.split()
					if string_file[0] == file:
						returned_file = string_file[1]
						break
						
				chocolate = open(returned_file,'rb')
				morango = chocolate.read(BUFFER_SIZE)
				s.send(morango)
				
				file_name = input_split[2]
				file_size = input_split[3]
				file_data = input_split[4]
				
			print file_name
			print file_size
			print file_data
		else:
			s.sendto("TRR ERR",(UDP_IP,TCS_PORT))

		
	#print data
	#s.close()
	

	
	#Ve se o cliente esta a pedir a lista de linguas corretamente
	# if input_split[0]=='ULQ' and len(input_split) == 1:
		
		# message = "ULQ\n"
		# print message
		# s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
		# s.sendto(message,(UDP_IP,TRS_PORT))
		# data = s.recv(BUFFER_SIZE)
		# s.close()
		# print "received data:", data

		# input_split = data.split()

		# print "lista: ", input_split
		# if (len(input_split)-2) == int(input_split[1]) and input_split[0]=='ULR' :
			# n_lan = int(input_split[1])
			# languagues = input_split[2:]
			# i=1
			# for l in languagues:
				# print i, ' ', l
				# i+=1
		# else:
			# print 'Mensagem errada do TCS'


	# elif input_split[0]=='request':
		# n = int(input_split[1])
		# if n > 0 and n <= n_lan :
			# print "fcfgvbhnj", n_lan-1
			# message = "UNQ " + languagues[ int(input_split[1]) - 1 ] + "\n"
			# s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
			# s.sendto(message,(UDP_IP,UDP_PORT))
			# data = s.recv(BUFFER_SIZE)
			# s.close()
			# print "received data:", data

			# data_split = data.split() 
		
		# if (len(input_split)) > 2 and input_split[2]=='t' and data_split[0] == "UNR" :
			# TCP_IP=data_split[1]
			# print TCP_IP
			# TCs_PORT=int(data_split[2])
			# print TCS_PORT
			# palavras=input_split[3:]
			# message = 'TRQ t ' + str(len(palavras))
			# for p in palavras:
				# if p!='':
					# message += ' ' + p
			# message += '\n'
			# s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			# s.connect((TCP_IP, TCS_PORT))
			# s.send(message)
			# data = s.recv(BUFFER_SIZE)
			# s.close()
			# print "received data:", data


		# elif len(input_split) > 2 and input_split[2]=='f':
			# TCP_IP=data_split[1]
			# print TCP_IP
			# TCS_PORT=int(data_split[2])
			# ola=input_split[3]
			# f = open(ola,'rb')
			# l = f.read(BUFFER_SIZE)
			
			
			# #if (int(input_split[1])==1) :
			# message = 'TRQ f ' + input_split[3]+ " " + str(os.stat(ola).st_size ) + " " + l +"\n"

			# s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			# s.connect((TCP_IP, TCS_PORT))
			# s.send(message)
			# s.send(l)

			# data = s.recv(BUFFER_SIZE)
			# s.close()
			# print "received data:", data

		# else:
			# print 'Dados errados, tente outra vez:'

	# else:
		# print 'Dados errados, tente outra vez:'
		
	s.close()
#################################################################################################################	
 

sys.exit('DONE CARALHO')

	
