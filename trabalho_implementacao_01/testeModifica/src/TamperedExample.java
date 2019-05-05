

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider; 

/**
 * TesteModifica
 * Tampered message, plain encryption, AES in CTR mode
 */
public class TamperedExample
{   
    public static void main(
        String[]    args)
        throws Exception
    {
        // Instanciar um novo Security provider
        
        int addProvider = Security.addProvider(new BouncyCastleFipsProvider());
        
        SecureRandom	random = new SecureRandom();
        IvParameterSpec ivSpec = Utils.createCtrIvForAES(1, random);
        Key             key = Utils.createKeyForAES(128, random);
        Cipher          cipher = Cipher.getInstance("AES/CTR/NoPadding", "BCFIPS");
        String          input = "Transferir 0000100 para Conta Corrente 1234-5678";

        System.out.println("Texto plano    : " + input);
        
        // etapa de cifragem
        
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        
        byte[] cipherText = cipher.doFinal(Utils.toByteArray(input));

        // etapa de falsificacao/adulteracao (tampering)
        
        cipherText[11] ^= '0' ^ '9';
        
        // etapa de decifragem
        
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        
        byte[] plainText = cipher.doFinal(cipherText);
        
        System.out.println("Texto decifrado: " + Utils.toString(plainText));
    }
}
