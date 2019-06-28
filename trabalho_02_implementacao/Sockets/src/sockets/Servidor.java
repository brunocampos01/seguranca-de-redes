package sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Carla * Código disponivel em:
 * https://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#19-6-cliente
 * Para executar este exemplo, execute primeiro o código do Servidor. Depois execute o código do Cliente.
 * No Cliente, digite uma msg. No servidor, visualize que a msg chegou!
 */
public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Porta 12345 aberta!");
        Socket cliente = servidor.accept();

        System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

        Scanner entrada = new Scanner(cliente.getInputStream());

        while (entrada.hasNextLine()) {
            System.out.println(entrada.nextLine());
        }
        entrada.close();
        servidor.close();
    }
}
