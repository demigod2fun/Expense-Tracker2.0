package service;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

/**
 * Service layer for authentication operations
 */
public class AuthService {
    private UserDAO userDAO = new UserDAO();
    
    public User login(String username, String password) throws Exception {
        User user = userDAO.getUserByUsername(username);
        
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }
    
    public boolean register(String name, String email, String username, String password) throws Exception {
        if (userDAO.userExists(username)) {
            return false;
        }
        
        String passwordHash = PasswordUtil.hashPassword(password);
        User user = new User(name, email, username, passwordHash);
        
        try {
            userDAO.registerUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean changePassword(int userId, String oldPassword, String newPassword) throws Exception {
        User user = userDAO.getUserById(userId);
        
        if (user != null && PasswordUtil.verifyPassword(oldPassword, user.getPasswordHash())) {
            String newHash = PasswordUtil.hashPassword(newPassword);
            user.setPasswordHash(newHash);
            userDAO.updateUser(user);
            return true;
        }
        return false;
    }
}