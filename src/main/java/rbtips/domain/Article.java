
package rbtips.domain;


public class Article {
    
    private String headline;
    private String author;

    public Article(String headline, String author) {
        this.headline = headline;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getHeadline() {
        return headline;
    }
    
}
