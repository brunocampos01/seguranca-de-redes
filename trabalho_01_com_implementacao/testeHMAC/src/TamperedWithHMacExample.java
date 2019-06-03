import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;

/**
 * TesteHMAC
 * Tampered message with HMac, encryption AES in CTR mode
 */
public class TamperedWithHMacExample {
    public static void main(String[] args) throws Exception {
        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());

        SecureRandom random = new SecureRandom();
        IvParameterSpec ivSpec = Utils.createCtrIvForAES(1, random);
        Key key = Utils.createKeyForAES(128, random);
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BCFIPS");
        String input = "Transferir 0000100 to AC 1234-5678";
        Mac hMac = Mac.getInstance("HMacSHA256", "BCFIPS");
        Key hMacKey = new SecretKeySpec(key.getEncoded(), "HMacSHA256");


        System.out.println("Texto original : " + input);

        // etapa de cifragem
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] cipherText = new byte[cipher.getOutputSize(input.length() + hMac.getMacLength())];
        int ctLength = cipher.update(Utils.toByteArray(input), 0, input.length(), cipherText, 0);

        hMac.init(hMacKey);
        hMac.update(Utils.toByteArray(input));

        ctLength += cipher.doFinal(hMac.doFinal(), 0, hMac.getMacLength(), cipherText, ctLength);

        // etapa de falsicacao - mudando o texto cifrado!
        cipherText[11] ^= '0' ^ '9';

        // tem troca de hash ?
        // ?
        // etapa de decifragem e verificacao do MAC

        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] plainText = cipher.doFinal(cipherText, 0, ctLength);
        int messageLength = plainText.length - hMac.getMacLength();

        hMac.init(hMacKey);
        hMac.update(plainText, 0, messageLength);

        byte[] messageMac = new byte[hMac.getMacLength()];
        System.arraycopy(plainText, messageLength, messageMac, 0, messageMac.length);

        System.out.println("Texto decifrado: " + Utils.toString(plainText, messageLength));
        System.out.println("Verificado o Mac: " + MessageDigest.isEqual(hMac.doFinal(), messageMac));
    }
}
