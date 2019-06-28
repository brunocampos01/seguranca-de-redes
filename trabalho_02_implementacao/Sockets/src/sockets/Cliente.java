/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Carla Código disponivel em:
 * https://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#19-6-cliente
 * Para executar este exemplo, execute primeiro o código do Servidor. Depois execute o código do Cliente.
 * No Cliente, digite uma msg. No servidor, visualize que a msg chegou!
 */
public class Cliente {

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket cliente = new Socket("127.0.0.1", 12345);
        System.out.println("O cliente se conectou ao servidor!");
        Scanner teclado = new Scanner(System.in);

        PrintStream saida = new PrintStream(cliente.getOutputStream());

        while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }
        saida.close();
        teclado.close();
    }

}
