
package rbtips.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rbtips.domain.Article;



public class ArticleTest{
    Article article;

    @Before
    public void SetUp(){
        article = new Article("Headline", "Author", "www.article.com");
        
    }

    @Test
    public void rightHeadline(){
        assertEquals("Headline" ,article.getHeadline());
    }
    
    @Test
    public void rightAuthor(){
        assertEquals("Author", article.getAuthor());
    }
    
    @Test
    public void rightUrl(){
        assertEquals("www.article.com", article.getUrl());
    }
    
    @Test
    public void rightToString(){
        assertEquals("Author: Headline url: www.article.com", article.toString());
    }
    
}

