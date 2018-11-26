package rbtips.domain;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import rbtips.dao.*;

public class AppServiceTest {

    AppService app;

    @Before
    public void setUp() {
        Database db = new Database("jdbc:sqlite:test.db");
        ArticleDao articleDao = new ArticleDao(db, "Articles");
        TagDao tagDao = new TagDao(db, "Tag");
        app = new AppService(articleDao, tagDao);
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

}
