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

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void create() throws SQLException {

        
    }

    @Test
    public void deleteUnions() {
        /*try {
            atd.create(1, 1);
            atd.create(1, 2);

        } catch (SQLException e) {
            e.printStackTrace();
        }*/

    }

    @Test
    public void isThereStillMoreUnionsToTag() {
    }
}
