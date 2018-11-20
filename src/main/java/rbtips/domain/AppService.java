
package rbtips.domain;

import java.util.ArrayList;
import java.util.List;

import rbtips.dao.ArticleDao;

public class AppService {
    //Sovelluslogiikkaluokka, näitä metodeja kutsutaan UI:sta
    
    private ArticleDao articleDao;

    public AppService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /**
     * Try to same new article to database if it is valid
     * @param headline article headline
     * @param author article author
     * @param url article url
     * @return
     */
    public boolean saveArticle(String headline, String author, String url) {
        List<String> allErrors = validateNewAricleUserInputs(headline, author, url);

        if (allErrors.isEmpty()) {
            try {
                Article a = new Article(headline, author, url);
                articleDao.create(a);
                return true;
            } catch(Exception e) {
                System.out.println("Something went wrong when creating new Article :(");
                return false;
            }
        } else {
            System.out.println("Article is not saved to RB-tips because some errors at input:\n");
            for (String error :allErrors) {
                System.out.println(error);
            }
            return false;
        }

    }

    /**
     * Find all articles at database and return it
     * @return ArrayList of articles if there is any
     */
    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            articles = articleDao.getAll();
        } catch(Exception e) {
            
        }
        return articles;
    }

    /**
     * Validate new
      * @param headline new article headline
     * @param author new author author
     * @param url new article url
     * @return error ArrayList
     */
    private List<String> validateNewAricleUserInputs(String headline, String author, String url) {
        List<String> errors = new ArrayList<>();

        if (headline.length() < 5) {
            errors.add("Headline must have atleast five characters");
        }

        if (author.length() < 3) {
            errors.add("Author must have atleast tree characters");
        }

        if (url.length() < 4) {
            errors.add("Put rigth URL");
        }

        return errors;
    }
}
