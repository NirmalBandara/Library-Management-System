import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDAO {
    private Connection connection; // Database connection

    // Constructor to initialize the database connection
    public BookDAO() {
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

    // Method to add a book to the database
    public void addBook(String title, String author, String publisher, int yearPublished) throws SQLException {
        String query = "INSERT INTO books (title, author, publisher, year_published) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, publisher);
            statement.setInt(4, yearPublished);
            statement.executeUpdate();
        }
    }

    // Method to update book details in the database
    public void updateBook(int bookId, String title, String author, String publisher, int yearPublished) throws SQLException {
        String query = "UPDATE books SET title = ?, author = ?, publisher = ?, year_published = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, publisher);
            statement.setInt(4, yearPublished);
            statement.setInt(5, bookId);
            statement.executeUpdate();
        }
    }

    // Method to delete a book from the database
    public void deleteBook(int bookId) throws SQLException {
        String query = "DELETE FROM books WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            statement.executeUpdate();
        }
    }

}
