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

    public void addTagsIfNotAlreadyExist(String tagsInput) throws SQLException {

        String[] tags = splitTags(tagsInput);

        for (String tag : tags) {
            try {
                checkIfTagAlreadyExistsIf(tag);
            } catch (Exception e) {
                System.out.println("Something went wrong, when trying to check tag from database");
            }

        }
    }

    private String[] splitTags(String tagsInput) {
        String noWhiteSpaces = tagsInput.replace("\\s+", "");
        String[] tags = noWhiteSpaces.split(",");
        return tags;
    }

    private void checkIfTagAlreadyExistsIf(String tagName) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SEARCH * FROM " + tableName + " WHERE name = ?");
        stmt.setString(1, tagName);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            Tag newTag = new Tag(tagName);
            add(newTag);
        }
    }

    public void add(Tag tag) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (name) VALUES (?)");

        stmt.setString(1, tag.getName());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public ArrayList<Integer> findByName(String tagNames) {
        String[] tags = splitTags(tagNames);
        ArrayList<Integer> tagIds = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE name LIKE ?");
            stmt.setString(1, "%" + tagNames + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tagIds.add(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tagIds;
    }

    public ArrayList<Article> searchTag(String tagNames) throws SQLException {
        ArrayList<Integer> tagIds = findByName(tagNames);
        //Use ArticleTagDao to find and return the matching articles to the list of tags (tagIds)
        return new ArrayList<Article>();
    }

}
