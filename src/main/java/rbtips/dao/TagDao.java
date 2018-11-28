package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import rbtips.domain.Article;

import rbtips.domain.Tag;

public class TagDao {

    private Database db;
    private String tableName;

    public TagDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    /**
     * Add tags to databse if it not exists there already
     * @param tagsInput
     * @throws SQLException
     */
    public void addTagsIfNotAlreadyExist(String tagsInput) throws SQLException {

        String[] tags = splitTags(tagsInput);

        for (String tag : tags) {
            try {
                if (!alreadyExists(tag)) {
                    Tag newTag = new Tag(tag);
                    add(newTag);
                }
            } catch (Exception e) {
                System.out.println("Something went wrong, when trying to check tag from database");
            }

        }
    }

    /**
     * Split String of tags to tag array. Take off whitespaces. Seperate tag by comman ','
     * @param tagsInput
     * @return string array of tags
     */
    public String[] splitTags(String tagsInput) {
        String noWhiteSpaces = tagsInput.replaceAll("\\s", "");
        String[] tags = noWhiteSpaces.split(",");
        return tags;
    }

    private boolean alreadyExists(String tagName) throws SQLException {
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
     * @param tag
     * @throws SQLException
     */
    public void add(Tag tag) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (name) VALUES (?)");

        stmt.setString(1, tag.getName());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public ArrayList<Integer> findByName(String tagNames) throws SQLException {
        String[] tags = splitTags(tagNames);
        ArrayList<Integer> tagIds = new ArrayList<>();

        for (String tag : tags) {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE name LIKE ?");
            stmt.setString(1, "%" + tag + "%");

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
     * @param article
     * @return
     */
    public ArrayList<String> findArticleTags(Article article) {
        ArrayList<String> tags = new ArrayList<>();

        try {
            Connection conn = db.getConnection();
            String query = "SELECT tag.name FROM " + tableName + ", Articles, ArticleTag WHERE Articles.headline = "
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
            e.printStackTrace();
        }


        return tags;
    }
}
