package rbtips.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import rbtips.dao.*;

public class AppServiceTest {

    Database db;
    AppService app;
    TagDao tagDao;
    ArticleTagDao articleTagDao;
    ArticleDao articleDao;

    @Before
    public void setUp() throws Exception {
        db = new Database("jdbc:sqlite:test.db");
        articleDao = new ArticleDao(db, "Articles");
        tagDao = new TagDao(db, "Tag");
        articleTagDao = new ArticleTagDao(db, "ArticleTag");
        app = new AppService(articleDao, tagDao, articleTagDao);

        db.initializeDatabase();
    }

    @Test
    public void retrievingSavedArticleWorks() {
        saveArticle();
        ArrayList<Article> articles = app.getAllArticles();
        assertEquals("Breaking news", articles.get(0).getHeadline());
    }

    @Test
    public void getAllArticlesReturnsRightAmountOfSavedArticles() {
        saveArticle();
        saveArticle();
        saveArticle();

        ArrayList<Article> articles = app.getAllArticles();
        assertEquals(3, articles.size());
    }

    @Test
    public void searchHeadlineWorksCorrectly() {
        saveArticle();
        ArrayList<Article> articles = app.searchHeadline("Breaking news");
        assertEquals("Breaking news", articles.get(0).getHeadline());
    }

    @Test
    public void searchTagWorksCorrectly() {
        saveArticle();
        ArrayList<Article> articles = app.searchTag("news");
        assertEquals("Breaking news", articles.get(0).getHeadline());
    }

    @Test
    public void filterTagsReturnCorrectArticles() throws SQLException {
        saveArticle();
        app.saveArticle("Breaking news1", "Journalist1", "http://news1.com", "tag");
        app.saveArticle("Breaking news2", "Journalist2", "http://news2.com", "tag,test");
        app.saveArticle("Breaking news3", "Journalist3", "http://news3.com", "news");

        ArrayList<Article> articles = articleDao.getAll();
        articles = articleDao.filterByTags(articles, app.splitTags("tag"));

        assertTrue(articles.size() == 2);

        boolean test1 = false;
        boolean test2 = false;

        for (Article a : articles) {
            if (a.getHeadline().equals("Breaking news1")) {
                test1 = true;
            }
            if (a.getHeadline().equals("Breaking news2")) {
                test2 = true;
            }
        }
        assertTrue(test1 && test2);
    }

    @Test
    public void MultipleFilterWorksCorrectly() throws SQLException {
        app.saveArticle("jes this is great", "author", "blog.fi", "tag,test");
        app.saveArticle("jes are rigth", "author2", "blog2.fi", "tag,");
        app.saveArticle("why im not in", "author", "blog.fi", "empty,lone");

        ArrayList<Article> articles = articleDao.getAll();
        articles = app.filterArticles("jes", "test");

        assertTrue(articles.size() == 1);

        assertTrue(articles.get(0).getHeadline().equals("jes this is great"));
    }

    private void saveArticle() {
        app.saveArticle("Breaking news", "Journalist", "http://news.com", "news");
    }
}
