package rbtips.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import rbtips.domain.Article;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
        try {
            Connection conn = DriverManager.getConnection(databaseAddress);
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS"
                    + "Artikkelit("
                    + "id    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + "otsikko TEXT NOT NULL,"
                    + "kirjoittaja TEXT NOT NULL"
                    + ");"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

}
