package rbtips.domain;

public class Book {

    private String isbn;
    private String publish_date;
    private String title;
    private String author;
    private String tags;
    
    
    
    public Book(String isbn, String title, String author, String publish_date){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publish_date = publish_date;
        this.tags = "";
    }
    
    public Book(String isbn, String title, String author, String publish_date, String tags){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publish_date = publish_date;
        this.tags = tags;
    }
    
    
    /*
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setPublishDate(String publish_date) {
        this.publish_date = publish_date;
    }
    */
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