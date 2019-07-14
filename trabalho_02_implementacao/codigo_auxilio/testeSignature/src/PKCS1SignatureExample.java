//package chapter4;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.util.Scanner;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider; // Inclui;
/**
 * Generating a PKCS1 v1.5 style signature.
 */
public class PKCS1SignatureExample
{
    public static void main(
        String[]    args)
        throws Exception
    {
        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());
        
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BCFIPS");
        
        keyGen.initialize(512, new SecureRandom());
        
        KeyPair             keyPair = keyGen.generateKeyPair();
        Signature           signature = Signature.getInstance("SHA256withRSA", "BCFIPS");

        String paraAssinar; 
        Scanner input = new Scanner(System.in);
        System.out.println("Digite a msg: ");
        paraAssinar = input.nextLine();
        
        // generate a signature
        signature.initSign(keyPair.getPrivate(), Utils4.createFixedRandom());

        byte[] message = new byte[] { (byte)'a', (byte)'b', (byte)'c' };

        signature.update(paraAssinar.getBytes());
        //signature.update(message);
        byte[]  sigBytes = signature.sign();
        
        // verify a signature
        signature.initVerify(keyPair.getPublic());

        signature.update(paraAssinar.getBytes());

        if (signature.verify(sigBytes))
        {
            System.out.println("signature verification succeeded.");
        }
        else
        {
            System.out.println("signature verification failed.");
        }
    }
}
