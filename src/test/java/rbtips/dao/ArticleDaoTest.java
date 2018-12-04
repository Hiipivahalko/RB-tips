package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
        articleDao = new ArticleDao(database, "Articles");
        database.initializeDatabase();
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

    @Test
    public void searchHeadlineReturnsListOfArticleObjects() throws Exception {
        articleDao.create(new Article("new", "author", "blog.fi"));
        ArrayList<Article> articles = articleDao.searchHeadline("new", false);
        assertEquals(1, articles.size());
    }

    @Test
    public void searchHeadlineReturnsCorrectArticle() throws Exception {
        Article a = new Article("new", "author", "blog.fi");
        articleDao.create(a);
        ArrayList<Article> articles = articleDao.searchHeadline("new", false);
        assertEquals(a.toString(), articles.get(0).toString());
    }

    @Test
    public void filterHeadlineReturnsCorrectArticles() throws SQLException {
        Article a = new Article("jes this is great", "author", "blog.fi", "tag,test");
        Article a2 = new Article("jes, you are right", "author2", "blog2.fi", "tag,test2");
        Article a3 = new Article("Im not in the group", "author3", "blog3.fi", "tag,test3");

        articleDao.create(a);
        articleDao.create(a2);
        articleDao.create(a3);

        ArrayList<Article> articles = articleDao.filterByHeadline(articleDao.getAll(), "jes");
        assertTrue(articles.size() == 2);

        boolean test1 = false;
        boolean test2 = false;

        for (Article article : articles) {
            if (article.equals(a)) {
                test1 = true;
            }
            if (article.equals(a2)) {
                test2 = true;
            }
        }

        assertTrue(test1 && test2);
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DROP TABLE Articles");
        statement.execute();
        statement.close();
        connection.close();
    }
}
