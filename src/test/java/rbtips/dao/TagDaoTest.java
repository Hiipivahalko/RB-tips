package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TagDaoTest {

    Database database;
    TagDao tagDao;

    @Before
    public void setUp() {
        database = new Database("jdbc:sqlite:test.db");
        tagDao = new TagDao(database, "Tags");
        database.initializeDatabase();
    }

    /*@Test
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
    }*/

    @After
    public void tearDown() throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DROP TABLE Articles");
        statement.execute();
        statement.close();
        connection.close();
    }
}
