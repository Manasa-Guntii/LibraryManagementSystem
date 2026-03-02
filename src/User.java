import java.sql.*;

public class User {
    private int userId;
    private String name;
    private String email;

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void addUser() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    public static void viewUsers() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("UserID\tName\tEmail");
            while (rs.next()) {
                System.out.println(rs.getInt("user_id") + "\t" +
                                   rs.getString("name") + "\t" +
                                   rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
    }
}