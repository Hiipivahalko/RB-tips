package rbtips.dao;

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
    }

    @Test
    public void addingOneTagWork() throws SQLException {
        
    }

}
