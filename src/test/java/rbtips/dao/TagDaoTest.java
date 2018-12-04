package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TagDaoTest {

    Database database;
    ArticleDao articleDao;
    TagDao tagDao;

    @Before
    public void setUp() {
        database = new Database("jdbc:sqlite:test.db");
        articleDao = new ArticleDao(database, "Articles");
        tagDao = new TagDao(database, "Tag");
        database.initializeDatabase();
    }

    @Test
    public void addingOneTagWorks() throws SQLException {
        String[] tags = new String[]{"tag"};
        tagDao.addTagsIfNotAlreadyExist(tags);
        assertTrue(tagDao.alreadyExists("tag"));
    }

    @Test
    public void addingTwoTagsWork() throws SQLException {
        String[] tags = new String[]{"tag1", "tag2"};
        tagDao.addTagsIfNotAlreadyExist(tags);
        assertTrue(tagDao.alreadyExists("tag1"));
        assertTrue(tagDao.alreadyExists("tag2"));
    }

    @Test
    public void findIdByNameReturnsOneIdInArrayList() throws SQLException {
        String[] tags = new String[]{"tag"};
        tagDao.addTagsIfNotAlreadyExist(tags);
        assertTrue(tagDao.findIdByName(tags).size() == 1);
    }

    @Test
    public void findByNameReturnTwoIdsInArrayList() throws SQLException {
        String[] tags = new String[]{"tag", "tag2"};
        tagDao.addTagsIfNotAlreadyExist(tags);
        assertTrue(tagDao.findIdByName(tags).size() == 2);
    }

    @After
    public void tearDown() throws SQLException {
        try (Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("DROP TABLE Articles")) {
            statement.execute();
        }
    }
}
