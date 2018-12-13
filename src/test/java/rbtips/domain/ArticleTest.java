package rbtips.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArticleTest {

    Article article;

    @Before
    public void SetUp() {
        article = new Article("Headline", "Author", "www.article.com");

    }

    @Test
    public void rightHeadline() {
        assertEquals("Headline", article.getHeadline());
    }

    @Test
    public void rightAuthor() {
        assertEquals("Author", article.getAuthor());
    }

    @Test
    public void rightUrl() {
        assertEquals("www.article.com", article.getUrl());
    }

    @Test
    public void rightToString() {
        assertEquals("Author: Headline (url: www.article.com)", article.toString());
    }

    @Test
    public void returnTrueIfEquals() {
        Article article_clone = article;
        assertEquals(true, article.equals(article_clone));
    }

    @Test
    public void returnFalseIfObjectIsNull() {
        Article null_article = null;
        assertEquals(false, article.equals(null_article));
    }

    @Test
    public void returnFalseIfObjectsClassIsWrong() {
        String string_article = ("Wrong class");
        assertEquals(false, article.equals(string_article));
    }

}
