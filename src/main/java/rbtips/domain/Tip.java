package rbtips.domain;

public class Tip {

    private String title;
    private String author;
    private String tags;
    private String date;

    public Tip(String headline, String author, String tags) {
        this.title = headline;
        this.author = author;
        this.tags = tags;
        this.date = null;
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
}
