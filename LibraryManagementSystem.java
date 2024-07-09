import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookDAO bookDAO = new BookDAO ();
    private static final MemberDAO memberDAO = new MemberDAO();
    private static final LoanDAO loanDAO = new LoanDAO();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nLibrary Management System");
                System.out.println("1. Add New Book");
                System.out.println("2. Update Book Details");
                System.out.println("3. Delete a Book");
                System.out.println("4. Add New Member");
                System.out.println("5. Loan a Book");
                System.out.println("6. Return a Book");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");
                int option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        addNewBook();
                        break;
                    case 2:
                        updateBookDetails();
                        break;
                    case 3:
                        deleteBook();
                        break;
                    case 4:
                        addNewMember();
                        break;
                    case 5:
                        loanBook();
                        break;
                    case 6:
                        returnBook();
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private static void addNewBook() {
        try {
            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            System.out.print("Enter author: ");
            String author = scanner.nextLine();
            System.out.print("Enter publisher: ");
            String publisher = scanner.nextLine();
            System.out.print("Enter year published: ");
            int yearPublished = Integer.parseInt(scanner.nextLine());
            bookDAO.addBook(title, author, publisher, yearPublished);
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input format for year published.");
        }
    }

    private static void updateBookDetails() {
        try {
            System.out.print("Enter book ID: ");
            int bookId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new title: ");
            String title = scanner.nextLine();
            System.out.print("Enter new author: ");
            String author = scanner.nextLine();
            System.out.print("Enter new publisher: ");
            String publisher = scanner.nextLine();
            System.out.print("Enter new year published: ");
            int yearPublished = Integer.parseInt(scanner.nextLine());
            bookDAO.updateBook(bookId, title, author, publisher, yearPublished);
            System.out.println("Book updated successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input format for book ID or year published.");
        }
    }

    private static void deleteBook() {
        try {
            System.out.print("Enter book ID: ");
            int book_Id = Integer.parseInt(scanner.nextLine());
            bookDAO.deleteBook(book_Id);
            System.out.println("Book deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input format for book ID.");
        }
    }

    private static void addNewMember() {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
            memberDAO.addMember(name, email, phone);
            System.out.println("Member added successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void loanBook() {
        try {
            System.out.print("Enter book ID: ");
            int bookId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter member ID: ");
            int memberId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter loan date (YYYY-MM-DD): ");
            LocalDate loanDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter due date (YYYY-MM-DD): ");
            LocalDate dueDate = LocalDate.parse(scanner.nextLine());

            // Validate dates
            if (dueDate.isBefore(loanDate)) {
                System.out.println("Error: Due date cannot be before loan date.");
                return;
            }

            boolean success = loanDAO.loanBook(bookId, memberId, loanDate, dueDate);

            if (success) {
                System.out.println("Book loaned successfully.");
            } else {
                System.out.println("Error: Could not loan book. Please check the details and try again.");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input format for book ID or member ID.");
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
        }
    }

    private static void returnBook() {
        try {
            System.out.print("Enter loan ID: ");
            int loanId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter return date (YYYY-MM-DD): ");
            LocalDate returnDate = LocalDate.parse(scanner.nextLine());

            loanDAO.returnBook(loanId, returnDate);
            System.out.println("Book returned successfully.");
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input format for loan ID.");
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
        }
    }
}
