package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rbtips.domain.Article;
import static org.junit.Assert.*;

public class ArticleDaoTest {

    Database database;
    ArticleDao articleDao;

    @Before
    public void setUp() throws Exception {
        database = new Database("jdbc:sqlite:test.db");
        articleDao = new ArticleDao(database);
    }

    @Test
    public void creatingAnArticleWorks() throws Exception {
        articleDao.create(new Article("headline", "mr author", "www.mrautor.com"));
        ArrayList<Article> articles = articleDao.getAll();
        assertEquals("mr author", articles.get(0).getAuthor());
    }
    
    @Test
    public void getAllWorksWhenDatabaseIsEmpty() throws Exception {
        ArrayList<Article> articles = articleDao.getAll();
        assertTrue(articles.isEmpty());
    }
    
    @Test
    public void getAllWorksWhenDatabaseHasOneArticle() throws Exception {
        articleDao.create(new Article("title", "writer", "www.url.com"));
        ArrayList<Article> articles = articleDao.getAll();
        assertEquals(1, articles.size());
    }
    
    
    @Test
    public void getAllWorksWhenDatabaseHasMultipleArticles() throws Exception {
        articleDao.create(new Article("headline", "mr author", "www.url.com"));
        articleDao.create(new Article("headline2", "author", "yle.fi"));
        articleDao.create(new Article("headline3", "author3", "www.url34.com"));
        
        ArrayList<Article> articles = articleDao.getAll();
        
        assertEquals(3, articles.size());
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DROP TABLE Artikkelit");
        statement.execute();
        statement.close();
        connection.close();
    }

}
