package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import rbtips.domain.Article;
import rbtips.domain.Book;

import rbtips.domain.Tag;

public class TagDao {

    private final Database db;
    private final String tableName;

    public TagDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    /**
     * Add tags to database if it not exists there already
     *
     * @param tags
     * @throws SQLException
     */
    public void addTagsIfNotAlreadyExist(String[] tags) throws SQLException {
        for (String tag : tags) {
            try {
                if (!alreadyExists(tag)) {
                    Tag newTag = new Tag(tag);
                    add(newTag);
                }
            } catch (SQLException e) {
                System.out.println("Something went wrong, when trying to check tag from database");
            }

        }
    }

    public boolean alreadyExists(String tagName) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE name = ?");
        stmt.setString(1, tagName);

        ResultSet rs = stmt.executeQuery();

        boolean exists = rs.next();

        stmt.close();
        rs.close();
        conn.close();

        return exists;
    }

    /**
     * Add new tag to database
     *
     * @param tag
     * @throws SQLException
     */
    private void add(Tag tag) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (name) VALUES (?)");

        stmt.setString(1, tag.getName());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public ArrayList<Integer> findIdByName(String[] tags) throws SQLException {
        ArrayList<Integer> tagIds = new ArrayList<>();
        for (String tag : tags) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE name = (?)");
            stmt.setString(1, tag);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tagIds.add(rs.getInt("id"));
            }

            stmt.close();
            rs.close();
            conn.close();

        }

        return tagIds;
    }

    /**
     * Find article tags from database
     *
     * @param article
     * @return
     */
    public ArrayList<String> findArticleTags(Article article) {
        ArrayList<String> tags = new ArrayList<>();

        try {
            Connection conn = db.getConnection();
            String query = "SELECT tag.name FROM "
                    + tableName
                    + ", Articles, ArticleTag WHERE Articles.headline = "
                    + "? and articles.id = ArticleTag.article_id and ArticleTag.tag_id = Tag.id";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, article.getHeadline());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tags.add(rs.getString(1));
            }

            stmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
        }
        return tags;
    }
    
    public ArrayList<String> findBookTags(Book book) {
        ArrayList<String> tags = new ArrayList<>();

        try {
            Connection conn = db.getConnection();
            String query = "SELECT tag.name FROM "
                    + tableName
                    + ", Books, BookTag WHERE Book.headline = "
                    + "? and book.id = BookTag.book_id and BookTag.tag_id = Tag.id";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, book.getTitle());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tags.add(rs.getString(1));
            }

            stmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
        }
        return tags;
    }

    /**
     * Delete tag from database
     * @param tagId
     */
    public void deleteTag(int tagId) {

        try (Connection conn = db.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            stmt.setInt(1, tagId);

            stmt.execute();
            stmt.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
