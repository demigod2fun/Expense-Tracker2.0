package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseConnection {
    private static final String PROPERTIES_FILE = "resources/config.properties";
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;
    
    static {
        try {
            Properties props = new Properties();
            FileInputStream input = new FileInputStream(PROPERTIES_FILE);
            props.load(input);
            input.close();
            
            DB_URL = props.getProperty("db.url");
            DB_USER = props.getProperty("db.username");
            DB_PASSWORD = props.getProperty("db.password");
            
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
