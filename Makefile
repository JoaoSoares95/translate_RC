all:
	cd User; make
	cd TRS; make
	cd TCS; make
	cat README.txt
	
user:
	cd User; make

trs:
	cd TRS; make

tcs:
	cd TCS; make

clean:
	cd User; make clean
	cd TRS; make clean
	cd TCS; make clean 
