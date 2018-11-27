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
    private ArticleTagDao articleTagDao;
    private String tableName;

    public TagDao(Database db, ArticleTagDao articleTagDao, String tableName) {
        this.db = db;
        this.articleTagDao = articleTagDao;
        this.tableName = tableName;
    }

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

    private String[] splitTags(String tagsInput) {
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

    public ArrayList<Article> searchTag(String tagNames) throws SQLException {
        ArrayList<Integer> tagIds = findByName(tagNames);

        // Tämän metodin pitäisi palauttaa lista Artikkeleistä, 
        // joissa on tägit, joiden id:t ovat tagIds-listalla
        // Luodaan lista palautettaville Artikkeleille
        ArrayList<Article> articles = new ArrayList<>();

        // Nyt pitäisi ottaa yhteyttä articleTagDao-luokkaan?
        // ???
        
        return articles;
    }

}
