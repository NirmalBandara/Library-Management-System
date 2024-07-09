import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberDAO {
    private Connection connection; // Database connection

    // Constructor to initialize the database connection
    public MemberDAO() {
        initializeConnection();
    }

    // Method to initialize the database connection
    private void initializeConnection() {
        try {
            connection = DatabaseConnection.getConnection(); // Use DatabaseConnection class to get connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a member to the database
    public void addMember(String name, String email, String phone) throws SQLException {
        String query = "INSERT INTO members (name, email, phone) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.executeUpdate();
        }
    }
}
