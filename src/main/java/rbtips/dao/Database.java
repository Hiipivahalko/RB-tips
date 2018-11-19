
package rbtips.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import rbtips.domain.Article;

public class Database {
    
    private String databaseAddress;

    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(databaseAddress);
    }
    
}
