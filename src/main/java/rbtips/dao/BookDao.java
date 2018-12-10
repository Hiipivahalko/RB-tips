
package rbtips.dao;

import java.io.IOException;
import rbtips.domain.Book;


public class BookDao {
    
    private Database db;
    private String tableName;
    private TagDao tagDao;
    private OpenLibrary olib;
    
    public BookDao(Database db, String tableName){
        this.db = db;
        this.tableName = tableName;
        this.tagDao = new TagDao(db, "");
        this.olib = new OpenLibrary();
    }
    
    public Book getByIsbn(String isbn) throws IOException {
        return olib.getByIsbn(isbn);
    }
    
    
    
    
    
}
