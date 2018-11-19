
package rbtips.domain;

import java.util.ArrayList;
import rbtips.dao.ArticleDao;

public class AppService {
    //Sovelluslogiikkaluokka, näitä metodeja kutsutaan UI:sta
    
    private ArticleDao articleDao;

    public AppService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }
    
    public boolean saveArticle(String headline, String author, String url) {
        try {
            Article a = new Article(headline, author, url);
            articleDao.create(a);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    
    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            articles = articleDao.getAll();
        } catch(Exception e) {
            
        }
        return articles;
    }
}
