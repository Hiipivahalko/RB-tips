package rbtips.domain;

public class Article {

    private String headline;
    private String author;
    private String url;

    public Article(String headline, String author, String url) {
        this.headline = headline;
        this.author = author;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getHeadline() {
        return headline;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return this.author + ": " + this.headline + " (url: " + this.url + ")";
    }

}
