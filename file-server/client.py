import socket
import sys

IP = sys.argv[1]
PORT = int(sys.argv[2])
FILE = sys.argv[3]
ADDR = (IP, PORT)
FORMAT = 'utf-8'

def main ():
  client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  client.connect(ADDR)
  client.send(FILE.encode(FORMAT))
  file = open(FILE, 'w')
  data = client.recv(1024).decode(FORMAT)
  file.write(data)
  file.close()
  client.close()

if __name__ == '__main__':
  main()
