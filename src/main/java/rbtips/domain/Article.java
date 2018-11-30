package rbtips.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class Article extends Tip{

    private String url;

    public Article(String headline, String author, String url) {
        super(headline, author, "");
        this.url = url;
    }

    public Article(String headline, String author, String url, String tags) {
        super(headline, author, tags);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return super.getAuthor() + ": " + super.getHeadline() + " (url: " + this.url + ")";
    }

}
