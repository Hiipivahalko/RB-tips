package rbtips;


import org.junit.*;
import static org.junit.Assert.assertEquals;
import rbtips.domain.Article;

public class ArticleTest{
    Article article;

    @Before
    public void SetUp(){
        article = new Article("Otsikko", "Kirjoittaja", "www.123.com");
        
    }

    @Test
    public void rightHeadline(){
        assertEquals("Otsikko" ,article.getHeadline());
    }
    
}