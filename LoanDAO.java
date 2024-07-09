import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoanDAO {
    private Connection connection; // Database connection

    // Constructor to initialize the database connection
    public LoanDAO() {
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

    // Method to loan a book to a member
    public boolean loanBook(int bookId, int memberId, LocalDate loanDate, LocalDate dueDate) throws SQLException {
        String query = "INSERT INTO loans (book_id, member_id, loan_date, due_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            statement.setInt(2, memberId);
            statement.setDate(3, java.sql.Date.valueOf(loanDate));
            statement.setDate(4, java.sql.Date.valueOf(dueDate));
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        }
    }

    // Method to mark a book as returned
    public void returnBook(int loanId, LocalDate returnDate) throws SQLException {
        String query = "UPDATE loans SET return_date = ? WHERE loan_id = ?"; // Assuming 'loan_id' is the correct column name
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, java.sql.Date.valueOf(returnDate));
            statement.setInt(2, loanId);
            statement.executeUpdate();
        }
    }
}
