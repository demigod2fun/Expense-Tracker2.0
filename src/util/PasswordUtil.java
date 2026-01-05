package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
            // ...existing code...
public static String getPassword() {
    return getPassword("Password: ");
}

public static String getPassword(String prompt) {
    java.io.Console console = System.console();
    if (console != null) {
        char[] pwd = console.readPassword("%s", prompt);
        if (pwd == null) return null;
        String result = new String(pwd);
        java.util.Arrays.fill(pwd, '\0'); // clear chars from memory
        return result;
    } else {
        System.out.print(prompt);
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(System.in))) {
            return br.readLine();
        } catch (java.io.IOException e) {
            return null;
        }
    }
}
// ...existing code...
// ...existing code...
public static boolean verifyPassword(String password, String stored) {
    if (password == null || stored == null) return false;
    try {
        String[] parts = stored.split(":");
        if (parts.length != 4) return false; // expected format algorithm:iterations:salt:hash

        String algorithm = parts[0];
        int iterations = Integer.parseInt(parts[1]);
        byte[] salt = java.util.Base64.getDecoder().decode(parts[2]);
        byte[] expectedHash = java.util.Base64.getDecoder().decode(parts[3]);

        javax.crypto.SecretKeyFactory skf = javax.crypto.SecretKeyFactory.getInstance(algorithm);
        javax.crypto.spec.PBEKeySpec spec = new javax.crypto.spec.PBEKeySpec(
                password.toCharArray(), salt, iterations, expectedHash.length * 8);
        byte[] actualHash = skf.generateSecret(spec).getEncoded();

        // constant-time comparison
        if (actualHash.length != expectedHash.length) return false;
        int diff = 0;
        for (int i = 0; i < actualHash.length; i++) {
            diff |= actualHash[i] ^ expectedHash[i];
        }
        return diff == 0;
    } catch (Exception e) {
        return false;
    }
}


    
    // Hash password using MD5 (for demo - use BCrypt in production)
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
