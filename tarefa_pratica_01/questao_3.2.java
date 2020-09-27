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

    public void inicia(String chaveCBC, String IV) throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
        // Instancia o cipher
        cipher = Cipher.getInstance("AES/CTR/NoPadding");

        // Gera uma chave AES
        KeyGenerator sKenGen = KeyGenerator.getInstance("AES");
        aesKey = sKenGen.generateKey();

        // Chave na String
        try {
            key = Hex.decodeHex(chaveCBC.toCharArray());
        } catch (DecoderException ex) {
            Logger.getLogger(ProjetoaesEntraDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        secretKey = new SecretKeySpec(key, "AES");

        // IV na String

        try { iv = Hex.decodeHex(IV.toCharArray()); } catch
        (DecoderException ex) {
            Logger.getLogger(ProjetoaesEntraDados.class.getName()).log(Level.SEVERE,
                    null, ex); } ivSpec = new IvParameterSpec(iv);
    } // fim inicia

    public String decrypt(String dec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        try {

            //cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] embytes = {};
            try {
                embytes = Hex.decodeHex(dec.toCharArray());
            } catch (DecoderException ex) {
                Logger.getLogger(ProjetoaesEntraDados.class.getName()).log(Level.SEVERE, null, ex);
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
        String chaveCTR = "a05e2679204241af07f6857d150a1fcd";
        String IV = "468ce1126a37b07138e78eab48344712";

        String textoCifradoCBC = "36466b5fddcfcb1b8a9479eb8c489e7139a3c35020b1e5ee808b39ff18b6abd812afe7" +
                "dbbca40e15df391a7c07ece1c8e10a49368b86a946c8379cd8fa01a47f1956671144b0ca18a4c812cde8f7b9";

        ProjetoaesEntraDados obj = new ProjetoaesEntraDados();
        obj.inicia(chaveCTR, IV);
        String decifrada = obj.decrypt(textoCifradoCBC);
        System.out.println("Mensagem decifrada = " + decifrada);
    }
}
