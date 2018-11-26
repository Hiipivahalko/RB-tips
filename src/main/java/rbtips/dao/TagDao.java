package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import rbtips.domain.Tag;

public class TagDao {

    private Database db;
    private String tableName;

    public TagDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    public void add(Tag tag) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + "(name) VALUES (?)");
        
        stmt.setString(1, tag.getName());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
