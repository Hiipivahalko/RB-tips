
package rbtips.dao;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rbtips.domain.Book;


public class BookDaoTest {
    
    BookDao bookDao;
    Database db;
    Book book;
    
    public BookDaoTest() {
        db = new Database("jdbc:sqlite:test.db");
        bookDao = new BookDao(db, "Book");
    }
    
    
    @Before
    public void setUp() throws IOException {
        book = bookDao.getByIsbn("0517226952");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void rightTitle() {
        assertEquals("The ultimate hitchhiker's guide", book.getTitle());
    }
    
    @Test
    public void rightPublish_date() {
        assertEquals("2005", book.getPublish_date());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
