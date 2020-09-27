import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjetoaesEntraDados {

    private Key aesKey;
    private byte[] key;
    private byte iv[];
    private IvParameterSpec ivSpec;
    private Cipher cipher;

    public void inicia() throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
        // Instancia o cipher
        cipher = Cipher.getInstance("AES/CTR/NoPadding");

        // Gera uma chave AES
        KeyGenerator sKenGen = KeyGenerator.getInstance("AES");
        aesKey = sKenGen.generateKey();

        // Gerando o iv com SecureRandom
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        iv = new byte[16];
        random.nextBytes(iv);
        ivSpec = new IvParameterSpec(iv);
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
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | InvalidAlgorithmParameterException
                | IllegalBlockSizeException
                | BadPaddingException e) {
        }
        return null;

    }

    public static void main(String args[]) {
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
