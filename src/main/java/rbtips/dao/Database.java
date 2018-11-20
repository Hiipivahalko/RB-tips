package rbtips.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
        try {
            Connection conn = DriverManager.getConnection(databaseAddress);
            Statement stmt = conn.createStatement();
            stmt.execute(createTableArticles());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void initializeDatabase() {
        try {
            Connection conn = DriverManager.getConnection(databaseAddress);
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE Articles");
            stmt.execute(createTableArticles());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    private String createTableArticles() {
        return "CREATE TABLE IF NOT EXISTS"
                + " Articles("
                + " id    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + " headline TEXT NOT NULL,"
                + " author TEXT NOT NULL,"
                + " url VARCHAR(255)"
                + ");";
    }

}
