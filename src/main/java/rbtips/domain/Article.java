package rbtips.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Article {

    private String headline;
    private String author;
    private String url;
    private String tags;

    public Article(String headline, String author, String url) {
        this.headline = headline;
        this.author = author;
        this.url = url;
    }

    public Article(String headline, String author, String url, String tags) {
        this.headline = headline;
        this.author = author;
        this.url = url;
        this.tags = tags;
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

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if (!Objects.equals(this.headline, other.headline)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }
    
    

}
