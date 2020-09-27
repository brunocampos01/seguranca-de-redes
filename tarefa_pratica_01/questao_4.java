/*
Crie um programa que recebe duas strings pelo teclado, calcula o hash (resumo criptográfico) e o MAC
de cada uma das strings escrevendo o resultado na tela. Teste e explique o funcionamento do
programa com entrada de strings iguais e depois com entrada de strings diferentes.
 */

import java.security.*;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class TamperedWithDigestExample
{
    public void calculaHash(String input) throws NoSuchProviderException,
                                                 NoSuchAlgorithmException,
                                                 NoSuchPaddingException,
                                                 InvalidAlgorithmParameterException,
                                                 InvalidKeyException,
                                                 ShortBufferException,
                                                 BadPaddingException,
                                                 IllegalBlockSizeException {
        SecureRandom	random = new SecureRandom();
        IvParameterSpec ivSpec = Utils.createCtrIvForAES(1, random);
        Key             key = Utils.createKeyForAES(128, random);
        Cipher          cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        MessageDigest   hash = MessageDigest.getInstance("SHA-256");


        System.out.println("Texto original : " + input);

        // etapa de cifragem
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] messageHash1 = hash.digest(Utils.toByteArray(input));

        System.out.println("Hash: "+ messageHash1);
        System.out.println("Tamanho do hash em bytes: "+hash.getDigestLength());
    }

    public void calculaMAC(String input) throws NoSuchProviderException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, ShortBufferException, IllegalBlockSizeException {
        SecureRandom	random = new SecureRandom();
        IvParameterSpec ivSpec = Utils.createCtrIvForAES(1, random);
        Key             key = Utils.createKeyForAES(128, random);
        Cipher          cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Mac             hMac = Mac.getInstance("HMacSHA256");
        Key             hMacKey = new SecretKeySpec(key.getEncoded(), "HMacSHA256");

        System.out.println("Texto original : " + input);

        // etapa de cifragem
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] cipherText = new byte[cipher.getOutputSize(input.length() + hMac.getMacLength())];

        hMac.init(hMacKey);
        byte[] messageMac = new byte[hMac.getMacLength()];

        System.out.println("Mac: " + messageMac);
    }

    public static void main(String[] args) throws Exception
    {
        TamperedWithDigestExample obj = new TamperedWithDigestExample();
        String primeira_msg;

        Scanner input = new Scanner(System.in);
        System.out.println("Digite a primeira msg: ");
        primeira_msg = input.nextLine();

        obj.calculaHash(primeira_msg);
        obj.calculaMAC(primeira_msg);


        System.out.println("Digite a segunda msg: ");
        primeira_msg = input.nextLine();

        obj.calculaHash(primeira_msg);
        obj.calculaMAC(primeira_msg);

    }
}
