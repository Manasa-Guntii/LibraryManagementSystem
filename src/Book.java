import java.sql.*;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private int quantity;

    public Book() {}

    public Book(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public void addBook() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public static void viewBooks() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("BookID\tTitle\tAuthor\tQuantity");
            while (rs.next()) {
                System.out.println(rs.getInt("book_id") + "\t" +
                                   rs.getString("title") + "\t" +
                                   rs.getString("author") + "\t" +
                                   rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching books: " + e.getMessage());
        }
    }
}