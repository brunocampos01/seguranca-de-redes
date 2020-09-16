
package hmacex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.Arrays;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

/**
 *
 * @author Carla
 * Este exemplo mostra o uso da classe HMac da BouncyCastle.
 */
public class Hmacex {

    
    public static void main(String[] args) throws IOException  {
        // Instanciar um novo Security provider
        int addProvider = Security.addProvider(new BouncyCastleProvider());

        // Define o Digest: SHA256, SHA512,...
        SHA256Digest digest = new SHA256Digest();
        
        // Instancia o HMac e passa o digest como parâmetro 
        HMac umHmac = new HMac(digest);
        
        System.out.println("Nome do SHA = " + digest.getAlgorithmName());
        System.out.println("Nome do algoritmo de MAC = " + umHmac.getAlgorithmName());
        System.out.println("Tamanho do MAC = " + umHmac.getMacSize());
        
        byte[] resBuf1 = new byte[umHmac.getMacSize()];
        byte[] resBuf2 = new byte[umHmac.getMacSize()];

        // Entrada dos dados
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Digite Mensagem 1 ");
        byte[] input = Utils.toByteArray(bufferRead.readLine());
        System.out.println("Digite Mensagem 2 ");
        byte[] input2 = Utils.toByteArray(bufferRead.readLine());
        
        
        // Define a chave do HMac
        KeyParameter key1 = new KeyParameter(Hex.decode("4a656665"));
        umHmac.init(key1);
        
        // Define a entrada do HMac
        umHmac.update(input, 0, input.length);
        
        // Define o buffer para colocar o resultado do HMac, resBuf1 é a tag
        umHmac.doFinal(resBuf1, 0);
        
        System.out.println("Mensagem 1 = " + Utils.toString(input));
        System.out.println("HMAC da Mensagem 1 = " + new String(Hex.encode(resBuf1)));

        umHmac.reset();
        KeyParameter key2 = new KeyParameter(Hex.decode("0123456789abcdef"));
        umHmac.init(key2);
        umHmac.update(input2, 0, input2.length);
        umHmac.doFinal(resBuf2, 0);
        System.out.println("Mensagem 2 = " + Utils.toString(input2));
        System.out.println("HMAC da Mensagem 2 = " + new String(Hex.encode(resBuf2)));

        if (!Arrays.equals(resBuf1, resBuf2)) {
            System.out.println("HMACs diferentes");
        } else {
            System.out.println("HMACs iguais");
        }
        
        System.out.println("*** AGORA.... Usando chaves iguais ***");
        umHmac.init(key1);
        umHmac.update(input2, 0, input2.length);
        umHmac.doFinal(resBuf2, 0);

        System.out.println("HMAC da Mensagem 2 = " + new String(Hex.encode(resBuf2)));
        if (!Arrays.equals(resBuf1, resBuf2)) {
            System.out.println("HMACs diferentes");
        } else {
            System.out.println("HMACs iguais");
        }

    }
}
