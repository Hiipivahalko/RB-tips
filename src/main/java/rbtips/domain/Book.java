package rbtips.domain;

public class Book extends Tip {

    private String isbn;
    private String publish_date;
    private String author;

    public Book(String title, String author, String publish_date, String isbn) {
        super(title, author, "", 1);
        this.isbn = isbn;
        this.author = author;
        this.publish_date = publish_date;
    }

    public Book(String title, String author, String publish_date, String isbn, String tags) {
        super(title, author, tags, 1);
        this.isbn = isbn;
        this.author = author;
        this.publish_date = publish_date;
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

    public String getPublishDate() {
        return publish_date;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return author + ": " + super.getHeadline() + " (" + publish_date + ")";
    }

}
