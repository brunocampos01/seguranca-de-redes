import java.net.*;
import java.io.*;

public class Server {
    private static int port = 12345;

    // Private Key = b
    private static int b = 3;

    // Parametros do cliente: public key(p, g) e private key(A) 
    private static double clientP, clientG, publicKeyClient;
    private static String publicKeyServer = diffieHellmann(b, clientG, clientP);


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
        publicKeyServer = Double.toString(B);                                                       

        return publicKeyServer;                                                    
    }

    public static void receiveParams(Socket server)
        throws IOException{

        // Private Key do servidor 
        System.out.println("Servidor: Private Key = " + b);

        // Stream de dados de entrada
        DataInputStream in = new DataInputStream(server.getInputStream());

        clientP = Integer.parseInt(in.readUTF()); // armazena p 
        System.out.println("Cliente: P = " + clientP);

        clientG = Integer.parseInt(in.readUTF()); // armazena g 
        System.out.println("Cliente: G = " + clientG);

        publicKeyClient = Double.parseDouble(in.readUTF()); // armazena A 
        System.out.println("Cliente: Public Key = " + publicKeyClient);
    }
    
    // cálculo key de sessão
    public static void sessionKey(double clientA, int b) {
        double keySession  = ((Math.pow(clientA, b)) % clientP); // cálculo do Diffie-Hellmann: g^ab mod p        
        System.out.println("Key Session: = " + keySession);
    }


    public static void main(String[] args) throws IOException, SocketTimeoutException {
        Socket socketServer = startServer(port);
        receiveParams(socketServer);
        sessionKey(publicKeyClient, b);

        socketServer.close();
    }

}
