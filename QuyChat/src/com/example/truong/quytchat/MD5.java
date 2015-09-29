package com.example.truong.quytchat;

/**
 * Created by Truong on 01/06/2015.
 */
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MD5 {
    public static String encryptMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("----------- Mã hóa 1 chiều với MD5 -----------");
        System.out.println("");
        System.out.println("Dữ liệu mã hóa: truong123");
        System.out.println("Kết quả đã mã hóa :" + encryptMD5("truong123"));
    }
}
