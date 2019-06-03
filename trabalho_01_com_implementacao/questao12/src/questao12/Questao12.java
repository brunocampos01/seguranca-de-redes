/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questao12;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author ccc
 */
public class Questao12 {
    
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    private static String digits = "0123456789abcdef";
    
    public static String toHex(byte[] data, int length) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i != length; i++) {
            int v = data[i] & 0xff;

            buf.append(digits.charAt(v >> 4));
            buf.append(digits.charAt(v & 0xf));
        }

        return buf.toString();
    }
    
    public static String toHex(byte[] data) {
        return toHex(data, data.length);
    }
    
    public static byte[] generateIv() throws NoSuchAlgorithmException, NoSuchProviderException {
        byte iv[] = new byte[16];
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        random.nextBytes(iv);
        
        return iv;
    }
    
    public static String generateDerivedKey(
            String password, String salt, Integer iterations) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, 128);
        SecretKeyFactory pbkdf2 = null;
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

    /*Usado para gerar o salt  */
    public static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        //SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    public static String aliceEnvia() throws NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        Security.addProvider(new BouncyCastleProvider());
        
        String senha;
        String msg;
        String msgCifrada;
        String saltChave;
        int it = 10000;

        Scanner input = new Scanner(System.in);
        System.out.println("Digite a senha: ");
        senha = input.nextLine();
        
        System.out.println("Digite a mensagem: ");
        msg = input.nextLine();
        
        saltChave = getSalt();

        String chaveDerivada = generateDerivedKey(senha, saltChave, it);
//        String iv = generateDerivedKey(senha, saltIv, it);
        byte[] iv = generateIv();
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        System.out.println("chave derivada: " + toHex(chaveDerivada.getBytes()));
        System.out.println("iv: " + toHex(iv));
        System.out.println("Mensagem original: " + msg);
        
//        KeyStore ks = KeyStore.getInstance("BCFKS", "BCFIPS");
        KeyStore ks = KeyStore.getInstance("BCFKS");
        
        ks.load(null, null);
        
        ks.store(new FileOutputStream("meukeystore.bcfks"), senha.toCharArray());
        
        ks.load(new FileInputStream("meukeystore.bcfks"), senha.toCharArray());
        
        byte[] keyBytes = chaveDerivada.getBytes();
        byte[] msgBytes = msg.getBytes();
        
        
        ks.setKeyEntry("chave", new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES"), senha.toCharArray(), null);
        ks.setKeyEntry("iv", new SecretKeySpec(iv, 0, iv.length, "AES"), senha.toCharArray(), null);
        
        ks.store(new FileOutputStream("meukeystore.bcfks"), senha.toCharArray());
        
        Key key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        
        byte[] cipherText = new byte[cipher.getOutputSize(iv.length + msgBytes.length)];

        int ctLength = cipher.update(iv, 0, iv.length, cipherText, 0);

        ctLength += cipher.update(msgBytes, 0, msgBytes.length, cipherText, ctLength);

        ctLength += cipher.doFinal(cipherText, ctLength);

        msgCifrada=toHex(cipherText, ctLength);
        
        
//        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
//
//        byte[] buf = new byte[cipher.getOutputSize(ctLength)];
//
//        int bufLength = cipher.update(cipherText, 0, ctLength, buf, 0);
//
//        bufLength += cipher.doFinal(buf, bufLength);
//
//        // remove the iv from the start of the message
//        byte[] plainText = new byte[bufLength - iv.length];
//
//        System.arraycopy(buf, iv.length, plainText, 0, plainText.length);
//
//        
//        System.out.println("Mensagem original reconstruída (sem keystore): " + new String(plainText));
        
        return msgCifrada;
    }
    
    public static String bobRecebe(String msgCifrada) throws NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, UnrecoverableKeyException, DecoderException {
        Security.addProvider(new BouncyCastleProvider());
        
        String senha;
        byte[] msgCifradaBytes = Hex.decodeHex(msgCifrada);

        Scanner input = new Scanner(System.in);
        System.out.println("Digite a senha: ");
        senha = input.nextLine();
        
        KeyStore ks = KeyStore.getInstance("BCFKS");
        ks.load(new FileInputStream("meukeystore.bcfks"), senha.toCharArray());
                
        SecretKey key = (SecretKey) ks.getKey("chave", senha.toCharArray());
        SecretKey ivKey = (SecretKey) ks.getKey("iv", senha.toCharArray());
        
        byte[] iv = ivKey.getEncoded();
        byte[] keyBytes = key.getEncoded();
        
//        System.out.println("chave (do keystore): " + Hex.encodeHexString(keyBytes));
//        System.out.println("iv (do keystore): " + Hex.encodeHexString(iv));

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] buf = new byte[cipher.getOutputSize(msgCifradaBytes.length)];

        int bufLength = cipher.update(msgCifradaBytes, 0, msgCifradaBytes.length, buf, 0);

        bufLength += cipher.doFinal(buf, bufLength);

        // remove the iv from the start of the message
        byte[] plainText = new byte[bufLength - iv.length];

        System.arraycopy(buf, iv.length, plainText, 0, plainText.length);
        
        return new String(plainText);
        
    }
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        String msgCifrada = aliceEnvia();
        String msgDecifrada = bobRecebe(msgCifrada);
        System.out.println("Mensagem original reconstruída: " + msgDecifrada);
    }
    
}
