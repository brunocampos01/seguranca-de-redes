import java.net.*;
import java.io.*;

public class Client {
    private static int port = 12345;
    private static String serverName = "127.0.0.1";

    // Private Key = a
    private static int a = 4;      //  escolher aleatório a em {1,…,p-1}
    
    // Slides: INE56807, pg 7
    // Parâmetros para calcular o Public Keys = p, g
    private static int p = 22307;  // Fixar um grande número primo p 
    private static int g = 9;      // Fixar um inteiro g em {1, …, p}

    private static String pString, gString;
    private static String publicKeyClient = diffieHellmann(p, g, a);

    
    // Socket
    public static Socket startClient(int port, String serverName) throws Exception {
        try {
            System.out.println("Conectando no servidor " + serverName + " na porta " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Cliente conectado com " + client.getRemoteSocketAddress());
            
            return client;

        } catch (Exception e) {
            throw new Exception("Exception:\n" + e);
        }
    }

    // Diffie-Hellmann                               
    public static String diffieHellmann(int p, int g, int a) {
        double A = ((Math.pow(g, a)) % p);  // cálculo do Diffie-Hellmann: g^a mod p  
        String publicKeyClient = Double.toString(A);
        
        return publicKeyClient; //public key client
    }
    
    // Envio de parâmetros
    public static void sendParams(Socket client) throws Exception {
        // Stream de dados de saída
        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);

        pString = Integer.toString(p);
        out.writeUTF(pString); // envia p 

        gString = Integer.toString(g);
        out.writeUTF(gString); // envia g

        // Private Key do cliente
        System.out.println("Cliente : Private Key = " + a);

        out.writeUTF(publicKeyClient); // envia o identificador A
    }

    // // cálculo key de sessão
    // public static void sessionKey(double server, int b) {
    //     double keySession  = ((Math.pow(server, b)) % clientP); // cálculo do Diffie-Hellmann: g^ab mod p        
    //     System.out.println("Key Session: = " + keySession);
    // }

    // Certificado
    public static void signature() {
    }
    
    public static void main(String[] args) throws Exception {
        Socket socketClient = startClient(port, serverName);
        sendParams(socketClient);
        // sessionKey(publicKeyClient, b);

        socketClient.close();
    }
} 
