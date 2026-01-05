package util;

import java.util.regex.Pattern;

public class ValidationUtil {
  
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && username.length() <= 50;
    }
    
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
    
    public static boolean isValidAmount(String amount) {
        try {
            double amt = Double.parseDouble(amount);
            return amt > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}