

import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider; // Incluido

/**
 * TesteModificaETrocaHash
 * Tampered message, encryption with digest, AES in CTR mode
 */
public class TamperedDigestExample
{   
    public static void main(
        String[]    args)
        throws Exception
    {
        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());
        
        SecureRandom	random = new SecureRandom();
        IvParameterSpec ivSpec = Utils.createCtrIvForAES(1, random);
        Key             key = Utils.createKeyForAES(128, random);
        Cipher          cipher = Cipher.getInstance("AES/CTR/NoPadding", "BCFIPS");
        String          input = "Transferir 0000100 para Conta Corrente 1234-5678";
        MessageDigest   hash = MessageDigest.getInstance("SHA256", "BCFIPS");
        
        System.out.println("Texto original : " + input);
        
        // etapa de cifragem
        
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        
        byte[] cipherText = new byte[cipher.getOutputSize(input.length() + hash.getDigestLength())];

        int ctLength = cipher.update(Utils.toByteArray(input), 0, input.length(), cipherText, 0);
        
        hash.update(Utils.toByteArray(input));
        
        ctLength += cipher.doFinal(hash.digest(), 0, hash.getDigestLength(), cipherText, ctLength);
        
        // etapa de falsificacao/adulteracao (tampering)
        
        cipherText[11] ^= '0' ^ '9';
        
        // etapa de troca do hash
        
        byte[] originalHash = hash.digest(Utils.toByteArray(input));
        byte[] tamperedHash = hash.digest(Utils.toByteArray("Transferir 9000100 para Conta Corrente 1234-5678"));
        
        for (int i = ctLength - hash.getDigestLength(), j = 0; i != ctLength; i++, j++)
        {
            cipherText[i] ^= originalHash[j] ^ tamperedHash[j];
        }
        
        // etapa de decifragem
        
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        
        byte[] plainText = cipher.doFinal(cipherText, 0, ctLength);
        int    messageLength = plainText.length - hash.getDigestLength();
        
        hash.update(plainText, 0, messageLength);
        
        byte[] messageHash = new byte[hash.getDigestLength()];
        System.arraycopy(plainText, messageLength, messageHash, 0, messageHash.length);
        
        System.out.println("Texto decifrado: " + Utils.toString(plainText, messageLength));
        System.out.println("Verificacao do hash: " + MessageDigest.isEqual(hash.digest(), messageHash));
    }
}
