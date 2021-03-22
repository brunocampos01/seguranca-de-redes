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

public class Questao3_1 {

    private Key aesKey;
    private byte[] key;
    private byte iv[];
    private IvParameterSpec ivSpec;
    private Cipher cipher;
    private SecretKeySpec secretKey;

    public void inicia(String chaveCBC, String IV) throws NoSuchAlgorithmException, 
                                                          NoSuchPaddingException,
                                                          NoSuchProviderException {
        // Instancia o cipher
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Gera uma chave AES
        KeyGenerator sKenGen = KeyGenerator.getInstance("AES");
        aesKey = sKenGen.generateKey();

        // Chave na String
        try {
            key = Hex.decodeHex(chaveCBC.toCharArray());
        } catch (DecoderException ex) {
            Logger.getLogger(Questao3_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        secretKey = new SecretKeySpec(key, "AES");

        // IV na String

        try {
            iv = Hex.decodeHex(IV.toCharArray());
        } catch
        (DecoderException ex) {
            Logger.getLogger(Questao3_1.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        ivSpec = new IvParameterSpec(iv);
    } // fim inicia


    public String decrypt(String dec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        try {

            //cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] embytes = {};
            try {
                embytes = Hex.decodeHex(dec.toCharArray());
            } catch (DecoderException ex) {
                Logger.getLogger(Questao3_1.class.getName()).log(Level.SEVERE, null, ex);
            }

            String decryptedString = new String(cipher.doFinal(embytes));

            return decryptedString;

        } catch (IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String args[]) throws InvalidKeyException,
                                                  InvalidAlgorithmParameterException,
                                                  NoSuchPaddingException,
                                                  NoSuchAlgorithmException,
                                                  NoSuchProviderException {
        String chave = "22f81e94409b96a82586b5987b8f9603";
        String IV = "d161fbaa4c64ecf7d2c4abd885751273";

        String textoCifrado = "a74c6f69c37b713e8960ec4394a0b9ee555c461a5c5c3f95ebdb658fde6c85a150e195fec75542f8fca70995c086f0b4";

        Questao3_1 obj = new Questao3_1();
        obj.inicia(chave, IV);

        String decifrada = obj.decrypt(textoCifrado);
        System.out.println("Mensagem decifrada = " + decifrada);
    }
}
