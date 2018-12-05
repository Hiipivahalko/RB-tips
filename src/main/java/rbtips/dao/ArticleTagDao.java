package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleTagDao {

    private final Database db;
    private final String tableName;

    public ArticleTagDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    public void create(int articleId, int tagId) throws SQLException {
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + "(article_id, tag_id) VALUES (?, ?)");
            
            stmt.setInt(1, articleId);
            stmt.setInt(2, tagId);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ArticleTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
