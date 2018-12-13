package rbtips.dao;

import org.junit.*;
import rbtips.domain.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ArticleTagDaoTest {

    Database db;
    String tableName = "ArticleTag";
    ArticleTagDao atd;
    ArticleDao articleDao;
    TagDao tagDao;

    @Before
    public void setUp() throws Exception {
        db = new Database("jdbc:sqlite:test.db");
        articleDao = new ArticleDao(db, "Articles");
        tagDao = new TagDao(db, "Tag");
        atd = new ArticleTagDao(db, "ArticleTag");
        db.initializeDatabase();
    }

    @Test
    public void creationWorksWithMultipleUnions() throws SQLException {
        atd.create(1, 1);
        atd.create(2, 5);
        atd.create(3, 7);

        assertTrue(atd.getRowAmount() == 3);

    }

    @Test
    public void deleteUnions() throws SQLException {
        atd.create(1, 1);
        atd.create(2, 5);
        atd.create(3, 7);

        assertTrue(atd.getRowAmount() == 3);

        atd.deleteUnions(2, 5);

        assertTrue(atd.getRowAmount() == 2);
    }

    @Test
    public void cannotDeleteAnUnionThatDoesNotExist() throws SQLException {
        atd.create(1, 1);
        atd.create(2, 5);
        atd.create(3, 7);

        assertTrue(atd.getRowAmount() == 3);

        atd.deleteUnions(5, 3);

        assertTrue(atd.getRowAmount() == 3);

    }

    @Test
    public void isThereStillMoreUnionsToTag() throws SQLException {
        atd.create(1, 1);
        assertTrue(atd.isThereStillMoreUnionsToTag(1));
        atd.deleteUnions(1, 1);
        assertFalse(atd.isThereStillMoreUnionsToTag(1));
    }
}
