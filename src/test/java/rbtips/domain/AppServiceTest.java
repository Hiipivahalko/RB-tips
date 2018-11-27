package rbtips.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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

    @Before
    public void setUp() throws Exception {
        db = new Database("jdbc:sqlite:test.db");
        ArticleDao articleDao = new ArticleDao(db, "Articles");
<<<<<<< HEAD
        articleTagDao = new ArticleTagDao(db, "ArticleTag");
        tagDao = new TagDao(db, articleTagDao, "Tag");
=======
>>>>>>> 6903e297325cd6dc4424ca9367251aeb7955daa1
        ArticleTagDao articleTagDao = new ArticleTagDao(db, "ArticleTag");
        TagDao tagDao = new TagDao(db, "Tag");
        app = new AppService(articleDao, tagDao, articleTagDao);
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
        //do something here!
    }

    private void saveArticle() {
        app.saveArticle("Breaking news", "Journalist", "http://news.com", "news");
    }

    @After
    public void tearDown() throws Exception {
        Connection conn = db.getConnection();
        PreparedStatement stmt1 = conn.prepareStatement("DROP TABLE Articles");
        PreparedStatement stmt2 = conn.prepareStatement("DROP TABLE Tag");
        stmt1.execute();
        stmt2.execute();
        stmt1.close();
        stmt2.close();
        conn.close();
    }
}
