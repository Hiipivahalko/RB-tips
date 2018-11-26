package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArticleTagDao {
    private Database db;
    private String tableName;

    public ArticleTagDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }
    
    public void create(int articleId, int tagId) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + "(article_id, tag_id) VALUES (?, ?)");
        
        stmt.setInt(1, articleId);
        stmt.setInt(2, tagId);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
