
package rbtips.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rbtips.domain.Book;


public class BookDaoTest {
    
    BookDao bookDao;
    Database database;
    Book book;
    
    
    
    @Before
    public void setUp() throws Exception {
        database = new Database("jdbc:sqlite:test.db");
        bookDao = new BookDao(database, "Book");
        database.initializeDatabase();
        book = bookDao.getByIsbn("0517226952");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void rightTitle() {
        assertEquals("The ultimate hitchhiker's guide", book.getHeadline());
    }
    
    @Test
    public void rightPublish_date() {
        assertEquals("2005", book.getPublishDate());
    }
    
    @Test
    public void rightAuthors() {
        assertEquals("Douglas Adams", book.getAuthor());
    }
    
    @Test
    public void creatingBookWorks() throws Exception {
        bookDao.create(new Book("TestHeadline345", "TestAuthor12", "1902", "1234557890"));
        ArrayList<Book> books = bookDao.getAll();
        assertEquals("1902", books.get(0).getPublishDate());
        assertEquals("TestHeadline345", books.get(0).getHeadline());
    }
    
    @Test
    public void getAllWhenDatabaseIsEmpty() throws SQLException {
        ArrayList<Book> books = bookDao.getAll();
        assertTrue(books.isEmpty());
    }
    
    @Test
    public void searchByHeadlineWorks() throws SQLException {
        bookDao.create(new Book("TestHeadline345", "TestAuthor12", "1902", "1234557890"));
        ArrayList<Book> books = bookDao.searchHeadline("TestHeadline345", true);
        assertEquals("1902", books.get(0).getPublishDate());
    }
    
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
