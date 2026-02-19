package com.gestionpaie.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordUtils {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyPassword(String password, String hash) {
        if (password == null || hash == null) return false;
        return hashPassword(password).equals(hash);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
/*package com.gestionpaie.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordUtils {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyPassword(String password, String hash) {
        if (password == null || hash == null) return false;
        return hashPassword(password).equals(hash);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}*/
