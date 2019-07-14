//package chapter4;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider; // Inclui;

/**
 * RSA example with OAEP Padding and random key generation.
 * Modificado por Carla - Maio 2018
 */
public class OAEPPaddedRSAExample
{
    public static void main(
        String[]    args)
        throws Exception
    {
        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());
        
        byte[]           input = new byte[] { 0x00, (byte)0xbe, (byte)0xef };
 
        Cipher	         cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding", "BCFIPS");
        SecureRandom     random = Utils4.createFixedRandom();
        
        // create the keys
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BCFIPS");

        //generator.initialize(386, random);

        KeyPair          pair = generator.generateKeyPair();
        Key              pubKey = pair.getPublic();
        Key              privKey = pair.getPrivate();

        System.out.println("input : " + Utils4.toHex(input));
        
        // encryption step
        
        cipher.init(Cipher.ENCRYPT_MODE, pubKey, random);

        byte[] cipherText = cipher.doFinal(input);

        System.out.println("cipher: " + Utils4.toHex(cipherText));
        
        // decryption step

        cipher.init(Cipher.DECRYPT_MODE, privKey);

        byte[] plainText = cipher.doFinal(cipherText);
        
        System.out.println("plain : " + Utils4.toHex(plainText));
        
        
        // Agora usando certificados digitais...
        // Criar chaves assimétricas
        // Chaves da CA
        KeyPair caKeyPair = generator.generateKeyPair();
        // Chaves de uma entidade assinada pela CA
        KeyPair eeKeyPair = generator.generateKeyPair();
      
        // Faz certificado da CA
        X509Certificate caCert = Cert.makeV1RsaCertificate(caKeyPair.getPrivate(), caKeyPair.getPublic());
        // Faz certificado da entidade
        X509Certificate eeCert = Cert.makeV3Certificate(caCert, caKeyPair.getPrivate(), eeKeyPair.getPublic());

        System.out.println("Criou certificados... ");
        
        System.out.println("CERTIFICADO da CA");        
        System.out.println(caCert.toString());
        
        System.out.println("CERTIFICADO da Entidade");        
        System.out.println(eeCert.toString());
        
        PublicKey pubkey = eeCert.getPublicKey();
        System.out.println("pub key da entidade = "+ pubkey.toString());
        
        
        // encryption step using certificate      
           
        cipher.init(Cipher.ENCRYPT_MODE, eeCert);
      
        cipherText = cipher.doFinal(input);

        System.out.println("encryption with certificate: " + Utils4.toHex(cipherText));
          
        // decryption step using private key

        cipher.init(Cipher.DECRYPT_MODE, eeKeyPair.getPrivate());

        plainText = cipher.doFinal(cipherText);
        
        System.out.println("decryption with private key : " + Utils4.toHex(plainText));
        
    }
}