package STS.Server;

import java.net.*;
import java.io.*;

public class Server {
    
    public static void startServer(int port) throws IOException,
                                                    SocketTimeoutException {
        try {
            // Implementação socket
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Aguardando por clientes " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Nova conexão com o cliente: " + server.getRemoteSocketAddress());

        } catch (SocketTimeoutException s) {
            throw new SocketTimeoutException("Socket timed out!\n" + s);
        } catch (IOException e) {
            throw new IOException("IOException:\n" + e);
        }
    }

    public static void main(String[] args) throws IOException, SocketTimeoutException {
        int port = 12345;

        startServer(port);
    }
    
} 
