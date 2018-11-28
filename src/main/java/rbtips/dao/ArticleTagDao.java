package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import rbtips.domain.Tag;

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
    
    public ArrayList<Tag> getAllByArticle(int articleId) throws SQLException {
        ArrayList<Tag> tags = new ArrayList<>();

        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE article_id = ?");
        stmt.setObject(1, articleId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Tag tag = new Tag(rs.getString("name"));
            tags.add(tag);
        }

        stmt.close();
        rs.close();
        conn.close();

        return tags;
    }
}
