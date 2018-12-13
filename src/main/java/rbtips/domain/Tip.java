package rbtips.domain;

public class Tip {

    private final String title;
    private final String author;
    private String tags;
    private String date;
    private final int type;

    public Tip(String headline, String author, String tags, int type) {
        this.title = headline;
        this.author = author;
        this.tags = tags;
        this.date = null;
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public String getHeadline() {
        return title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }
}
