package rbtips.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rbtips.dao.ArticleDao;
import rbtips.dao.ArticleTagDao;
import rbtips.dao.TagDao;

public class AppService {
    //Sovelluslogiikkaluokka, näitä metodeja kutsutaan UI:sta

    private ArticleDao articleDao;
    private TagDao tagDao;
    private ArticleTagDao articleTagDao;

    public AppService(ArticleDao articleDao, TagDao tagDao, ArticleTagDao articleTagDao) {
        this.articleDao = articleDao;
        this.tagDao = tagDao;
        this.articleTagDao = articleTagDao;
    }

    /**
     * Try to same new article to database if it is valid
     *
     * @param headline article headline
     * @param author article author
     * @param url article url
     * @return
     */
    public boolean saveArticle(String headline, String author, String url, String tagNames) {
        List<String> allErrors = validateNewArticleUserInputs(headline, author, url);
        ArrayList<Integer> tagIds = new ArrayList<>();

        if (allErrors.isEmpty()) {
            try {
                Article a = new Article(headline, author, url, tagNames);
                articleDao.create(a);
                int articleId = articleDao.getIdByHeadline(headline);

                tagDao.addTagsIfNotAlreadyExist(tagNames);
                tagIds = tagDao.findIdByName(tagNames);
                for (int id : tagIds) {
                    articleTagDao.create(articleId, id);
                }

                return true;
            } catch (Exception e) {
                System.out.println("Something went wrong when creating new Article :(");
                return false;
            }
        } else {
            System.out.println("Article is not saved to RB-tips because some errors at input:\n");
            for (String error : allErrors) {
                System.out.println(error);
            }
            return false;
        }

    }

    /**
     * Search articles in the database with matching headline
     *
     * @return ArrayList of articles with wanted headline if found any
     */
    public ArrayList<Article> searchHeadline(String headline) {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            articles = articleDao.searchHeadline(headline, false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return articles;
    }

    /**
     * Search articles in the database with matching tags
     *
     * @return ArrayList of articles with wanted tags if found any
     */
    public ArrayList<Article> searchTag(String tagNames) {


        ArrayList<Article> articles = new ArrayList<>();

        try {
            String[] tags = tagDao.splitTags(tagNames);
            System.out.println(Arrays.toString(tags));
            for (String tag :tags) {
                ArrayList<Article> temp = articleDao.searchArticleByTags(tag);

                for(Article article : temp) {
                    if(!articles.contains(article)) {
                        articles.add(article);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }

    /**
     * Find all articles at database and return it
     *
     * @return ArrayList of articles if there is any
     */
    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            articles = articleDao.getAll();
        } catch (Exception e) {

        }
        return articles;
    }

    /**
     * Validate new
     *
     * @param headline new article headline
     * @param author new author author
     * @param url new article url
     * @return error ArrayList
     */
    private List<String> validateNewArticleUserInputs(String headline, String author, String url) {
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

    public String getAllTagsByArticle(Article a) throws SQLException {

        ArrayList<String> tags = new ArrayList<>();
        try {
            int id = articleDao.getIdByHeadline(a.getHeadline());
            tags = tagDao.findArticleTags(a);
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        String tagsSeperateByComma = String.join(",",tags); // this convert ArrayList of Strings to one String and separate old strings by comma
        return tagsSeperateByComma;
    }
}
