//package chapter4;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.Security;

/**
 * Two party key agreement using Diffie-Hellman
 */
public class BasicDHExample {
    private static BigInteger g512 = new BigInteger(
            "153d5d6172adb43045b68ae8e1de1070b6137005686d29d3d73a7"
                    + "749199681ee5b212c9b96bfdcfa5b20cd5e3fd2044895d609cf9b"
                    + "410b7a0f12ca1cb9a428cc", 16);
    private static BigInteger p512 = new BigInteger(
            "9494fec095f3b85ee286542b3836fc81a5dd0a0349b4c239dd387"
                    + "44d488cf8e31db8bcb7d33b41abb9e5a33cca9144b1cef332c94b"
                    + "f0573bf047a3aca98cdf3b", 16);

    public static void main(
            String[] args)
            throws Exception {

        // Instanciar um novo Security provider
        int addProvider;
        addProvider = Security.addProvider(new BouncyCastleFipsProvider());
        DHParameterSpec dhParams = new DHParameterSpec(p512, g512);
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH", "BCFIPS");
        keyGen.initialize(dhParams, Utils4.createFixedRandom());

        // set up
        KeyAgreement aKeyAgree = KeyAgreement.getInstance("DH", "BCFIPS");
        KeyPair aPair = keyGen.generateKeyPair();
        KeyAgreement bKeyAgree = KeyAgreement.getInstance("DH", "BCFIPS");
        KeyPair bPair = keyGen.generateKeyPair();

        // two party agreement
        aKeyAgree.init(aPair.getPrivate());
        bKeyAgree.init(bPair.getPrivate());

        aKeyAgree.doPhase(bPair.getPublic(), true);
        bKeyAgree.doPhase(aPair.getPublic(), true);

        // escrevendo na tela os elemento do DH
        System.out.println("Chave privada de a: " + aPair.getPrivate());
        System.out.println("Chave pública de a: " + aPair.getPublic());

        // escrevendo na tela os elemento do DH
        System.out.println("Chave privada de b: " + bPair.getPrivate());
        System.out.println("Chave pública de b: " + bPair.getPublic());


        // gera os bytes da chave compartilhada - mostra em hexa e como hashes

        byte[] aShared = aKeyAgree.generateSecret();
        byte[] bShared = bKeyAgree.generateSecret();
        System.out.println(Utils4.toHex(aShared));
        System.out.println(Utils4.toHex(bShared));


        MessageDigest hash = MessageDigest.getInstance("SHA256", "BCFIPS");
        System.out.println(Utils4.toHex(hash.digest(aShared)));
        System.out.println(Utils4.toHex(hash.digest(bShared)));
    }
}
