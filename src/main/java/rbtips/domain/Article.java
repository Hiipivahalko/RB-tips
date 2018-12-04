package rbtips.domain;

import java.util.Objects;

public class Article extends Tip {

    private final String url;

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
        if (!Objects.equals(super.getHeadline(), other.getHeadline())) {
            return false;
        }
        if (!Objects.equals(super.getHeadline(), other.getHeadline())) {
            return false;
        }
        return Objects.equals(this.url, other.url);
    }

    /*@Override
    public void setTags(String tags) {
        super.setTags(tags);
    }*/
}
