package rbtips.domain;

import java.sql.SQLException;
import java.util.ArrayList;
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
    BookDao bookDao;
    BookTagDao bookTagDao;

    @Before
    public void setUp() throws Exception {
        db = new Database("jdbc:sqlite:test.db");
        articleDao = new ArticleDao(db, "Articles");
        tagDao = new TagDao(db, "Tag");
        articleTagDao = new ArticleTagDao(db, "ArticleTag");
        bookDao = new BookDao(db, "Book");
        bookTagDao = new BookTagDao(db, "BookTag");
        app = new AppService(articleDao, bookDao, tagDao, articleTagDao, bookTagDao);

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
        articles = articleDao.filterByTags(articles, "tag");

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

    /**
     * Testing filtering with both filter inputs
     *
     * @throws SQLException
     */
    @Test
    public void multipleFilterWorksCorrectly() throws SQLException {
        app.saveArticle("jes this is great", "author", "blog.fi", "tag,test");
        app.saveArticle("jes are rigth", "author2", "blog2.fi", "tag,");
        app.saveArticle("why im not in", "author", "blog.fi", "empty,lone");

        ArrayList<Article> articles = app.filterArticles("jes", "test");

        assertTrue(articles.size() == 1);

        assertTrue(articles.get(0).getHeadline().equals("jes this is great"));

    }

    @Test
    public void multipleFilteringWithOnlyHeadlineInput() {
        app.saveArticle("jes this is great", "author", "blog.fi", "tag,test");
        app.saveArticle("jes are rigth", "author2", "blog2.fi", "tag,");
        app.saveArticle("why im not in", "author", "blog.fi", "empty,lone");

        ArrayList<Article> articles = app.filterArticles("why", "");

        assertTrue(articles.size() == 1);

        assertTrue(articles.get(0).getHeadline().equals("why im not in"));
    }

    @Test
    public void multipleFilteringWithOnlyTagInput() {
        app.saveArticle("jes this is great", "author", "blog.fi", "tag,test");
        app.saveArticle("jes are rigth", "author2", "blog2.fi", "winnerTag");
        app.saveArticle("why im not in", "author", "blog.fi", "empty,lone");

        ArrayList<Article> articles = app.filterArticles("", "winnerTag");

        assertTrue(articles.size() == 1);

        assertTrue(articles.get(0).getHeadline().equals("jes are rigth"));
    }

    @Test
    public void anExistingArticleCanBeDeleted() {
        Article a = new Article("jes this is great", "author", "www.blog.fi", "tag, test");
        app.saveArticle("jes this is great", "author", "www.blog.fi", "tag,test");
        app.saveArticle("jes are rigth", "author2", "blog2.fi", "winnerTag");
        app.saveArticle("why im not in", "author", "blog.fi", "empty,lone");

        app.deleteTip(a);

        ArrayList<Article> articles = app.getAllArticles();

        boolean notFound = false;

        for (Article article : articles) {
            if (article.getHeadline().equals("jes this is great")) {
                notFound = true;
            }
        }

        assertTrue(!notFound);
        assertTrue(articles.size() == 2);
    }

    private void saveArticle() {
        app.saveArticle("Breaking news", "Journalist", "http://news.com", "news");
    }
}
