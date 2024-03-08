from socket import *


def process_request(request):
    parts = request.split(',')
    if len(parts) != 3:
        return "Erro: Requisição inválida"
    ope = parts[0]
    ope1 = float(parts[1])
    ope2 = float(parts[2])
    if ope == 'add':
        return ope1 + ope2
    elif ope == 'sub':
        return ope1 - ope2
    elif ope == 'mul':
        return ope1 * ope2
    elif ope == 'div':
        if ope2 != 0:
            return ope1 / ope2
        else:
            return "Erro: Divisão por zero"
    else:
        return "Erro: Operação inválida"


serverPort = 12000
serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('localhost', serverPort))
serverSocket.listen(1)

print('Servidor entrou no modo Listen...')

timeout = 15


while True:

    connectionSocket, addr = serverSocket.accept()
    print('Conectado por', addr)
    request = connectionSocket.recv(1024).decode()
    result = process_request(request)
    connectionSocket.send(str(result).encode())
    connectionSocket.close()

    serverSocket.settimeout(timeout)

    try:
        serverSocket.accept()
    except timeout:
        print("Tempo limite atingido. Encerrando o servidor...")
        break


serverSocket.close()
