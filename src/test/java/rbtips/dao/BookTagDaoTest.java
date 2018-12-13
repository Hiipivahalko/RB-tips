package rbtips.dao;

import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class BookTagDaoTest {
    
    Database db;
    String tableName = "BookTag";
    TagDao tagDao;
    BookDao bookDao;
    BookTagDao btd;
    
    @Before
    public void setUp() {
        db = new Database("jdbc:sqlite:test.db");
        tagDao = new TagDao(db, "Tag");
        bookDao = new BookDao(db, "Book");
        btd = new BookTagDao(db, "BookTag");
        db.initializeDatabase();
    }
    
    @Test
    public void creationWorksWithMultipleUnions() throws SQLException {
        btd.create(1, 1);
        btd.create(2, 5);
        btd.create(3, 7);

        assertTrue(btd.getRowAmount() == 3);
    }
    
    @Test
    public void isThereStillMoreUnionsToTag() throws SQLException {
        btd.create(1, 1);
        assertTrue(btd.isThereStillMoreUnionsToTag(1));
        btd.deleteUnions(1, 1);
        assertFalse(btd.isThereStillMoreUnionsToTag(1));
    }
    
}
