
package rbtips.domain;

import java.util.ArrayList;
import java.util.List;

import rbtips.dao.ArticleDao;
import rbtips.dao.TagDao;

public class AppService {
    //Sovelluslogiikkaluokka, näitä metodeja kutsutaan UI:sta
    
    private ArticleDao articleDao;
    private TagDao tagDao;

    public AppService(ArticleDao articleDao, TagDao tagDao) {
        this.articleDao = articleDao;
        this.tagDao = tagDao;
    }

    /**
     * Try to same new article to database if it is valid
     * @param headline article headline
     * @param author article author
     * @param url article url
     * @return
     */
    public boolean saveArticle(String headline, String author, String url, String tagNames) {
        List<String> allErrors = validateNewAricleUserInputs(headline, author, url);
        ArrayList<Integer> tagIds;

        if (allErrors.isEmpty()) {
            try {
                Article a = new Article(headline, author, url);
                articleDao.create(a);
                tagDao.addTagsIfNotAlreadyExist(tagNames);
                tagIds = tagDao.findByName(tagNames);
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
     * Search articles in the database for with matching headline
     * @return ArrayList of articles with wanted headline if found any
     */
    public ArrayList<Article> searchArticleWithHeadline(String headline) {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            articles = articleDao.searchHeadline(headline);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return articles;
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
