package rbtips.dao;

import java.io.IOException;
import java.sql.*;
import rbtips.domain.Book;

public class BookDao {
    
    private Database db;
    private String tableName;
    private TagDao tagDao;
    private OpenLibrary olib;
    
    public BookDao(Database db, String tableName){
        this.db = db;
        this.tableName = tableName;
        this.tagDao = new TagDao(db, "Tag");
        this.olib = new OpenLibrary();
    }
    
    public Book getByIsbn(String isbn) throws IOException {
        return olib.getByIsbn(isbn);
    }
    
    public void create(Book book) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + "(headline, author, releaseYear, isbn) VALUES (?, ?, ?, ?)");
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        
    }
    
    
    
    
    
}
