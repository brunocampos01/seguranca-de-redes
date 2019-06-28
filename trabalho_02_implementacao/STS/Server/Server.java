package STS.Server;

import java.net.*;
import java.io.*;

public class Server {
    private static int port = 12345;

    // Private Key = b
    private static int b = 3;

    // Parametros do cliente: public key(p, g) e private key(A) 
    private double clientP, clientG, clientA;
    private static String keyServer = diffieHellmann(b, clientG, clientP);

    // socket
    public static Socket startServer(int port) throws IOException, SocketTimeoutException {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Aguardando por clientes na porta: " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Nova conexão com o cliente: " + server.getRemoteSocketAddress());
            
            return server;

        } catch (SocketTimeoutException s) {
            throw new SocketTimeoutException("Socket timed out!\n" + s);
        } catch (IOException e) {
            throw new IOException("IOException:\n" + e);
        }
    }
    
    //  Diffie-Hellmann                                                        
    public static String diffieHellmann(int b, double clientG, double clientP) {            
        double B = ((Math.pow(clientG, b)) % clientP);  // cálculo do Diffie-Hellmann: g^b mod p  
        keyServer = Double.toString(B);                                                       
                                                                                               
        return keyServer;                                                                     
    }                                                                                       
     
    
    public void receiveParams(int b, double clientP, double clientG, double clientA, Socket server)
        throws IOException{
        
        clientP = this.clientP;
        clientG = this.clientG;
        clientA = this.clientA;

        // Private Key do servidor 
        System.out.println("Servidor: Private Key = " + b);

        // Stream de dados de entrada
        DataInputStream in = new DataInputStream(server.getInputStream());

        clientP = Integer.parseInt(in.readUTF()); // armazena p 
        System.out.println("Cliente: P = " + clientP);

        clientG = Integer.parseInt(in.readUTF()); // armazena g 
        System.out.println("Cliente: G = " + clientG);

        clientA = Double.parseDouble(in.readUTF()); // armazena A 
        System.out.println("Cliente: Public Key = " + clientA);
    }
    
    // cálculo key de sessão
    public static double sessionKey(double clientA, int b) {
        System.out.println("Key clientA: = " + clientP);
        System.out.println("Key b: = " + b);

        double keySession  = ((Math.pow(clientA, b)) % clientP); // cálculo do Diffie-Hellmann: g^ab mod p        
        System.out.println("Key Session: = " + keySession);

        return keySession;
    }


    public static void main(String[] args) throws IOException, SocketTimeoutException {
        Socket socketServer = startServer(port);
        receiveParams(b, clientP, clientG, clientA, socketServer);
        sessionKey(clientA, b);
        socketServer.close(); 
    }
    
} 
