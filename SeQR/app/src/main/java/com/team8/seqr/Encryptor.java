package com.team8.seqr;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Encryptor {
    private KeyPair keyPair;

    public Encryptor() {
        try {
            generateKeyPair();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateKeyPair() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator gen;
        gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(1024, secureRandom);
        keyPair = gen.genKeyPair();
    }

    public String getPublicKey() {
        byte[] bytePublicKey = keyPair.getPublic().getEncoded();
        String base64PublicKey = Base64.getEncoder().encodeToString(bytePublicKey);
        return base64PublicKey;
    }

    public String getPrivateKey() {
        byte[] bytePrivateKey = keyPair.getPrivate().getEncoded();
        String base64PrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey);
        return base64PrivateKey;
    }

    public String encryptWithPublicKey(String plainText, String publicKeyString)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        PublicKey publicKey = null;
        try {
            byte[] publicBytes = Base64.getDecoder().decode(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //byte[] decodedKey = Base64.getDecoder().decode(key);
        //SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytePlain = cipher.doFinal(plainText.getBytes());
        String encrypted = Base64.getEncoder().encodeToString(bytePlain);
        return encrypted;
    }

    public String decryptWithPrivateKey(String encrypted, String privateKeyString)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

        PrivateKey privateKey = null;
        try {
            byte[] publicBytes = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec rkeySpec = new PKCS8EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(rkeySpec);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytePlain = cipher.doFinal(byteEncrypted);
        String decrypted = new String(bytePlain, "utf-8");
        return decrypted;
    }
}
