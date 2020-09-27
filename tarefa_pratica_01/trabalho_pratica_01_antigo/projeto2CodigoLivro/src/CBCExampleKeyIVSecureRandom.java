import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Symmetric encryption example with padding and CBC using AES with the
 * initialization vector. Modificado para usar o AES.
 */
public class CBCExampleKeyIVSecureRandom {
    public static void main(String[] args) throws Exception {
        byte[] input = new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

        // Incluido: Instanciar um novo Security provider
        int addProvider = Security.addProvider(new BouncyCastleFipsProvider());

        if (Security.getProvider("BCFIPS") == null) {
            System.out.println("Bouncy Castle provider NAO disponivel");
        } else {
            System.out.println("Bouncy Castle provider esta disponivel");
        }

        // Incluido: Gera uma chave AES
        System.out.print("Gerando chave AES -> ");
        KeyGenerator sKenGen = KeyGenerator.getInstance("AES");
        Key aesKey = sKenGen.generateKey();
        System.out.println("Chave AES = " + Utils.toHex(aesKey.getEncoded()));

        // Incluido: Gerando o iv com SecureRandom

        //SecureRandom random = SecureRandom.getInstanceStrong();
        //System.out.println("Algoritmo no SecureRandom"+java.security.Security.getProperty( "securerandom.strongAlgorithms" ));

        System.out.print("Gerando IV -> ");
        byte iv[] = new byte[16];
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        System.out.println("IV = " + Utils.toHex(iv));


        // Instanciando cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BCFIPS");

        System.out.println("input : " + Utils.toHex(input));

        // encryption pass
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);

        byte[] cipherText = new byte[cipher.getOutputSize(iv.length + input.length)];
        int ctLength = cipher.update(iv, 0, iv.length, cipherText, 0);
        ctLength += cipher.update(input, 0, input.length, cipherText, ctLength);
        ctLength += cipher.doFinal(cipherText, ctLength);

        System.out.println("cipher: " + Utils.toHex(cipherText, ctLength) + " bytes: " + ctLength);

        // decryption pass
        cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);

        byte[] buf = new byte[cipher.getOutputSize(ctLength)];
        int bufLength = cipher.update(cipherText, 0, ctLength, buf, 0);
        bufLength += cipher.doFinal(buf, bufLength);

        // remove the iv from the start of the message
        byte[] plainText = new byte[bufLength - iv.length];

        System.arraycopy(buf, iv.length, plainText, 0, plainText.length);
        System.out.println("plain : " + Utils.toHex(plainText, plainText.length) + " bytes: " + plainText.length);
    }
}
