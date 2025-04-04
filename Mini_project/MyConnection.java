 
package mini_project;

/**
 *
 * @author ACER
 */
import java.sql.*;

public class MyConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/tecmis";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        Connection conn = null; // Initialize the connection variable
        try {
            // Attempt to establish the connection
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // Handle any SQL exceptions that may occur
            e.printStackTrace();
             
            throw new RuntimeException("Failed to connect to the database.");
        }
        // Return the established connection
        return conn;
    }
}
