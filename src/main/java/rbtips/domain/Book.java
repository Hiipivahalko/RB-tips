package rbtips.domain;

import java.util.Objects;

public class Book {

    private String isbn;
    private String publish_date;
    private String title;
    private String author;
    
    

    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setPublishDate(String publish_date) {
        this.publish_date = publish_date;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    
    
    
    

    @Override
    public String toString() {
        return author+ ": " + title + " (" + publish_date + ")";
    }


}