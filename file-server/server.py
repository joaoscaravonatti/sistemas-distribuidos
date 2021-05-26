import socket
import sys

IP = sys.argv[1]
PORT = int(sys.argv[2])
DIR = sys.argv[3]
ADDR = (IP, PORT)
FORMAT = 'utf-8'

def main ():
  server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  server.bind(ADDR)
  server.listen()
  conn, addr = server.accept()
  filename = conn.recv(1024).decode(FORMAT)
  file = open(f'{DIR}/{filename}', 'r')
  data = file.read()
  conn.send(data.encode(FORMAT))
  file.close()
  conn.close()
  server.close()

if __name__ == '__main__':
  main()
