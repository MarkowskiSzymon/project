package com.example.project.Utils;

import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password_hash {


    public String tryHash(String password){
        MessageDigest digest = null;
        String hash = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            hash = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            Log.e("App", "Error: " + e);
        }
        return hash;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);

        }
        return sb.toString();
    }
}
