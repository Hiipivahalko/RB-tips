package rbtips.domain;

public class Book {

    private String isbn;
    private String publish_date;
    private String title;
    private String author;
    private String tags;
    private Author[] authors;
    private String date;
    
    
    
    public Book(String title, String author, String publish_date, String isbn){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publish_date = publish_date;
        this.tags = "";
    }
    
    public Book(String title, String author, String publish_date, String isbn, String tags){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publish_date = publish_date;
        this.tags = tags;
    }
    
    
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
    
    public String getPublishDate() {
        return publish_date;
    }
    
    
    public String getAuthor() {
        
        if(author.equals("")){
            for(Author a : authors) {
                author += a.getName() + ", ";
            }
            return author.substring(0, author.length() - 2); 
        }
        return author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public String getTags() {
        return tags;
    }
    
    @Override
    public String toString() {
        return author+ ": " + title + " (" + publish_date + ")";
    }

    
    private class Author {
        private String name;
        
        public String getName() {
            return name;
        }
    }

}