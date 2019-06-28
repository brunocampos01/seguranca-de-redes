import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args)
    {
        try {
            String pstr, gstr, Astr;
            int port = 12345;
            String serverName = "127.0.0.1";

            // Slides: INE56807, pg 7            
            int p = 6000;  // Fixar um grande número primo p 
            int g = 9;  // Fixar um inteiro g em {1, …, p}
            int a = 4;  // identificador da parte do cliente | escolher aleatório a em {1,…,p-1}

            // Implementação socket
            System.out.println("Conectando no servidor " + serverName + " na porta " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Cliente conectado com " + client.getRemoteSocketAddress());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
