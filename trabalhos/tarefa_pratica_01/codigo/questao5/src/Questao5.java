import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Questao5 {

    private Key aesKey;
    private byte[] key;
    private byte iv[];
    private IvParameterSpec ivSpec;
    private Cipher cipher;
    private SecretKeySpec secretKey;

    public void inicia() throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
        cipher = Cipher.getInstance("AES/GCM/NoPadding");
       
        System.out.print("Gerando chave \t-> "); 
        KeyGenerator sKenGen = KeyGenerator.getInstance("AES"); 
        aesKey = sKenGen.generateKey();
        System.out.println("Chave AES \t= " + Hex.encodeHexString(aesKey.getEncoded()));

        String senha = "123456789";
        String salt;
        int it = 10000;

        salt = getSalt();
        System.out.println("chave usando PBKDF2: " + generateDerivedKey(senha, salt, it));
        
    } // fim inicia

    public static String generateDerivedKey(String password, String salt, Integer iterations) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, 128);
        SecretKeyFactory pbkdf2;
        String derivedPass = null;
        try {
            pbkdf2 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            SecretKey sk = pbkdf2.generateSecret(spec);
            derivedPass = Hex.encodeHexString(sk.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return derivedPass;
    }

    public String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }
    

    public String encrypt(String strToEncrypt) {
        try {
            try {
                inicia();
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(Questao5.class.getName()).log(Level.SEVERE, null, ex);
            }
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
            //cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            return Hex.encodeHexString(cipher.doFinal(strToEncrypt.getBytes()));
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
        Questao5 obj = new Questao5();

        String paraCifrar;

        Scanner input = new Scanner(System.in);
        System.out.println("Digite a msg para cifrar: ");
        paraCifrar = input.nextLine();

        System.out.println("Mensagem original = " + paraCifrar);
        String cifrada = obj.encrypt(paraCifrar);
        System.out.println("Mensagem cifrada = " + cifrada);
    }
}
