
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
    
    public BookDaoTest() {
        db = new Database("jdbc:sqlite:test.db");
        bookDao = new BookDao(db, "Book");
    }
    
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void rightTitle() throws IOException {
        Book book = bookDao.getByIsbn("0517226952");
        assertEquals("The ultimate hitchhiker's guide", book.getTitle());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
