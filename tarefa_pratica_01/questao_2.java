/**
 *
 * Crie um programa que permite ao usuário entrar com uma string pelo teclado, 
 * o programa cifra a string e mostra a string cifrada na tela. 
 * O código deve “sortear” uma boa chave e IV. 
 * Use o modo CTR (counter) do algoritmo AES para cifrar. 
 *
 * Use o projeto3Aes para auxiliar.
 */
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class ProjetoaesEntraDados {

    private Key aesKey;
    private byte[] key;
    private byte iv[];
    private IvParameterSpec ivSpec;
    private Cipher cipher;
    private SecretKeySpec secretKey;

    public void inicia() throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
        // Instancia o cipher
        cipher = Cipher.getInstance("AES/CTR/NoPadding");
        
        // Gera uma chave AES
        System.out.print("Gerando chave \t-> ");
        KeyGenerator sKenGen = KeyGenerator.getInstance("AES");
        aesKey = sKenGen.generateKey();
        System.out.println("Chave AES \t= " + Hex.encodeHexString(aesKey.getEncoded()));
        
        // Gerando o iv com SecureRandom
        System.out.print("Gerando IV \t-> ");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        iv = new byte[16];
        random.nextBytes(iv);
        ivSpec = new IvParameterSpec(iv);
        System.out.println("IV \t= " + Hex.encodeHexString(iv));
    } // fim inicia

    public String encrypt(String strToEncrypt) {
        try {
            try {
                inicia();
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(ProjetoaesEntraDados.class.getName()).log(Level.SEVERE, null, ex);
            }
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
            final String encryptedString = Hex.encodeHexString(cipher.doFinal(strToEncrypt.getBytes()));
            return encryptedString;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
        }
        return null;

    }
    
    
    public static void main(String args[]) throws InvalidKeyException, InvalidAlgorithmParameterException {
        ProjetoaesEntraDados obj = new ProjetoaesEntraDados();

        String paraCifrar;

        Scanner input = new Scanner(System.in);
        System.out.println("Digite a msg para cifrar: ");
        paraCifrar = input.nextLine();

        System.out.println("Mensagem original = " + paraCifrar);
        String cifrada = obj.encrypt(paraCifrar);
        System.out.println("Mensagem cifrada = " + cifrada);
    }
}
