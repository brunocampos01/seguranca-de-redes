package testekeystore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.crypto.KeyGenerator;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.fips.FipsDRBG;
import org.bouncycastle.crypto.util.BasicEntropySourceProvider;
import org.bouncycastle.operator.OperatorCreationException;
import java.security.cert.Certificate;

/**
 * Código para demonstrar o uso do keystore padrão FIPS da BouncyCastle Esse
 * keystore guarda chaves secretas (simétricas), certificados de chaves públicas
 * e chaves privadas
 *
 * @author Carla
 * @version 3.0 - maio de 2018
 */
public class KeyStrAdap {

    static KeyStore keyStore;
    
    public KeyStrAdap(String storeFilename, char[] storePassword) 
            throws FileNotFoundException, IOException, KeyStoreException, 
            NoSuchProviderException, NoSuchAlgorithmException, CertificateException {
        keyStore = KeyStore.getInstance("BCFKS", "BCFIPS");
        File arquivo = new File(storeFilename);
        if (!arquivo.exists()) {
            // Cria do zero o keystore
            keyStore.load(null, null);
            // Cria o arquivo e armazena a senha mestre
            keyStore.store(new FileOutputStream(storeFilename), storePassword);
        } else {
            // Carrega keystore que já existe usando a senha mestre do keystore 
            keyStore.load(new FileInputStream(storeFilename), storePassword);
        }
    }

    public void storeSecretKey(String storeFilename, char[] storePassword, String alias, 
            char[] keyPass, Key secretKey)
            throws GeneralSecurityException, IOException {    

        keyStore.setKeyEntry(alias, secretKey, keyPass, null);
        keyStore.store(new FileOutputStream(storeFilename), storePassword);
    }

    public void storeCertificate(String storeFilename, char[] storePassword, String alias, 
            X509Certificate trustedCert)
            throws GeneralSecurityException, IOException {
        keyStore.setCertificateEntry(alias, trustedCert);
        keyStore.store(new FileOutputStream(storeFilename), storePassword);

    }

    public  void storePrivateKey(String storeFilename, char[] storePassword, String alias, char[] keyPass, PrivateKey eeKey, X509Certificate[] eeCertChain)
            throws GeneralSecurityException, IOException {
        keyStore.setKeyEntry(alias, eeKey, keyPass, eeCertChain);
        keyStore.store(new FileOutputStream(storeFilename), storePassword);
    }

    private void printKeyStore(String storeFilename, char[] storePassword) throws NoSuchProviderException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        
        System.out.println("KeyStore type: " + keyStore.getType());
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String elem = aliases.nextElement();
            if (keyStore.isKeyEntry(elem)) {
                System.out.println("Chave = " + elem);
            } else if (keyStore.isCertificateEntry(elem)) {
                System.out.println("Certificado = " + elem);
                Certificate cert = keyStore.getCertificate(elem);
                System.out.println("Chave publica guardada no certificado:" + cert.getPublicKey());
                System.out.println("Tipo do certificado:" + cert.getType());
            }
        }

        //Provider prov = keyStore.getProvider();
        //System.out.println("Provider detais "+prov.getServices());
    }

    public static void main(String[] args)
            throws GeneralSecurityException, IOException, OperatorCreationException {

        // Install Provider FIPS
        Security.addProvider(new BouncyCastleFipsProvider());

        // Adicionado para resolver problema da lentidao no Linux - Sugerido por Marcio Sagaz
        CryptoServicesRegistrar.setSecureRandom(FipsDRBG.SHA512_HMAC
                .fromEntropySource(new BasicEntropySourceProvider(new SecureRandom(), true)).build(null, false));

        // Escrevendo os providers do ambiente
        // Conferir se aparece o provider BCFIPS
        
        /**
         * Provider[] providers = Security.getProviders(); for (Provider p :
         * providers) { System.out.println("Provider Name : " + p.getName());
         * System.out.println("Provider Information : " + p.getInfo());
         * System.out.println("Provider Version : " + p.getVersion());
         *
         * System.out.println("-------------------------------"); }
         */
      
              
        // Criar o keystore no diretorio atual
        KeyStrAdap obj;
        char[] storePass = "password".toCharArray();
        String keystoreFilename = "meukeystore.bcfks";
        obj = new KeyStrAdap(keystoreFilename, storePass);

        // Armazena duas chaves secretas
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        Key key1 = keyGenerator.generateKey();
        Key key2 = keyGenerator.generateKey();
        
        obj.storeSecretKey(keystoreFilename, "senhamestre".toCharArray(), "aeskey1", 
                "password1".toCharArray(), key1);
        obj.storeSecretKey(keystoreFilename, "senhamestre".toCharArray(), "aeskey2", 
                "password2".toCharArray(), key2);
        

        // Recupera chaves do keystore e imprime as chaves na tela 
        SecretKey sk1 = (SecretKey) keyStore.getKey("aeskey1", "password1".toCharArray());
        System.out.println("Chave aeskey1 = " + Hex.encodeHexString(sk1.getEncoded()));

        SecretKey sk2 = (SecretKey) keyStore.getKey("aeskey2", "password2".toCharArray());
        //System.out.println(new BigInteger(1, sk2.getEncoded()).toString(16));
        System.out.println("Chave aeskey2 = " + Hex.encodeHexString(sk2.getEncoded()));


        // Armazena mais uma chave no keystore         
        char[] pass3 = "password3".toCharArray();     
        obj.storeSecretKey(keystoreFilename, storePass, "aeskey3", pass3, ExValues.SampleAesKey);
        

        // Criar chaves assimétricas
        // Chaves da CA
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BCFIPS");
        KeyPair caKeyPair = generator.generateKeyPair();
        // Chaves de uma entidade assinada pela CA
        KeyPair eeKeyPair = generator.generateKeyPair();

        // Faz certificado da CA
        X509Certificate caCert = Cert.makeV1RsaCertificate(caKeyPair.getPrivate(), caKeyPair.getPublic());
        // Faz certificado da entidade
        X509Certificate eeCert = Cert.makeV3Certificate(caCert, caKeyPair.getPrivate(), eeKeyPair.getPublic());

        // Armazena certificado da CA no keystore
        obj.storeCertificate(keystoreFilename, storePass, "trustedca certificate", caCert);

        // Armazena certificado da entidade no keystore
        obj.storeCertificate(keystoreFilename, storePass, "certificate", eeCert);

        // Armazena chave privada da entidade no keystore
        obj.storePrivateKey(keystoreFilename, storePass, "private key", "privatePassword".toCharArray(), 
                eeKeyPair.getPrivate(), new X509Certificate[]{eeCert});

        obj.printKeyStore(keystoreFilename, storePass);

    }
}
