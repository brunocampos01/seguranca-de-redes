import java.net.*;
import java.io.*;

public class Client {

    public static void startClient(int port, String serverName) throws Exception {
        try {
            // Implementação socket
            System.out.println("Conectando no servidor " + serverName + " na porta " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Cliente conectado com " + client.getRemoteSocketAddress());

        } catch (Exception e) {
            throw new Exception("Exception:\n" + e);
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 12345;
        String serverName = "127.0.0.1";

        startClient(port, serverName);
    }
} 
