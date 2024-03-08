from socket import *

serverName = 'localhost'
serverPort = 12000
clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((serverName, serverPort))
sentence = input('Digite a operação no formato "operacao,valor1,valor2": ')
clientSocket.send(sentence.encode('utf-8'))
modifiedSentence = clientSocket.recv(1024)
text = modifiedSentence.decode('utf-8')
print("Resultado recebido do servidor:", text)
clientSocket.close()
