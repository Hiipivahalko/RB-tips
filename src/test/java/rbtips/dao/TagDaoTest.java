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
        addTags("tag");
        assertTrue(tagDao.alreadyExists("tag"));
    }

    @Test
    public void addingTwoTagsWork() throws SQLException {
        addTags("tag1", "tag2");
        assertTrue(tagDao.alreadyExists("tag1") && tagDao.alreadyExists("tag2"));
    }

    @Test
    public void addingOneTagTwoTimesWorks() throws SQLException {
        addTags("tag1");
        addTags("tag2");
        assertTrue(tagDao.alreadyExists("tag1") && tagDao.alreadyExists("tag2"));
    }

    @Test
    public void findIdByNameReturnsOneIdInArrayList() throws SQLException {
        String[] tags = addTags("tag");
        assertTrue(tagDao.findIdByName(tags).size() == 1);
    }

    @Test
    public void findByNameReturnTwoIdsInArrayList() throws SQLException {
        String[] tags = addTags("tag1", "tag2");
        assertTrue(tagDao.findIdByName(tags).size() == 2);
    }

    @Test
    public void deleteTag() throws SQLException {
        String[] tags = addTags("testTag", "anotherOne");

        int tagId = tagDao.findIdByName(tags).get(0);
        tagDao.deleteTag(tagId);
        assertTrue(tagDao.findIdByName(tags).size() == 1);

        assertTrue(!tagDao.alreadyExists("testTag") && tagDao.alreadyExists("anotherOne"));
    }

    public String[] addTags(String tag) throws SQLException {
        String[] tags = new String[]{tag};
        tagDao.addTagsIfNotAlreadyExist(tags);
        return tags;
    }

    public String[] addTags(String tag1, String tag2) throws SQLException {
        String[] tags = new String[]{tag1, tag2};
        tagDao.addTagsIfNotAlreadyExist(tags);
        return tags;
    }

    @After
    public void tearDown() throws SQLException {
        try (Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("DROP TABLE Articles")) {
            statement.execute();
        }
    }
}
