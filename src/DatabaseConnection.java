import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Method database connection
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/quizzifydb";  // database URL
        String username = "root";  //username
        String password = "";      // password

        try {
            // establish the connection
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            // Error
            System.out.println("Connection failed: " + e.getMessage());
            throw e;
        }
    }
}
