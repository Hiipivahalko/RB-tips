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
            createTables(stmt);
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
            stmt.execute("DROP TABLE Tag");
            stmt.execute("DROP TABLE ArticleTag");
            createTables(stmt);

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    private void createTables(Statement stmt) throws SQLException {
        stmt.execute(createTableArticles());
        stmt.execute(createTableTag());
        stmt.execute(createTableArticleTag());
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

    private String createTableTag() {
        return "CREATE TABLE IF NOT EXISTS"
                + " Tag("
                + " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + " name VARCHAR(255)"
                + ");";
    }

    private String createTableArticleTag() {
        return "CREATE TABLE IF NOT EXISTS"
                + " ArticleTag("
                + " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + " article_id INTEGER,"
                + " tag_id INTEGER,"
                + " FOREIGN KEY (article_id) REFERENCES Articles(id),"
                + " FOREIGN KEY (tag_id) REFERENCES Tag(id)"
                + ");";
    }

}
