
package rbtips.domain;

import rbtips.dao.ArticleDao;

public class AppService {
    //Sovelluslogiikkaluokka, näitä metodeja kutsutaan UI:sta
    
    private ArticleDao articleDao;

    public AppService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }
    
    public boolean saveArticle(String author, String headline, String url) {
        try {
            Article a = new Article(headline, author, url);
            articleDao.create(a);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
