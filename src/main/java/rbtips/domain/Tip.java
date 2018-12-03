
package rbtips.domain;

import java.util.ArrayList;

public class Tip {
    
    private String headline;
    private String author;
    private String tags;

    public Tip(String headline, String author, String tags) {
        this.headline = headline;
        this.author = author;
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public String getHeadline() {
        return headline;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
