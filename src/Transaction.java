import java.sql.*;
import java.time.LocalDate;

public class Transaction {
    private int transactionId;
    private int userId;
    private int bookId;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public Transaction() {}

    public Transaction(int userId, int bookId) {
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = LocalDate.now();
    }

    public void issueBook() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String check = "SELECT quantity FROM books WHERE book_id=?";
            PreparedStatement stmt = conn.prepareStatement(check);
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int qty = rs.getInt("quantity");
                if (qty > 0) {
                    String sql = "INSERT INTO transactions (user_id, book_id, issue_date) VALUES (?, ?, ?)";
                    PreparedStatement insert = conn.prepareStatement(sql);
                    insert.setInt(1, userId);
                    insert.setInt(2, bookId);
                    insert.setDate(3, Date.valueOf(issueDate));
                    insert.executeUpdate();

                    String update = "UPDATE books SET quantity=? WHERE book_id=?";
                    PreparedStatement updStmt = conn.prepareStatement(update);
                    updStmt.setInt(1, qty - 1);
                    updStmt.setInt(2, bookId);
                    updStmt.executeUpdate();

                    System.out.println("Book issued successfully!");
                } else {
                    System.out.println("Book not available.");
                }
            } else {
                System.out.println("Book not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error issuing book: " + e.getMessage());
        }
    }

    public void returnBook() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE transactions SET return_date=? WHERE user_id=? AND book_id=? AND return_date IS NULL";
            PreparedStatement stmt = conn.prepareStatement(sql);
            returnDate = LocalDate.now();
            stmt.setDate(1, Date.valueOf(returnDate));
            stmt.setInt(2, userId);
            stmt.setInt(3, bookId);
            int updated = stmt.executeUpdate();
            if (updated > 0) {
                String updateBook = "UPDATE books SET quantity = quantity + 1 WHERE book_id=?";
                PreparedStatement upd = conn.prepareStatement(updateBook);
                upd.setInt(1, bookId);
                upd.executeUpdate();
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("No issued record found for this book and user.");
            }
        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }
}