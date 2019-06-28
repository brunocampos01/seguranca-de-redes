import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args)  throws IOException, SocketTimeoutException{
        try {
            int port = 12345;

            // Server Key 
            int b = 3;

            // Parâmetros do Client: p, g, e key 
            double clientP, clientG, clientA;

            // Implementação socket
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Aguardadndo por clientes " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Nova conexão com o cliente: " + server.getRemoteSocketAddress());
            
        } catch (SocketTimeoutException s) {
            throw new SocketTimeoutException("Socket timed out!\n" + s);
        } catch (IOException e) {
            throw new IOException("IOException:\n" + e);
        }
    }
} 
