package member.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashedPassword {

	public static boolean verifyPassword(String password, String hashedPassword) {
        // Compare the hashed password with the hashed user input
        return hashedPassword.equals(hashPassword(password));
    }
	
	public static String hashPassword(String password) {
        // Hash the password using SHA-256 algorithm
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
