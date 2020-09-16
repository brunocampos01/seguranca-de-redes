
/**
 *
 * Nesse projeto você irá programar dois sistemas de decifragem, um usando o AES em modo CBC e outro
 * usando o AES no modo contador (counter mode – CTR). Em ambos os casos um IV de 16 bytes é
 * escolhido de forma aleatória. Para o modo CBC use o esquema de padding PKCS5. Para o modo CTR use
 * NoPadding.
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
        cipher = Cipher.getInstance("AES/CTR/NoPadding"); // 3.1
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // 3.2
       
          // Gera uma chave AES
//        System.out.print("Gerando chave \t-> "); 
//        KeyGenerator sKenGen = KeyGenerator.getInstance("AES"); 
//        aesKey = sKenGen.generateKey();
//        System.out.println("Chave AES \t= " + Hex.encodeHexString(aesKey.getEncoded()));
//        
        // Chave na String
        String chave1 = "53efb4b1157fccdb9902676329debc52";
        try {
            key = Hex.decodeHex(chave1.toCharArray());
        } catch (DecoderException ex) {
            Logger.getLogger(ProjetoaesEntraDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        secretKey = new SecretKeySpec(key, "AES");
         
        // Gerando o iv com SecureRandom
        System.out.print("Gerando IV \t-> "); 
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        iv = new byte[16];
        random.nextBytes(iv);
        ivSpec = new IvParameterSpec(iv);
        System.out.println("IV \t= " + Hex.encodeHexString(iv));
        
        /**
        // IV na String
         String iv1 = "2e4d285ae4837d9c746fc36a18dc2758";
         
          try { iv = Hex.decodeHex(iv1.toCharArray()); } catch
          (DecoderException ex) {
          Logger.getLogger(ProjetoaesEntraDados.class.getName()).log(Level.SEVERE,
          null, ex); } ivSpec = new IvParameterSpec(iv);
         */
    } // fim inicia

    public String decrypt(String dec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        try {

            cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
            //cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
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

    public static void main(String args[]) throws InvalidKeyException, InvalidAlgorithmParameterException {
        ProjetoaesEntraDados obj = new ProjetoaesEntraDados();

        String paraCifrar;

        Scanner input = new Scanner(System.in);
        System.out.println("Digite a msg para cifrar: ");
        paraCifrar = input.nextLine();

        System.out.println("Mensagem original = " + paraCifrar);
        String cifrada = obj.encrypt(paraCifrar);
        System.out.println("Mensagem cifrada = " + cifrada);
        String decifrada = obj.decrypt(cifrada);
        System.out.println("Mensagem decifrada = " + decifrada);
    }
}
