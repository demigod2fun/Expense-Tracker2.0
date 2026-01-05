package listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session listener for tracking active sessions
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    
    private static int activeSessions = 0;
    
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        activeSessions++;
        System.out.println("Session created. Total active sessions: " + activeSessions);
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        activeSessions--;
        System.out.println("Session destroyed. Total active sessions: " + activeSessions);
    }
    
    public static int getActiveSessions() {
        return activeSessions;
    }
}