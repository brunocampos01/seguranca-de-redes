import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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
    private SecretKeySpec secretKey;

    public void inicia() throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
        // Instancia o cipher
        cipher = Cipher.getInstance("AES/CTR/NoPadding");
        //cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Gera uma chave AES
//        System.out.print("Gerando chave \t-> ");
//        KeyGenerator sKenGen = KeyGenerator.getInstance("AES");
//        aesKey = sKenGen.generateKey();
//        System.out.println("Chave AES \t= " + Hex.encodeHexString(aesKey.getEncoded()));

        // Chave na String
         String chave1 = "abd95641ecb005d475496cd0bda4555f";
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

         // IV na String
//         String iv1 = "abd95641ecb005d475496cd0bda4555f";
//
//         try { iv = Hex.decodeHex(iv1.toCharArray()); } catch
//         (DecoderException ex) {
//         Logger.getLogger(ProjetoaesEntraDados.class.getName()).log(Level.SEVERE, null, ex); }
//         ivSpec = new IvParameterSpec(iv);
//         System.out.println(ivSpec);

    } // fim inicia

    public String encrypt(String strToEncrypt) {
        try {
            try {
                inicia();
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(ProjetoaesEntraDados.class.getName()).log(Level.SEVERE, null, ex);
            }
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
            //cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            final String encryptedString = Hex.encodeHexString(cipher.doFinal(strToEncrypt.getBytes()));
            return encryptedString;
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidKeyException |
                InvalidAlgorithmParameterException |
                IllegalBlockSizeException |
                BadPaddingException e) {
        }
        return null;
    }

    public String decrypt(String dec) throws  NoSuchAlgorithmException,
                                              NoSuchPaddingException,
                                              NoSuchProviderException {
        try {
            inicia();
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

        } catch (InvalidKeyException |
                InvalidAlgorithmParameterException |
                IllegalBlockSizeException |
                BadPaddingException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String args[]) throws
                                                InvalidKeyException,
                                                InvalidAlgorithmParameterException,
                                                NoSuchAlgorithmException,
                                                NoSuchPaddingException,
                                                NoSuchProviderException {
        ProjetoaesEntraDados obj = new ProjetoaesEntraDados();

//        String paraCifrar;
//
//        Scanner input = new Scanner(System.in);
//        System.out.println("Digite a msg para cifrar: ");
//        paraCifrar = input.nextLine();
//
//        System.out.println("Mensagem original = " + paraCifrar);
//        String cifrada = obj.encrypt(paraCifrar);
//        System.out.println("Mensagem cifrada = " + cifrada);
//        String decifrada = obj.decrypt(cifrada);
//        System.out.println("Mensagem decifrada = " + decifrada);

        String paraDecifrar;

        Scanner input = new Scanner(System.in);
        System.out.println("Digite a msg para Decifrar: ");
        paraDecifrar = input.nextLine();

        String decifrada = obj.decrypt(paraDecifrar);
        System.out.println("Mensagem decifrada = " + decifrada);


    }
}
