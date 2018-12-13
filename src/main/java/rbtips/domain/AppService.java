package rbtips.domain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rbtips.dao.ArticleDao;
import rbtips.dao.ArticleTagDao;
import rbtips.dao.BookDao;
import rbtips.dao.BookTagDao;
import rbtips.dao.TagDao;

public class AppService {
    //Sovelluslogiikkaluokka, näitä metodeja kutsutaan UI:sta

    private final ArticleDao articleDao;
    private final BookDao bookDao;
    private final TagDao tagDao;
    private final ArticleTagDao articleTagDao;
    private final BookTagDao bookTagDao;

    public AppService(ArticleDao articleDao, BookDao bookDao, TagDao tagDao, ArticleTagDao articleTagDao, BookTagDao bookTagDao) {
        this.articleDao = articleDao;
        this.tagDao = tagDao;
        this.articleTagDao = articleTagDao;
        this.bookDao = bookDao;
        this.bookTagDao = bookTagDao;
    }

    /**
     * Try to same new article to database if it is valid
     *
     * @param headline article headline
     * @param author article author
     * @param url article url
     * @param tagNames
     * @return
     */
    public boolean saveArticle(String headline, String author, String url, String tagNames) {
        List<String> allErrors = validateNewArticleUserInputs(headline, author, url);
        ArrayList<Integer> tagIds;

        if (allErrors.isEmpty()) {
            try {
                Article a = new Article(headline, author, url, tagNames);
                articleDao.create(a);
                int articleId = articleDao.getIdByHeadline(headline);

                String[] tags = splitTags(tagNames);
                tagDao.addTagsIfNotAlreadyExist(tags);
                tagIds = tagDao.findIdByName(tags);
                for (int id : tagIds) {
                    articleTagDao.create(articleId, id);
                }

                return true;
            } catch (SQLException e) {
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
    
    public boolean saveBook(String headline, String author, String publish_date, String isbn, String tagNames) {
        List<String> allErrors = validateNewBookUserInputs(headline, author, publish_date, isbn);
        ArrayList<Integer> tagIds;

        if (allErrors.isEmpty()) {
            try {
                Book book = new Book(headline, author, publish_date, isbn, tagNames);
                bookDao.create(book);
                int bookId = bookDao.getIdByHeadline(headline);

                String[] tags = splitTags(tagNames);
                tagDao.addTagsIfNotAlreadyExist(tags);
                tagIds = tagDao.findIdByName(tags);
                for (int id : tagIds) {
                    bookTagDao.create(bookId, id);
                }

                return true;
            } catch (SQLException e) {
                System.out.println("Something went wrong when creating new Book :(");
                return false;
            }
        } else {
            System.out.println("Book is not saved to RB-tips because some errors at input:\n");
            for (String error : allErrors) {
                System.out.println(error);
            }
            return false;
        }
    }

    /**
     * Search articles in the database with matching headline
     *
     * @param headline
     * @return ArrayList of articles with wanted headline if found any
     */
    public ArrayList<Article> searchHeadline(String headline) {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            articles = articleDao.searchHeadline(headline, false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return articles;
    }
    
    public ArrayList<Book> searchBookHeadline(String headline) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            books = bookDao.searchHeadline(headline, false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return books;
    }

    /**
     * Search articles in the database with matching tags
     *
     * @param tagNames
     * @return ArrayList of articles with wanted tags if found any
     */
    public ArrayList<Article> searchTag(String tagNames) {

        ArrayList<Article> articles = new ArrayList<>();

        try {
            String[] tags = splitTags(tagNames);
            System.out.println(Arrays.toString(tags));
            for (String tag : tags) {
                ArrayList<Article> temp = articleDao.searchArticleByTags(tag);

                for (Article article : temp) {
                    if (!articles.contains(article)) {
                        articles.add(article);
                    }
                }
            }

        } catch (SQLException e) {
        }

        return articles;
    }
    
    public ArrayList<Book> searchBookByTag(String tagNames) {

        ArrayList<Book> books = new ArrayList<>();

        try {
            String[] tags = splitTags(tagNames);
            System.out.println(Arrays.toString(tags));
            for (String tag : tags) {
                ArrayList<Book> temp = bookDao.searchBookByTags(tag);

                for (Book book : temp) {
                    if (!books.contains(book)) {
                        books.add(book);
                    }
                }
            }

        } catch (SQLException e) {
        }

        return books;
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
        } catch (SQLException e) {

        }
        return articles;
    }
    
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            books = bookDao.getAll();
        } catch (SQLException e) {

        }
        return books;
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
    
    private List<String> validateNewBookUserInputs(String headline, String author, String publish_date, String isbn) {
        List<String> errors = new ArrayList<>();

        if (headline.length() < 5) {
            errors.add("Headline must have atleast five characters");
        }

        if (author.length() < 3) {
            errors.add("Author must have atleast tree characters");
        }
        
        if(publish_date.length() > 4 || publish_date.length() < 1) {
            errors.add("Publish date must be a year");
        }
        
        if(isbn.length() != 10){
            errors.add("ISBN must have 10 characters without the lines between them");
        }
        
        return errors;
    }

    public String getAllTagsByArticle(Article a) throws SQLException {

        ArrayList<String> tags = new ArrayList<>();
        try {
            int id = articleDao.getIdByHeadline(a.getHeadline());
            tags = tagDao.findArticleTags(a);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        String tagsSeperateByComma = String.join(",", tags); // this convert ArrayList of Strings to one String and separate old strings by comma
        return tagsSeperateByComma;
    }
    
    public String getAllTagsByBook(Book b) throws SQLException {

        ArrayList<String> tags = new ArrayList<>();
        try {
            int id = bookDao.getIdByHeadline(b.getHeadline());
            tags = tagDao.findBookTags(b);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        String tagsSeperateByComma = String.join(",", tags); // this convert ArrayList of Strings to one String and separate old strings by comma
        return tagsSeperateByComma;
    }

    public ArrayList<Article> filterArticles(String headline, String tag) {
        ArrayList<Article> articles = getAllArticles();
        articles = articleDao.filterByHeadline(articles, headline);
        articles = articleDao.filterByTags(articles, tag);

        return articles;
    }
    
    public ArrayList<Book> filterBooks(String headline, String tag) {
        ArrayList<Book> books = getAllBooks();
        books = bookDao.filterByHeadline(books, headline);
        books = bookDao.filterByTags(books, tag);

        return books;
    }

    /**
     * Split String of tags to tag array. Take off whitespace. Separate tags by
     * commas ','
     *
     * @param input
     * @return string array of tags
     */
    public String[] splitTags(String input) {
        return input.replaceAll("\\s", "").split(",");
    }

    /**
     * Delete tip from software and all links between tip at database if it's
     * possible
     *
     * @param article
     */
    public void deleteTip(Article article) {

        String[] tags = splitTags(article.getTags());
        int articleId;
        try {
            ArrayList<Integer> tagIds = tagDao.findIdByName(tags);
            articleId = articleDao.getIdByHeadline(article.getHeadline());
            for (int tagId : tagIds) {
                articleTagDao.deleteUnions(articleId, tagId);
                if (!articleTagDao.isThereStillMoreUnionsToTag(tagId)) {
                    tagDao.deleteTag(tagId);
                }
            }
            articleDao.deleteArticle(articleId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteBook(Book book) {

        String[] tags = splitTags(book.getTags());
        int bookId;
        try {
            ArrayList<Integer> tagIds = tagDao.findIdByName(tags);
            bookId = bookDao.getIdByHeadline(book.getHeadline());
            for (int tagId : tagIds) {
                articleTagDao.deleteUnions(bookId, tagId);
                if (!articleTagDao.isThereStillMoreUnionsToTag(tagId)) {
                    tagDao.deleteTag(tagId);
                }
            }
            bookDao.deleteBook(bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markAsRead(Article article) throws SQLException {
        articleDao.markAsRead(articleDao.getIdByHeadline(article.getHeadline()));
    }
    
    public void markAsRead(Book book) throws SQLException {
        articleDao.markAsRead(bookDao.getIdByHeadline(book.getHeadline()));
    }
    
    public Book getBookByIsbn(String isbn) throws IOException {
        return bookDao.getByIsbn(isbn);
    }
}
