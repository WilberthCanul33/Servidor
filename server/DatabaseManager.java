import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:database/users.db";

    public static void init() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean register(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement check = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            check.setString(1, username);
            ResultSet rs = check.executeQuery();
            if (rs.next()) return false;

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean login(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
}