
/**
 * Código adaptado de várias fontes:
 * https://github.com/bcgit/bc-java/blob/master/prov/src/test/java/org/bouncycastle/jce/provider/test/AESTest.java
 * GCMTest.java da BouncyCastle
 * Versão Agosto 2017
 */
import java.security.Key;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;

import org.bouncycastle.jce.provider.BouncyCastleProvider; // Incluido
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

//import org.apache.commons.codec.DecoderException;
//import org.apache.commons.codec.binary.Hex;
public class AESTestGCM {

    private static final int MAC_SIZE = 128;

    public AESTestGCM() {
    }

    protected boolean areEqual(
            byte[] a,
            byte[] b) {
        return Arrays.areEqual(a, b);
    }

    protected void fail(
            String message
    ) {
        throw new RuntimeException(message);
    }

    // Teste do codigo original do site
    private void gcmTest()
            throws Exception {

        // Test Case 15 from McGrew/Viega
        //  chave (K)
        String pK = "feffe9928665731c6d6a8f9467308308feffe9928665731c6d6a8f9467308308";
        byte[] K = org.apache.commons.codec.binary.Hex.decodeHex(pK.toCharArray());

        //  texto plano (P)
        String pP;
        pP = "d9313225f88406e5a55909c5aff5269a"
                + "86a7a9531534f7da2e4c303d8a318a72"
                + "1c3c0c95956809532fcf0e2449a6b525"
                + "b16aedf5aa0de657ba637b391aafd255";
        byte[] P = org.apache.commons.codec.binary.Hex.decodeHex(pP.toCharArray());

        //  nonce (IV)
        String pN;
        pN = "cafebabefacedbaddecaf888";
        byte[] N = org.apache.commons.codec.binary.Hex.decodeHex(pN.toCharArray());

        //  tag (T)
        String T = "b094dac5d93471bdec1a502270e3cc6c";

        //  texto cifrado (C)
        String pC;
        pC = "522dc1f099567d07f47f37a32a84427d"
                + "643a8cdcbfe5c0c97598a2bd2555d1aa"
                + "8cb08e48590dbb3da7b08b1056828838"
                + "c5f61e6393ba7a0abcc9f662898015ad"
                + T;
        byte[] C = org.apache.commons.codec.binary.Hex.decodeHex(pC.toCharArray());

        Key key;
        Cipher in, out;

        in = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        out = Cipher.getInstance("AES/GCM/NoPadding", "BC");

        key = new SecretKeySpec(K, "AES");

        in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(N));

        byte[] enc = in.doFinal(P);

        if (!areEqual(enc, C)) {
            fail("ciphertext doesn't match in GCM");
        }

        out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(N));

        byte[] dec = out.doFinal(C);

        if (!areEqual(dec, P)) {
            fail("plaintext doesn't match in GCM");
        }

    }

    // Teste usando GCMBlockCipher da BouncyCastle
    private void gcmTestWithGCMBlockCipherBC()
            throws Exception {

        // Aproveitando alguns dados do Test Case 15 from McGrew/Viega
        //  chave (K)
        String pK = "feffe9928665731c6d6a8f9467308308feffe9928665731c6d6a8f9467308308";
        byte[] K = org.apache.commons.codec.binary.Hex.decodeHex(pK.toCharArray());

        //  texto plano (P)
        byte[] P;

        /**
         * String pP; pP = "d9313225f88406e5a55909c5aff5269a" +
         * "86a7a9531534f7da2e4c303d8a318a72" +
         * "1c3c0c95956809532fcf0e2449a6b525" +
         * "b16aedf5aa0de657ba637b391aafd255";
         */
        //= org.apache.commons.codec.binary.Hex.decodeHex(pP.toCharArray());
        //  nonce (IV)
        String pN;
        pN = "cafebabefacedbaddecaf888";
        byte[] N = org.apache.commons.codec.binary.Hex.decodeHex(pN.toCharArray());

        //  tag (T)
        String T;
        //= "b094dac5d93471bdec1a502270e3cc6c";

        //  texto cifrado (C)
        byte[] C;

        /**
         * String pC; pC = "522dc1f099567d07f47f37a32a84427d" +
         * "643a8cdcbfe5c0c97598a2bd2555d1aa" +
         * "8cb08e48590dbb3da7b08b1056828838" +
         * "c5f61e6393ba7a0abcc9f662898015ad" + T;
         */
        //= org.apache.commons.codec.binary.Hex.decodeHex(pC.toCharArray());
        // Mensagem de entrada        
        String input = "Transferir 0000100 para CC 1234-5678";
        P = input.getBytes();

        System.out.println("Msg = " + input);

        // CIFRAR criando GCMBlockCipher
        // Instancia um GCM com AES usando o formato da BouncyCastle
        GCMBlockCipher gcm = new GCMBlockCipher(new AESEngine());

        // Parametros a serem passados para o GCM: chave, tamanho do mac, o nonce
        KeyParameter key2 = new KeyParameter(K);
        AEADParameters params = new AEADParameters(key2, MAC_SIZE, N);

        // true para cifrar
        gcm.init(true, params);
        int outsize = gcm.getOutputSize(P.length);
        byte[] outc = new byte[outsize];
        //processa os bytes calculando o offset para cifrar
        int lengthOutc = gcm.processBytes(P, 0, P.length, outc, 0);

        try {
            //cifra os bytes
            gcm.doFinal(outc, lengthOutc);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }

        System.out.println("Msg cifrada = " + Hex.toHexString(outc));

        // Recupera tag do GCM
        byte[] encT1 = gcm.getMac();
        //System.out.println("Tag msg cifrada = " + Hex.toHexString(encT1));

        // tampering step - mudando o texto cifrado para ver se eh detectado!        
        // A msg cifrada FOI MODIFICADA
        outc[11] ^= '0' ^ '9';

        // DECIFRAR usando GCMBlockCipher
        // false para decifrar
        gcm.init(false, params);

        int outsize2 = gcm.getOutputSize(outc.length);
        byte[] out2 = new byte[outsize2];
        int offOut2 = gcm.processBytes(outc, 0, outc.length, out2, 0);        
    
        try {  
            gcm.doFinal(out2, offOut2);           
            String decifrado = new String(out2);
            System.out.println("Msg decifrada = \t\t" + decifrado);
            byte[] encT2 = gcm.getMac();
            System.out.println("Tag msg cifrada modificada = \t" + Hex.toHexString(encT2));

        } catch (InvalidCipherTextException e) {
            System.err.println("Erro de decifragem: " + e.getMessage());
            //e.printStackTrace();
        }        

    }

    public static void main(
            String[] args) {
        System.out.println("Teste de GCM com AES");
        int addProvider1 = Security.addProvider(new BouncyCastleProvider());
        //int addProvider2 = Security.addProvider(new BouncyCastleFipsProvider());
        AESTestGCM obj = new AESTestGCM();
        try {
            // Teste do código original do site
            obj.gcmTest();

            // Teste usando o GCMBlockCipher da BouncyCastle
            obj.gcmTestWithGCMBlockCipherBC();

        } catch (Exception ex) {
            Logger.getLogger(AESTestGCM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
