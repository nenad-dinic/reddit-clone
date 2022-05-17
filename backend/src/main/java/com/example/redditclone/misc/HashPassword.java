package com.example.redditclone.misc;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashPassword {
    public static String createHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] MessageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, MessageDigest);
            String hashPassword = no.toString(16);
            while (hashPassword.length() < 32) {
                hashPassword = "0" + hashPassword;
            }
            return hashPassword;
        } catch (Exception e) {
            return null;
        }
    }
}
