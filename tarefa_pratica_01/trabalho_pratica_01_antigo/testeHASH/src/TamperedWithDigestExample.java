import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;

/**
 * TesteHASH
 * Tampered message, encryption with digest, AES in CTR mode
 */
public class TamperedWithDigestExample {
    public static void main(String[] args) throws Exception {
        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());

        SecureRandom random = new SecureRandom();
        IvParameterSpec ivSpec = Utils.createCtrIvForAES(1, random);
        Key key = Utils.createKeyForAES(128, random);
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding", "BCFIPS");
        String input = "Transferir 0000100 para Conta Corrente 1234-5678";
        MessageDigest hash = MessageDigest.getInstance("SHA256", "BCFIPS");

        System.out.println("Texto original : " + input);

        // etapa de cifragem
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] cipherText = new byte[cipher.getOutputSize(input.length() + hash.getDigestLength())];
        int ctLength = cipher.update(Utils.toByteArray(input), 0, input.length(), cipherText, 0);

        // Esse código tinha problema: dois hash.digest...
        // Para consertar, Troquei de update para digest, colocando o valor no array de bytes
        byte[] messageHash1 = hash.digest(Utils.toByteArray(input));

        System.out.println("Hash em hexa: " + Utils.toHex(messageHash1));
        System.out.println("Tamanho do hash em bytes: " + hash.getDigestLength());

        ctLength += cipher.doFinal(messageHash1, 0, hash.getDigestLength(), cipherText, ctLength);


        // etapa de falsificacao/adulteracao (tampering)
        cipherText[11] ^= '0' ^ '9';

        // etapa de decifragem
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] plainText = cipher.doFinal(cipherText, 0, ctLength);
        int messageLength = plainText.length - hash.getDigestLength();
        hash.update(plainText, 0, messageLength);
        byte[] messageHash = new byte[hash.getDigestLength()];

        System.arraycopy(plainText, messageLength, messageHash, 0, messageHash.length);
        System.out.println("Texto decifrado: " + Utils.toString(plainText, messageLength));
        System.out.println("Verificacao do hash: " + MessageDigest.isEqual(hash.digest(), messageHash));
    }
}
