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

public class Questao3_2 {

    private Key aesKey;
    private byte[] key;
    private byte iv[];
    private IvParameterSpec ivSpec;
    private Cipher cipher;
    private SecretKeySpec secretKey;

    public void inicia(String chaveCBC, String IV) throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
        // Instancia o cipher
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Gera uma chave AES
        KeyGenerator sKenGen = KeyGenerator.getInstance("AES");
        aesKey = sKenGen.generateKey();

        // Chave na String
        try {
            key = Hex.decodeHex(chaveCBC.toCharArray());
        } catch (DecoderException ex) {
            Logger.getLogger(Questao3_2.class.getName()).log(Level.SEVERE, null, ex);
        }
        secretKey = new SecretKeySpec(key, "AES");

        // IV na String

        try { iv = Hex.decodeHex(IV.toCharArray()); } catch
        (DecoderException ex) {
            Logger.getLogger(Questao3_2.class.getName()).log(Level.SEVERE,
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
                Logger.getLogger(Questao3_2.class.getName()).log(Level.SEVERE, null, ex);
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
        String chave = "53efb4b1157fccdb9902676329debc52";
        String IV = "d161fbaa4c64ecf7d2c4abd885751273";

        String textoCifradoCBC = "234b8c41d1f75bb2c5a4ca436fe641b8ac294ef53441d2acb56facfe93af71a432bd7f84efa645abd20f9c58125aec6c";

        Questao3_2 obj = new Questao3_2();
        obj.inicia(chave, IV);
        String decifrada = obj.decrypt(textoCifradoCBC);
        System.out.println("Mensagem decifrada = " + decifrada);
    }
}
