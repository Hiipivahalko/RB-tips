package rbtips.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


// Waiting for completion of TagDao
public class TagDaoTest {

    Database database;
    TagDao tagDao;
    ArticleTagDao articleTagDao;

    @Before
    public void setUp() {
        
        database = new Database("jdbc:sqlite:test.db");
        articleTagDao = new ArticleTagDao(database, "ArticleTag");
        tagDao = new TagDao(database, articleTagDao, "Tags");
    }


}
