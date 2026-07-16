import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=CoursesPlatformDB";
    private static final String USER = "Sa";
    private static final String PASSWORD = "2004";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("SQL Server JDBC Driver not found.");
            e.printStackTrace();
            return null;
        }
    }
}
