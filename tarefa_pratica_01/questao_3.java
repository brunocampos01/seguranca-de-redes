
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

//    public void inicia(String Chave, String IV, String texto) throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
//        // Instancia o cipher
//        cipher = Cipher.getInstance("AES/CTR/NoPadding"); // 3.1
//        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // 3.2
//        
//        // Chave na String
//        String chave1 = Chave;
//        try {
//            key = Hex.decodeHex(chave1.toCharArray());
//        } catch (DecoderException ex) {
//            Logger.getLogger(ProjetoaesEntraDados.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        secretKey = new SecretKeySpec(key, "AES");
//         
//        // iv
//        ivSpec = new IvParameterSpec(IV);
//        System.out.println("IV \t= " + Hex.encodeHexString(iv));
//    } // fim inicia

    public String decrypt(String decryptMode, String Chave, String IV, String texto) throws InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            cipher.init(decryptMode, Chave, IV);
            byte[] embytes = {};
            try {
                embytes = Hex.decodeHex(texto.toCharArray());
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

        textoCifradoCBC = "701f7fa45d9bb922c3cb15a519ba40ede1769eb753650886d6e69ebcad9c2816002679896a65a921d25\n" +
            "e00793078474e3dbeca9a2838031c490e5ae9d1ea143f"
        System.out.println("Mensagem cifrada = " + textoCifradoCBC);
        String decifradaCBC = obj.decrypt(
            "AES/CBC/PKCS5Padding",
            '53efb4b1157fccdb9902676329debc52',
            'd161fbaa4c64ecf7d2c4abd885751273',
            textoCifradoCBC);
        System.out.println("Mensagem CTR Decifrada = " + decifradaCBC);
        
        textoCifradoCTR = "36466b5fddcfcb1b8a9479eb8c489e7139a3c35020b1e5ee808b39ff18b6abd812afe7dbbca40e15df391\n" +
            "a7c07ece1c8e10a49368b86a946c8379cd8fa01a47f1956671144b0ca18a4c812cde8f7b9"

        System.out.println("Mensagem cifrada = " + textoCifradoCTR);
        String decifradaCTR = obj.decrypt(
            "AES/CTR/NoPadding",
            "a05e2679204241af07f6857d150a1fcd",
            "468ce1126a37b07138e78eab48344712",
            textoCifradoCTR);
        System.out.println("Mensagem CTR decifrada = " + decifradaCTR);
    }
}
